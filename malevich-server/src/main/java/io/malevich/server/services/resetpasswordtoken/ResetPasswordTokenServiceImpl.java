package io.malevich.server.services.resetpasswordtoken;

import io.malevich.server.dao.resetpasswordtoken.ResetPasswordTokenDao;
import io.malevich.server.entity.ResetPasswordTokenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class ResetPasswordTokenServiceImpl implements ResetPasswordTokenService {

  @Autowired
  private ResetPasswordTokenDao dao;
  
  @Override
  @Transactional(readOnly = true)
  public List<ResetPasswordTokenEntity> findAll() {
        return this.dao.findAll();
  }

  @Override
  @Transactional
  public ResetPasswordTokenEntity save(ResetPasswordTokenEntity resetPasswordTokenEntity) {
    return dao.save(resetPasswordTokenEntity);
  }

  @Override
  @Transactional
  public void delete(ResetPasswordTokenEntity resetPasswordTokenEntity) {
    dao.delete(resetPasswordTokenEntity);
  }

  @Override
  @Transactional
  public Optional<ResetPasswordTokenEntity> findByToken(String token) {
    return dao.findByToken(token);
  }

}
