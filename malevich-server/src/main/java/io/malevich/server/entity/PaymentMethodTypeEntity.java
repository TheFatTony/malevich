package io.malevich.server.entity;

import io.malevich.server.entity.utils.JpaConverterJson;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Map;


@javax.persistence.Entity
@Table(name = "payment_method_type")
public class PaymentMethodTypeEntity implements Entity {


  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  @Getter
  @Setter
  @Convert(converter = JpaConverterJson.class)
  @Column(name = "name_ml")
  private Map<String, String> nameMl;

}
