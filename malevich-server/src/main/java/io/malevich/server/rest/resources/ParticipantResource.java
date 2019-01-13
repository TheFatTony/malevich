package io.malevich.server.rest.resources;

import io.malevich.server.domain.GalleryEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.domain.TraderOrganizationEntity;
import io.malevich.server.domain.TraderPersonEntity;
import io.malevich.server.services.participant.ParticipantService;
import io.malevich.server.services.participanttype.ParticipantTypeService;
import io.malevich.server.transfer.ParticipantDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/participant")
public class ParticipantResource {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ParticipantDto getCurrent() {
        ParticipantEntity entity = participantService.getCurrent();
        return convertToDto(entity);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ParticipantDto> list() {
        List<ParticipantEntity> allEntries = this.participantService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> update(@RequestBody ParticipantDto dto) {
        ParticipantEntity entity = participantService.convertToEntity(dto);
        participantService.update(entity);
        return ResponseEntity.ok().build();
    }

    private ParticipantDto convertToDto(ParticipantEntity entity) {
        return modelMapper.map(entity, ParticipantDto.class);
    }

    private ParticipantEntity convertToEntity(ParticipantDto dto) {
        return modelMapper.map(dto, ParticipantEntity.class);
    }

}
