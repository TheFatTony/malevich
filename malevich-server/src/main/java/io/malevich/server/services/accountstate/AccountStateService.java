package io.malevich.server.services.accountstate;

import io.malevich.server.entity.AccountStateEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface AccountStateService {

    List<AccountStateEntity> findAll();

}
