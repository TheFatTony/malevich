package io.malevich.server.services.usertype;

import io.malevich.server.dao.usertype.UserTypeDao;
import io.malevich.server.entity.UserTypeEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class UserTypeServiceImpl implements UserTypeService {

  @Autowired
  private UserTypeDao userTypeDao;


  @Override
  @Transactional(readOnly = true)
  public List<UserTypeEntity> findAll() {
        return this.userTypeDao.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public UserTypeEntity getOne(Long id) {
    return userTypeDao.getOne(id);
  }

}
