package io.mathlina.beautysalon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentModel {

  private Long id;

  private Long masterId;

  private Long clientId;

  private Byte grade;

  private String text;

}
