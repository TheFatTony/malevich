package io.malevich.server.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;


@javax.persistence.Entity
@Table(name = "counterparty")
public class CounterpartyEntity implements Entity {

  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Getter
  @Setter
  @Fetch(FetchMode.JOIN)
  @ManyToOne(cascade = CascadeType.MERGE)
  private CounterpartyTypeEntity type;

  @Getter
  @Setter
  @Fetch(FetchMode.JOIN)
  @OneToOne(cascade = CascadeType.MERGE)
  private TraderEntity trader;

  @Getter
  @Setter
  @Fetch(FetchMode.JOIN)
  @OneToOne(cascade = CascadeType.MERGE)
  private GalleryEntity gallery;

}
