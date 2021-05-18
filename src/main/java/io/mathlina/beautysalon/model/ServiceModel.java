package io.mathlina.beautysalon.model;

import io.mathlina.beautysalon.exception.UnsupportedLocale;
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

    public String getNameByLocale(String localeCode) {
        switch (localeCode) {
            case "uk_UA":
                return nameUa;
            case "en":
                return nameEn;
            default:
                throw new UnsupportedLocale("Unsupported Locale");
        }
    }

}
