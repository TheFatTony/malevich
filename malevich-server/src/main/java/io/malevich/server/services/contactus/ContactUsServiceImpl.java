package io.malevich.server.services.contactus;

import io.malevich.server.domain.ContactUsEntity;
import io.malevich.server.repositories.contactus.ContactUsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ContactUsServiceImpl implements ContactUsService {

    @Autowired
    private ContactUsDao contactUsDao;

    @Override
    @Transactional
    public ContactUsEntity save(ContactUsEntity entity) {
        return this.contactUsDao.save(entity);
    }
}
