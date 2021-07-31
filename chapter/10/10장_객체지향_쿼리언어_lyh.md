ORM방식으로 복잡한 쿼리를 만들 수 있게 도와주는 기능입니다.
JPA는 JPQL을 분석해 적절한 SQL을 만들어 DB를 조회합니다.

### 종류
Native SQL : JPQL 대신 SQL을 사용할 수 있음
QueryDSL : SQL 작성을 도와주는 `빌더 클래스` 모음

**빌더클래스**

쿼리를 코드로 작성할 수 있기 때문에 컴파일 시점에 오류가 발생합니다.
쿼리 전용 엔티티(`메타모델`)를 만들어주는 어노테이션 프로세서가 필요합니다.

QueryDSL
```java
    //준비
    JPAQuery query = new JPAQuery(em);
    QMember member = QMember.member;
    
    //쿼리, 결과조회
    List<Member> members = 
            query.from(member)
            .where(member.username.eq("kim"))
            .list(member);
```

**직접 SQL**

DB 의존적인 쿼리입니다. JPQL이 지원하지 않는 SQL을 작성해야할 때 NativeSQL을 사용합니다.

**JDBC, MyBatis 같은 SQL 매퍼 프레임워크**

JDBC나 MyBatis를 JPA와 함께 사용하려면 적절한 시점에 영속성 컨텍스트를 강제로 `플러시` 해야합니다.
JPA를 우회하는 매퍼에 대해 JPA는 인식하지 못하기 때문에 영속성 컨텍스트와 DB의 불일치가 일어나
데이터 무결성을 훼손시킬 수 있기 때문입니다. 
이런 이슈를 해결하기 위해선 SQL 실행 직전에 스프링 AOP를 통해 강제 플러시해서
영속성 컨텍스트와 DB를 동기화하는 것입니다.

### JPQL

대소문자 구분
- 엔티티와 속성 (Member, username)

엔티티 이름
  
별칭은 필수
- `SELECT username FROM Member m` // `username -> m.username`

### 프로젝션

SELECT 절에 조회할 대상을 지정하는 것 

여러 값을 조회할 땐 쿼리객체 TypedQuery가 아닌 `Query`를 사용해야 합니다.

DTO객체를 사용할 땐 `NEW 명령어`를 사용해 코드를 줄일 수 있습니다.
```java
    List<UserDTO> useNewCommandResultList =
            em.createQuery("SELECT new learn.jpa.model.ch10.UserDTO(m.username, m.age)" +
                    "FROM Member m", UserDTO.class).getResultList();
```

### 페이징 API

처리하기 까다로운 페이징 SQL을 JPA는 두 API로 추상화했습니다.
- setFirstResult(int startPosition) : 조회 시작 위치(0부터 시작)
- setMaxResults(int maxResult) : 조회할 데이터 수
 
데이터베이스마다 SQL이 다르며 오라클과 SQLServer는 페이징 쿼리를 따로 공부해야할 정도로 복잡합니다.
하지만 JPA의 DB Dialect 덕분에 같은 API로 처리할 수 있습니다.  

### 집합과 정렬

집합 함수
- COUNT, MAX/MIN, AVG, SUM

GROUP BY, HAVING
- GROUP BY는 통계 데이터를 구할 때 특정 그룹끼리 묶어줍니다. 다음은 팀 이름 기준으로 묶은 예제입니다.
```sql
    SELECT t.name, COUNT(m.age), SUM(m.age), AVG(m.age), MAX(m.age), MIN(m.age)
    FROM Member m LEFT JOIN m.team t
    GROUP BY t.name
```
HAVING은 GROUP BY와 함께 사용하는데 그룹화한 통계 데이터를 기준으로 필터링합니다.
```sql
    SELECT t.name, COUNT(m.age), SUM(m.age), AVG(m.age), MAX(m.age), MIN(m.age)
    FROM Member m LEFT JOIN m.team t
    GROUP BY t.name
    HAVING AVG(m.age) >= 10
```

통계 쿼리(리포팅 쿼리)는 코드를 수십 라인에서 몇 줄로 처리해줄 수 있습니다. 하지만 통계 쿼리는
전체 데이터를 기준으로 하기때문에 실시간으로 사용하기엔 부담이 많습니다. 따라서 결과가 많다면
통계 결과만 저장하는 `테이블을 별도로 만들어 두고`==`실시간 쿼리를 사용하지 말고 데이터 테이블을 만들자`사용자가 적은 새벽에 통계 쿼리를 실행해서 그 결과를
보관하는 것이 좋습니다.

### JPQL 조인

**내부 조인**
```java
    String teamName = "팀A";
    String query = "SELECT m FROM Member m INNER JOIN m.team t WHERE t.name = :teamName";
    List<Member> members = em.createQuery(query, Member.class)
            .setParameter("teamName", teamName)
            .getResultList();   
```

**외부 조인**
보통 OUTER를 생략하고 사용합니다.

컬렉션 조인
일대다나 다대다 관계처럼 Collection을 사용하는 곳에 조인하는 것을 컬렉션 조인이라 합니다.

**세타 조인**

세타 조인은 WHERE절을 이용해 전혀 관계없는 엔티티도 조인을 할 수 있습니다.

```sql
--JPQL--
select count(m) from Member m, Team t
where m.username = t.name

--SQL--
SELECT COUNT(M.ID)
FROM
    MEMBER M CROSS JOIN TEAM T
WHERE
    M.USERNAME=T.NAME
```

**JOIN ON 절**
보통 WHERE절 사용할 때와 같으므로 OUTER 조인에만 사용합니다.
```sql
--JPQL--
select m,t from Member m
left join m.team t on t.name = 'A'

--SQL--
SELECT m.*, t.* FROM Member m
LEFT JOIN Team t ON m.TEAM.ID=t.id and t.name='A'
```

### 페치 조인

JPQL에서 성능 최적화를 위해 제공하는 기능입니다. 연관된 엔티티나 컬렉션을 한 번에 같이 조회하는 기능입니다.
`join fetch` 명령어로 사용할 수 있습니다.

**엔티티 페치 조인**

회원 엔티티를 조회하면서 연관된 팀 엔티티도 함께 조회하는 JPQL
```sql
select m
from Member m join fetch m.team
```
- 일반 JPQL과는 다르게 m.team 다음에 별칭이 없는데 페치 조인은 별칭을 사옹할 수 없습니다.
- 지연로딩을 설정했을 때 페치조인은 팀도 함께 조회했기 때문에 프록시가 아닌 실제 엔티티입니다.
따라서 연관된 팀을 사용해도 지연 로딩이 일어나지 않습니다. 또한 회원 엔티티가 준영속 상태가 되어도
연관된 팀을 조회할 수 있습니다. 
  
- 참고 : `save the transient instance before flushing` 오류 발생 시 `Child` 객체를 `Parent` 엔티티에
추가할 때 DB에 저장되어있지 않으면 발생하는 오류입니다. 해결은 `cascade={CascadeType.ALL}`을
`Parent` 엔티티에 `Child`에 대한 참조를 걸어주는 것으로 가능합니다.
- **이슈** : 상호참조 메서드 사용 시 `@ToString`에 exclude 붙여서 사용해줘야 합니다. 
  양쪽 엔티티에 `@ToString`이 걸려있다면 무한루프를 돕니다.

페치조인의 특징과 한계 :

- 글로벌 로딩 전략을 지연로딩으로 두고 `@OneToMahy(fetch="FetchType.LAZY)`, 필요한 곳에서만
페치 조인을 적용하는 것이 효과적입니다. 페치 조인은 연관된 엔티티를 쿼리 시점에 조회하므로 지연 로딩이 발생되지 않습니다.
따라서 준영속 상태에서도 객체 그래프를 탐색할 수 있습니다.

- 페치 조인 대상에는 별칭을 줄 수 없습니다. 
- 둘 이상의 컬렉션을 페치할 수 없고, `컬렉션`을 페치 조인하면 페이징 API를 사용할 수 없습니다.

- **종합하자면** 페치 조인은 객체 그래프(형태)를 유지할 때 사용하기 좋고, 
여러 테이블을 조인해서 엔티티가 가진 모양이 아닌 전혀 다른 결과를 내야 한다면 페치 조인보다
여러 테이블에서 필요한 필드들만 조회해서 DTO로 반환하는 것이 더 효과적일 수 있습니다.
  
### 경로탐색

`Order 예시`
- 상태 필드 탐색 `private String name`
- 단일 값 연관경로 탐색
`@ManyToOne private Mebmer member` + `묵시적 조인`
```sql
--JPQL--
select o.member from Order o
--SQL--
select m.*
from Order o
    inner join Member m on o.member_id = m.id
```
- 컬렉션 값 연관경로 탐색 `@OneToMany private List<OrderItem> orderItems ...`
```sql
select t.members from Team t --성공--
select t.members.username from Team t --실패--
```

**경로탐색 묵시적 조인 시 주의사항**
- 항상 내부 조인이 발생합니다.
- 컬렉션은 경로 탐색의 끝이며 컬렉션에서 경로 탐색을 하려면 명시적으로 조인을 해서 별칭을 얻어야 합니다.
- 조인이 성능상 차지하는 부분은 아주 큽니다. 묵시적 조인은 조인이 일어나는 상황을
한눈에 파악하기 어렵기 때문에 이슈가 발생할 수 있는 복잡한 애플리케이션일수록 `명시적 조인`을 사용해야합니다.

### 서브쿼리

```sql
--나이가 평균보다 많은 회원
select m from Member m
where m.age > (select avg(m2.age) from Member m2)
--한 건이라도 주문한 고객
select o.member from Order o
where (select COUNT(o) from Order o where m= o.member) > 0
--> size 기능을 사용할 경우
select m from Member m
where m.orders.size > 0
```

**종류**
- EXISTS : 서브쿼리에 결과가 존재하면 참
```sql
select m from Member m
where exists (select t from m.team t where t.name = 'team1')
```
- ALL, ANY/SOME : 모든 혹은 하나의 조건을 만족하면 참(비교 연산자와 함께 사용)
```sql
  select o from Order o
  where o.orderAmount > ALL (select p.stockAmount from Product p)
  
  select m from Member m
  where m.team = ANY (select t from Team t)
```
- IN : 결과 중 하나라도 같은 것이 있으면 참
```sql
--멤버 20세 이상을 보유한 팀
  select t from Team t
  where t IN (select t2 From Team t2 JOIN t2.members m2 where m2.age >= 20)
```
### 조건식
- AND/OR/NOT, IN, Like(%,_), IS [NOT] NULL 
**컬렉션 식**
- IS [NOT] EMPTY
**스칼라 식** 

숫자, 문자, 날짜, case, 엔티티 타입 같은 가장 기본적인 타입들을 말합니다.

### Named 쿼리: 정적 쿼리

- 동적 쿼리 : `em.createQuery("select ..")` 처럼 JPQL을 문자로 완성해서 직접 넘기는 것을 동적 쿼리라 합니다.
- 정적 쿼리 : 미리 정의한 쿼리에 이름을 부여해 필요할 때 사용할 수 있는데 이것을 정적쿼리라고 합니다.
정적 쿼리는 오류를 빨리 확인할 수 있고, 사용하는 시점에서 파싱된 결과를 재사용하므로 성능상 이점도 있습니다.
  그리고 변하지 않는 정적 SQL이 생성되므로 DB의 조회 성능 최적화에도 도움이 됩니다.
  Named 쿼리는 @NamedQuery 어노테이션을 사용해 자바 코드에 작성하거나 XML 문서에 작성할 수 있습니다.

## [QueryDSL](https://querydsl.com/static/querydsl/latest/reference/html/ch02s03.html#d0e1200)

### QueryDSL 시작

기본 Q 생성
import static를 이용해 쿼리 타입의 기본 인스턴스를 사용하면
코드를 더 간결하게 쓸 수 있습니다.
`import static learn.jpa.model.ch10.QMember.member;`

### 페이징과 정렬
OFFSET
- 반드시 `order by`와 함께 사용해야 합니다. 

### 서브쿼리
```java
QCustomer customer = QCustomer.customer;
QCustomer customer2 = new QCustomer("customer2");
queryFactory.select(customer.all())
    .from(customer)
    .where(customer.status.eq(
        SQLExpressions.select(customer2.status.max()).from(customer2)))
    .fetch()
```

### [프로젝션](https://querydsl.com/static/querydsl/latest/reference/html/ch03s02.html)

@QueryProjection
- Projections.constructor를 대체할 수 있습니다.
- Q 인스턴스 필드가 StringPath, NumberPath라서 사용못합니다. 원인은?

### 수정,삭제,배치 쿼리

JPQL 배치 쿼리와 같이 영속성 컨텍스트를 무시하고 데이터베이스를 직접 쿼리합니다.

### 동적쿼리

`BooleanBuilder`를 사용해 특정 조건에 따른 동적 쿼리를 편리하게 생성할 수 있습니다.

### 메소드 위임
`@QueryDelegate` 메소드 위임 기능을 사용하면 쿼리 타입에 검색 조건을 직접 정의할 수 있습니다.











