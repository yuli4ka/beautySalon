package io.mathlina.beautysalon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimetableModel {

    private Long id;

    private UserModel client;

    private MasterModel master;

    private ServiceModel service;

    private LocalDateTime dateTime;

    private Boolean done;

    private boolean paid;

}
