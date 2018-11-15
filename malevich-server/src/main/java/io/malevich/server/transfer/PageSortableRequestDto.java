package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageSortableRequestDto extends PageResponseDto {

    private int page;

    private int size;

    private int sort;
}
