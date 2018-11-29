package io.malevich.server.transfer;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
public class DocumentDto {

    private Long id;

    private String fileName;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Timestamp effectiveDate;

    private DocumentTypeDto documentType;

    private byte[] content;

}
