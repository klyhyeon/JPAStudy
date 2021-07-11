package exercise.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book extends Item {

    private String author;

    private String isbn;
}
