package io.malevich.server.services.country;


import io.malevich.server.domain.CountryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CountryService {

    List<CountryEntity> findAll();

    CountryEntity find(Long id);
}
