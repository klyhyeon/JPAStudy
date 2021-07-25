package learn.jpa.model.ch10;

import lombok.Builder;
import lombok.ToString;

@ToString
public class UserDTO {

    private String username;
    private int age;

    @Builder
    public UserDTO (String username, int age) {
        this.username = username;
        this.age = age;
    }
}
