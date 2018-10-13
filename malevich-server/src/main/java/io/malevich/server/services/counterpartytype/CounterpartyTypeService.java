package io.malevich.server.services.counterpartytype;

import io.malevich.server.entity.CounterpartyTypeEntity;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface CounterpartyTypeService {

  List<CounterpartyTypeEntity> findAll();

}
