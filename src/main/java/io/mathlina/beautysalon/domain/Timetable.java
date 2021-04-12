package io.mathlina.beautysalon.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Timetable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  private User client;

  @ManyToOne
  private Master master;

  @ManyToOne
  private Service service;

  private LocalDateTime dateTime;

  private Boolean done;

  private boolean paid;

}
