package io.malevich.server.transfer;

import com.yinyang.core.server.transfer.UserDto;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DelayedChangeDto {


    private Long id;

    private String typeId;

    private UserDto user;

    private Object payload;

    private String comment;

}
