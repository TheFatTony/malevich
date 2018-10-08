package io.malevich.server.services.trader;

import io.malevich.server.entity.TraderEntity;
import io.malevich.server.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TraderService {

    List<TraderEntity> findAll();

    TraderEntity find(Long id);

    TraderEntity findByUserName(String name);

    TraderEntity update(TraderEntity trader);
}
