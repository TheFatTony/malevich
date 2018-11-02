package io.malevich.server.rest.resources;

import io.malevich.server.domain.AddressEntity;
import io.malevich.server.services.address.AddressService;
import io.malevich.server.transfer.AddressDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/addresses")
public class AddressResource {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AddressService addressService;

    @Autowired
    private ModelMapper modelMapper;

//    @RequestMapping(value = "/trader/{id}", method = RequestMethod.GET)
//    public List<AddressDto> findByTrader(@PathVariable(value = "id") long traderId) {
//        this.logger.info("list()");
//        List<AddressEntity> allEntries = this.addressService.findByTraderId(traderId);
//        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
//    }

//    @RequestMapping(value = "/insert", method = RequestMethod.POST)
//    public ResponseEntity<AddressEntity> insert(@RequestBody AddressDto addressDto){
//        AddressEntity addressEntity = convertToEntity(addressDto);
//        addressEntity = this.addressService.create(addressEntity);
//        return ResponseEntity.ok().build();
//    }

    private AddressDto convertToDto(AddressEntity files) {
        AddressDto filesDto = modelMapper.map(files, AddressDto.class);
        return filesDto;
    }

    private AddressEntity convertToEntity(AddressDto filesDto) {
        AddressEntity files = modelMapper.map(filesDto, AddressEntity.class);
        return files;
    }

}
