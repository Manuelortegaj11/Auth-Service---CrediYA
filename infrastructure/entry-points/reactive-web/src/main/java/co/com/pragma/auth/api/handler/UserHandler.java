package co.com.pragma.auth.api.handler;

import co.com.pragma.auth.api.dto.CreateUserRequest;
import co.com.pragma.auth.api.dto.CreateUserResponse;
import co.com.pragma.auth.model.user.User;
import co.com.pragma.auth.model.role.Role;
import co.com.pragma.auth.usecase.user.IUserUseCase;
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

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(CreateUserRequest.class)
                .map(dto -> User.builder()
                        .name(dto.name())
                        .lastName(dto.lastName())
                        .email(dto.email())
                        .identityDocument(dto.identityDocument())
                        .phoneNumber(dto.phoneNumber())
                        .baseSalary(dto.baseSalary())
                        .idRole(Role.builder().idRole(dto.idRole()).build()) // Solo necesitamos el ID del rol
                        .build())
                .flatMap(userUseCase::save)
                .map(user -> new CreateUserResponse(user.getId(), user.getName(), user.getEmail()))
                .flatMap(response -> ServerResponse.ok().bodyValue(response))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }
}