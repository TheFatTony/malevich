package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.AddressEntity;
import io.malevich.server.services.address.AddressService;
import io.malevich.server.transfer.AddressDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/addresses")
public class AddressResource extends RestResource<AddressDto, AddressEntity> {

    @Autowired
    private AddressService addressService;

    public AddressResource() {
        super(AddressDto.class, AddressEntity.class);
    }

//    @RequestMapping(value = "/trader/{id}", method = RequestMethod.GET)
//    public List<AddressDto> findByTrader(@PathVariable(value = "id") long traderId) {
//        List<AddressEntity> allEntries = this.addressService.findByTraderId(traderId);
//        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
//    }

//    @RequestMapping(value = "/insertPayment", method = RequestMethod.POST)
//    public ResponseEntity<AddressEntity> insertPayment(@RequestBody AddressDto addressDto){
//        AddressEntity addressEntity = convertToEntity(addressDto);
//        addressEntity = this.addressService.create(addressEntity);
//        return ResponseEntity.ok().build();
//    }


}
