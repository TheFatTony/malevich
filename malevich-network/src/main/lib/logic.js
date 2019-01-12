'use strict';

/**
 * processPayment
 * @param {io.malevich.network.Payment} payment - payment
 * @transaction
 */
async function processPayment(payment) { // eslint-disable-line no-unused-vars
    const registryTrader = await getParticipantRegistry('io.malevich.network.Trader'); // eslint-disable-line no-undef

    let counterparty = await registryTrader.get(payment.party.getIdentifier());

    if (payment.paymentType === 'IN') {
        counterparty.balance = counterparty.balance + payment.amount;
    } else if (payment.paymentType === 'OUT') {
        counterparty.balance = counterparty.balance - payment.amount;
    }
    await registryTrader.update(counterparty);
}