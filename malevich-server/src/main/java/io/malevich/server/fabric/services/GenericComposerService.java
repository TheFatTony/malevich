package io.malevich.server.fabric.services;

import org.springframework.stereotype.Service;

@Service
public interface GenericComposerService<T> {

    void submit(T entity);

}
