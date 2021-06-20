# 2.2 H2 DB설치
already port in use Exception : C:/Users/[username] > h2.properties 파일에서 port 설정 변경할 수 있습니다.

H2 콘솔 server 실행할 때 Database "C:/Users/klyhy/test" not found, either pre-creat :
  - JDBC URL : jdbc:h2:~/test 로 먼저 연결한다음 (DB가 설치됨) localhost를 연결합니다.

# 2.5 JPA 설정
교재에선 hibernate 의존을 사용했지만 스터디 프로젝트는 hiberante core, jpa가 내장된 spring-boot-starter-data-jpa를 사용합니다.
 
spring.datasource로 시작하는 속성을 사용합니다.
 - DB 방언을 설정하는 hibernate.dialect 중요성이 큽니다.

 ##코드##
```java
 spring:
   h2:
     console:
       enabled: true
   datasource:
     driver-class-name: org.h2.Driver
     username: sa
     password:
     url: jdbc:h2:tcp//localhost/~/test
   jpa:
     database-platform: org.hibernate.dialect.H2Dialect
```

# 2.5.1 DB 방언
JPA는 특정 DB에 종속적이지 않지만 각 DB가 제공하는 SQL문법과 함수는 약간씩 차이가 있습니다.
 - 데이터 타입 : MySQL(VARCHAR), 오라클(VARCHAR2)

JPA는 특정 DB에 종속되는 기능을 많이 사용할 경우를 해결하기 위해 다양한 방언 클래스를 제공합니다. 따라서 DB가 변경되더라도 DB 방언만 교체해주면 됩니다. 

# 2.5 애플리케이션 개발
교재에선 spring을 썼기 떄문에 JPA가 가동될 때 구조만 참고하면 됩니다.
```java
//엔티티 매니저 팩토리 생성 
EntityManagerFactory emf = 
    Persistence.createEntityManagerFactory("jpabook");
//엔티티 매니저 생성
EntityManager em = emf.createEntityManager();
// 트랜잭션 획득
EntityTransaction tx = em.getTransaction();

try {
    
    tx.begin(); // 트랜잭션 시작
    logic(em); // 비즈니스 로직 실행
    tx.commit(); // 트랜잭션 커밋
} catch (Exceptino e) {
    tx.rollback(); // 트랜잭션 롤백        
} finally{
        em.close(); // 엔티티 매니저 종료
}
emf.close(); // 엔티티 매니저 팩토리 종료

//비즈니스 로직
private static void logic(EntityManager em) {...}
```
엔티티 매니저 팩토리
- 엔티티 매니저 팩토리는 DB 커넥션 풀도 생성하므로 생성 비용이 큽니다. 따라서 애플리케이션 전체에서 딱 한 번만 생성하고 공유해서 사용해야 합니다.

엔티티 매니저
- JPA의 기능 대부분은 엔티티 매니저가 제공합니다. 이를 사용하면 DB에 CRUD를 할 수 있습니다. 또한 엔티티 매니저는 내부에 데이터소스(DB 커넥션)을 유지하면서 DB와 통신합니다.
따라서 스레드 간에 공유하거나 재사용하면 안됩니다.
  
트랜잭션
- JPA를 사용하면 트랜잭션 안에서 데이터를 변경해야 합니다. 트랜잭션 없이 데이터를 변경할 경우 예외가 발생합니다.

# CRUD 특징
UPDATE
- JPA 표준 코드 기준 EntityManager.update() 같은 메서드가 없고 member.setAge(20) 처럼 엔티티 값만 변경하면 UPDATE SQL을 생성해서 DB에 값을 변경합니다.

# JPQL
JPA를 사용하면 DB에 대한 처리는 JPA에 맡겨야 합니다. 개발자는 엔티티 객체를 중심으로 개발해야 합니다.

문제는 테이블이 아닌 객체를 대상으로 READ 할 때 발생합니다. 엔티티로 변환하기엔 비용이 크기 때문이죠. 이 때 사용하는 것이 JPQL이며 검색 조건이 포함된 SQL입니다. 
 - JPQL : 엔티티 객체를 대상으로 쿼리합니다. (e.g. 클래스와 필드)
 - SQL : DB 테이블을 대상으로 쿼리합니다. 