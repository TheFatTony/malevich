package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageSortableRequestDto {

    private int page;

    private int size;

    private String sort;
}
