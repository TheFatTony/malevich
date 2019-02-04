package io.malevich.server.services.paymentmethod;

import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.domain.PaymentMethodEntity;
import io.malevich.server.repositories.paymentmethod.PaymentMethodDao;
import io.malevich.server.services.participant.ParticipantService;
import io.malevich.server.services.paymentmethodtype.PaymentMethodTypeService;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.*;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private PaymentMethodDao paymentMethodDao;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private PaymentMethodTypeService paymentMethodTypeService;

    @Autowired
    private NetworkParameters networkParameters;

    @Autowired
    private MemoryBlockStore memoryBlockStore;

    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodEntity> findAll() {
        ParticipantEntity participantEntity = participantService.getCurrent();
        return paymentMethodDao.findByParticipant_Id(participantEntity.getId());
    }

    @Override
    @Transactional
    public PaymentMethodEntity save(PaymentMethodEntity paymentMethod) {
        ParticipantEntity participantEntity = participantService.getCurrent();
        paymentMethod.setParticipant(participantEntity);
        return paymentMethodDao.save(paymentMethod);
    }

    @Override
    public PaymentMethodEntity generateBtc() {
        ParticipantEntity participantEntity = participantService.getCurrent();

        Optional<PaymentMethodEntity> existing = findAll().stream().filter(m -> paymentMethodTypeService.getBitcoinType().equals(m.getType())).findFirst();

        PaymentMethodBitcoinEntity address;

        if(existing.isPresent()){
            address = (PaymentMethodBitcoinEntity)existing.get();
        } else {
            address = new PaymentMethodBitcoinEntity();
            address.setType(paymentMethodTypeService.getBitcoinType());
            address.setParticipant(participantEntity);
        }

        Wallet wallet = createWallet();
        ByteArrayOutputStream walletDump =  new ByteArrayOutputStream();
        try {
            wallet.saveToFileStream(walletDump);
        } catch (IOException e) {
            new RuntimeException("Unable to save wallet seed");
        }
        address.setBtcAddress(getBtcAddress(wallet));
        address.setWallet(walletDump.toByteArray());

        return paymentMethodDao.save(address);
    }

    private Wallet createWallet() {
        Wallet wallet = new Wallet(networkParameters);
        BlockChain chain = null;
        try {
            chain = new BlockChain(networkParameters, wallet, memoryBlockStore);
            PeerGroup peerGroup = new PeerGroup(networkParameters, chain);
            peerGroup.addWallet(wallet);
            peerGroup.start();
            return wallet;
        } catch (BlockStoreException e) {
            e.printStackTrace();
        }
        return wallet;
    }

    private String getBtcAddress(Wallet wallet) {
        Address a = wallet.currentReceiveAddress();
        ECKey b = wallet.currentReceiveKey();
        Address c = wallet.freshReceiveAddress();

        assert b.toAddress(wallet.getParams()).equals(a);
        assert !c.equals(a);
        return a.toString();
    }

}
