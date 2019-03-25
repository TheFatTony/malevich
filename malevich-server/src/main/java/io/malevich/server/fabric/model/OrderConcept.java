package io.malevich.server.fabric.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderConcept {

    private String id;

    private Double amount;

    private String orderType;

    private String orderStatus;

    private String artworkStock;

    private String counterparty;


}
