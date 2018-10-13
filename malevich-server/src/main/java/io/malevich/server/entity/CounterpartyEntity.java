package io.malevich.server.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;


@javax.persistence.Entity
@Table(name = "counterparty")
public class CounterpartyEntity implements Entity {


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
  @Column(name = "trader_id")
  private long traderId;

  @Getter
  @Setter
  @Column(name = "gallery_id")
  private long galleryId;

}
