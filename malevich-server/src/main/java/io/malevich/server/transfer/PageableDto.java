package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageableDto {

    private int page;

    private int size;

    private List<ArtworkStockDto> stockDtoCopies;

    private Long totalElements;

    private int totalPages;

    PageableDto() {
    }

    public PageableDto(List<ArtworkStockDto> stockDtoCopies, Long totalElements, int totalPages) {
        this.stockDtoCopies = stockDtoCopies;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
