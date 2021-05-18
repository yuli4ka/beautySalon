package io.mathlina.beautysalon.dto;

import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.model.MasterModel;
import lombok.Value;

@Value
public class MasterDto {

    //TODO: use mapping
    public MasterDto(MasterModel master) {
        this.id = master.getId();
        this.name = master.getName();
        this.surname = master.getSurname();
        this.grade = master.getGrade();
    }

    //TODO: delete
    public MasterDto(Master master) {
        this.id = master.getId();
        this.name = master.getName();
        this.surname = master.getSurname();
        this.grade = master.getGrade();
    }

    Long id;

    String name;

    String surname;

    double grade;

}
