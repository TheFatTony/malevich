package io.malevich.server.repositories.document;

import io.malevich.server.domain.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentDao extends JpaRepository<DocumentEntity, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM document d JOIN gallery_document gd ON d.id = gd.document_id JOIN document_type dt ON " +
                    "d.document_type_id = dt.id WHERE " +
                    "gd.gallery_id = :id " +
                    "AND lower(dt.user_type) = lower('gallery')")
    List<DocumentEntity> findGalleryDocs(@Param("id") Long id);

    @Query(nativeQuery = true,
            value = "SELECT * FROM document d JOIN trader_document td ON d.id = td.document_id JOIN document_type dt ON " +
                    "d.document_type_id = dt.id WHERE " +
                    "td.trader_id = :id " +
                    "AND lower(dt.user_type) = lower('trader')")
    List<DocumentEntity> findTraderDocs(@Param("id") Long id);
}
