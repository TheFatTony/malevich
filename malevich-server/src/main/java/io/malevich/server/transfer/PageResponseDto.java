package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResponseDto {

    private List<ArtworkStockDto> stockDtoCopies;

    private Long totalElements;

    private int totalPages;

    PageResponseDto() {
    }

    public PageResponseDto(List<ArtworkStockDto> stockDtoCopies, Long totalElements, int totalPages) {
        this.stockDtoCopies = stockDtoCopies;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
