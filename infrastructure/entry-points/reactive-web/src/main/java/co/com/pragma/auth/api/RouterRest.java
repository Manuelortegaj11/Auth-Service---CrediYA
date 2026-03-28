package co.com.pragma.auth.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Configuration
public class RouterRest {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(UserRouterDetailed userRouterDetailed, RoleRouterDetailed roleRouterDetailed) {
        return RouterFunctions.route()
                .nest(path("/api"), builder -> {
                    builder.add(userRouterDetailed.userRoutes(null)); // El handler se inyectará automáticamente
                    builder.add(roleRouterDetailed.roleRoutes(null)); // El handler se inyectará automáticamente
                })
                .build();
    }
}