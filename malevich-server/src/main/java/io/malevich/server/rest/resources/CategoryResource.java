package io.malevich.server.rest.resources;

import io.malevich.server.domain.CategoryEntity;
import io.malevich.server.services.category.CategoryService;
import io.malevich.server.transfer.CategoryDto;
import io.malevich.server.transfer.FileDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;


    //    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<CategoryDto> list() {
        this.logger.info("list()");
        List<CategoryEntity> allEntries = this.categoryService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    private CategoryDto convertToDto(CategoryEntity files) {
        CategoryDto filesDto = modelMapper.map(files, CategoryDto.class);
        return filesDto;
    }

    private CategoryEntity convertToEntity(FileDto filesDto) {
        CategoryEntity files = modelMapper.map(filesDto, CategoryEntity.class);
        return files;
    }

}
