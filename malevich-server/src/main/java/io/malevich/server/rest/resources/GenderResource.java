package io.malevich.server.rest.resources;

import com.yinyang.core.server.transfer.FileDto;
import io.malevich.server.domain.GenderEntity;
import io.malevich.server.services.gender.GenderService;
import io.malevich.server.transfer.GenderDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/genders")
public class GenderResource {

    @Autowired
    private GenderService genderService;

    @Autowired
    private ModelMapper modelMapper;

    //    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<GenderDto> list() {
        List<GenderEntity> allEntries = this.genderService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    private GenderDto convertToDto(GenderEntity files) {
        GenderDto filesDto = modelMapper.map(files, GenderDto.class);
        return filesDto;
    }

    private GenderEntity convertToEntity(FileDto filesDto) {
        GenderEntity files = modelMapper.map(filesDto, GenderEntity.class);
        return files;
    }

}
