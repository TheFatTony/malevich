package io.malevich.server.transfer;


import lombok.Getter;
import lombok.Setter;

public class LobStorageDto {


    @Getter @Setter
    private long id;

    @Getter @Setter
    private long fileId;

    @Getter @Setter
    private String content;

    @Getter @Setter
    private long fileSize;


}
