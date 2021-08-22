**공통 인터페이스 상속**
* JpaRepository(Spring Data JPA) > PagingAndSortingRepository > CrudRepository(Spring Data)

**쿼리 메소드**
* 메서드로 쿼리를 만들어낼 수 있습니다.
  * Spring Data JPA에 내장된 메서드 이름으로 생성
  ```java
    Student findStudentScoreLessThan(int score);
    ```
  * JPA NamedQuery
    * 쿼리에 이름을 지정해두고 @NamedQuery를 사용해 꺼내쓸 수 있습니다.
  * 실행할 Named 쿼리가 없으면 메서드 쿼리 전략을 사용합니다.
  * @Query로 메서드에 직접 쿼리 작성
  ```java
  @Query("select s from Student s where s.score > ?1")
  Student findStudentScoreLessThan(int score);
  ```
  * 파라미터 바인딩
  ```java
    @Query("select s from Student s where s.score > :score")
    Student findStudentScoreLessThan(@Param("score") int score);
  
    select s from Student s where s.score > ?1 //위치 기반
    select s from Student s where s.score > :score //이름 기반
  ```
  * 벌크성 수정쿼리는 @Modifying을 사용합니다.
  * 반환타입은 반환값이 복수일 땐 Collection을 사용하며 결과가 없을 땐 null을 반환합니다.
  단건을 지정해둔 쿼리에 복수의 결과값이 반환된다면 NonUniqueResultException을 반환합니다.
  * 페이징과 정렬
    * `Sort`, `Pageable`이란 2가지 특별한 파라미터를 제공합니다.
  ```java
    //count 쿼리 사용
    Page<Student> findByName(String name, Pageable pageable);
  ```
  * Spring Data 파라미터 엔티티 자동변환
    * 파라미터에 @RequestParam으로 엔티티를 담아서 받을 수 있습니다. 컨트롤러 레이어에서 엔티티를 수정하더라도
    변경감지기능이 동작하지 않는데 이는 준영속성 컨텍스트와 연관이 있습니다.
  * Spring Data JPA가 사용하는 구현체
    * Spring Data JPA가 제공하는 공통 인터페이스는 SimpleJpaRepository 클래스가 구현합니다.
    * @Repository: JPA 예외를 Spring이 추상화한 예외로 변환합니다.
    * @Transactional: JPA의 모든 변경은 트랜잭션 안에서 이루어집니다. Spring Data JPA가 제공하는 공통 인터페이스를 사용하면
    데이터를 변경(등록, 수정, 삭제)하는 메서드에 @Transactional 처리가 되어있습니다. 따라서 서비스 계층에 트랜잭션을 시작하지 
    않으면 레포지토리에서 트랜잭션을 시작합니다. 
      * `readonly = true` 를 설정하면 데이터를 변경하지 않는 트랜잭션에서 플러시를 생략해서 약간의 성능 향상을 얻을 수 있습니다.
    * 

