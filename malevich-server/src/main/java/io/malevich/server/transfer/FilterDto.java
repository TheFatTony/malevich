package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterDto extends PageRequestDto {

    private long categoryId;

    private double minPrice;

    private double maxPrice;
}
