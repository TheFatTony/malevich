package io.malevich.server.repositories.artworkstock.filter;

import io.malevich.server.domain.ArtworkEntity;
import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.domain.enums.SortEnum;
import io.malevich.server.transfer.FilterDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.persistence.Transient;
import javax.persistence.criteria.*;

public class FilterSpecification implements Specification<ArtworkStockEntity> {

    @Transient
    private FilterDto filterDto;

    public FilterSpecification(FilterDto filterDto) {
        super();
        this.filterDto = filterDto;
    }

    @Nullable
    @Override
    public Predicate toPredicate(Root<ArtworkStockEntity> artworkStock, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        Join<ArtworkStockEntity, ArtworkEntity> artwork = artworkStock.join("artwork");
        artwork.join("category");
        artwork.join("thumbnail");
        artwork.join("image");

        Predicate clause = null;

        if (filterDto.getSort().equalsIgnoreCase(SortEnum.NAME.getValue())) {
            criteriaQuery.orderBy(criteriaBuilder.asc(artwork.get("titleMl")));
        }
        if (filterDto.getSort().equalsIgnoreCase(SortEnum.PLTH.getValue())) {
            criteriaQuery.orderBy(criteriaBuilder.asc(criteriaBuilder.coalesce(artworkStock.get("instantPrice"), artworkStock.get("lastPrice"))));
        }
        if (filterDto.getSort().equalsIgnoreCase(SortEnum.PHTL.getValue())) {
            criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.coalesce(artworkStock.get("instantPrice"), artworkStock.get("lastPrice"))));
        }
        if (filterDto.getCategoryId() != 0) {
            clause = criteriaBuilder.and(criteriaBuilder.equal(artwork.get("category"), filterDto.getCategoryId()));
        }
        return clause;
    }
}
