package io.mathlina.beautysalon.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
public class Master extends User {

  private double grade;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "master_services",
      joinColumns = { @JoinColumn(name = "user_id") },
      inverseJoinColumns = { @JoinColumn(name = "service_id") }
  )

  @Exclude
  private List<Service> services;

}
