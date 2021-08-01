package learn.jpa.model.ch10;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {

    private String username;
    private int age;

    @QueryProjection
    public UserDTO(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
