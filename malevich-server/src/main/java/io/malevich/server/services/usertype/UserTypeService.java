package io.malevich.server.services.usertype;

import io.malevich.server.domain.UserTypeEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserTypeService {

    List<UserTypeEntity> findAll();

    UserTypeEntity getOne(Long id);

}
