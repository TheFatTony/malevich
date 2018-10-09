package io.malevich.server.services.address.country;


import io.malevich.server.entity.AddressEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface AddressService {

    List<AddressEntity> findAll();

    AddressEntity find(Long id);

    List<AddressEntity> findByTraderId(Long traderId);
}
