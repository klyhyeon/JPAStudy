package learn.jpa.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member implements Serializable {
    //receiver가 deserialize할 때 sender가 보낸 클래스와 같은지 검증하기 위해 UID를 사용
    private static final long serialVersionUID = 3990803224604257521L;
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME")
    private String username;
    private int age;
}
