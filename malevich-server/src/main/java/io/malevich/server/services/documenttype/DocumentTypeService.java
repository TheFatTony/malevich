package io.malevich.server.services.documenttype;

import io.malevich.server.domain.DocumentTypeEntity;

import java.util.List;

public interface DocumentTypeService {

    List<DocumentTypeEntity> findByUserType(String userType);
}
