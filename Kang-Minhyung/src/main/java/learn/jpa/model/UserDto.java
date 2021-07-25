package learn.jpa.model;

public class UserDto {
    private String name;
    private int age;

    public UserDto() {
    }

    public UserDto(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserDto{" + "name='" + name + '\'' + ", age=" + age + '}';
    }
}
