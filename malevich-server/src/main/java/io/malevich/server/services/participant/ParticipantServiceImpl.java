package io.malevich.server.services.participant;

import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.repositories.participant.ParticipantDao;
import lombok.extern.slf4j.Slf4j;
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
    @Transactional
    public ParticipantEntity save(ParticipantEntity participantEntity) {
        return dao.save(participantEntity);
    }
}
