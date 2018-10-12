package io.malevich.server.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;


@javax.persistence.Entity
@Table(name = "orders")
public class OrderEntity implements Entity {


  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Getter
  @Setter
  @Column(name = "type_id")
  private String typeId;

  @Getter
  @Setter
  @Column(name = "artwork_id")
  private long artworkId;

  @Getter
  @Setter
  @Column(name = "party_id")
  private long partyId;

  @Getter
  @Setter
  @Column(name = "trade_type_id")
  private String tradeTypeId;

  @Getter
  @Setter
  @Column(name = "amount")
  private double amount;

  @Getter
  @Setter
  @Column(name = "effective_date")
  private java.sql.Timestamp effectiveDate;

  @Getter
  @Setter
  @Column(name = "transaction_id")
  private long transactionId;

}
