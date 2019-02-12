package io.malevich.server.domain;

import com.yinyang.core.server.core.jpa.JpaConverterJson;
import com.yinyang.core.server.domain.YAbstractPersistable;
import io.malevich.server.aop.KycRequiredFor;
import io.malevich.server.domain.enums.KycLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Map;


@javax.persistence.Entity
@Table(name = "organization")
public class OrganizationEntity extends YAbstractPersistable<Long> {


    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "legal_name_ml")
    @KycRequiredFor(levels = {KycLevel.T_TIER1, KycLevel.G_TIER1})
    private Map<String, String> legalNameMl;


}
