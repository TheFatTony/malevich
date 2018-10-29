package io.malevich.server.dao.payments;

import io.malevich.server.entity.PaymentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PaymentsDao extends JpaRepository<PaymentsEntity, Long> {

  List<PaymentsEntity> findAll();

}
