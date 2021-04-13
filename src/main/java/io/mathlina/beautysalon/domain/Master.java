package io.mathlina.beautysalon.domain;

import io.mathlina.beautysalon.dto.ServiceDto;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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

  @Exclude
  private List<Service> services;

}
