package io.malevich.server.services.counterparty;

import io.malevich.server.domain.*;
import io.malevich.server.repositories.counterparty.CounterpartyDao;
import io.malevich.server.services.auth.AuthService;
import io.malevich.server.services.delayedchange.DelayedChangeService;
import io.malevich.server.services.user.UserService;
import io.malevich.server.transfer.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class CounterpartyServiceImpl implements CounterpartyService {

    @Autowired
    private AuthService authService;

    @Autowired
    private CounterpartyDao counterpartyDao;

    @Autowired
    private DelayedChangeService delayedChangeService;

    @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public List<CounterpartyEntity> findAll() {
        return this.counterpartyDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CounterpartyEntity> findById(Long counterpartyId) {
        return counterpartyDao.findById(counterpartyId);
    }

    @Override
    @Transactional(readOnly = true)
    public CounterpartyEntity getCurrent() {
        UserDto user = authService.getUser();

        if (user == null)
            return null;

        CounterpartyEntity entity = counterpartyDao.findByUserName(user.getName()).orElse(null);
        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public CounterpartyEntity getMalevich() {
        return findById(1L).get();
    }

    @Override
    @Transactional
    public CounterpartyEntity save(CounterpartyEntity counterpartyEntity){
        return counterpartyDao.save(counterpartyEntity);
    }

    @Override
    @Transactional
    public CounterpartyEntity update(CounterpartyEntity counterpartyEntity) {
        CounterpartyEntity currentPartyEntity = getCurrent();

        boolean isNew = currentPartyEntity == null;
        if (!isNew) {
            counterpartyEntity.setId(currentPartyEntity.getId());
            counterpartyEntity.getUser().setId(currentPartyEntity.getUser().getId());
            UserEntity user = counterpartyEntity.getUser();

            if(counterpartyEntity.getPerson() != null && currentPartyEntity.getPerson() != null){
                PersonEntity person = counterpartyEntity.getPerson();
                PersonEntity currentPerson = currentPartyEntity.getPerson();

                person.setId(currentPerson.getId());
            }

            if(counterpartyEntity.getGallery() != null && currentPartyEntity.getGallery() != null){
                GalleryEntity gallery = counterpartyEntity.getGallery();
                GalleryEntity currentGallery = currentPartyEntity.getGallery();

                gallery.setId(currentGallery.getId());
            }


            DelayedChangeEntity delayedChangeEntity = new DelayedChangeEntity();
            delayedChangeEntity.setTypeId("COUNTERPARTY");
            delayedChangeEntity.setPayload(counterpartyEntity);
            delayedChangeEntity.setReferenceId(counterpartyEntity.getId());
            delayedChangeEntity.setUser(user);
            delayedChangeService.save(delayedChangeEntity);
        } else {
            UserDto user = authService.getUser();
            UserEntity userEntity = userService.findByName(user.getName());
            counterpartyEntity.setUser(userEntity);

            counterpartyDao.save(counterpartyEntity);
        }

        return counterpartyEntity;
    }

}
