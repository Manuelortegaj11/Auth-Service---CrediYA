package co.com.pragma.auth.r2dbc.repositories.user;
import co.com.pragma.auth.r2dbc.entities.UserEntity;
import co.com.pragma.auth.r2dbc.dto.UserWithRole;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserReactiveRepository extends ReactiveCrudRepository<UserEntity, Long>, ReactiveQueryByExampleExecutor<UserEntity> {

    // Buscar por email
    Mono<UserEntity> findByEmail(String email);

    // Verificar existencia por email
    Mono<Boolean> existsByEmail(String email);

    // Verificar existencia por documento de identidad
    Mono<Boolean> existsByIdentityDocument(String identityDocument);

    // Buscar usuario con role usando JOIN (DTO)
    @Query("SELECT u.id_user, u.name, u.last_name, u.email, u.identity_document, u.phone_number, " +
            "u.base_salary, u.id_role, r.name AS role_name, r.description AS role_description " +
            "FROM users u LEFT JOIN roles r ON u.id_role = r.id_role " +
            "WHERE u.id_user = :id")
    Mono<UserWithRole> findByIdWithRole(Long id);

    // Listar todos los usuarios con role usando JOIN (DTO)
    @Query("SELECT u.id_user, u.name, u.last_name, u.email, u.identity_document, u.phone_number, " +
            "u.base_salary, u.id_role, r.name AS role_name, r.description AS role_description " +
            "FROM users u LEFT JOIN roles r ON u.id_role = r.id_role")
    Flux<UserWithRole> findAllWithRole();
}

