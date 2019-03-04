'use strict';

/**
 * Determine the net transfer between a banking pair, accounting for exchange rates
 * @param {io.malevich.network.Counterparty} party array of TransferRequest objects
 * @return {io.malevich.network.Counterparty} net amount in USD
 */
async function getCounterparty(party) { // eslint-disable-line no-unused-vars
    const registryTrader = await getParticipantRegistry('io.malevich.network.Trader');
    const registryGallery = await getParticipantRegistry('io.malevich.network.Gallery');
    
    let counterparty = null;
    if (party.getFullyQualifiedType() === "io.malevich.network.Gallery") 
        counterparty = await registryGallery.get(party.getIdentifier());
    else if (party.getFullyQualifiedType() === "io.malevich.network.Trader") 
        counterparty = await registryTrader.get(party.getIdentifier());

    return counterparty;
}

/**
 * Determine the net transfer between a banking pair, accounting for exchange rates
 * @param {io.malevich.network.Counterparty} party array of TransferRequest objects
 * @return {io.malevich.network.Counterparty} net amount in USD
 */
async function updateCounterparty(party) { // eslint-disable-line no-unused-vars
    const registryTrader = await getParticipantRegistry('io.malevich.network.Trader');
    const registryGallery = await getParticipantRegistry('io.malevich.network.Gallery');

    if (party.getFullyQualifiedType() === "io.malevich.network.Gallery") 
            await registryGallery.update(party);
        else if (party.getFullyQualifiedType() === "io.malevich.network.Trader") 
            await registryTrader.update(party);

    return party;
}

/**
 * processPayment
 * @param {io.malevich.network.Payment} payment - payment
 * @transaction
 */
async function processPayment(payment) { // eslint-disable-line no-unused-vars
    let counterparty = await getCounterparty(payment.party);

    if (payment.paymentType === 'IN') {
        counterparty.balance = counterparty.balance + payment.amount;
    } else if (payment.paymentType === 'OUT') {
        counterparty.balance = counterparty.balance - payment.amount;
    }
    
    await updateCounterparty(counterparty);
}

/**
 * processBonuses
 * @param {io.malevich.network.Bonuses} payment - payment
 * @transaction
 */
async function processBonuses(bonuses) { // eslint-disable-line no-unused-vars
    let counterparty = await getCounterparty(bonuses.party);
    counterparty.bonuses = counterparty.bonuses + bonuses.bonuses;
    await updateCounterparty(counterparty);
}

/**
 * placeOrder
 * @param {io.malevich.network.Order} order - order
 * @transaction
 */
async function placeOrder(order) { // eslint-disable-line no-unused-vars

    const registry = await getAssetRegistry('io.malevich.network.OrderAsset');
    const registryTrader = await getParticipantRegistry('io.malevich.network.Trader');
    const registryGallery = await getParticipantRegistry('io.malevich.network.Gallery');
    const registryMalevich = await getParticipantRegistry('io.malevich.network.Malevich');
    const tradeHistoryRegistry = await getAssetRegistry('io.malevich.network.TradeHistory');
    const registryArtworkStock = await getAssetRegistry('io.malevich.network.ArtworkStock');
    const registryCommissionRule = await getAssetRegistry('io.malevich.network.CommissionRule');
    const factory = getFactory();
    
    if (order.order.orderStatus === 'CANCELED') {
        var updateOrder = await registry.get(order.order.id);
        updateOrder.order.orderStatus = 'CANCELED';
        await registry.update(updateOrder);
        if (updateOrder.order.counterparty.getFullyQualifiedType() === "io.malevich.network.Trader") {
            let uptadeParty = null;
            uptadeParty = await registryTrader.get(updateOrder.order.counterparty.getIdentifier());
            uptadeParty.balance = uptadeParty.balance + updateOrder.order.amount;
            await registryTrader.update(uptadeParty);
        }
        
        return;
    }

    if (order.order.orderStatus !== 'OPEN') {
        throw new Error('!#{Go fuck yourself}#!');
    }

    if (order.order.amount <= 0) {
        throw new Error('!#{Zero or negative amount is not allowed}#!');
    }
    

    

    var ordersAskQuery = buildQuery('SELECT io.malevich.network.OrderAsset WHERE ((order.artworkStock == _$artworkStock))');
    var results = await query(ordersAskQuery, { artworkStock: 'resource:io.malevich.network.ArtworkStock#' + order.order.artworkStock.getIdentifier()});
    var askCount = 0;
    var orderToCancel = null;


    var currentAsk = null;
    var matchingBid = null;

    results.forEach(async existingOrders => {
        if ((order.order.orderType === 'ASK') && (existingOrders.order.orderType === 'ASK') && (existingOrders.order.orderStatus === 'OPEN')) {
            askCount++;
        } else if ((order.order.orderType === 'BID') && (existingOrders.order.orderType === 'ASK') && (existingOrders.order.orderStatus === 'OPEN')) {
            currentAsk = existingOrders;
        } else if ((existingOrders.order.orderType === 'BID') && (order.order.orderType === 'BID') 
        && (existingOrders.order.orderStatus === 'OPEN') 
        && (existingOrders.order.counterparty.getIdentifier() === order.order.counterparty.getIdentifier())) {
            orderToCancel = existingOrders;
        }

    });

    if (orderToCancel != null) {
        throw new Error('!#{Bid already exists for this ArtWork}#!');
    }

    if (askCount > 0) {
        throw new Error('!#{Ask already exists for this ArtWork}#!');
    }

    if ((currentAsk != null) && (order.order.amount > currentAsk.order.amount))  {
        throw new Error('!#{Bid higher then an Ask}#!');
    }

    var orderAsset = factory.newResource('io.malevich.network', 'OrderAsset', order.order.id);
    orderAsset.order = order.order;
    orderAsset.order.orderStatus = 'OPEN';
    if (orderAsset.order.orderType === 'BID') {
        let chargeParty = await registryTrader.get(orderAsset.order.counterparty.id);
        chargeParty.balance = chargeParty.balance - orderAsset.order.amount;
        if (chargeParty.balance < 0) {
            throw new Error('!#{Insufficient Funds}#!');
        }
        await registryTrader.update(chargeParty);
    }
    
    if ((orderAsset.order.orderType === 'ASK')) {
        if ((currentAsk == null)) {
            currentAsk = orderAsset;
        }

        results.forEach(async existingOrders => {
            if (currentAsk.order.amount === existingOrders.order.amount) {
                matchingBid = existingOrders;
            }
        });
    } else if ((orderAsset.order.orderType === 'BID')) {
        results.forEach(async existingOrders => {
            if (order.order.amount === existingOrders.order.amount) {
                matchingBid = orderAsset;
            }
        });
    }

    if ((matchingBid != null) && (currentAsk != null)) {
        if (matchingBid.order.counterparty.getIdentifier() === currentAsk.order.counterparty.getIdentifier()) {
            throw new Error('!#{You cant sell work to yourself}#!');
        }

        if (currentAsk.getIdentifier() === orderAsset.getIdentifier()) {
            orderAsset.order.orderStatus = 'EXECUTED';
            await registry.add(orderAsset);
        } else {
            currentAsk.order.orderStatus = 'EXECUTED';
            await registry.update(currentAsk);
        }

        if (matchingBid.getIdentifier() === orderAsset.getIdentifier()) {
            orderAsset.order.orderStatus = 'EXECUTED';
            await registry.add(orderAsset);
        } else {
            matchingBid.order.orderStatus = 'EXECUTED';
            await registry.update(matchingBid);
        }


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

        let uptadeParty = await getCounterparty(currentAsk.order.counterparty);

        if (uptadeParty.bonuses >= (matchingBid.order.amount * sumCommisions + matchingBid.order.amount * galleryCommisions)) {
            uptadeParty.bonuses = uptadeParty.bonuses - matchingBid.order.amount * sumCommisions - matchingBid.order.amount * galleryCommisions;
            uptadeParty.balance = uptadeParty.balance + matchingBid.order.amount;
        } else {
            uptadeParty.balance = uptadeParty.balance + matchingBid.order.amount - matchingBid.order.amount * sumCommisions - matchingBid.order.amount * galleryCommisions;
        }

        await updateCounterparty(uptadeParty);
        
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

    } else {
        await registry.add(orderAsset);
    }

}

/**
 * cancelOrder
 * @param {io.malevich.network.Order} order - order
 * @transaction
 */
async function cancelOrder(order) { // eslint-disable-line no-unused-vars

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