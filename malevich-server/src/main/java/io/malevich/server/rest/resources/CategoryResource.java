package io.malevich.server.rest.resources;

import com.yinyang.core.server.transfer.FileDto;
import io.malevich.server.domain.CategoryEntity;
import io.malevich.server.services.category.CategoryService;
import io.malevich.server.transfer.CategoryDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;


    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CategoryDto> list() {
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
