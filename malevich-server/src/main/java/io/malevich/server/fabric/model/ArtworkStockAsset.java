package io.malevich.server.fabric.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtworkStockAsset extends CounterpartyParticipant {

    private String id;

    private String artwork;

    private String holder;

    private String owner;

}
