package io.malevich.server.repositories.address;

import io.malevich.server.domain.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDao extends JpaRepository<AddressEntity, Long> {

//    @Query("select i from AddressEntity i where i.trader.id = :traderId")
//    List<AddressEntity> findByTreaderId(@Param("traderId") long traderId);

}
