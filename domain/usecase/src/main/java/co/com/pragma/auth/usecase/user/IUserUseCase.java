package co.com.pragma.auth.usecase.user;

import co.com.pragma.auth.model.user.User;
import reactor.core.publisher.Mono;

public interface IUserUseCase {
    Mono<User> save(User user);
    Mono<User> findById(Long id);
    Mono<Boolean> existsByEmail(String email);
    Mono<Boolean> existsByIdentityDocument(String identityDocument);
}
