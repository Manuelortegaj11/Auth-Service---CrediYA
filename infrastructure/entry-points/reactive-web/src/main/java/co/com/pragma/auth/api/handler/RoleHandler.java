package co.com.pragma.auth.api.handler;

import co.com.pragma.auth.api.dto.CreateRoleRequest;
import co.com.pragma.auth.api.dto.CreateRoleResponse;
import co.com.pragma.auth.model.role.Role;
import co.com.pragma.auth.usecase.role.IRoleUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class RoleHandler {

    private final IRoleUseCase roleUseCase;

    public RoleHandler(IRoleUseCase roleUseCase) {
        this.roleUseCase = roleUseCase;
    }

    @Operation(summary = "Create a new role", description = "Creates a new role in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role created successfully",
                    content = @Content(schema = @Schema(implementation = CreateRoleResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public Mono<ServerResponse> createRole(ServerRequest request) {
        return request.bodyToMono(CreateRoleRequest.class)
                .map(dto -> Role.builder()
                        .name(dto.name())
                        .description(dto.description())
                        .build())
                .flatMap(roleUseCase::save)
                .map(role -> new CreateRoleResponse(role.getIdRole(), role.getName(), role.getDescription()))
                .flatMap(response -> ServerResponse.ok().bodyValue(response))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }
}