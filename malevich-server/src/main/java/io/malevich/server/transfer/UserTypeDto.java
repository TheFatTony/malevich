package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class UserTypeDto {

    private long id;

    private String typeName;

    private String description;

}
