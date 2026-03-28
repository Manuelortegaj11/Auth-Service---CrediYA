package co.com.pragma.auth.usecase.user;

import co.com.pragma.auth.model.user.User;
import co.com.pragma.auth.model.user.gateways.UserRepository;
import co.com.pragma.auth.model.role.gateways.RoleRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase implements IUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public Mono<User> save(User user) {
        return userRepository.existsByEmail(user.getEmail())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new RuntimeException("El correo ya existe"));
                    }
                    return userRepository.existsByIdentityDocument(user.getIdentityDocument());
                })
                .flatMap(docExists -> {
                    if (docExists) {
                        return Mono.error(new RuntimeException("El documento ya existe"));
                    }
                    return roleRepository.findById(user.getIdRole().getIdRole());
                })
                .flatMap(role -> {
                    if (role == null) {
                        return Mono.error(new RuntimeException("El rol especificado no existe"));
                    }
                    return userRepository.save(user);
                });
    }

    @Override
    public Mono<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Mono<Boolean> existsByIdentityDocument(String identityDocument) {
        return userRepository.existsByIdentityDocument(identityDocument);
    }
}