package io.malevich.server.rest.resources;

import io.malevich.server.domain.HelpCategoryEntity;
import io.malevich.server.domain.HelpTopicEntity;
import io.malevich.server.services.help.HelpCategoryService;
import io.malevich.server.services.help.HelpTopicService;
import io.malevich.server.transfer.HelpCategoryDto;
import io.malevich.server.transfer.HelpFilterDto;
import io.malevich.server.transfer.HelpTopicDto;
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
@RequestMapping(value = "/help")
public class HelpResource {

    @Autowired
    private HelpCategoryService helpCategoryService;

    @Autowired
    private HelpTopicService helpTopicService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/addCategory")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addCategory(@RequestBody HelpCategoryDto helpCategoryDto) {
        this.helpCategoryService.save(convertToEntity(helpCategoryDto));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/categoryList")
    @ResponseStatus(HttpStatus.OK)
    public List<HelpCategoryDto> categoryList() {
        return this.helpCategoryService.findAll().stream().map(allData -> convertToDto(allData)).collect(Collectors.toList());
    }

    @PostMapping("/addTopic")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addTopics(@RequestBody HelpTopicDto topicDto) {
        this.helpTopicService.save(convertToEntity(topicDto));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/topicList")
    @ResponseStatus(HttpStatus.OK)
    public List<HelpTopicDto> topicList() {
        return this.helpTopicService.findAll().stream().map(allData -> convertToDto(allData)).collect(Collectors.toList());
    }

    @GetMapping("/topic/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<HelpTopicDto> getTopic(@PathVariable Long id) {
        return this.helpTopicService.findByCategoryId(id).stream().map(allData -> convertToDto(allData)).collect(Collectors.toList());
    }

    @PostMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public List<HelpTopicDto> filter(@RequestBody HelpFilterDto filterDto) {
        return this.helpTopicService.filter(filterDto.getLang(), filterDto.getSearchValue()).stream().map(allData -> convertToDto(allData)).collect(Collectors.toList());
    }

    private HelpCategoryEntity convertToEntity(HelpCategoryDto dto) {
        return modelMapper.map(dto, HelpCategoryEntity.class);
    }

    private HelpCategoryDto convertToDto(HelpCategoryEntity entity) {
        return modelMapper.map(entity, HelpCategoryDto.class);
    }

    private HelpTopicEntity convertToEntity(HelpTopicDto dto) {
        return modelMapper.map(dto, HelpTopicEntity.class);
    }

    private HelpTopicDto convertToDto(HelpTopicEntity entity) {
        return modelMapper.map(entity, HelpTopicDto.class);
    }
}
