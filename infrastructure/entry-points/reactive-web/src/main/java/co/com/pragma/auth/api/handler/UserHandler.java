package co.com.pragma.auth.api.handler;

import co.com.pragma.auth.api.dto.CreateUserRequest;
import co.com.pragma.auth.api.dto.CreateUserResponse;
import co.com.pragma.auth.model.user.User;
import co.com.pragma.auth.model.role.Role;
import co.com.pragma.auth.usecase.user.IUserUseCase;
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
public class UserHandler {

    private final IUserUseCase userUseCase;

    public UserHandler(IUserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @Operation(summary = "Create a new user", description = "Creates a new user in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = CreateUserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(CreateUserRequest.class)
                .map(dto -> User.builder()
                        .name(dto.name())
                        .lastName(dto.lastName())
                        .email(dto.email())
                        .identityDocument(dto.identityDocument())
                        .phoneNumber(dto.phoneNumber())
                        .baseSalary(dto.baseSalary())
                        .idRole(Role.builder().idRole(dto.idRole()).build())
                        .build())
                .flatMap(userUseCase::save)
                .map(user -> new CreateUserResponse(user.getId(), user.getName(), user.getEmail()))
                .flatMap(response -> ServerResponse.ok().bodyValue(response))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }
}