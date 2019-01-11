package io.malevich.server.services.registertoken;

import com.google.common.collect.Lists;
import com.yinyang.core.server.domain.MailQueueEntity;
import com.yinyang.core.server.domain.RegisterTokenEntity;
import com.yinyang.core.server.domain.UserEntity;
import com.yinyang.core.server.domain.UserTypeEntity;
import com.yinyang.core.server.domain.enums.Role;
import com.yinyang.core.server.repositories.registertoken.RegisterTokenDao;
import com.yinyang.core.server.services.mailqueue.MailQueueService;
import com.yinyang.core.server.services.user.UserService;
import io.malevich.server.domain.*;
import io.malevich.server.services.counterparty.CounterpartyService;
import io.malevich.server.services.counterpartytype.CounterpartyTypeService;
import io.malevich.server.services.participant.ParticipantService;
import io.malevich.server.services.participanttype.ParticipantTypeService;
import io.malevich.server.transfer.RegisterFormStepTwoDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringWriter;
import java.util.*;


@Slf4j
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterTokenDao registerTokenDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private CounterpartyTypeService counterpartyTypeService;

    @Autowired
    private MailQueueService mailQueueService;

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ParticipantTypeService participantTypeService;

    @Autowired
    private CounterpartyService counterpartyService;

    @Value("${yinyang.client.url}")
    private String clientUrl;


    @Override
    @Transactional
    public RegisterTokenEntity saveToken(RegisterTokenEntity registerTokenEntity) {
        return registerTokenDao.save(registerTokenEntity);
    }

    @Override
    @Transactional
    public void deleteToken(RegisterTokenEntity registerTokenEntity) {
        registerTokenDao.delete(registerTokenEntity);
    }

    @Override
    @Transactional
    public Optional<RegisterTokenEntity> findToken(String token) {
        return registerTokenDao.findByToken(token);
    }

    @Override
    @Transactional
    public RegisterTokenEntity register(RegisterTokenEntity entity, String lang) {

        entity.setToken(UUID.randomUUID().toString());
        entity.setEffectiveDate(new java.sql.Timestamp(System.currentTimeMillis()));
        entity = saveToken(entity);


        VelocityContext context = new VelocityContext();
        context.put("link", clientUrl + "/#/user/register?token=" + entity.getToken());

        StringWriter stringWriter = new StringWriter();
        velocityEngine.mergeTemplate("templates/mail/user_activation_link_template_" + lang + ".vm", "UTF-8", context, stringWriter);
        mailQueueService.create(new MailQueueEntity(entity.getUserName(), messageSource.getMessage("registration.confirm", null, new Locale(lang)), stringWriter.toString()));

        return entity;
    }

    private boolean isGallery(UserTypeEntity type){
        if(type == null || type.getId() == null) return false;

        return type.getId().equals(1L);
    }

    private boolean isOrganization(UserTypeEntity type){
        if(type == null || type.getId() == null) return false;

        return type.getId().equals(1L) || type.getId().equals(3L);
    }

    @Override
    @Transactional
    public UserEntity register2(String token, RegisterFormStepTwoDto registerInfo) {
        RegisterTokenEntity registerTokenEntity = findToken(token).get();

        List<Role> roles = Lists.newArrayList(Role.USER);
        if (isGallery(registerTokenEntity.getUserType()))
            roles.add(Role.GALLERY);
        else
            roles.add(Role.TRADER);

        UserEntity user = new UserEntity(
                registerTokenEntity.getUserName(),
                bCryptPasswordEncoder.encode(registerInfo.getPassword()),
                new HashSet<>(roles),
                true);

        user = userService.save(user);

        CounterpartyEntity counterparty = new CounterpartyEntity();
        ParticipantEntity participant;

        if (isGallery(registerTokenEntity.getUserType())) {
            participant = new GalleryEntity();
            participant.setType(participantTypeService.getGalleryType());

            counterparty.setType(counterpartyTypeService.getGalleryType());
        } else {
            counterparty.setType(counterpartyTypeService.getTraderType());

            if (isOrganization(registerTokenEntity.getUserType())) {
                participant = new TraderOrganizationEntity();
                participant.setType(participantTypeService.getTraderOrganizationType());
            } else {
                participant = new TraderPersonEntity();
                participant.setType(participantTypeService.getTraderPersonType());
            }
        }

        participant.setUsers(Lists.newArrayList(user));
        participant = participantService.save(participant, user);

        counterparty.setParticipant(participant);
        counterpartyService.save(counterparty);

        deleteToken(registerTokenEntity);
        return user;
    }

}
