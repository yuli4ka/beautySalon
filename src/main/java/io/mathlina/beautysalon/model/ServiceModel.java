package io.mathlina.beautysalon.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "masterIds")
public class ServiceModel {

    private Long id;

    private String nameEn;

    private String nameUa;

    //  duration in minutes
    private Integer duration;

    private Integer price;

    private List<Long> masterIds;
}
