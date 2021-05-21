package io.mathlina.beautysalon.service.impl;

import io.mathlina.beautysalon.model.ServiceModel;
import io.mathlina.beautysalon.service.MyServiceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class MyServiceServiceImplIntegrationTest {

    private static final Long ID_1 = 1L;
    private static final String MANICURE_UA = "манікюр";
    private static final String MANICURE_EN = "manicure";
    private static final int PRICE = 350;
    private static final int DURATION = 90;

    @Autowired
    private MyServiceService myServiceService;

    @Test
    public void findByIdShouldReturnServiceModel() {
        ServiceModel expected = ServiceModel.builder()
                .id(ID_1)
                .nameUa(MANICURE_UA)
                .nameEn(MANICURE_EN)
                .price(PRICE)
                .duration(DURATION)
                .build();

        Optional<ServiceModel> actualServiceModelOptional = myServiceService.findById(ID_1);

        if (actualServiceModelOptional.isEmpty()) {
            fail();
        } else {
            ServiceModel actualServiceModel = actualServiceModelOptional.get();
            assertEquals(expected, actualServiceModel);
        }

    }


}