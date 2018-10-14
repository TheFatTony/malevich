package io.malevich.server.services.address;


import io.malevich.server.entity.AddressEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {

    List<AddressEntity> findAll();

    AddressEntity find(Long id);

//    List<AddressEntity> findByTraderId(Long traderId);

//    AddressEntity create(AddressEntity addressEntity);
}
