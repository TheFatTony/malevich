package io.malevich.server.rest.resources;


import io.malevich.server.dao.accesstoken.AccessTokenDao;
import io.malevich.server.dao.user.UserDao;
import io.malevich.server.entity.AccessTokenEntity;
import io.malevich.server.entity.UserEntity;
import io.malevich.server.services.mailqueue.MailQueueService;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.StringWriter;


@RestController
@RequestMapping(value = "/test")
public class TestResource {


    @Autowired
    private MailQueueService mailQueueService;

    @Autowired
    private VelocityEngine velocityEngine;



    @Autowired
    private AccessTokenDao accessTokenDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String test() {
        AccessTokenEntity accessTokenEntity = new AccessTokenEntity(userDao.findByName("admin@malevich.io"), "fhgjkl");
        accessTokenDao.save(accessTokenEntity);



        throw new NullPointerException();

//        VelocityContext context = new VelocityContext();
//        context.put("test", "World !!!");
//
//        StringWriter stringWriter = new StringWriter();
//        stringWriter.append(messageSource.getMessage("welcome.message", null, LocaleContextHolder.getLocale()));
//        velocityEngine.mergeTemplate("templates/mail/confirm_email.vm", "UTF-8", context, stringWriter);
//        mailQueueService.create(new MailQueue("anton.alexeyev85@gmail.com", "Testing Subject", stringWriter.toString()));
//        return stringWriter.toString();
    }



}