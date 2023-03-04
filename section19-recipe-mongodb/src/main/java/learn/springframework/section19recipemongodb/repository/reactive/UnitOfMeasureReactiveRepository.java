package learn.springframework.section19recipemongodb.repository.reactive;

import learn.springframework.section19recipemongodb.domain.documents.UnitOfMeasure;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UnitOfMeasureReactiveRepository extends ReactiveMongoRepository<UnitOfMeasure, String> {

    Mono<UnitOfMeasure> findByDescription(String description);

    default Mono<Boolean> isEmpty() {
        return count().map(cnt -> cnt == 0);
    }
}
