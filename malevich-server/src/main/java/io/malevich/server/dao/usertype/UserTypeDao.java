package io.malevich.server.dao.usertype;


import io.malevich.server.dao.Dao;
import io.malevich.server.entity.UserTypeEntity;
import org.springframework.stereotype.Component;

@Component
public interface UserTypeDao extends Dao<UserTypeEntity, Long> {
	

}
