package io.malevich.server.transfer;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LobStorageDto {


    private Long id;

    private Long fileId;

    private String content;

    private Long fileSize;


}
