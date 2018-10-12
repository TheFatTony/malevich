package io.malevich.server.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;


@javax.persistence.Entity
@Table(name = "counterparty_type")
public class CounterpartyTypeEntity implements Entity {


  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  @Getter
  @Setter
  @Column(name = "name")
  private String name;

  @Getter
  @Setter
  @Column(name = "name_ml")
  private String nameMl;

}
