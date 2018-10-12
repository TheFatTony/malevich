package io.malevich.server.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;


@javax.persistence.Entity
@Table(name = "trade_type")
public class TradeTypeEntity implements Entity {


  @Getter
  @Setter
  @Column(name = "id")
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
