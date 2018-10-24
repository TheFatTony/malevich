package io.malevich.server.services.counterparty;

import io.malevich.server.entity.CounterpartyEntity;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface CounterpartyService {

  List<CounterpartyEntity> findAll();

  CounterpartyEntity findCounterpartyEntitiesByGalleryId(Long galleryId);

}
