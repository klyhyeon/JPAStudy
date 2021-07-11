package learn.jpa.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@NoArgsConstructor
@Getter
public class Album extends Item{

    private String artist;

    @Builder
    public Album(String artist) {
        this.artist = artist;
    }
}
