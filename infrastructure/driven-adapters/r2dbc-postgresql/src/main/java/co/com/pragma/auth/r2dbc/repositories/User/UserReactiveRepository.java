package co.com.pragma.auth.r2dbc.repositories.User;
import co.com.pragma.auth.r2dbc.entities.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserReactiveRepository extends ReactiveCrudRepository<UserEntity, Long>, ReactiveQueryByExampleExecutor<UserEntity> {

    Mono<UserEntity> findByEmail(String email);

    Mono<Boolean> existsByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM users WHERE identity_document = :identityDocument")
    Mono<Boolean> existsByIdentityDocument(String identityDocument);

    @Query("SELECT u.*, r.id as role_id, r.name as role_name, r.description as role_description " +
            "FROM users u LEFT JOIN roles r ON u.id_role = r.id WHERE u.id = :id")
    Mono<UserEntity> findByIdWithRole(Long id);

    @Query("SELECT u.*, r.id as role_id, r.name as role_name, r.description as role_description " +
            "FROM users u LEFT JOIN roles r ON u.id_role = r.id")
    Flux<UserEntity> findAllWithRole();
}