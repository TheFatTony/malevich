package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class InvolvementDto {

    private Double galleries;

    private Double members;

    private Double artworks;

    private Double artValue;

}
