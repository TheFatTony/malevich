package io.malevich.server.rest.resources;

import io.malevich.server.domain.ContactUsEntity;
import io.malevich.server.services.contactus.ContactUsService;
import io.malevich.server.transfer.ContactUsDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/contactus")
public class ContactUsResource {

    @Autowired
    private ContactUsService contactUsService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> save(@RequestBody ContactUsDto dto) {
        this.contactUsService.save(convertToEntity(dto));
        return ResponseEntity.ok().build();
    }

    private ContactUsEntity convertToEntity(ContactUsDto dto) {
        return modelMapper.map(dto, ContactUsEntity.class);
    }

}
