package io.mathlina.beautysalon.model.mapper;

import io.mathlina.beautysalon.domain.*;
import io.mathlina.beautysalon.dto.UserRegistrationDto;
import io.mathlina.beautysalon.model.*;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class Mapper extends ConfigurableMapper {

    private final PasswordEncoder passwordEncoder;

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

        factory.classMap(UserRegistrationDto.class, UserModel.class)
                .byDefault()
                .customize(new CustomMapper<UserRegistrationDto, UserModel>() {
                    @Override
                    public void mapAtoB(UserRegistrationDto userDTO, UserModel userModel, MappingContext context) {
                        userModel.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                        userModel.setActive(false);
                        userModel.setRole(Collections.singleton(Role.CLIENT));
                        userModel.setActivationCode(UUID.randomUUID().toString());
                    }
                })
                .register();
    }

}
