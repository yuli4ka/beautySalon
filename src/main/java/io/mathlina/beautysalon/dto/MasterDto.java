package io.mathlina.beautysalon.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MasterDto {

  private String name;

  private String surname;

  private Float grade;

}
