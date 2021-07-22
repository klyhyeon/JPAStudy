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

## JPQL

대소문자 구분
- 엔티티와 속성 (Member, username)

엔티티 이름
  
별칭은 필수
- `SELECT username FROM Member m` //username -> m.username 

