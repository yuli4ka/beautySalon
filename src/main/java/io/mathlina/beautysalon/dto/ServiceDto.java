package io.mathlina.beautysalon.dto;

import io.mathlina.beautysalon.domain.Service;
import java.time.Duration;
import java.util.Locale;
import lombok.Value;

@Value
public class ServiceDto {

  public ServiceDto(Service service, String localeCode) {
    this.id = service.getId();
    //TODO: get name by current locale
    this.name = service.getNameByLocale(localeCode);
    this.duration = service.getDuration();
    this.price = service.getPrice();
  }

  Long id;

  String name;

  Duration duration;

  Integer price;
}
