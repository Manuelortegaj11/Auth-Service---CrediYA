package co.com.pragma.auth.api;

import co.com.pragma.auth.api.handler.UserHandler;
import co.com.pragma.auth.api.handler.RoleHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterRest {

    private final RouterFunction<ServerResponse> userRoutes;
    private final RouterFunction<ServerResponse> roleRoutes;

    public RouterRest(
            @Qualifier("userRoutes") RouterFunction<ServerResponse> userRoutes,
            @Qualifier("roleRoutes") RouterFunction<ServerResponse> roleRoutes) {
        this.userRoutes = userRoutes;
        this.roleRoutes = roleRoutes;
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return userRoutes.andOther(roleRoutes);
    }
}
