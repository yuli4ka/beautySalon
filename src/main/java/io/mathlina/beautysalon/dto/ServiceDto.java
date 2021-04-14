package io.mathlina.beautysalon.dto;

import io.mathlina.beautysalon.domain.Service;
import lombok.Value;

@Value
public class ServiceDto {

  public ServiceDto(Service service, String localeCode) {
    this.id = service.getId();
    this.name = service.getNameByLocale(localeCode);
    this.duration = service.getDuration();
    this.price = service.getPrice();
  }

  Long id;

  String name;

  Integer duration;

  Integer price;
}
