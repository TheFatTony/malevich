package io.malevich.server.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;


@javax.persistence.Entity
@Table(name = "reset_password_token")
public class ResetPasswordTokenEntity implements Entity {


  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Getter
  @Setter
  @Column(name = "token")
  private String token;

  @Getter
  @Setter
  @Fetch(FetchMode.JOIN)
  @ManyToOne()
  private UserEntity user;

  @Getter
  @Setter
  @Column(name = "effective_date")
  private java.sql.Timestamp effectiveDate;

}
