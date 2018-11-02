package io.malevich.server.services.counterpartytype;

import io.malevich.server.repositories.counterpartytype.CounterpartyTypeDao;
import io.malevich.server.domain.CounterpartyTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CounterpartyTypeServiceImpl implements CounterpartyTypeService {

    @Autowired
    private CounterpartyTypeDao dao;


    @Override
    @Transactional(readOnly = true)
    public List<CounterpartyTypeEntity> findAll() {
        return this.dao.findAll();
    }

}
