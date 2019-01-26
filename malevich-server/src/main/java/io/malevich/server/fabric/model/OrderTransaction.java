package io.malevich.server.fabric.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderTransaction  {

    private OrderConcept order;

    private String transactionId;

    private Timestamp timestamp;


}
