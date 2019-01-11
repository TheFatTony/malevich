package io.malevich.server.domain;

import com.yinyang.core.server.core.jpa.JpaConverterJson;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Map;


@javax.persistence.Entity
@Table(name = "order_status")
public class OrderStatusEntity extends AbstractPersistable<String> {



    @Getter
    @Setter
    @Convert(converter = JpaConverterJson.class)
    @Column(name = "name_ml")
    private Map<String, String> nameMl;

}
