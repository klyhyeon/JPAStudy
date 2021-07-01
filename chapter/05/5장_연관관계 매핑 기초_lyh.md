# 5장_연관관계 매핑 기초

목적 : 객체의 참조와 외래 키를 매핑하는 것

- 방향 : [단방향, 양방향]
- 다중성 : [다대일(N:1), 일대다(1:N), 일대일(1:1), 다대다(N:M)]
    
    예를 들어 회원과 팀이 관계가 있을 때 여러 회원은 한 팀에 속하므로 (N:1)이며 한 팀에 여러 회원이 소속될 수 있으므로 (N:M)입니다.
- 연관관계의 주인 : 객체를 양방향 연관관계로 만들면 연관관계의 주인을 정해야 합니다. 

## 5.1 단방향 연관관계
객체 연관관계
- 회원 객체는 Memeber.team 필드로 팀 객체와 연관관계를 맺습니다. 하지만 반대(team -> member)로 접근하는 필드는 없습니다.

테이블 연관관계
- 회원 테이블은 TEAM_ID `외래 키`로 팀 테이블과 연관관계를 맺습니다. 따라서 이는 양방향 관계입니다.
```sql
  SELECT *
  FROM MEMBER M 
  JOIN TEAM T ON M.TEAM_ID = T.TEAM_ID
```
```java
//단방향
    Class A {
        B b;
    }
    
    Class B { }

//양방향
    Class A {
        B b;
    }
    
    Class B {
        A a;
    }
```

- 객체는 참조(주소)로 연관관계를 맺지만, 테이블은 외래 키로 연관관계를 맺습니다. 참조는 단방향인 반면에 외래 키는 양방향 입니다.

@JoinColumn

- 외래 키를 매핑할 때 사용합니다.

| 속성        | 기능           |기본값  |
| ------------- | ------------- | ----- |
| name      | 매핑할 외래 키 이름 | 필드명 + _ + 참조하는 테이블의 기본 키 컬럼명 |
| referencedColumnName      | 외래키가 참조하는 대상 테이블의 컬럼명      |   참조하는 테이블의 기본 컬럼명 |
| foreignKey(DDL) |  외래 키 제약조건을 직접 지정할 수 있다. 이 속성은 테이블을 생성할 때만 사용한다.      |   |

@ManyToOne

| 속성        | 기능           |기본값  |
| ------------- | ------------- | ----- |
| Fetch      | 글로벌 페치 전략** | @ManyToOnje=FetchType.EAGER, @OneToMany=FetchType.Lazy  |
| referencedColumnName      | 외래키가 참조하는 대상 테이블의 컬럼명      |   참조하는 테이블의 기본 컬럼명 |
| foreignKey(DDL) |  외래 키 제약조건을 직접 지정할 수 있다. 이 속성은 테이블을 생성할 때만 사용한다.      |   |

**글로벌 페치 전략
- Lazy는 명령을 통해서만 relationships 데이터를 가져오기 때문에 불필요한 DB 접근을 막을 수 있다는 장점이 있고 성능개선에 도움이 될 수 있습니다.
- 하지만 Eager 방식은 연관관계에 있는 테이블을 모두 Join해 id를 검색합니다. 이는 즉시로딩으로 불리우며 Lazy 방식은 지연로딩으로 불립니다. 따라서 즉시로딩은 연관관계가 없는 DB 스키마일 때(@ManyToOne) 추천하는 방식입니다.

조회

- 객체 그래프 탐색 : member.getTeam()을 사용해서 member와 연관된 team 엔티티를 조회할 수 있습니다.
- 객체지향 쿼리 사용(JPQL) : 
  - 팀1에 소속된 회원만 조회하는 SQL :
```sql
#JPQL(엔티티)
  SELECT m
  FROM Member m
  JOIN m.team t WHERE t.name =: teamName
  
#SQL(DB)
    SELECT M.* FROM MEMBER MEMBER
    INNER JOIN
        TEAM TEAM1 ON MEMBER.TEAM_ID = TEAM1_.ID
    WHERE
        TEAM1_.NAME='팀1'
```
```java
  List<Member> resultList = em.createQuery(jpql, Member.class)...
```

연관된 엔티티 삭제

연관된 엔티티를 삭제하려면 기존에 있던 연관관계를 먼저 제거하고 삭제해야 합니다. 그렇지 않으면 외래 키 제약조건으로 인해 DB에서 오류가 발생합니다.

```java
    member1.setTeam(null);
    member2.setTeam(null);
    em.remove(team); //삭제
```

  