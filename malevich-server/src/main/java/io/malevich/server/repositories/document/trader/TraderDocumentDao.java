package io.malevich.server.repositories.document.trader;

import io.malevich.server.domain.TraderDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TraderDocumentDao extends JpaRepository<TraderDocumentEntity, Long> {

    @Modifying
    @Query("delete from TraderDocumentEntity tde where tde.document.id =:id1 and tde.trader.id =:id2 ")
    void deleteById(@Param("id1") Long documentId, @Param("id2") Long traderId);
}
