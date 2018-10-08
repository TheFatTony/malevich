package io.malevich.server.transfer;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LobStorageDto {


    private long id;

    private long fileId;

    private String content;

    private long fileSize;


}
