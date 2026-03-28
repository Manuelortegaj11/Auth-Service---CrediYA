package co.com.pragma.auth.usecase.role;

import co.com.pragma.auth.model.role.Role;
import reactor.core.publisher.Mono;

public interface IRoleUseCase {
    Mono<Role> save(Role role);
    Mono<Role> findById(Long id);
    Mono<Boolean> existsByName(String name);
}
