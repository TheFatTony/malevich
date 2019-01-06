package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TraderPersonDto extends ParticipantDto {

    private PersonDto person;

}