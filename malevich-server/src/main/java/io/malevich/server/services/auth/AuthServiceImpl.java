package io.malevich.server.services.auth;


import io.malevich.server.entity.*;
import io.malevich.server.entity.enums.Role;
import io.malevich.server.rest.util.JWTUtil;
import io.malevich.server.services.accesstoken.AccessTokenService;
import io.malevich.server.services.mailqueue.MailQueueService;
import io.malevich.server.services.registertoken.RegisterTokenService;
import io.malevich.server.services.resetpasswordtoken.ResetPasswordTokenService;
import io.malevich.server.services.user.UserService;
import io.malevich.server.services.usertype.UserTypeService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringWriter;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserTypeService userTypeService;

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private RegisterTokenService registerTokenService;

    @Autowired
    private ResetPasswordTokenService resetPasswordTokenService;

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

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    protected AuthServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        return this.userService.findByName(username);
    }

    @Transactional
    public UserEntity findUserByAccessToken(String accessTokenString) {
        AccessTokenEntity accessTokenEntity = this.accessTokenService.findByToken(accessTokenString);

        if (null == accessTokenEntity) {
            return null;
        }

        if (accessTokenEntity.isExpired()) {
            this.accessTokenService.delete(accessTokenEntity);
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
        UserTypeEntity traderUser = userTypeService.getOne(2L);

        RegisterTokenEntity entity = new RegisterTokenEntity();
        entity.setUserName(userName);
        entity.setUserType(traderUser);
        entity.setToken(UUID.randomUUID().toString());
        entity.setEffectiveDate(new java.sql.Timestamp(System.currentTimeMillis()));
        entity = registerTokenService.save(entity);


        VelocityContext context = new VelocityContext();
        context.put("link", "http://localhost:4200/#/auth/register?token=" + entity.getToken());

        StringWriter stringWriter = new StringWriter();
        velocityEngine.mergeTemplate("templates/mail/user_activation_link_template_" + lang + ".vm", "UTF-8", context, stringWriter);
        mailQueueService.create(new MailQueueEntity(entity.getUserName(), messageSource.getMessage("registration.confirm", null, new Locale(lang)), stringWriter.toString()));

        return entity;
    }


    @Override
    @Transactional
    public UserEntity register2(String token, String password){
        RegisterTokenEntity registerTokenEntity = this.registerTokenService.findByToken(token).get();

        UserEntity user = new UserEntity(
                registerTokenEntity.getUserName(),
                bCryptPasswordEncoder.encode(password),
                new HashSet<>(Arrays.asList(Role.USER, Role.TRADER)),
                true);

        user = this.userService.save(user);
        registerTokenService.delete(registerTokenEntity);
        return user;
    }

    @Override
    @Transactional
    public ResetPasswordTokenEntity reset(String lang, String userName) {
        UserEntity user = userService.findByName(userName);
        ResetPasswordTokenEntity entity = new ResetPasswordTokenEntity();
        entity.setUser(user);
        entity.setToken(UUID.randomUUID().toString());
        entity.setEffectiveDate(new java.sql.Timestamp(System.currentTimeMillis()));
        entity = resetPasswordTokenService.save(entity);

        VelocityContext context = new VelocityContext();
        context.put("link", "http://localhost:4200/#/auth/reset?token=" + entity.getToken());
        context.put("email", user.getName());

        StringWriter stringWriter = new StringWriter();
        velocityEngine.mergeTemplate("templates/mail/user_reset_password_link_template_" + lang + ".vm", "UTF-8", context, stringWriter);
        mailQueueService.create(new MailQueueEntity(entity.getUser().getUsername(), messageSource.getMessage("reset.password.confirm", null, new Locale(lang)), stringWriter.toString()));

        return entity;
    }

    @Override
    @Transactional
    public UserEntity setNewPassword(String token, String password){
        ResetPasswordTokenEntity tokenEntity = resetPasswordTokenService.findByToken(token).get();

        UserEntity userEntity = tokenEntity.getUser();
        userEntity.setPassword(bCryptPasswordEncoder.encode(password));
        userEntity = userService.save(userEntity);
        resetPasswordTokenService.delete(tokenEntity);

        return userEntity;
    }

    private List<String> createRoleMap(UserDetails userDetails) {
        List<String> roles = new ArrayList<String>();
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
        return roles;
    }

}
