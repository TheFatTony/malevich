package io.malevich.server.services.paymentmethodbitcoin;

import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.repositories.paymentmethod.PaymentMethodDao;
import io.malevich.server.services.participant.ParticipantService;
import io.malevich.server.services.paymentmethodtype.PaymentMethodTypeService;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentMethodBitcoinServiceImpl implements PaymentMethodBitcoinService {

    @Autowired
    private PaymentMethodDao paymentMethodDao;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private PaymentMethodTypeService paymentMethodTypeService;

    @Autowired
    private NetworkParameters networkParameters;

    @Autowired
    private BlockChain blockChain;

    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodBitcoinEntity> findAllAll() {
        return paymentMethodDao.findByType_Id(paymentMethodTypeService.getBitcoinType().getId()).stream().map(m -> (PaymentMethodBitcoinEntity) m).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodBitcoinEntity> findAll() {
        ParticipantEntity participantEntity = participantService.getCurrent();
        return paymentMethodDao.findByParticipant_IdAndType_Id(participantEntity.getId(), paymentMethodTypeService.getBitcoinType().getId())
                .stream().map(m -> (PaymentMethodBitcoinEntity) m).collect(Collectors.toList());
    }

    @Override
    public PaymentMethodBitcoinEntity generateBtc() {
        ParticipantEntity participantEntity = participantService.getCurrent();

        Optional<PaymentMethodBitcoinEntity> existing = findAll().stream().findFirst();

        PaymentMethodBitcoinEntity address;

        if (existing.isPresent()) {
            address = existing.get();
        } else {
            address = new PaymentMethodBitcoinEntity();
            address.setType(paymentMethodTypeService.getBitcoinType());
            address.setParticipant(participantEntity);
        }

        Wallet wallet = createWallet();
        ByteArrayOutputStream walletDump = new ByteArrayOutputStream();
        try {
            wallet.saveToFileStream(walletDump);
        } catch (IOException e) {
            new RuntimeException("Unable to save wallet seed");
        }
        address.setBtcAddress(getBtcAddress(wallet));
        address.setWallet(walletDump.toByteArray());

        return paymentMethodDao.save(address);
    }

    @Override
    public void save(PaymentMethodBitcoinEntity account) {
        paymentMethodDao.save(account);
    }

    private Wallet createWallet() {
        Wallet wallet = new Wallet(networkParameters);
        PeerGroup peerGroup = new PeerGroup(networkParameters, blockChain);
        peerGroup.addPeerDiscovery(new DnsDiscovery(networkParameters));
        peerGroup.addWallet(wallet);
        peerGroup.start();
        peerGroup.stop();

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
