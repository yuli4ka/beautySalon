package io.mathlina.beautysalon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceModel {

    private Long id;

    private String nameEn;

    private String nameUa;

    //  duration in minutes
    private Integer duration;

    private Integer price;

    private List<Long> masterIds;

}
