package io.malevich.server.domain;

import com.yinyang.core.server.domain.YAbstractPersistable;
import io.malevich.server.aop.KycRequiredFor;
import io.malevich.server.domain.enums.KycLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;


@javax.persistence.Entity
@Table(name = "person")
public class PersonEntity extends YAbstractPersistable<Long> {

    @Getter
    @Setter
    @Column(name = "first_name")
    @KycRequiredFor(levels = {KycLevel.T_TIER1})
    private String firstName;

    @Getter
    @Setter
    @Column(name = "last_name")
    @KycRequiredFor(levels = {KycLevel.T_TIER1})
    private String lastName;

    @Getter
    @Setter
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    @KycRequiredFor(levels = {KycLevel.T_TIER1})
    private GenderEntity gender;

    @Getter
    @Setter
    @Column(name = "date_of_birth")
    @KycRequiredFor(levels = {KycLevel.T_TIER1})
    private Timestamp dateOfBirth;

}
