package io.malevich.server.services.registertoken;

import com.google.common.collect.Lists;
import io.malevich.server.domain.*;
import io.malevich.server.domain.enums.Role;
import io.malevich.server.repositories.registertoken.RegisterTokenDao;
import io.malevich.server.services.counterparty.CounterpartyService;
import io.malevich.server.services.counterpartytype.CounterpartyTypeService;
import io.malevich.server.services.mailqueue.MailQueueService;
import io.malevich.server.services.participant.ParticipantService;
import io.malevich.server.services.participanttype.ParticipantTypeService;
import io.malevich.server.services.user.UserService;
import io.malevich.server.services.usertype.UserTypeService;
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
    private UserTypeService userTypeService;

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

    @Value("${malevich.client.url}")
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
    public RegisterTokenEntity register(String lang, String userName) {
        UserTypeEntity userType = userTypeService.getOne(2L);

        RegisterTokenEntity entity = new RegisterTokenEntity();
        entity.setUserName(userName);
        entity.setUserType(userType);
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


    @Override
    @Transactional
    public UserEntity register2(String token, RegisterFormStepTwoDto registerInfo) {
        RegisterTokenEntity registerTokenEntity = findToken(token).get();

        List<Role> roles = Lists.newArrayList(Role.USER);
        if (registerInfo.getIsGallery())
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

        if (registerInfo.getIsGallery()) {
            participant = new GalleryEntity();
            participant.setType(participantTypeService.getGalleryType());

            counterparty.setType(counterpartyTypeService.getGalleryType());
        } else {
            counterparty.setType(counterpartyTypeService.getTraderType());

            if (registerInfo.getIsOrganization()) {
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
