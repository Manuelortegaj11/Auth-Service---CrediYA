package co.com.pragma.auth.model.role.gateways;
import co.com.pragma.auth.model.role.Role;
import reactor.core.publisher.Mono;

public interface RoleRepository {
    Mono<Role> save(Role role);
    Mono<Role> findById(Long idRole);
    Mono<Boolean> existsByName(String name);
}