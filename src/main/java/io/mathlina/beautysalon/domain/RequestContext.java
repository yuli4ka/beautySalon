package io.mathlina.beautysalon.domain;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Data
public class RequestContext {
    private String repositorySwitch;
}
