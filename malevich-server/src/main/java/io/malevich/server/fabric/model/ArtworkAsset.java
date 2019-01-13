package io.malevich.server.fabric.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtworkAsset extends CounterpartyParticipant {

    private String token;

    private String description;

    private Double estimatedPrice;


}
