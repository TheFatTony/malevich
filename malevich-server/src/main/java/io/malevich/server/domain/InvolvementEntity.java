package io.malevich.server.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@EqualsAndHashCode
public class InvolvementEntity {

    @Getter
    @Setter
    private Double galleries;

    @Getter
    @Setter
    private Double members;

    @Getter
    @Setter
    private Double artworks;

    @Getter
    @Setter
    private Double artValue;

}
