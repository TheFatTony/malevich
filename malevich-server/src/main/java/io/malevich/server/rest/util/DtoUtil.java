package io.malevich.server.rest.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class DtoUtil<E, D> {


    @Autowired
    private ModelMapper modelMapper;

    public D convertToDto(E entity, Class<D> clazz) {
        D dto = modelMapper.map(entity, clazz);
        return dto;
    }

    public E convertToEntity(D dto, Class<E> clazz) {
        E entity = modelMapper.map(dto, clazz);
        return entity;
    }


}
