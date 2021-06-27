package learn.jpa.model;

import javax.persistence.*;

@Entity
public class LobTestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    String text;

    @Lob
    Byte[] bytes;

    public LobTestEntity() {
    }

    public LobTestEntity(String text, Byte[] bytes) {
        this.text = text;
        this.bytes = bytes;
    }
}

