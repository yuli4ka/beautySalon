package io.mathlina.beautysalon.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Master extends User {

  private Float grade;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "master_services",
      joinColumns = { @JoinColumn(name = "user_id") },
      inverseJoinColumns = { @JoinColumn(name = "service_id") }
  )
  private List<Service> services;

}
