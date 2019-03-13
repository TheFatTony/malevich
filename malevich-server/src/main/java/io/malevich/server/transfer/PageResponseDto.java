package io.malevich.server.transfer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class PageResponseDto {

    private List<?> data;

    private Long totalElements;

    private int totalPages;

    private String sortBy;

    private int number;

    private int size;
}
