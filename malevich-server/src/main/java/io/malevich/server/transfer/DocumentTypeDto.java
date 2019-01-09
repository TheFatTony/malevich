package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter
public class DocumentTypeDto {

    private Long id;

    private Map<String, String> nameMl;

    private String userType;

}
