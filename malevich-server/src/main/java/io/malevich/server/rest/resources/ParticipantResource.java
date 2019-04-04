package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.services.participant.ParticipantService;
import io.malevich.server.transfer.ParticipantDto;
import lombok.extern.slf4j.Slf4j;
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
public class ParticipantResource extends RestResource<ParticipantDto, ParticipantEntity> {

    @Autowired
    private ParticipantService participantService;

    public ParticipantResource() {
        super(ParticipantDto.class, ParticipantEntity.class);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ParticipantDto getCurrent() {
        ParticipantEntity entity = participantService.getCurrent();

        if(entity == null)
            return null;

        return convertToDto(entity);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

}
