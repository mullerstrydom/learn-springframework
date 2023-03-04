package learn.springframework.section19recipemongodb.domain.documents;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Note {

    @Id
    private String id;
    private String note;

    @DBRef
    private Recipe recipe;
}
