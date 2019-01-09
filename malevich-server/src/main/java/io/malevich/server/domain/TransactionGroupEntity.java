package io.malevich.server.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import com.yinyang.core.server.domain.Entity;

@EqualsAndHashCode
@javax.persistence.Entity
@Table(name = "transaction_group")
public class TransactionGroupEntity implements Entity {


  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Getter
  @Setter
  @Column(name = "type")
  private String type;

}
