package learn.springframework.section19recipemongodb.repository.reactive;

import learn.springframework.section19recipemongodb.domain.documents.Note;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface NoteReactiveRepository extends ReactiveMongoRepository<Note, String> {

}
