package co.com.pragma.auth.usecase.role;
import co.com.pragma.auth.model.role.Role;
import co.com.pragma.auth.model.role.gateways.RoleRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RoleUseCase implements IRoleUseCase {

    private final RoleRepository roleRepository;

    @Override
    public Mono<Role> save(Role role) {
        return roleRepository.existsByName(role.getName())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new RuntimeException("El rol ya existe"));
                    }
                    return roleRepository.save(role);
                });
    }

    @Override
    public Mono<Role> findById(Long idRole) {
        return roleRepository.findById(idRole);
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return roleRepository.existsByName(name);
    }
}
