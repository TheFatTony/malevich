package io.malevich.server.dao.address;

import io.malevich.server.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDao extends JpaRepository<AddressEntity, Long> {

    List<AddressEntity> findByTreaderId(long traderId);

}
