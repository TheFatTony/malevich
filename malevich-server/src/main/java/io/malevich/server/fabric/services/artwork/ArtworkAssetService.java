package io.malevich.server.fabric.services.artwork;

import com.yinyang.core.server.fabric.GenericComposerService;
import io.malevich.server.domain.ArtworkEntity;
import org.springframework.stereotype.Service;

@Service
public interface ArtworkAssetService extends GenericComposerService<ArtworkEntity> {

}
