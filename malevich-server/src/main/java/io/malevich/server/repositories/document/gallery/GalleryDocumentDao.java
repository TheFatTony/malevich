package io.malevich.server.repositories.document.gallery;

import io.malevich.server.domain.GalleryDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryDocumentDao extends JpaRepository<GalleryDocumentEntity, Long> {

    @Modifying
    @Query("delete from GalleryDocumentEntity gde where gde.document.id =:id1 and gde.gallery.id =:id2 ")
    void deleteById(@Param("id1") Long documentId, @Param("id2") Long galleryId);
}
