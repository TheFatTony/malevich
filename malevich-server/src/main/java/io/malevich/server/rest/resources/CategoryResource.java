package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.CategoryEntity;
import io.malevich.server.services.category.CategoryService;
import io.malevich.server.transfer.CategoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/categories")
public class CategoryResource extends RestResource<CategoryDto, CategoryEntity> {

    @Autowired
    private CategoryService categoryService;

    public CategoryResource() {
        super(CategoryDto.class, CategoryEntity.class);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CategoryDto> list() {
        List<CategoryEntity> allEntries = this.categoryService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

}
