package io.malevich.server.services.wishlist;

import io.malevich.server.domain.WishListEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WishListService {

    WishListEntity save(WishListEntity entity);

    Page<WishListEntity> findAllPageable(Pageable pageable);

    List<WishListEntity> findAll();

    void delete(Long id);
}
