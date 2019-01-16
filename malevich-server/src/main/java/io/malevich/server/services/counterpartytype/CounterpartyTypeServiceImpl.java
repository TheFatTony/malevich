package io.malevich.server.services.counterpartytype;

import io.malevich.server.repositories.counterpartytype.CounterpartyTypeDao;
import io.malevich.server.domain.CounterpartyTypeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
public class CounterpartyTypeServiceImpl implements CounterpartyTypeService {

    private final Map<String, CounterpartyTypeEntity> values;

    @Autowired
    private CounterpartyTypeDao counterpartyTypeDao;

    @Autowired
    public CounterpartyTypeServiceImpl(CounterpartyTypeDao counterpartyTypeDao) {
        this.counterpartyTypeDao = counterpartyTypeDao;

        values = counterpartyTypeDao.findAll().stream().collect(Collectors.toMap(i -> i.getId(), i -> i));
    }


    @Override
    @Transactional(readOnly = true)
    public List<CounterpartyTypeEntity> findAll() {
        return this.counterpartyTypeDao.findAll();
    }

    @Override
    public CounterpartyTypeEntity getTraderType(){
        return values.get("T");
    }

    @Override
    public CounterpartyTypeEntity getGalleryType(){
        return values.get("G");
    }

    @Override
    public CounterpartyTypeEntity getMalevichType(){
        return values.get("X");
    }

}
