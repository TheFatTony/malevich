'use strict';


/**
 * test
 * @param {io.malevich.network.Test} test - test
 * @transaction
 */
async function test(test) { // eslint-disable-line no-unused-vars
    const factory = getFactory();

    const registry = await getAssetRegistry('io.malevich.network.TestAsset');
    var testAsset = factory.newResource('io.malevich.network', 'TestAsset', test.id);
    testAsset.amount = test.amount;
    await registry.add(testAsset);

    testAsset = await transfersTest(testAsset);
}


/**
 * Determine the net transfer between a banking pair, accounting for exchange rates
 * @param {io.malevich.network.TestAsset} testAsset array of TransferRequest objects
 * @return {io.malevich.network.TestAsset} net amount in USD
 */
async function transfersTest(testAsset) { // eslint-disable-line no-unused-vars
    const registry = await getAssetRegistry('io.malevich.network.TestAsset');
    testAsset.amount = testAsset.amount + 1200;
    await registry.update(testAsset);
    return testAsset;
}

/**
 * processPayment
 * @param {io.malevich.network.Payment} payment - payment
 * @transaction
 */
async function processPayment(payment) { // eslint-disable-line no-unused-vars
    const registryTrader = await getParticipantRegistry('io.malevich.network.Trader'); // eslint-disable-line no-undef
    const registryGallery = await getParticipantRegistry('io.malevich.network.Gallery');

    let counterparty = null;
        if (payment.party.getFullyQualifiedType() === "io.malevich.network.Gallery") 
            counterparty = await registryGallery.get(payment.party.getIdentifier());
        else if (payment.party.getFullyQualifiedType() === "io.malevich.network.Trader") 
            counterparty = await registryTrader.get(payment.party.getIdentifier());

    if (payment.paymentType === 'IN') {
        counterparty.balance = counterparty.balance + payment.amount;
    } else if (payment.paymentType === 'OUT') {
        counterparty.balance = counterparty.balance - payment.amount;
    }
    
    if (payment.party.getFullyQualifiedType() === "io.malevich.network.Gallery") 
            await registryGallery.update(counterparty);
        else if (payment.party.getFullyQualifiedType() === "io.malevich.network.Trader") 
            await registryTrader.update(counterparty);
}

/**
 * processBonuses
 * @param {io.malevich.network.Bonuses} payment - payment
 * @transaction
 */
async function processBonuses(bonuses) { // eslint-disable-line no-unused-vars
    const registryTrader = await getParticipantRegistry('io.malevich.network.Trader'); // eslint-disable-line no-undef
    const registryGallery = await getParticipantRegistry('io.malevich.network.Gallery');

    let counterparty = null;
    if (bonuses.party.getFullyQualifiedType() === "io.malevich.network.Gallery") 
        counterparty = await registryGallery.get(bonuses.party.getIdentifier());
    else if (bonuses.party.getFullyQualifiedType() === "io.malevich.network.Trader") 
        counterparty = await registryTrader.get(bonuses.party.getIdentifier());

    counterparty.bonuses = counterparty.bonuses + bonuses.bonuses;
        
    if (bonuses.party.getFullyQualifiedType() === "io.malevich.network.Gallery") 
        await registryGallery.update(counterparty);
    else if (bonuses.party.getFullyQualifiedType() === "io.malevich.network.Trader") 
        await registryTrader.update(counterparty);
}

/**
 * placeOrder
 * @param {io.malevich.network.Order} order - order
 * @transaction
 */
async function placeOrder(order) { // eslint-disable-line no-unused-vars

    if (order.order.amount <= 0) {
        throw new Error('Zero or negative amount is not allowed');
    }
    

    const registry = await getAssetRegistry('io.malevich.network.OrderAsset');
    const registryTrader = await getParticipantRegistry('io.malevich.network.Trader');
    const registryGallery = await getParticipantRegistry('io.malevich.network.Gallery');
    const registryMalevich = await getParticipantRegistry('io.malevich.network.Malevich');
    const tradeHistoryRegistry = await getAssetRegistry('io.malevich.network.TradeHistory');
    const registryArtworkStock = await getAssetRegistry('io.malevich.network.ArtworkStock');
    const registryCommissionRule = await getAssetRegistry('io.malevich.network.CommissionRule');
    const factory = getFactory();

    var ordersAskQuery = buildQuery('SELECT io.malevich.network.OrderAsset WHERE ((order.artworkStock == _$artworkStock))');
    var results = await query(ordersAskQuery, { artworkStock: 'resource:io.malevich.network.ArtworkStock#' + order.order.artworkStock.getIdentifier()});
    var askCount = 0;
    var currentAsk = null;
    var matchingBid = null;

    results.forEach(async existingOrders => {
        if ((order.order.orderType === 'ASK') && (existingOrders.order.orderType === 'ASK') && (existingOrders.order.orderStatus === 'OPEN')) {
            askCount++;
        } else if ((order.order.orderType === 'BID') && (existingOrders.order.orderType === 'ASK') && (existingOrders.order.orderStatus === 'OPEN')) {
            currentAsk = existingOrders;
        }

    });
    if (askCount > 0) {
        throw new Error('Ask already exists for this ArtWork');
    } else {

        var orderAsset = factory.newResource('io.malevich.network', 'OrderAsset', order.order.id);
        orderAsset.order = order.order;
        orderAsset.order.orderStatus = 'OPEN';

        if (orderAsset.order.orderType === 'BID' && currentAsk == null) {
            let chargeParty = await registryTrader.get(orderAsset.order.counterparty.id);
            chargeParty.balance = chargeParty.balance - orderAsset.order.amount;
            if (chargeParty.balance < 0) {
                throw new Error('Insufficient Funds');
            }
            await registryTrader.update(chargeParty);
        }

        if (currentAsk != null) {
            if (currentAsk.order.amount === order.order.amount) {
                orderAsset.order.orderStatus = 'EXECUTED';
                matchingBid = orderAsset;
            }
        }

        await registry.add(orderAsset);
    }


    if (matchingBid != null) {
        if (matchingBid.order.counterparty.getIdentifier() === currentAsk.order.counterparty.getIdentifier()) {
            throw new Error('You cant sell work to yourself');
        }
        
        currentAsk.order.orderStatus = 'EXECUTED';
        await registry.update(currentAsk);

        const tradeHistoryAsset = factory.newResource('io.malevich.network', 'TradeHistory', order.order.id);
        tradeHistoryAsset.askOrder = currentAsk;
        tradeHistoryAsset.bidOrder = matchingBid;
        tradeHistoryAsset.artworkStock = order.order.artworkStock;
        tradeHistoryAsset.effectiveDate = order.timestamp.toString();
        tradeHistoryAsset.amount = matchingBid.order.amount;
        await tradeHistoryRegistry.add(tradeHistoryAsset);


        const commissions = await registryCommissionRule.getAll();

        let sumCommisions = 0;
        let galleryCommisions = 0;
        for (let i = 0; i < commissions.length; i++) {
            if (commissions[i].name !== 'Gallery') {
                sumCommisions += commissions[i].value;
            } else {
                galleryCommisions = commissions[i].value;
            }
        }

        let malevichParty = await registryMalevich.get('1');
        malevichParty.balance = malevichParty.balance + (matchingBid.order.amount * sumCommisions);
        await registryMalevich.update(malevichParty);


        
        if (matchingBid.order.counterparty.getFullyQualifiedType() === "io.malevich.network.Gallery") 
            uptadeCounterparty = await registryGallery.get(matchingBid.order.counterparty.getIdentifier());
        else if (matchingBid.order.counterparty.getFullyQualifiedType() === "io.malevich.network.Trader") 
            uptadeCounterparty = await registryTrader.get(matchingBid.order.counterparty.getIdentifier());
        
        if (uptadeCounterparty.balance < matchingBid.order.amount) {
            throw new Error('Insufficient Funds ');
        }
        
        uptadeCounterparty.balance = uptadeCounterparty.balance - matchingBid.order.amount;

        if (matchingBid.order.counterparty.getFullyQualifiedType() === "io.malevich.network.Gallery") 
            await registryGallery.update(uptadeCounterparty);
        else if (matchingBid.order.counterparty.getFullyQualifiedType() === "io.malevich.network.Trader") 
            await registryTrader.update(uptadeCounterparty);

        let uptadeParty = null;
        if (currentAsk.order.counterparty.getFullyQualifiedType() === "io.malevich.network.Gallery") 
            uptadeParty = await registryGallery.get(currentAsk.order.counterparty.getIdentifier());
        else if (currentAsk.order.counterparty.getFullyQualifiedType() === "io.malevich.network.Trader") 
            uptadeParty = await registryTrader.get(currentAsk.order.counterparty.getIdentifier());


        if (uptadeParty.bonuses >= (matchingBid.order.amount * sumCommisions + matchingBid.order.amount * galleryCommisions)) {
            uptadeParty.bonuses = uptadeParty.bonuses - matchingBid.order.amount * sumCommisions - matchingBid.order.amount * galleryCommisions;
            uptadeParty.balance = uptadeParty.balance + matchingBid.order.amount;
        } else {
            uptadeParty.balance = uptadeParty.balance + matchingBid.order.amount - matchingBid.order.amount * sumCommisions - matchingBid.order.amount * galleryCommisions;
        }

        if (currentAsk.order.counterparty.getFullyQualifiedType() === "io.malevich.network.Gallery") 
            await registryGallery.update(uptadeParty);
        else if (currentAsk.order.counterparty.getFullyQualifiedType() === "io.malevich.network.Trader") 
            await registryTrader.update(uptadeParty);
        
        let uptadeArtwork = await registryArtworkStock.get(currentAsk.order.artworkStock.getIdentifier());
        uptadeArtwork.owner = matchingBid.order.counterparty;
        await registryArtworkStock.update(uptadeArtwork);

        let galleryParty = await registryGallery.get(uptadeArtwork.holder.getIdentifier());
        if (uptadeParty.getIdentifier() === galleryParty.getIdentifier()) {
                uptadeParty.balance = uptadeParty.balance + (matchingBid.order.amount * galleryCommisions);
            await registryGallery.update(uptadeParty);
        } else {
            galleryParty.balance = galleryParty.balance + (matchingBid.order.amount * galleryCommisions);
            await registryGallery.update(galleryParty);
        }
    }
}

/**
 * testData
 * @param {io.malevich.network.TestData} testData - testData
 * @transaction
 */
async function testData(testData) { // eslint-disable-line no-unused-vars
    const factory = getFactory();

    const registryMalevich = await getParticipantRegistry('io.malevich.network.Malevich');
    const registryGallery = await getParticipantRegistry('io.malevich.network.Gallery');
    const registryTrader = await getParticipantRegistry('io.malevich.network.Trader');
    const registryCommissionRule = await getAssetRegistry('io.malevich.network.CommissionRule');

    const malevichAsset = factory.newResource('io.malevich.network', 'Malevich', '1');
    malevichAsset.email = 'malevich@malevich.io';
    malevichAsset.balance = 0;
    malevichAsset.bonuses = 0;
    await registryMalevich.add(malevichAsset);

    const gallery1Asset = factory.newResource('io.malevich.network', 'Gallery', '2');
    gallery1Asset.email = 'gallery1@malevich.io';
    gallery1Asset.balance = 0;
    gallery1Asset.bonuses = 0;
    await registryGallery.add(gallery1Asset);

    const trader1Asset = factory.newResource('io.malevich.network', 'Trader', '3');
    trader1Asset.email = 'trader1@malevich.io';
    trader1Asset.balance = 0;
    trader1Asset.bonuses = 0;
    await registryTrader.add(trader1Asset);

    const trader2Asset = factory.newResource('io.malevich.network', 'Trader', '4');
    trader2Asset.email = 'trader2@malevich.io';
    trader2Asset.balance = 0;
    trader2Asset.bonuses = 0;
    await registryTrader.add(trader2Asset);

    const commissionRule1Asset = factory.newResource('io.malevich.network', 'CommissionRule', 'Gallery');
    commissionRule1Asset.value = 0.005;
    await registryCommissionRule.add(commissionRule1Asset);

    const commissionRule2Asset = factory.newResource('io.malevich.network', 'CommissionRule', 'Malevich');
    commissionRule2Asset.value = 0.024;
    await registryCommissionRule.add(commissionRule2Asset);

    const commissionRule3Asset = factory.newResource('io.malevich.network', 'CommissionRule', 'Endorser');
    commissionRule3Asset.value = 0.001;
    await registryCommissionRule.add(commissionRule3Asset);

}