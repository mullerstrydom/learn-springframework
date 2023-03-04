package learn.springframework.section19recipemongodb.repository.reactive;

import learn.springframework.section19recipemongodb.domain.documents.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String> {

    Mono<Category> findByDescription(String description);

    default Mono<Boolean> isEmpty() {
        return count().map(cnt -> cnt == 0);
    }
}
