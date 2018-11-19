package io.malevich.server.fabric.services;

import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.fabric.model.Artwork;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
public class ComposerServiceImpl implements ComposerService {

    @Value("${malevich.composer.url}")
    private String composerUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders fabricHeaders;

    @Override
    public void addArtwork(ArtworkStockEntity artworkStockEntity) {
        Artwork artwork = new Artwork(artworkStockEntity.getId().toString(),
                "resource:io.malevich.network.Counetrparty#" + artworkStockEntity.getGallery().getId().toString(), "1000");
        doPost(artwork);

    }

    private String doPost(Object arg){
        HttpEntity<Artwork> requestBody = new HttpEntity(arg, fabricHeaders);
        ResponseEntity<String> res = restTemplate.exchange(composerUrl + "/Artwork", HttpMethod.POST, requestBody, String.class);
        log.trace(res.getBody());
        return res.getBody();
    }
}
