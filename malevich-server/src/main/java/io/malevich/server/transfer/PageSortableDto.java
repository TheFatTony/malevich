package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageSortableDto extends PageableDto {

    private int sort;
}
