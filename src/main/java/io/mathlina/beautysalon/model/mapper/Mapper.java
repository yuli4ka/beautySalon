package io.mathlina.beautysalon.model.mapper;

import io.mathlina.beautysalon.domain.Comment;
import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.domain.Service;
import io.mathlina.beautysalon.domain.Timetable;
import io.mathlina.beautysalon.model.CommentModel;
import io.mathlina.beautysalon.model.MasterModel;
import io.mathlina.beautysalon.model.ServiceModel;
import io.mathlina.beautysalon.model.TimetableModel;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper extends ConfigurableMapper {

    protected void configure(MapperFactory factory) {
        factory.classMap(Comment.class, CommentModel.class)
                .field("master.id", "masterId")
                .field("client.id", "clientId")
                .byDefault()
                .register();
        
        factory.classMap(Master.class, MasterModel.class)
                .field("services{id}", "serviceIds{}")
                .byDefault()
                .register();

        factory.classMap(Service.class, ServiceModel.class)
                .field("masters{id}", "masterIds{}")
                .byDefault()
                .register();
    }

}
