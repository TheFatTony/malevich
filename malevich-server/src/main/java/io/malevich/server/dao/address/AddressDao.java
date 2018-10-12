package io.malevich.server.dao.address;

import io.malevich.server.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDao extends JpaRepository<AddressEntity, Long> {

//    @Query("select i from AddressEntity i where i.trader.id = :traderId")
//    List<AddressEntity> findByTreaderId(@Param("traderId") long traderId);

}
