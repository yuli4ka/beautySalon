package io.mathlina.beautysalon.domain;

import io.mathlina.beautysalon.exception.UnsupportedLocale;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.ToString.Exclude;

@Data
@Entity
public class Service {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "service_id")
  private Long id;

  private String nameEn;

  private String nameUa;

//  duration in minutes
  private Integer duration;

  private Integer price;

  @Exclude
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "services")
  private List<Master> masters;

  public String getNameByLocale(String localeCode) {
    switch (localeCode){
      case "uk_UA": return nameUa;
      case "en": return nameEn;
      default: throw new UnsupportedLocale("Unsupported Locale");
    }
  }

}
