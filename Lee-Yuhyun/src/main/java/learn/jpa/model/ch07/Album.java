package learn.jpa.model.ch07;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue("A") //엔티티를 저장할 때 사용할 구분자; 부모 테이블 컬럼에 저장됩니다.
@PrimaryKeyJoinColumn(name = "ALBUM_ID") //부모키를 상속받지 않고 새로운 기본키를 생성하고 싶을 때 사용합니다.
public class Album extends Item{

    private String artist;
}
