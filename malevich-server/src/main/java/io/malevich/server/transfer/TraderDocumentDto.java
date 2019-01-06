package io.malevich.server.transfer;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TraderDocumentDto {

    private Long id;

    private DocumentDto document;

    private TraderPersonDto trader;

}
