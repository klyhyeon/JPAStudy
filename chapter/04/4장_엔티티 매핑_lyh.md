매핑 어노테이션
- 객체와 테이블 : @Entity, @Table
- 기본 키 : @Id
- 필드와 컬럼 : @Column
- 연관관계 매핑 : @ManyToOne, @JoinColumn

@Entity
- 기본 생성자는 필수(파라미터가 없는 public 또는 protected 생성자)