package co.com.pragma.auth.api;

import co.com.pragma.auth.api.handler.RoleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoleRouterDetailed {

    @Bean
    public RouterFunction<ServerResponse> roleRoutes(RoleHandler handler) {
        return route(POST("/api/roles"), handler::createRole);
    }
}
