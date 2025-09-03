package co.com.pragma.auth.api.handler;

import co.com.pragma.auth.api.dto.CreateRoleRequest;
import co.com.pragma.auth.api.dto.CreateRoleResponse;
import co.com.pragma.auth.model.role.Role;
import co.com.pragma.auth.r2dbc.repositories.role.RoleReactiveRepositoryAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class RoleHandler {

    private final RoleReactiveRepositoryAdapter roleRepository;

    public RoleHandler(RoleReactiveRepositoryAdapter roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Mono<ServerResponse> createRole(ServerRequest request) {
        return request.bodyToMono(CreateRoleRequest.class)
                .flatMap(dto -> {
                    Role role = new Role();
                    role.setName(dto.name());
                    role.setDescription(dto.description());

                    return roleRepository.save(role)
                            .map(saved -> new CreateRoleResponse(
                                    saved.getIdRole(),
                                    saved.getName(),
                                    saved.getDescription()
                            ));
                })
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }
}
