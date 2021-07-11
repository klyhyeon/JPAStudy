package learn.jpa.model.ch07;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "MEMBER_ID")) // 부모에게 상속받은 id를 재정의 하고싶을 때 사용합니다.
public class Member extends BaseEntity{

    private String email;
}
