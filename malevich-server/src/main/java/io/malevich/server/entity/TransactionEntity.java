package io.malevich.server.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;


@javax.persistence.Entity
@Table(name = "transactions")
public class TransactionEntity implements Entity {


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
  @Column(name = "effective_date")
  private java.sql.Timestamp effectiveDate;

  @Getter
  @Setter
  @Column(name = "party_id")
  private long partyId;

  @Getter
  @Setter
  @Column(name = "counterparty_id")
  private long counterpartyId;

  @Getter
  @Setter
  @Column(name = "artwork_id")
  private long artworkId;

  @Getter
  @Setter
  @Column(name = "amount")
  private double amount;

  @Getter
  @Setter
  @Column(name = "quantity")
  private long quantity;

}
