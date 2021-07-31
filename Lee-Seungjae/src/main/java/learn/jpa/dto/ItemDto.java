package learn.jpa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemDto {
    private String name;
    private int price;

    public ItemDto(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
