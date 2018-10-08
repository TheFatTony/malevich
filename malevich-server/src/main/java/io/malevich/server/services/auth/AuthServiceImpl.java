package io.malevich.server.services.auth;


import io.malevich.server.dao.accesstoken.AccessTokenDao;
import io.malevich.server.dao.registertoken.RegisterTokenDao;
import io.malevich.server.dao.user.UserDao;
import io.malevich.server.dao.usertype.UserTypeDao;
import io.malevich.server.entity.*;
import io.malevich.server.rest.util.JWTUtil;
import io.malevich.server.services.mailqueue.MailQueueService;
import io.malevich.server.transfer.AccessTokenDto;
import io.malevich.server.transfer.LoginFormDto;
import io.malevich.server.transfer.UserDto;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserTypeDao userTypeDao;

    @Autowired
    private AccessTokenDao accessTokenDao;

    @Autowired
    private RegisterTokenDao registerTokenDao;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MailQueueService mailQueueService;


    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private MessageSource messageSource;

    protected AuthServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        return this.userDao.findByName(username);
    }

    @Transactional
    public UserEntity findUserByAccessToken(String accessTokenString) {
        AccessTokenEntity accessTokenEntity = this.accessTokenDao.findByToken(accessTokenString);

        if (null == accessTokenEntity) {
            return null;
        }

        if (accessTokenEntity.isExpired()) {
            this.accessTokenDao.delete(accessTokenEntity);
            return null;
        }

        return accessTokenEntity.getUser();
    }

    public AccessTokenEntity createAccessToken(UserEntity user) {
        AccessTokenEntity accessTokenEntity = new AccessTokenEntity(user, jwtUtil.generateToken(user.getUsername()));
//        accessTokenDao.save(accessTokenEntity);
        return accessTokenEntity;
    }

    @Override
    @Transactional
    public UserDto getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = null;
        try {
            userDetails = (UserDetails) principal;
        } catch (ClassCastException e) {
            return null;
        }
        return new UserDto(userDetails.getUsername(), null, this.createRoleMap(userDetails));
    }

    @Override
    @Transactional
    public AccessTokenDto authenticate(LoginFormDto loginFormDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginFormDto.getUsername(), loginFormDto.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        return new AccessTokenDto(this.createAccessToken((UserEntity) authentication.getPrincipal()).getToken());
    }

    @Override
    @Transactional
    public RegisterTokenEntity register(String lang, String userName) {
        UserTypeEntity traderUser = userTypeDao.getOne(2L);

        RegisterTokenEntity entity = new RegisterTokenEntity();
        entity.setUserName(userName);
        entity.setUserType(traderUser);
        entity.setToken(UUID.randomUUID().toString());
        entity.setEffectiveDate(new java.sql.Timestamp(System.currentTimeMillis()));
        entity = registerTokenDao.save(entity);


        VelocityContext context = new VelocityContext();
        context.put("link", "http://localhost:4200/#/auth/register?token=" + entity.getToken());

        StringWriter stringWriter = new StringWriter();
        velocityEngine.mergeTemplate("templates/mail/user_activation_link_template_"+ lang+ ".vm", "UTF-8", context, stringWriter);
        mailQueueService.create(new MailQueueEntity(entity.getUserName(), messageSource.getMessage("registration.confirm", null, new Locale(lang)), stringWriter.toString()));

        return entity;
    }


    private List<String> createRoleMap(UserDetails userDetails) {
        List<String> roles = new ArrayList<String>();
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
        return roles;
    }
}
