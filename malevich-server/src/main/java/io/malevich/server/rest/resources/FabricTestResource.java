package io.malevich.server.rest.resources;

import io.malevich.server.domain.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Properties;


@Slf4j
@RestController
@RequestMapping(value = "/test")
public class FabricTestResource {


    public void queryBlockChain(HFClient client) throws ProposalException, InvalidArgumentException {
        // get channel instance from client
        Channel channel = client.getChannel("mychannel");
        // create chaincode request
        QueryByChaincodeRequest qpr = client.newQueryProposalRequest();
        // build cc id providing the chaincode name. Version is omitted here.
        ChaincodeID fabcarCCId = ChaincodeID.newBuilder().setName("fabcar").build();
        qpr.setChaincodeID(fabcarCCId);
        // CC function to be called
        qpr.setFcn("queryAllCars");
        Collection<ProposalResponse> res = channel.queryByChaincode(qpr);
        // display response
        for (ProposalResponse pres : res) {
            String stringResponse = new String(pres.getChaincodeActionResponsePayload());
            log.info(stringResponse);
        }
    }

    public Channel getChannel(HFClient client) throws InvalidArgumentException, TransactionException {
        // initialize channel
        // peer name and endpoint in fabcar network
        Peer peer = client.newPeer("peer0.org1.example.com", "grpc://localhost:7051");
        // eventhub name and endpoint in fabcar network
        EventHub eventHub = client.newEventHub("eventhub01", "grpc://localhost:7053");
        // orderer name and endpoint in fabcar network
        Orderer orderer = client.newOrderer("orderer.example.com", "grpc://localhost:7050");
        // channel name in fabcar network
        Channel channel = client.newChannel("mychannel");
        channel.addPeer(peer);
        channel.addEventHub(eventHub);
        channel.addOrderer(orderer);
        channel.initialize();
        return channel;
    }

    public HFClient getHfClient() throws Exception {
        // initialize default cryptosuite
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        // setup the client
        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(cryptoSuite);
        return client;
    }

    public AppUser getUser(HFCAClient caClient, AppUser registrar, String userId) throws Exception {
        AppUser appUser = tryDeserialize(userId);
        if (appUser == null) {
            RegistrationRequest rr = new RegistrationRequest(userId, "org1");
            String enrollmentSecret = caClient.register(rr, registrar);
            Enrollment enrollment = caClient.enroll(userId, enrollmentSecret);
            appUser = new AppUser(userId, "org1", "Org1MSP", enrollment);
            serialize(appUser);
        }
        return appUser;
    }

    public AppUser getAdmin(HFCAClient caClient) throws Exception {
        AppUser admin = tryDeserialize("admin");
        if (admin == null) {
            Enrollment adminEnrollment = caClient.enroll("admin", "adminpw");
            admin = new AppUser("admin", "org1", "Org1MSP", adminEnrollment);
            serialize(admin);
        }
        return admin;
    }

    public void serialize(AppUser appUser) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(
                Paths.get(appUser.getName() + ".jso")))) {
            oos.writeObject(appUser);
        }
    }

    public AppUser tryDeserialize(String name) throws Exception {
        if (Files.exists(Paths.get(name + ".jso"))) {
            return deserialize(name);
        }
        return null;
    }

    public AppUser deserialize(String name) throws Exception {
        try (ObjectInputStream decoder = new ObjectInputStream(
                Files.newInputStream(Paths.get(name + ".jso")))) {
            return (AppUser) decoder.readObject();
        }
    }

    public HFCAClient getHfCaClient(String caUrl, Properties caClientProperties) throws Exception {
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        HFCAClient caClient = HFCAClient.createNewInstance(caUrl, caClientProperties);
        caClient.setCryptoSuite(cryptoSuite);
        return caClient;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String test(@PathVariable Long fileId) throws Exception {
        // create fabric-ca client
        HFCAClient caClient = getHfCaClient("http://ec2-52-70-133-135.compute-1.amazonaws.com:7054", null);

        // enroll or load admin
        AppUser admin = getAdmin(caClient);
        log.info(admin.toString());

        // register and enroll new user
        AppUser appUser = getUser(caClient, admin, "hfuser");
        log.info(appUser.toString());

        // get HFC client instance
        HFClient client = getHfClient();
        // set user context
        client.setUserContext(admin);

        // get HFC channel using the client
        Channel channel = getChannel(client);
        log.info("Channel: " + channel.getName());

        // call query blockchain example
        queryBlockChain(client);


        return "ok!!!!!!!!";
    }
}
