package learn.jpa.model.ch07;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity {
    //DB 테이블과 매핑은 필요없고 자식 엔티티에게 공통으로 사용되는 정보만 제공하면 됩니다.
    @Id@GeneratedValue
    private Long id;
    private String name;
}
