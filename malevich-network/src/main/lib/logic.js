'use strict';

/* global getAssetRegistry getFactory emit query getSerializer */


/**
 * placeOrder
 * @param {io.malevich.network.PlaceOrder} placeOrder - placeOrder
 * @transaction
 */
async function placeOrder(placeOrder) { // eslint-disable-line no-unused-vars

    if (placeOrder.order.amount <= 0) {
        throw new Error('Zero or negative amount is not allowed');
    }

    const registry = await getAssetRegistry('io.malevich.network.OrderAsset');
    const registryTrader = await getParticipantRegistry('io.malevich.network.Trader');
    const tradeHistoryRegistry = await getAssetRegistry('io.malevich.network.TradeHistory');
    const registryArtworkStock = await getAssetRegistry('io.malevich.network.ArtworkStock');
    const factory = getFactory();


    var ordersAskQuery = buildQuery('SELECT io.malevich.network.OrderAsset WHERE ((order.artworkStock == _$artworkStock))');
    var results = await query(ordersAskQuery, { artworkStock: 'resource:io.malevich.network.ArtworkStock#' + placeOrder.order.artworkStock.getIdentifier()});
    var askCount = 0;
    var currentAsk = null;
    var matchingBid = null;
    results.forEach(async existingOrders => {
        if ((placeOrder.order.orderType === 'ASK') && (existingOrders.order.orderType === 'ASK') && (existingOrders.order.orderStatus === 'OPEN')) {
            askCount++;
        } else if ((placeOrder.order.orderType === 'BID') && (existingOrders.order.orderType === 'ASK') && (existingOrders.order.orderStatus === 'OPEN')) {
            currentAsk = existingOrders;
        }
    });
    if (askCount > 0) {
        throw new Error('Ask already exists for this ArtWork');
    } else {
        const orderAsset = factory.newResource('io.malevich.network', 'OrderAsset', placeOrder.order.id);
        orderAsset.order = placeOrder.order;
        orderAsset.order.orderStatus = 'OPEN';

        if ((currentAsk != null) && (currentAsk.order.amount === placeOrder.order.amount)) {
            orderAsset.order.orderStatus = 'EXECUTED';
            matchingBid = orderAsset;
        }

        await registry.add(orderAsset);
    }

    if (matchingBid != null) {
        var results = await query(ordersAskQuery, { artworkStock: 'resource:io.malevich.network.ArtworkStock#' + placeOrder.order.artworkStock.getIdentifier()});
        results.forEach(async existingOrders => {
            if ((existingOrders != matchingBid) && (existingOrders != currentAsk)) {
                let existingOrders1 = await registry.get(existingOrders.order.getIdentifier());
                existingOrders1.order.orderStatus = 'CLOSE';
                await registry.update(existingOrders1);
            }
        });
        currentAsk.order.orderStatus = 'EXECUTED';
        await registry.update(currentAsk);

        // matchingBid.order.orderStatus = 'EXECUTED';
        // await registry.add(matchingBid);

        const tradeHistoryAsset = factory.newResource('io.malevich.network', 'TradeHistory', placeOrder.order.id);
        tradeHistoryAsset.askOrder = currentAsk;
        tradeHistoryAsset.bidOrder = matchingBid;
        await tradeHistoryRegistry.add(tradeHistoryAsset);

        let uptadeArtwork = await registryArtworkStock.get(matchingBid.order.artworkStock.getIdentifier());
        uptadeArtwork.owner = matchingBid.order.сounterparty;
        await registryArtworkStock.update(uptadeArtwork);

        let uptadeCounterparty = await registryTrader.get(uptadeArtwork.owner.getIdentifier());
        uptadeCounterparty.balance = uptadeCounterparty.balance - matchingBid.order.amount;
        await registryTrader.update(uptadeCounterparty);

    }
}

/**
 * updateBalance
 * @param {io.malevich.network.UpdateBalance} updateBalance - updateBalance
 * @transaction
 */
async function updateBalance(updateBalance) { // eslint-disable-line no-unused-vars
    const registryBalanceAsset = await getAssetRegistry('io.malevich.network.BalanceAsset'); // eslint-disable-line no-undef
    const registryTrader = await getParticipantRegistry('io.malevich.network.Trader'); // eslint-disable-line no-undef
    const factory = getFactory();


    const balanceAsset = factory.newResource('io.malevich.network', 'BalanceAsset', '!!!!');
    balanceAsset.balance = updateBalance.balance;
    await registryBalanceAsset.add(balanceAsset);


    let uptadeCounterparty = await registryTrader.get(balanceAsset.balance.сounterparty.getIdentifier());

    uptadeCounterparty.balance = updateBalance.balance.newBalance;
    await registryTrader.update(uptadeCounterparty);


}