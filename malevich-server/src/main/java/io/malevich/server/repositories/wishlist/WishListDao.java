package io.malevich.server.repositories.wishlist;

import io.malevich.server.domain.WishListEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListDao extends JpaRepository<WishListEntity, Long> {

    @Query("select wle from WishListEntity wle where wle.participant.id =:id")
    Page<WishListEntity> findAll(Pageable pageable, @Param("id") Long id);
}
