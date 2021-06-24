package learn.jpa.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class ColumnTestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "custom_name")
    String text0;

    @Column(nullable = false)
    String text1;

    @Column(unique = true)
    String text2;

    @Column(columnDefinition = "varchar(100)")
    String text3;

    @Column(length = 20)
    String text4;

    @Column(precision = 10, scale = 3)
    BigDecimal number1;
}

