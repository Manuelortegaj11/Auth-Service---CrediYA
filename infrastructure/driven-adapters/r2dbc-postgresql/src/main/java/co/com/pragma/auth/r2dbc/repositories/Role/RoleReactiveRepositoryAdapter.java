package co.com.pragma.auth.r2dbc.repositories.Role;
import co.com.pragma.auth.model.role.Role;
import co.com.pragma.auth.model.role.gateways.RoleRepository;
import co.com.pragma.auth.r2dbc.entities.RoleEntity;
import co.com.pragma.auth.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Repository
public class RoleReactiveRepositoryAdapter
        extends ReactiveAdapterOperations<
        Role,
        RoleEntity,
        Long,
        RoleReactiveRepository>
        implements RoleRepository {

    private final TransactionalOperator transactionalOperator;

    public RoleReactiveRepositoryAdapter(RoleReactiveRepository repository,
                                         ObjectMapper mapper,
                                         TransactionalOperator transactionalOperator) {
        super(repository, mapper, d -> mapper.map(d, Role.class));
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Mono<Role> save(Role role) {
        return super.save(role).as(transactionalOperator::transactional);
    }

    @Override
    public Mono<Role> findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return repository.existsByName(name);
    }
}
