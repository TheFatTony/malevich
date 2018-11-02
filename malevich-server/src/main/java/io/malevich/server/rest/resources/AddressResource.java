package io.malevich.server.rest.resources;

import io.malevich.server.services.address.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/addresses")
public class AddressResource {

    @Autowired
    private AddressService addressService;

//    @RequestMapping(value = "/trader/{id}", method = RequestMethod.GET)
//    public List<AddressDto> findByTrader(@PathVariable(value = "id") long traderId) {
//        List<AddressEntity> allEntries = this.addressService.findByTraderId(traderId);
//        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
//    }

//    @RequestMapping(value = "/insert", method = RequestMethod.POST)
//    public ResponseEntity<AddressEntity> insert(@RequestBody AddressDto addressDto){
//        AddressEntity addressEntity = convertToEntity(addressDto);
//        addressEntity = this.addressService.create(addressEntity);
//        return ResponseEntity.ok().build();
//    }

}
