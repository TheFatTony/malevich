package io.malevich.server.transfer;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AccountStateDto {


    private Long id;

    private ParticipantDto participant;

    private ArtworkStockDto artworkStock;

    private Long quantity;

    private Double amount;

}
