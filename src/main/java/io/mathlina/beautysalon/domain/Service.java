package io.mathlina.beautysalon.domain;

import java.time.Duration;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
public class Service {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "service_id")
  private Long id;

  private String nameEn;

  private String nameUa;

  private Duration duration;

  private Integer price;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "services")
  private List<Master> masters;

}
