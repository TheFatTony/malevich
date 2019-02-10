package io.malevich.server.services.bitcoin;


import org.bitcoinj.core.PeerGroup;
import org.springframework.stereotype.Service;

@Service
public interface BitcoinService {


    void downloadBlockchain(PeerGroup peerGroup);
}
