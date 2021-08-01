package learn.jpa.model;

import lombok.*;
import org.springframework.data.domain.Page;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@ToString @Getter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends BaseEntity {
    @NonNull
    private String title;

    @NonNull
    private String author;

    @NonNull
    private Publisher publisher;

    @Builder(toBuilder = true)
    public static Book createBook(String title, String author, Publisher publisher) {
        return new Book(title, author, publisher);
    }

    public String getPublisherName() {
        return publisher.name;
    }

    public String getPublisherCountry() {
        return publisher.country;
    }
}
