package io.malevich.server.services.participant;

import com.google.common.collect.Lists;
import com.yinyang.core.server.domain.UserEntity;
import com.yinyang.core.server.services.auth.AuthService;
import com.yinyang.core.server.services.user.UserService;
import io.malevich.server.config.MyAuthenticationProvider;
import io.malevich.server.domain.*;
import io.malevich.server.fabric.services.gallery.GalleryParticipantService;
import io.malevich.server.fabric.services.trader.TraderParticipantService;
import io.malevich.server.repositories.participant.ParticipantDao;
import io.malevich.server.services.delayedchange.DelayedChangeService;
import io.malevich.server.services.participanttype.ParticipantTypeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class ParticipantServiceImpl implements ParticipantService {

    @Autowired
    private ParticipantDao dao;

    @Autowired
    private DelayedChangeService delayedChangeService;

    @Autowired
    private ParticipantTypeService participantTypeService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private GalleryParticipantService galleryParticipantService;

    @Autowired
    private TraderParticipantService traderParticipantService;

    @Override
    public List<ParticipantEntity> findAll() {
        return dao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ParticipantEntity> findById(Long participantId) {
        return dao.findById(participantId);
    }

    @Override
    @Transactional(readOnly = true)
    public ParticipantEntity getCurrent() {
        return dao.findByUsers_Name(authService.getUserEntity().getUsername()).orElse(null);
    }

    @Override
    public ParticipantEntity convertToEntity(Object payload) {
        if (payload instanceof ParticipantEntity)
            return (ParticipantEntity) payload;

        ParticipantEntity abstractParticipant = modelMapper.map(payload, ParticipantEntity.class);

        if (abstractParticipant.getType() == null)
            return null;

        String type = abstractParticipant.getType().getId();

        if (participantTypeService.getTraderPersonType().getId().equals(type))
            return modelMapper.map(payload, TraderPersonEntity.class);

        if (participantTypeService.getTraderOrganizationType().getId().equals(type))
            return modelMapper.map(payload, TraderOrganizationEntity.class);

        if (participantTypeService.getGalleryType().getId().equals(type))
            return modelMapper.map(payload, GalleryEntity.class);

        return modelMapper.map(payload, ParticipantEntity.class);
    }

    private static boolean isNullOrEmpty(String value) {
        return value == null || "".equals(value);
    }

    private boolean isTraderPersonTier1(TraderPersonEntity participantEntity) {
        if (participantEntity == null)
            return false;

        PersonEntity personEntity = participantEntity.getPerson();

        if (personEntity == null)
            return false;

        if (isNullOrEmpty(personEntity.getFirstName()))
            return false;

        if (isNullOrEmpty(personEntity.getLastName()))
            return false;

        if (personEntity.getDateOfBirth() == null)
            return false;

        if (isNullOrEmpty(participantEntity.getPhoneNumber()))
            return false;

        if (participantEntity.getCountry() == null)
            return false;

        return true;
    }

    @Override
    @Transactional
    public ParticipantEntity save(ParticipantEntity participantEntity, UserEntity user) {
        ParticipantEntity currentParticipant = dao.findByUsers_Name(user.getName()).orElse(null);

        boolean isNew = currentParticipant == null;
        if (!isNew) {
            participantEntity.setId(currentParticipant.getId());
            participantEntity.setUsers(currentParticipant.getUsers());

            if (participantEntity instanceof TraderPersonEntity && currentParticipant instanceof TraderPersonEntity) {
                TraderPersonEntity traderPersonEntity = ((TraderPersonEntity) participantEntity);
                PersonEntity person = traderPersonEntity.getPerson();
                PersonEntity currentPerson = ((TraderPersonEntity) currentParticipant).getPerson();
                if (person != null && currentPerson != null)
                    person.setId(currentPerson.getId());

                //tier check
                if (isTraderPersonTier1(traderPersonEntity)) {
                    for (UserEntity userEntity : traderPersonEntity.getUsers()) {
                        boolean changes = userEntity.getRoles().add(MyAuthenticationProvider.ROLE_TRADER_TIER0);
                        if (changes)
                            userService.save(userEntity);
                    }
                }
            } else if (participantEntity instanceof TraderOrganizationEntity && currentParticipant instanceof TraderOrganizationEntity) {
                OrganizationEntity organization = ((TraderOrganizationEntity) participantEntity).getOrganization();
                OrganizationEntity currentOrganization = ((TraderOrganizationEntity) currentParticipant).getOrganization();
                if (organization != null && currentOrganization != null)
                    organization.setId(currentOrganization.getId());
            } else if (participantEntity instanceof GalleryEntity && currentParticipant instanceof GalleryEntity) {
                OrganizationEntity organization = ((GalleryEntity) participantEntity).getOrganization();
                OrganizationEntity currentOrganization = ((GalleryEntity) currentParticipant).getOrganization();
                if (organization != null && currentOrganization != null)
                    organization.setId(currentOrganization.getId());
            }
        } else {
            participantEntity.setUsers(Lists.newArrayList(user));

            if ("G".equals(participantEntity.getType().getId()))
                galleryParticipantService.create(participantEntity);
            else
                traderParticipantService.create(participantEntity);
        }

        participantEntity = dao.save(participantEntity);

        return participantEntity;
    }

    @Override
    @Transactional
    public ParticipantEntity update(ParticipantEntity participantEntity) {
        UserEntity user = authService.getUserEntity();
        DelayedChangeEntity delayedChangeEntity = new DelayedChangeEntity();
        delayedChangeEntity.setTypeId("PARTICIPANT");
        delayedChangeEntity.setPayload(participantEntity);
        delayedChangeEntity.setReferenceId(participantEntity.getId());
        delayedChangeEntity.setUser(user);
        delayedChangeService.save(delayedChangeEntity);
        return participantEntity;
    }
}
