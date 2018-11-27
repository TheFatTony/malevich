package io.malevich.server.repositories.artworkstock.filter;

import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.transfer.FilterDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class FilterSpecificationImpl implements Specification<ArtworkStockEntity> {

    private FilterDto filterDto;

    public 

    @Nullable
    @Override
    public Predicate toPredicate(Root<ArtworkStockEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
