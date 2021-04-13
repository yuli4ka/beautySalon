package io.mathlina.beautysalon.dto;

import io.mathlina.beautysalon.domain.Master;
import lombok.Value;

@Value
public class MasterDto {

  public MasterDto(Master master) {
    this.id = master.getId();
    this.name = master.getName();
    this.surname = master.getSurname();
    this.grade = master.getGrade();
  }

  Long id;

  String name;

  String surname;

  double grade;

}
