package io.malevich.server.fabric.services.gallery;

import io.malevich.server.domain.GalleryEntity;
import io.malevich.server.domain.TransactionEntity;
import org.springframework.stereotype.Service;

@Service
public interface ComposerGalleryService {

     void createGallery(GalleryEntity gallery);

}
