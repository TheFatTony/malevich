package io.malevich.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.malevich.server.core.dto.DTOModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.persistence.EntityManager;
import java.util.List;

@Configuration
public class WebMvcConfig {
//    extends WebMvcConfigurerAdapter {

//    private ObjectMapper objectMapper;
//
//    private ModelMapper modelMapper;
//
//    @Autowired
//    public WebMvcConfig(ObjectMapper objectMapper, ModelMapper modelMapper) {
//        this.objectMapper = objectMapper;
//        this.modelMapper = modelMapper;
//    }
//
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        super.addArgumentResolvers(argumentResolvers);
//        argumentResolvers.add(new DTOModelMapper(objectMapper, modelMapper));
//    }
}
