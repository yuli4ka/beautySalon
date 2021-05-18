package io.mathlina.beautysalon.model.mapper;

import io.mathlina.beautysalon.domain.Comment;
import io.mathlina.beautysalon.model.CommentModel;
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
    }
}
