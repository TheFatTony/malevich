package io.malevich.server.services.wishlist;

import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.domain.WishListEntity;
import io.malevich.server.repositories.wishlist.WishListDao;
import io.malevich.server.services.participant.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListServiceImpl implements WishListService {

    @Autowired
    private WishListDao wishListDao;

    @Autowired
    private ParticipantService participantService;

    @Override
    @Transactional
    public WishListEntity save(WishListEntity entity) {
        ParticipantEntity current = participantService.getCurrent();

        if (current == null)
            return null;

        entity.setParticipant(current);

        WishListEntity wish = wishListDao
                .findByParticipant_IdAndArtworkStock_Id(
                        entity.getParticipant().getId(),
                        entity.getArtworkStock().getId()
                );

        if (wish != null)
            return null;

        return this.wishListDao.save(entity);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<WishListEntity> findAllPageable(Pageable pageable) {
        ParticipantEntity current = participantService.getCurrent();
        if (current == null)
            return Page.empty();

        return this.wishListDao.findAll(pageable, current.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WishListEntity> findAll() {
        ParticipantEntity current = participantService.getCurrent();

        if (current == null)
            return new ArrayList<>();

        return this.wishListDao.findAll(current.getId());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.wishListDao.deleteById(id);
    }

}
