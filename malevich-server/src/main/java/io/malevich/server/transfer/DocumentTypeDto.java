package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;


@Getter
@Setter
public class DocumentTypeDto {

    private String id;

    private Map<String, String> nameMl;

    private List<ParticipantTypeDto> participantTypes;

}
