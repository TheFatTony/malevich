package io.malevich.server.services.participant;

import com.google.common.collect.Lists;
import io.malevich.server.domain.*;
import io.malevich.server.repositories.participant.ParticipantDao;
import io.malevich.server.services.auth.AuthService;
import io.malevich.server.services.delayedchange.DelayedChangeService;
import io.malevich.server.services.participanttype.ParticipantTypeService;
import io.malevich.server.services.user.UserService;
import io.malevich.server.transfer.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    private UserService userService;

    @Autowired
    private DelayedChangeService delayedChangeService;

    @Autowired
    private ParticipantTypeService participantTypeService;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private AuthService authService;


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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserDetails))
            return null;

        UserDetails userDetails = (UserDetails) principal;

        return dao.findByUsers_Name(userDetails.getUsername()).orElse(null);
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

    @Override
    @Transactional
    public ParticipantEntity save(ParticipantEntity participantEntity, UserEntity user) {
        ParticipantEntity currentParticipant = dao.findByUsers_Name(user.getName()).orElse(null);

        boolean isNew = currentParticipant == null;
        if (!isNew) {
            participantEntity.setId(currentParticipant.getId());
            participantEntity.setUsers(currentParticipant.getUsers());

            if (participantEntity instanceof TraderPersonEntity && currentParticipant instanceof TraderPersonEntity) {
                PersonEntity person = ((TraderPersonEntity) participantEntity).getPerson();
                PersonEntity currentPerson = ((TraderPersonEntity) currentParticipant).getPerson();
                if (person != null && currentPerson != null)
                    person.setId(currentPerson.getId());
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
        }

        return dao.save(participantEntity);
    }

    @Override
    @Transactional
    public ParticipantEntity update(ParticipantEntity participantEntity) {
        UserEntity user = userService.findByName(authService.getUser().getName());
        DelayedChangeEntity delayedChangeEntity = new DelayedChangeEntity();
        delayedChangeEntity.setTypeId("PARTICIPANT");
        delayedChangeEntity.setPayload(participantEntity);
        delayedChangeEntity.setReferenceId(participantEntity.getId());
        delayedChangeEntity.setUser(user);
        delayedChangeService.save(delayedChangeEntity);
        return participantEntity;
    }
}
