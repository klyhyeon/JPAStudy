# π JPA(Java Persistence API)
μμ±μ: νμ°½ν

---

## π About JPA

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FtMvTg%2FbtqUL9JlI78%2FTf6mCkkrelMZCxgqfK99qk%2Fimg.png)

`JPA`λ `ORM`μ `Java` νλ«νΌμ νΈνλκ² μ¬μ©νκΈ° μν `μκ΅¬μ¬ν­μ κ·κ²©νν νμ€ API`μ΄λ€.

μ΄ APIλ `javax.persistence` ν¨ν€μ§μ μ μλΌμμΌλ©°, λ¬Έμ κ·Έλλ‘ λλΆλΆμ `μκ΅¬μ¬ν­`μ΄ `μ μ`λ§ λμ΄ μλ€.

![image](https://user-images.githubusercontent.com/71188307/122570766-0819c100-d087-11eb-87d8-cbdddb76570d.png)

ν¨ν€μ§λ₯Ό κΉλ³΄λ©΄ λλΆλΆμ ν΄λμ€κ° `interface`μμ μ μ μλ€.

μ¬λ¬ λ²€λμμ μ΄ APIλ₯Ό κ΅¬ννμ¬ μ κ³΅νκ³  μλλ° κ΅¬νμ²΄λ₯Ό μ κ³΅νλ λ²€λλ€μ

`Eclipse Link`, `Data Nucleus`, `OpenJPA`, `TopLink Essentials`, `Hibernate` λ±μ΄ μμΌλ©°,

2021λ νμ¬ μ  μΈκ³μ μΌλ‘ κ°μ₯ λ§μ΄ μ¬μ©λλ λ²€λλ `Hibernate`μ΄λ€.

μν€λ°±κ³Όμ λ°λ₯΄λ©΄ 2019λλΆν° JPAλ `Jakarta Persistence`λ‘ μ΄λ¦μ΄ λ°λλ©° v3.0μ΄ μΆμλμΌλ©°, 

μ μ΄λ―Έμ§λ `Spring-Boot` μ΅μ  λ²μ μμ μΊ‘μ²ν μ΄λ―Έμ§λ‘, jarμ΄λ¦λ `jakarta.persistence`μμ μ μ μλ€.

`Jakarta Persistence v3.0`μ μκ΅¬μ¬ν­μ λ§μ‘±νλ κ΅¬νμ²΄λ₯Ό μ κ³΅νλ λ²€λλ μ΄ 3μ’λ₯λ‘

`Data Nucleus v6.0`, `EclipseLink v3.0`, `Hibernate v5.5`μ΄λ€.

```text
Jakarta Persistence 3.0
Renaming of the JPA API to Jakarta Persistence happened in 2019, followed by the release of v3.0 in 2020.

Main features included were:

Rename of all packages from javax.persistence to jakarta.persistence.
Rename of all properties prefix from javax.persistence to jakarta.persistence.
Vendors supporting Jakarta Persistence 3.0

DataNucleus (from version 6.0)
EclipseLink (from version 3.0)
Hibernate (from version 5.5)
```

μ΄λ€ λ²€λλ₯Ό μ¬μ©νλλΌλ JPAλΌλ APIκ° κ°μ λΌμκΈ° λλ¬Έμ λ΄λΆμ μΌλ‘ λμμ΄ λ€λ₯Ό μ μμΌλ μ€μ  μ¬μ©λ²μλ ν° μ°¨μ΄κ° μλ€.

---

## π About Spring-Data-JPA

![image](https://user-images.githubusercontent.com/71188307/122573610-dc4c0a80-d089-11eb-9ee1-114b2486b651.png)

JPAλ₯Ό κ΅¬νν μμ κ΅¬μ²΄ ν΄λμ€λ₯Ό κ·Έλλ‘ μ¬μ©ν  κ²½μ° μ΄λ° μ λ° μ€μ μ λ§€λ² λ°λ‘ ν΄μ€μΌ νλ κ²½μ°κ° λ§μλ°,

μ΄λ₯Ό νλ¨κ³ λ μΆμννμ¬ μμ£Ό μ¬μ©νλ κΈ°λ₯λ€μ νΈλ¦¬νκ² μΈ μ μκ² λ§λ€μ΄ λ κ³μΈ΅μ΄λ€.

κ°μΈμ μΌλ‘ κ°μ₯ λ§μ΄ μ¬μ©νκ² λλκ²μ `org.springframework.data.jpa.repository` ν¨ν€μ§μ `SimpleJpaRepository`μ΄λ€.

μ€μ λ‘ κ°μ₯ λ§μ΄ μ¬μ©λλ `save`, `saveAll`, `delete`, `deleteAll`, `findById`, `findBy~` λ±μ APIλ€μ΄

`SimpleJpaRepository`μ μ μλΌμμΌλ©°, μ€μ  κ΅¬νλΆ μ½λλ JPAλ₯Ό λνν μμ€μ μμ£Ό λ¨μν μ½λλ€μ΄λ€.

```java
@Transactional
@Override
public <S extends T> S save(S entity) {

    Assert.notNull(entity, "Entity must not be null.");

    if (entityInformation.isNew(entity)) {
        em.persist(entity);
        return entity;
    } else {
        return em.merge(entity);
    }
}

@Transactional
@Override
public <S extends T> List<S> saveAll(Iterable<S> entities) {
    
    Assert.notNull(entities, "Entities must not be null!");
    
    List<S> result = new ArrayList<S>();
    
    for (S entity : entities) {
        result.add(save(entity));
    }
    return result;
}

@Override
@Transactional
@SuppressWarnings("unchecked")
public void delete(T entity) {
      
    Assert.notNull(entity, "Entity must not be null!");
    
    if (entityInformation.isNew(entity)) {
        return;
    }
    
    Class<?> type = ProxyUtils.getUserClass(entity);
    
    T existing = (T) em.find(type, entityInformation.getId(entity));
    
    // if the entity to be deleted doesn't exist, delete is a NOOP
    if (existing == null) {
        return;
    }
    
    em.remove(em.contains(entity) ? entity : em.merge(entity));
}
```

JPAλ₯Ό μ²μ μ νλ μ¬λμ μμ§ μ λͺ¨λ₯΄κ² μ§λ§, JPAμ λͺ¨λ  λμμ `transaction` μμμ μ§νλμ΄μΌ μμμ΄ λ°μλκΈ° λλ¬Έμ,

`SimpleJpaRepository`μ λͺ¨λ  public APIμλ `@Transactional`μ΄ μμ±λΌμλ€.

λ§μ½ JPAλ₯Ό νμ΅νκ³ μ νλλ° `transaction`μ΄ λ­μ§ μ λͺ¨λ₯Έλ€λ©΄ μ΄ λΆλΆμ λν μ ννμ΅μ΄ νμμ μΌλ‘ μκ΅¬λλ€κ³  λ³Ό μ μκ² λ€.

---

## π JPA λμ μλ¦¬

![image](https://user-images.githubusercontent.com/71188307/122574473-a4919280-d08a-11eb-93b7-8272704a5d65.png)

κ΅μ₯ν λ³΅μ‘νκ² κ΅¬μ±λΌμλλ°, κ°λ¨νκ² ν΅μ¬λ§ μκ°νμλ©΄ λͺ¨λ  μμμ΄ `EntityManager` μμ£Όλ‘ λμκ°λ€.

`EntityManager`λ `μμμ± μ»¨νμ€νΈ`λΌλ μ΄λ¦μ κ°μ λ°μ΄ν°λ² μ΄μ€λ₯Ό κ΄λ¦¬νλ κ°μ²΄μ΄κΈ° λλ¬Έμ΄λ€.

DB μμμ΄ νμν μμ μ `EntityManager`λ₯Ό μμ±ν΄μΌ νλλ°, μ΄λ `DataSource`μ λ§€νλ `Persistence`μμ `connection`μ μ»μ΄μ 

`EntityManagerFactory`κ° `EntityManager`λ₯Ό μμ±ν΄μ£Όκ³ , `EntityManager`λ `connection`μ Lazy μνλ‘ κ°μ§λ€.

κ·Έλ¦¬κ³  μ€μ  μμμ΄ μμλλ νμ΄λ°μ `EntityManager`λ `EntityTransaction`κ°μ²΄λ₯Ό μμ±ν΄ `transaction`μ μμνλ©° `connection`μ μ°κ²°νλ€.

μ΄ν μμμ΄ λλλ©΄ `EntityTransaction`μ νκΈ°νλ©° `transaction`μ μ’λ£νκ³  `EntityManager`λν νκΈ°νλ€.

`EntityManager`κΉμ§ νκΈ°νλ μ΄μ λ `EntityManager`κ° `Thread-Safe`νμ§ μκΈ° λλ¬Έμ΄λ©°, `EntityManager` μΈμ€ν΄μ€λ λ§€ μμλ§λ€ μλ‘ μμ±λλ€.

![image](https://user-images.githubusercontent.com/71188307/122577926-567e8e00-d08e-11eb-9740-be932a5bbb13.png)

μ΄ κ³Όμ μ΄ λλ΅ μλμ κ°μ μ½λλ‘ λνλλ€.

```java
EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook"); //μν°ν° λ§€λμ  ν©ν λ¦¬ μμ±
EntityManager em = emf.createEntityManager(); //μν°ν° λ§€λμ  μμ±
EntityTransaction tx = em.getTransaction(); //νΈλμ­μ νλ

try {
    tx.begin(); //νΈλμ­μ μμ
    logic(em);  //λΉμ¦λμ€ λ‘μ§
    tx.commit();//νΈλμ­μ μ»€λ°
} catch (Exception e) {
    tx.rollback(); //νΈλμ­μ λ‘€λ°±
} finally {
    em.close(); //μν°ν° λ§€λμ  μ’λ£
}

emf.close(); //μν°ν° λ§€λμ  ν©ν λ¦¬ μ’λ£
```

`Spring-Data-JPA`λ₯Ό μ¬μ©ν  κ²½μ° μμ μμμ λλΆλΆ μ κ²½μ°μ§ μμλ λμ§λ§, 

λ§μ½ μμ JPAλ₯Ό μ¬μ©νλ€λ©΄ `EntityManagerFactory`λ μ±κΈν€ λ±μ κΈ°λ²μ μ¬μ©νμ¬ μ νλ¦¬μΌμ΄μ μ μ²΄μμ μ¬νμ©νλκ² λΉμ©μ μ’λ€.

---

## π JPA μ¬μ©

2021λ 6μ κΈ°μ€ μ κ· νλ‘μ νΈλ λλΆλΆ `Spring-Boot`μΌλ‘ μμνκΈ° λλ¬Έμ `xml`κΈ°λ°μ μ€μ μ μ μΈνλ©°,

`Maven`λ³΄λ€λ `Gradle`μ΄ μμ£Ό νλ°νκ² μ¬μ©λκ³  μμΌλ―λ‘ `Gradle` μ€μ μΌλ‘ λμ²΄νλ€.

λ°μ΄ν°λ² μ΄μ€λ `H2`λ‘ μ€μ νκ³ , `boilerplate code`λ₯Ό μμ±νλ μκ³ λ₯Ό μ€μ΄κΈ° μν΄ `lombok`μ μΆκ°νλ€.

```groovy
//build.gradle

dependencies {
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    annotationProcessor 'org.springframework.boot:spring-boot-configuration-processor'
    
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    runtimeOnly 'com.h2database:h2'
}
```

```yaml
#application.yaml
spring.datasource.url=jdbc:h2:tcp://localhost/~/learn-jpa
spring.datasource.username=sa
spring.datasource.password=
```


```java
@Entity // JPA κΈ°λ₯μ μ¬μ©νκΈ° μν κ°μ²΄μμ λͺμ (νμ΄λΈκ³Ό λ§€νλλ κ°μ²΄) 
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements Serializable {
    private static final long serialVersionUID = 3990803224604257521L;
    
    @Id // ν΄λΉ νλκ° DBμ PKμμ λͺμ 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // κΈ°λ³Έν€ μμ±μ λ΅μ auto_incrementλ‘ μ€μ 
    // DBκ° auto_incrementλ₯Ό μ§μνμ§ μμΌλ©΄ sequenceλ‘ μ€μ λλ€
    private Long id;
    private String name;
    private int age;
    
    @Builder
    public Member(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
```


```java
@DataJpaTest
class MemberTest {
    @PersistenceUnit
    EntityManagerFactory emf; // EntityManagerFactory μμ±
    
    EntityManager em;
    EntityTransaction tx;
    
    @BeforeEach
    void setUp() { // νμ€νΈμΌμ΄μ€κ° μμλλ©΄ λ¨Όμ  μ€νλ  μ½λλΈλ­
        em = emf.createEntityManager(); // EntityManagerFactoryμμ EntityManager μμ±
        tx = em.getTransaction(); // EntityManagerμμ trasaction νλ
    }
    
    @AfterEach
    void tearDown() { // νμ€νΈμΌμ΄μ€κ° μ’λ£λλ©΄ μ€νλ  μ½λλΈλ­
        em.close();
        emf.close();
    }
    
    @Test
    void memberTest() throws Exception {
        tx.begin(); // transaction start
        // given
        Member member = Member.builder()
                              .name("νκΈΈλ")
                              .age(30)
                              .build(); // Member μΈμ€ν΄μ€ μμ±
        
        // when
        em.persist(member); // Member μΈμ€ν΄μ€λ₯Ό μμμ± μ»¨νμ€νΈμ μ μ₯
        em.flush(); // μμμ± μ»¨νμ€νΈμ λ³κ²½μ¬ν­μ DBμ λ°μ
        
        Member findMember = em.find(Member.class, member.getId()); // μμμ± μ»¨νμ€νΈμμ IDλ‘ Memberλ₯Ό μ‘°ν
        
        
        // then
        assertThat(findMember).isSameAs(member); // μμμ± μ»¨νμ€νΈμ μ μ₯λ Memberμ μ‘°νν Memberκ° λμΌνμ§ 
        tx.commit(); // transaction μ’λ£
    }
}
```
