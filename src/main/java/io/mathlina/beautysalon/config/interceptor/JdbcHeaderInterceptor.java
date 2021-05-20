package io.mathlina.beautysalon.config.interceptor;

import io.mathlina.beautysalon.domain.RequestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class JdbcHeaderInterceptor implements HandlerInterceptor {

    private final RequestContext requestContext;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String jdbcHeader = request.getHeader("jdbc");

        requestContext.setRepositorySwitch(jdbcHeader);

        return true;
    }

}
