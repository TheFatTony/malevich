package io.malevich.server.repositories.document;

import io.malevich.server.domain.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentDao extends JpaRepository<DocumentEntity, Long> {

    List<DocumentEntity> findByCounterparty_Id(Long counterpartyId);
}
