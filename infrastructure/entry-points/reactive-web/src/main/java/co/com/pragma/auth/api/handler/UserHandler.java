package co.com.pragma.auth.api.handler;

import co.com.pragma.auth.api.dto.CreateUserRequest;
import co.com.pragma.auth.api.dto.CreateUserResponse;
import co.com.pragma.auth.model.user.User;
import co.com.pragma.auth.model.role.Role; // Agrega esta importación
import co.com.pragma.auth.r2dbc.repositories.user.UserReactiveRepositoryAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    private final UserReactiveRepositoryAdapter userRepository;

    public UserHandler(UserReactiveRepositoryAdapter userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(CreateUserRequest.class)
                .flatMap(dto -> {
                    User user = new User();
                    user.setName(dto.name());
                    user.setLastName(dto.lastName());
                    user.setEmail(dto.email());
                    user.setIdentityDocument(dto.identityDocument());
                    user.setPhoneNumber(dto.phoneNumber());
                    user.setBaseSalary(dto.baseSalary());
                    // Crear un objeto Role con el ID del DTO
                    Role role = new Role();
                    role.setIdRole(dto.idRole()); // Asumiendo que Role tiene un setter setIdRole
                    user.setIdRole(role);

                    return userRepository.save(user)
                            .map(saved -> new CreateUserResponse(
                                    saved.getId(), // Corrige getIdUser() a getId()
                                    saved.getName(),
                                    saved.getEmail()
                            ));
                })
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }
}