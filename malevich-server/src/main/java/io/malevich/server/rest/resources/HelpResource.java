package io.malevich.server.rest.resources;

import io.malevich.server.domain.HelpCategoryEntity;
import io.malevich.server.services.help.HelpCategoryService;
import io.malevich.server.transfer.HelpCategoryDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
//@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "/help")
public class HelpResource {

    @Autowired
    private HelpCategoryService helpCategoryService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/addCategory")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> addCategory(@RequestBody HelpCategoryDto helpCategoryDto) {
        this.helpCategoryService.save(convertToEntity(helpCategoryDto));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/categoryList")
    @ResponseStatus(HttpStatus.OK)
    public List<HelpCategoryDto> categoryList() {
        List<HelpCategoryEntity> entityList = this.helpCategoryService.findAll();
        return entityList.stream().map(allData -> convertToDto(allData)).collect(Collectors.toList());
    }

    private HelpCategoryEntity convertToEntity(HelpCategoryDto dto) {
        return modelMapper.map(dto, HelpCategoryEntity.class);
    }

    private HelpCategoryDto convertToDto(HelpCategoryEntity entity) {
        return modelMapper.map(entity, HelpCategoryDto.class);
    }
}
