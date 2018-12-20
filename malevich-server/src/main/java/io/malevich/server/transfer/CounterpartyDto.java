package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CounterpartyDto {

    private Long id;

    private String typeId;

    private UserDto user;

    private TraderDto trader;

    private GalleryDto gallery;

}
