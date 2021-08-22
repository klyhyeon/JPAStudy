스프링 데이터 JPA는 스프링 프레임워크에서 JPA를 편리하게 사용할 수 있도록 지원하는 프로젝트다.  이 프로젝트는 데이터 접근 계층을 개발할 때 지루하게 반복되는 CRUD 문제를 해결한다.

스프링 데이터 JPA는 스프링 데이터 프로젝트의 하위 프로젝트 중 하나다.  즉 스프링 데이터 JPA 말고도 다른 DB(MONGO DB, REDIS 등등)를 위한 프로젝트가 있다는 뜻이다.

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbBhNCy%2Fbtrcs9rc8JE%2FxCEPUefg9hEwsUH3r3K6xK%2Fimg.png)

### 스프링 데이터 JPA 설정

스프링 데이터 JPA를 사용하기 위해선 먼저 의존성 설정을 해줘야 한다.

```
implementation('org.springframework.boot:spring-boot-starter-data-jpa')
```

### 공통 인터페이스 기능

스프링 데이터 JPA는 간단한 CRUD 기능을 공통으로 처리하는 JpaRepository 인터페이스를 제공한다.  가장 간단한 방법은 이 인터페이스를 상속받는 것이다.

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F1rv5G%2FbtrcLBWIFUU%2FJkylxdbOTYshBkWOjl0sy1%2Fimg.png)

```
public interface MemberRepository extends JpaRepository<Member, Long> {
}
```

JpaRepository에 들어가는 T는 레포지토리를 통해서 접근하고자 하는 엔티티 클래스가 들어가며 ID는 해당 엔티티 클래스의 ID 타입이 들어간다.  위 코드는 Member 엔티티 클래스를 사용하며 ID타입은 Long이다.

공통 인터페이스는 다음과 같은 메소드를 사용할 수 있다.

-   save(s) : 새로운 엔티티는 저장하고 이미 있는 엔티티는 수정한다.
-   delete(T) : 엔티티를 삭제한다.
-   findOne(ID) : 엔티티 하나를 조회한다.
-   getOne(ID) : 엔티티를 프록시로 조회한다.
-   findAll(...) : 모든 엔티티를 조회한다.

그리고 JpaRepository의 구현클래스인 SimpleRepository에 들어가면 JpaRepository의 함수들이 구현되어 있다.

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fc9NpOa%2FbtrcSt3ufrQ%2F4ueA75DAAEHAPi5nMvgifK%2Fimg.png)

보면은 @Transactional이 readOnly로 걸려있다.  즉 별도로 서비스단에 @Transactional을 걸지 않더라도 Jpa 메소드들은 트랜잭션이 걸려서 동작한다는 것이다.  단순히 조회를 담당하는 메소드들은 readOnly가 걸려있고 삭제나 저장과 같은 데이터에 변경이 일어나는 메소드는 readOnly가 걸려있지 않다.

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FlNM8Z%2FbtrcIflNRDN%2FtyhvIFR0gK6KYQyzkXdZQ1%2Fimg.png)

### 쿼리 메소드 기능

스프링 데이터 JPA는 쿼리를 메소드 형식으로 호출한다.

```
Optional<Member> findByAge(int age);
```

이 메소드는 Optional<Member>를 리턴하며 age를 가지고 조회를 한다.

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fb3AXes%2Fbtrcp0ImMhG%2F3ejB2zME8fiRQRAIYsT6FK%2Fimg.png)

물론 정해진 규칙에 따라서 메소드 이름을 지어야 하며 일반적으로 By는 where절, By뒤에 붙는 컬럼명이 where절의 조건식이 된다.  한번에 여러 커럼을 이용할 수 있으며 And, Or를 사용할 수 있다.

```
Optional<Member> findByAgeAndName(int age, String name);
```

### JPA NamedQuery

스프링 데이터 JPA는 메소드 이름으로 JPA Named 쿼리를 호출할 수 있다.

```
@Query("select m from Member m where m.age > ?1")
Optional<Member> findByAge(int age);
```

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcnudHP%2Fbtrcvw0i1jG%2FAkqxfsinVUPeIcP12Yjv70%2Fimg.png)

기존에 findByAge는 age가 일치하는 것을 조회하지만 NamedQuery를 통해 입력받은 age보다 큰 것으로 조회한다.

### 반환 타입

스프링 데이터 JPA는 유연한 반환타입을 지원하는데 결과가 한 건 이상이면 컬렉션 인터페이스를 사용하고 단건이면 반환타입을 지정한다.

### 페이징과 정렬

스프링 데이터 JPA는 쿼리 메소드에 페이징과 정렬기능을 사용할 수 있도록 2가지 파라미터를 제공한다.

-   org.springframework.data.domain.Sort : 정렬
-   org.springframework.data.domain.Pageable: 페이징 기능(내부에 Sort 포함)

Page를 사용하면 스프링 데이터 JPA는 페이징 기능을 제공하기 위해 검색된 전체 데이터 건수를 조회하는 count 쿼리를 추가로 호출한다.

```
List<Member> findByAge(int age, Pageable pageable);
Page<Member> findByAge(int age, Pageable pageable);
```

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FQ6cdU%2Fbtrctv8E7Ra%2FalokKo86iNwNXaRkKZ5GT1%2Fimg.png)

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F7AzFV%2Fbtrcc5wSeSJ%2FymPpnGnVd9c6D7lEaZwax0%2Fimg.png)