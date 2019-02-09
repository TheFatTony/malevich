package io.malevich.server.domain;

import com.yinyang.core.server.domain.YAbstractPersistable;
import io.malevich.server.domain.enums.KycLevel;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;


@javax.persistence.Entity
@Table(name = "kyc_level")
public class KycLevelEntity extends YAbstractPersistable<String> {

  @Getter
  @Setter
  @Column(name = "value")
  private long value;

}
