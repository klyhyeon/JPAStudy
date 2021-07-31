package exercise.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Album extends Item {

    private String artist;

    private String etc;
}
