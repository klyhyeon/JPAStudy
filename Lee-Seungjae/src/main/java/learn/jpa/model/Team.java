package learn.jpa.model;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import learn.jpa.config.BooleanYNConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    @Convert(converter = BooleanYNConverter.class)
    private boolean abc;

    public Team(boolean abc) {
        this.abc = abc;
    }
}
