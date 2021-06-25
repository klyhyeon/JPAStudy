package learn.jpa.model;

import javax.persistence.*;

@Entity
@TableGenerator(
        name = "MEMBER_TABLE_SEQ_GENERATOR",
        table = "MEMBER_TABLE_SEQ",
        pkColumnValue = "MEMBER_SEQ",
        initialValue = 15, allocationSize = 30
)
public class MemberTable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_TABLE_SEQ_GENERATOR")
    private Long id;
}


