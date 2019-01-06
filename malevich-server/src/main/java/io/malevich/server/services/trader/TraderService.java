package io.malevich.server.services.trader;

import io.malevich.server.domain.TraderOrganizationEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TraderService {

    List<TraderOrganizationEntity> findAll();

    TraderOrganizationEntity find(Long id);

    TraderOrganizationEntity findByUserName(String name);

    TraderOrganizationEntity update(TraderOrganizationEntity trader);

    TraderOrganizationEntity save(TraderOrganizationEntity traderEntity);

    TraderOrganizationEntity getCurrentTrader();
}
