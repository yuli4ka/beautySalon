package io.mathlina.beautysalon.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

}
