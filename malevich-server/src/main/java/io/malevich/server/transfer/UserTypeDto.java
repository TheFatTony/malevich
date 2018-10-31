package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class UserTypeDto {

    private Long id;

    private String typeName;

    private String description;

}
