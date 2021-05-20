package io.mathlina.beautysalon.model.mapper;

import io.mathlina.beautysalon.domain.*;
import io.mathlina.beautysalon.dto.ServiceDto;
import io.mathlina.beautysalon.dto.UserRegistrationDto;
import io.mathlina.beautysalon.exception.UnsupportedLocale;
import io.mathlina.beautysalon.model.*;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.context.i18n.LocaleContextHolder;
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

        factory.classMap(ServiceModel.class, ServiceDto.class)
                .byDefault()
                .customize(new CustomMapper<ServiceModel, ServiceDto>() {
                    @Override
                    public void mapAtoB(ServiceModel serviceModel, ServiceDto serviceDto, MappingContext context) {
                        switch (LocaleContextHolder.getLocale().toString()) {
                            case "uk_UA":
                                serviceDto.setName(serviceModel.getNameUa());
                                break;
                            case "en":
                                serviceDto.setName(serviceModel.getNameEn());
                                break;
                            default:
                                throw new UnsupportedLocale("Unsupported Locale");
                        }
                    }
                })
                .register();

        factory.classMap(Service.class, ServiceDto.class)
                .byDefault()
                .customize(new CustomMapper<Service, ServiceDto>() {
                    @Override
                    public void mapAtoB(Service service, ServiceDto serviceDto, MappingContext context) {
                        switch (LocaleContextHolder.getLocale().toString()) {
                            case "uk_UA":
                                serviceDto.setName(service.getNameUa());
                                break;
                            case "en":
                                serviceDto.setName(service.getNameEn());
                                break;
                            default:
                                throw new UnsupportedLocale("Unsupported Locale");
                        }
                    }
                })
                .register();
    }

}
