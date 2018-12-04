package io.malevich.server.services.wishlist;

import io.malevich.server.domain.WishListEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishListService {

    WishListEntity save(WishListEntity entity);

    Page<WishListEntity> findAll(Pageable pageable);

    void delete(Long id);
}
