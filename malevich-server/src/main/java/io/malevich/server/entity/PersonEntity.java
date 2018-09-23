package io.malevich.server.entity;

import io.malevich.server.entity.utils.JpaConverterJson;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

@javax.persistence.Entity
@Table(name = "person")
public class PersonEntity implements Entity {

  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Getter
  @Setter
  @Column(name = "full_name")
  private String fullName;

  @Getter
  @Setter
  @Convert(converter = JpaConverterJson.class)
  @Column(name = "full_name_ml")
  private Map<String, String> fullNameMl;

}
