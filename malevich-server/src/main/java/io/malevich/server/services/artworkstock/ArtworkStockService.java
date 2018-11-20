package io.malevich.server.services.artworkstock;

import io.malevich.server.domain.ArtworkStockEntity;
import io.malevich.server.transfer.FilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ArtworkStockService {

    List<ArtworkStockEntity> findAll();

    void add(ArtworkStockEntity artworkStockEntity);

    void delete(long id);

    ArtworkStockEntity find(long id);

    Page<ArtworkStockEntity> filterStocks(Pageable pageable, FilterDto filterDto);
}
