# π Persistence Context
μμ±μ: νμ°½ν

---

## π About Persistence Context

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fdi6KwS%2FbtqWTX1Neqx%2FqHaJqE96vs8MXXOQKoDWk0%2Fimg.png)

`μμμ± μ»¨νμ€νΈ(Persistence Context)`λ κ°λ¨νκ² μ€λͺνμλ©΄ μ νλ¦¬μΌμ΄μκ³Ό λ°μ΄ν°λ² μ΄μ€ μ¬μ΄μ μμΉν κ°μμ λ°μ΄ν°λ² μ΄μ€μ΄λ€.

λ΄λΆ λ°μ΄ν°λ `HashMap`μΌλ‘ κ΅¬μ±λΌμμΌλ©°, **_key=value : id=Entity_** λ‘ λμκ°λ€.

κ·Έλ¦¬κ³  μ΄ κ°μ λ°μ΄ν°λ² μ΄μ€λ₯Ό κ΄λ¦¬νλ κ°μ²΄κ° `EntityManager`μ΄λ€.

`EntityManager`λ `Thraed-Safe`νμ§ μκ³ , κ°μ κ³ μ ν `Scope`λ₯Ό κ°κΈ° λλ¬Έμ λ³΄ν΅ ν μ€λ λμμ νλλ§ μμ±νλ©°

`Transaction`μμμλ§ μ λλ‘ λμνλ―λ‘ `Transaction` μ€μ μ΄ λ§€μ° μ€μνλ€.

`EntityManager`λ₯Ό μμ±νκΈ° μν΄μλ `EntityManagerFactory`κ° νμνλ©° `EntityManagerFactory`λ `DBCP`μ λ§€νλμ΄μλ€.

`Hibernate`μμ `EntityManagerFactory`λ₯Ό κ΅¬νν μ½ν¬λ¦¬νΈ ν΄λμ€λ `SessionFactoryImpl`μ΄λ©° μλμ κ°μ μκ·Έλμ²λ₯Ό κ°λλ€.

```
public interface SessionFactory extends EntityManagerFactory{}

public interface SessionFactoryImplementor extends SessionFactory{}

public class SessionFactoryImpl implements SessionFactoryImplementor{}
```

`SessionFactoryImpl`μ λν΄μ κ³΅μλ¬Έμμμλ νκΈ°μ κ°μ΄ μ€λͺνκ³  μλ€.

> `SessionFactoryImpl`λ `SessionFactory interface`λ₯Ό κ΅¬νν μ½ν¬λ¦¬νΈ ν΄λμ€μ΄λ©°, λ€μκ³Ό κ°μ μ±μμ κ°λλ€
>
> -   μΊμ κ΅¬μ±μ μ€μ νλ€ (λΆλ³μ±μ κ°λλ€)
> -   `Entity-νμ΄λΈ λ§€ν,` `Entity μ°κ΄κ΄κ³ λ§€ν` κ°μ μ»΄νμΌμ± λ§€νμ μΊμνλ€
> -   λ©λͺ¨λ¦¬λ₯Ό μΈμνμ¬ μΏΌλ¦¬λ₯Ό μΊμνλ€
> -   `PreparedStatements`λ₯Ό κ΄λ¦¬νλ€
> -   `ConnectionProvider`μκ² JDBC κ΄λ¦¬λ₯Ό μμνλ€
> -   `SessionImpl`λ₯Ό μμ±ν΄λΈλ€
>
> λν, λ€μκ³Ό κ°μ νΉμ§ λ° μ£Όμμ¬ν­μ΄ μλ€.
>
> μ΄ ν΄λμ€λ ν΄λΌμ΄μΈνΈμκ² λ°λμ λΆλ³ κ°μ²΄λ‘ λ³΄μ¬μΌ νλ©°, μ΄λ λͺ¨λ  μ’λ₯μ μΊμ± νΉμ νλ§μ μνν  κ²½μ°μλ λ§μ°¬κ°μ§λ‘ μ μ©λλ€.  
> `SessionFactoryImpl`λ `Thread-Safe νκΈ°` λλ¬Έμ λμμ μΌλ‘ μ¬μ©λλ κ²μ΄ λ§€μ° ν¨μ¨μ μ΄λ€. λν λκΈ°νλ₯Ό μ¬μ©ν  κ²½μ° λ§€μ° λλ¬Όκ² μ¬μ©λμ΄μΌ νλ€.

μ¦, `EntityManagerFactory`λ λΆλ³ κ°μ²΄μ΄κΈ° λλ¬Έμ `Thread-Safe νλ©°` μ΄ λ§μΈμ¦μ¨, `μ±κΈν€ ν¨ν΄`μ μ΄μ©νμ¬ κ³μν΄μ μ¬νμ©ν  μ μμλ μλ―Ένλ€κ³  λ³Ό μ μλ€.

λ€μμ `μ±κΈν€`κ³Ό `μ μ  ν©ν λ¦¬`λ₯Ό νμ©νμ¬ `EntityManagerFactory`μ `EntityManager`λ₯Ό μ¬μ©νλ μμ μ΄λ€.

```
public class PersistenceFactory {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnitName");

    private PersistenceFactory() {
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}

@SpringBootTest
class JpaTest {
    @Test
    @DisplayName("μμ_JPA_νμ€νΈ_μν")
    void jpaTest() throws Exception {
        EntityManager em = PersistenceFactory.getEntityManager(); // get Singleton instance
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        // given
        Member member = Member.builder()
                              .name("νκΈΈλ")
                              .age(30)
                              .build();

        // when
        try {
            em.persist(member);
            em.flush();

            Member findMember = em.find(Member.class, member.getId());

            // then
            assertThat(findMember).isSameAs(member);
            tx.commit();
        }
        catch(Exception e) {
            tx.rollback();
        }
        finally {
            em.close();
        }
    }
}
```

`μμμ± μ»¨νμ€νΈ(Persistence Context)`λ₯Ό μ¬μ©νκΈ° μν΄μλ `EntityManager`κ° νμνκ³ , `EntityManager`λ₯Ό μ¬μ©νκΈ° μν΄μλ `EntityManagerFactory`κ° νμν¨μ μμμΌλ©°, `EntityManagerFactory`μ λν΄ λμΆ©μ΄λλ§ μμλ³΄μλ€.

κ·Έλ λ€λ©΄ μ΄μ  `EntityManager`μ λν΄ μμλ³΄μ.

μ°μ  `EntityManager`λ νΉμ΄ν μ±μ§μ νλ κ°λλ€. `DB Connection`μ `LAZY` μνλ‘ κ°μ Έκ°λ€λ κ²μΈλ°,

μ΄κ² λ¬΄μ¨ λ§μ΄λλ©΄, `EntityManager`κ° μμ±λλ©° `DBCP`μ λ§€νλ `EntityManagerFacotry`μμ μμ°μ€λ½κ² `Connection`μ μ»μ§λ§ μ΄ `Connection`μ΄ κΌ­ νμν μν©, μ¦ νμ  ν  `flush` λ©μλκ° νΈμΆλλ μμ μμμΌ `Connection`μ μ¬μ©νλ€λ μ μ΄λ€.

μ΄λ `Connection`μ΄ κ³Όλνκ² λ¨μ©λμ΄ `Connection`μ΄ λΆμ‘±ν΄μ§ μν©μ λλΉνκΈ° μν μ€κ³ μλλ‘ λ³΄μΈλ€.

μ΄λ¬ν μμ±μ΄ μμμ μ°μ  μΈμ§νμ.

`EntityManager`λ μ νλ¦¬μΌμ΄μκ³Ό λ°μ΄ν°λ² μ΄μ€ μ¬μ΄μ μμΉν κ°μμ λ°μ΄ν°λ² μ΄μ€λΌκ³  νμμλ€.

μΌλ¨ κ³΅μλ¬Έμλ₯Ό λ¨Όμ  μ°Έκ³ νλ€.

> `EntityManager` μΈμ€ν΄μ€λ `μμμ± μ»¨νμ€νΈ(PersistenceContext)`μ μ°κ²°λλ©°, μμμ± μ»¨νμ€νΈλ λ¨μν `Entity`μ μ§ν©μ΄λ€.
>
> μμμ± μ»¨νμ€νΈ λ΄μμ λͺ¨λ  `Entity` μΈμ€ν΄μ€μ μλͺμ£ΌκΈ°κ° κ΄λ¦¬λλ©°, `EntityManager`μ λͺ¨λ  `Public API`λ€μ μ΄ μλͺμ£ΌκΈ°λ₯Ό κ΄λ¦¬νλ λ° μ¬μ©λλ€.

μ°μ  μ΄λ₯Ό μ΄λ―Έμ§λ‘ λ³΄λ©΄ λ€μκ³Ό κ°λ€.

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FEAuNE%2FbtqWXbjZeim%2FZtefOIORznYkSE48qJZXJK%2Fimg.png)

μ μ΄λ―Έμ§μμ μμμ± μ»¨νμ€νΈλΌκ³  μ νμλ νλ `μμμ± μ»¨νμ€νΈ(PersistenceContext)`κ° `HashMap`μΌλ‘ κ°μ§κ³  μλ `Entity`λ€μ μλ―Ένλ€.

λν `findById`, `flush` λ±μ APIλ€μ μ΄ `μμμ± μ»¨νμ€νΈ(PersistenceContext)`λ₯Ό κ΄λ¦¬νλ `EntityManager`μ APIλΌκ³  λ³Ό μ μκ² λ€.

μΌλ°μ μΌλ‘ `HashMap` Keyμ λ°μ΄ν°λ² μ΄μ€μ PKκ°μ΄ λ€μ΄κ°κ² λλ©°,

`Entity` κ°μ²΄μ `λ°μ΄ν°λ² μ΄μ€ νμ΄λΈ`μ λ§€ννκ³  **_(μ°κ΄κ΄κ³ λ§€ν)_** λ°μνλ λ°μ΄ν°λ€μ λ°μ΄ν°λ² μ΄μ€μ μ§μ  ν΅μ νλ κ²μ΄ μλ,

λ΄λΆμ μΌλ‘ μ‘΄μ¬νλ `HashMap`μ μ¬μ©ν΄ `EntityManager`κ° κ°μμ λ°μ΄ν°λ² μ΄μ€ **_(=μμμ± μ»¨νμ€νΈ)_** λ₯Ό μ μ΄νλ©° μ²λ¦¬νλ κ²μ΄λ€.

μ΄λ κ² ν¨μΌλ‘μ¨ `DB Connection`μ μ΅μννμ¬ λ°μ΄ν°λ² μ΄μ€ λΆνλ₯Ό μ€μΌ μ μκ² λλ€.

μ΄ν μμμ΄ λλκ±°λ μμ μ€μ `flush`κ° νΈμΆλλ©΄ `μμμ± μ»¨νμ€νΈ(PersistenceContext)`μ λ°μ΄ν°λ₯Ό λͺ¨λ νκΊΌλ²μ λ°μ΄ν°λ² μ΄μ€μ λ°μνλ€.

λλ΅ μ΄λ¬ν κ΅¬μ‘°λ‘ λμκ°λ©°, μ΄ μ λμ μ€λͺμΌλ‘λ μμ§ κ°μ΄ μ‘νμ§ μμ μ μλ€. κ΄μ°?λ€. JPAμμ κ°μ₯ μ΄ν΄νκΈ° νλ  μμ­μ΄κΈ° λλ¬Έμ΄λ€.

λλ΅μ μΈ κ°μ΄λΌλ μ‘κ³  λ§μ μλ£λ₯Ό λ³΄λ©° μ¬μ©νλ€ λ³΄λ©΄ μ΄ν΄νλ μκ°μ΄ λ°λμ μ¬ κ²μ΄λ€.

μ°μ  `μμμ± μ»¨νμ€νΈ(PersistenceContext)`μ μνκ³  `EntityManager`κ° μ²λ¦¬νλ λ°μ΄ν°λ€μλ 4κ°μ§ μνκ° μ‘΄μ¬νλ€.

μ΄λ₯Ό `Entity μλͺμ£ΌκΈ°(Life Cycle)`λΌκ³  λΆλ₯Έλ€.

μ€λͺνκΈ° μ μ μ°μ  μμ μ½λλ₯Ό λ€μ μ²¨λΆνλ€.

```
@SpringBootTest
class JpaTest {
    @Test
    @DisplayName("μμ_JPA_νμ€νΈ_μν")
    void jpaTest() throws Exception {
        EntityManager em = PersistenceFactory.getEntityManager(); // get Singleton instance
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        // given
        Member member = Member.builder()
                              .name("νκΈΈλ")
                              .age(30)
                              .build();

        // when
        try {
            em.persist(member);
            em.flush();

            Member findMember = em.find(Member.class, member.getId());

            // then
            assertThat(findMember).isSameAs(member);
            tx.commit();
        }
        catch(Exception e) {
            tx.rollback();
        }
        finally {
            em.close();
        }
    }
}
```

`μμμ± μ»¨νμ€νΈ(PersistenceContext)`μ μν `Entity`κ°μ²΄λ λ€μκ³Ό κ°μ 4κ°μ§ `μλͺμ£ΌκΈ°(Life Cycle)`λ₯Ό κ°λλ€.

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbB7G1m%2FbtqWX5X6QAM%2FH51gsvUP1ojcyLUS39zwE1%2Fimg.png)

> `λΉμμ(transient)`
>
> -   Entityκ° λ°©κΈ μΈμ€ν΄μ€ν λμμΌλ©° μμ§ μμμ± μ»¨νμ€νΈμ μ°κ΄λμ§ μμ μν
> -   λ°μ΄ν°λ² μ΄μ€μ λκΈ°νλμ§ μμμΌλ©° μΌλ°μ μΌλ‘ ν λΉλ μλ³μ κ°μ΄ μ¬μ©λμ§ μμκ±°λ μλ³μ κ°μ΄ ν λΉλμ§ μμ μνλ₯Ό μλ―Έ

```
Member member = Member.builder()
                      .name("νκΈΈλ")
                      .age(30)
                      .build();
```

λ³΄λ€μνΌ `Entity` κ°μ²΄κ° λ§ μμ±λλ€.

μ΄ κ°μ²΄μλ `id`κ° μκΈ° λλ¬Έμ `EntityManager`λ μ΄ κ°μ²΄μ `μμμ± μ»¨νμ€νΈ(PersistenceContext)`λ₯Ό λΉκ΅νμ¬ λΉμμ μνλ‘ κ°μ£Όνκ² λλ€.

> `μμ(managed or persistent)`
>
> -   Entityμ μ°κ΄λ μλ³μκ° μμΌλ©° μμμ± μ»¨νμ€νΈμ μ°κ΄λ μν
> -   λ°μ΄ν°λ² μ΄μ€μ λΌλ¦¬μ μΌλ‘ λκΈ°νλμ΄ μμΌλ λ¬Όλ¦¬μ μΌλ‘λ μ‘΄μ¬νμ§ μμ μλ μλ μν

```
em.persist(member);
```

λΉμμ μνμΈ `Entity`μ λ°μ΄ν°κ° `μμμ± μ»¨νμ€νΈ(PersistenceContext)`μ λ±λ‘λμμΌλ©°, λ΄λΆμ μΌλ‘ `HashMap`μΌλ‘ `Entity`λ₯Ό κ΄λ¦¬νκΈ° λλ¬Έμ Keyκ°μ΄ λ°λμ νμνλ€.

νμ§λ§ λ°©κΈ λ±λ‘λ `Entity`λ λΉμμ μνμ΄λ―λ‘ Keyκ°μΌλ‘ μ¨μΌ ν  idκ° nullμ΄κΈ° λλ¬Έμ `Entity` κ°μ²΄μ μ μΈλ `@GeneratedValue`λ₯Ό μ°Έμ‘°νμ¬ idλ₯Ό μμ±ν΄λΈλ€. μλ¬΄κ²λ μ€μ νμ§ μμ κ²½μ° `AUTO`λ‘ λμνλ©°, `AUTO`λ `DBCP`μ μ°κ²°λ λ°μ΄ν°λ² μ΄μ€μ κΈ°λ³Έ λμμ λ°λΌκ°λ€.

μΌλ°μ μΌλ‘ κ΅­λ΄ μκ³μμ κ°μ₯ λ§μ΄ μ¬μ©νλ μμ© λ°μ΄ν°λ² μ΄μ€λ `MySQL` νΉμ `MariaDB`μΈλ° **_(λ¬΄λ£μ¬μ)_**, μ΄ λ°μ΄ν°λ² μ΄μ€λ€μ κ²½μ° `auto_increment`λ₯Ό μ§μνλ―λ‘ μ΄ κ²½μ° `IDENTITY`μ λ΅μ ν΅ν΄ μ¬μ©νκ² λλ€.

```
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

> `μ€μμ(detached)`
>
> -   Entityμ μ°κ΄λ μλ³μκ° μμ§λ§ λ μ΄μ μμμ± μ»¨νμ€νΈμ μ°κ΄λμ§ μμ μν(λΆλ¦¬λ μν)
> -   μΌλ°μ μΌλ‘ μμμ± μ»¨νμ€νΈκ° close λμκ±°λ μΈμ€ν΄μ€κ° μμμ± μ»¨νμ€νΈμμ μ κ±°λ μνλ₯Ό μλ―Έ

```
em.detach(member);
```

μ΄ μ€μμ μνκ° μ²μμ λ§μ΄ ν·κ°λ¦°λ€.

νμ§λ§ λ€νμ€λ½κ²λ μ€μμ μνμ λΉμμ μνλ κ²°μ μ μΈ μ°¨μ΄κ° λ± νλ μ‘΄μ¬νλ€.

λΉμμ μνλ μλ³μκ° μμ μλ μκ³ , μμ μλ μλ€. μ΄κ² λ¬΄μ¨ λ§μ΄λλ©΄,

μκΈ° λΉμμ μν μμ λ κ°λ°μκ° `Entity` κ°μ²΄ μμ± μ idκ°μ μ΄κΈ°ννμ§ μμμΌλ―λ‘ μλ³μκ° μ‘΄μ¬νμ§ μλλ€. **_(WrapperμΈ Longμ΄λ―λ‘ Nullable νλ€)_**

νμ§λ§ μ€μμ μνμ κ²½μ° μλ³μκ° **_λ°λμ_** μ‘΄μ¬νλ€.

μ€μμ μνλ κ°μ²΄κ° `μμμ± μ»¨νμ€νΈ(PersistenceContext)`μ νλ² μνλ€κ° λ¨μ΄μ Έ λμ¨ κ°μ²΄. μ¦ `μμμ± μ»¨νμ€νΈ(PersistenceContext)` λ΄λΆ `HashMap`μμ `Entity`κ° μ­μ λ μνλ₯Ό μλ―Ένλλ°,

λΉμμ μνμ κ°μ²΄λ₯Ό `EntityManager`λ₯Ό ν΅ν΄ `μμμ± μ»¨νμ€νΈ(PersistenceContext)`μ λ±λ‘νκ² λλ©΄ idλ₯Ό μ΄λ»κ²λ  μμ±ν΄λ΄κ³ , λ°λλ‘ λ°μ΄ν°λ² μ΄μ€μμ λ°μ΄ν°λ₯Ό μ½μ΄μλ€λ©΄ λ°μ΄ν°λ² μ΄μ€ νμ΄λΈμ κΈ°λ³Έ ν€κ°Β μμμκ° μκΈ°λλ¬Έμ **_(λ¬΄κ²°μ± μ μ½μ‘°κ±΄)_** `μμμ± μ»¨νμ€νΈ(PersistenceContext)`μλ idκ° λ°λμ μ‘΄μ¬νκΈ° λλ¬Έμ΄λ€.

μ¬κΈ°μ μ¬λ―Έμλ νμμ΄ νλ μκΈ°λλ°, κ·Έλ λ€λ©΄ κ°λ°μκ° μ²μ `Entity` κ°μ²΄λ₯Ό μμ±ν΄λΌ λ idλ₯Ό μ΄κΈ°νν΄μ μμ±νλ€λ©΄ μ΄λ»κ² λ κΉ?

μ λ΅μ `EntityManager`κ° idκ° μ‘΄μ¬νλ λΉμμ μνμ `Entity`κ°μ²΄λ₯Ό λΉμμ μνκ° μλ μ€μμ μνλ‘ κ°μ£Όνλ€.

`EntityManager`μμ₯μμ μ€μμ μνλΌλ κ²μ `μμμ± μ»¨νμ€νΈ(PersistenceContext)` λ΄λΆ `HashMap`μ κ°μ μλ³μλ₯Ό κ°λ `Entity`κ° μ‘΄μ¬νμ§ μλλ°, `Entity`μλ μλ³μκ° μλ λͺ¨λ  κ²½μ°λ₯Ό μλ―ΈνκΈ° λλ¬Έμ΄λ€.

κ·Έλμ μ΄λ° κ²½μ° `persist` λΏλ§ μλλΌ `merge` λ©μλλ λ¨Ήνλ€. λΉμμ μνμμλ λΆκ΅¬νκ³  λ§μ΄λ€.

> `μ­μ (removed)`
>
> -   Entityμλ μ°κ΄λ μλ³μκ° μκ³  μμμ± μ»¨νμ€νΈμ μ°κ΄λμ΄ μμ§λ§ λ°μ΄ν°λ² μ΄μ€μμ μ­μ λλλ‘ μμ½λ μν
> -   μ΅μ’μ μΌλ‘ μμμ± μ»¨νμ€νΈμ λ°μ΄ν°λ² μ΄μ€μμ λͺ¨λ μ­μ λλ€

```
em.remove(member);
```

μ΄ λ©μλκ° νΈμΆλλ©΄ ν΄λΉ `Entity`κ°μ²΄λ `EntityManager`λ₯Ό ν΅ν΄ `μμμ± μ»¨νμ€νΈ(PersistenceContext)`μμ μ­μ κ° **_μμ½_** λλ©°,

μ΄ν `flush`κ° νΈμΆλλ©΄ `μμμ± μ»¨νμ€νΈ(PersistenceContext)`μμ μ­μ λ¨κ³Ό λμμ λ°μ΄ν°λ² μ΄μ€λ₯Ό ν₯ν΄ `deleteμΏΌλ¦¬`κ° λ°μνλ€.

---

## π 1μ°¨ μΊμ

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fb4VXkb%2FbtqV0DQnvHx%2FWdrqKf43s8SZzqOp5gtXqK%2Fimg.png)

`μμ(persistent)` μνμ `Entity`λ λͺ¨λ μ΄κ³³μ `HashMap(key(@Id) = value(Entity))`μΌλ‘ μ μ₯λλ€.

λ°λΌμ `μλ³μ(@Id)`κ° μλ€λ©΄ μ λλ‘ λμνμ§ λͺ»νλ©° μμΈλ₯Ό λ°μμν¨λ€.

```
// Entity - λΉμμ(transient)
Member member = new Member();
member.setId("member1");
member.setUsername("member");


// Entity - μμ(persistent)
em.persist(member);


// 1μ°¨ μΊμ μ‘°ν
em.find(Member.class, "member1");
```

JPAλ μ΄λ ν λͺλ Ήμ λν΄ λ°μ΄ν°λ² μ΄μ€μ μ κ·ΌνκΈ° μ μ ν­μ μ΄ 1μ°¨ μΊμμμ λ¨Όμ  μμμ μ§ννλ€.

μ΄λ κ² λμνλ μ΄μ λ λ°μ΄ν°λ² μ΄μ€ μ μμ μ΅μννμ¬ λ¦¬μμ€λ₯Ό ν¨μ¨μ μΌλ‘ μ¬μ©ν  μ μκΈ° λλ¬Έμ΄λ€.

λ°λΌμ μ μ½λμμ `em.find()`κ° μ€νλ  μμ μ μ€μ  λ°μ΄ν°λ² μ΄μ€μ μ κ·Όν΄μ κ°μ κ°μ Έμ€λ κ² μλκ³ 

1μ°¨ μΊμμ μ μ₯λμ΄ μλ **_(νμ§λ§ λ°μ΄ν°λ² μ΄μ€μλ μμ§ λ°μλμ§ μμ)_** member κ°μ²΄λ₯Ό κ°μ Έμ¨λ€.

```
@Test
public void cashTest() {
    try {

        System.out.println("=================== new Member ===================");
        Member member = Member.builder()
                              .name("κ°λμ")
                              .age(30)
                              .build();
        System.out.println("=================== em.persist ===================");
        em.persist(member);
        System.out.println("==================================================");

        System.out.println("=================== em.find ===================");
        Member findMember = em.find(Member.class, member.getId());
        System.out.println("member = " + findMember);

        tx.commit();
    }
    catch(Exception e) {
        tx.rollback();
    }
}
```

```
=================== new Member ===================
=================== em.persist ===================
Hibernate: 
    call next value for hibernate_sequence
==================================================
=================== em.find ===================
member = Member(id=1, name=κ°λμ, age=30)
Hibernate: 
    /* insert board.Member
        */ insert 
        into
            MEMBER
            (age, name, id) 
        values
            (?, ?, ?)
```

μΏΌλ¦¬ μμλ₯Ό λ³΄μ.

`em.find()`κ° μ€νλ νμμΌ `insertμΏΌλ¦¬`κ° λκ°κ³  `selectμΏΌλ¦¬`λ κ·Έ μ΄λμλ λ³΄μ΄μ§ μλλ€.

μ μ΄λ° νμμ΄ λ°μνλλ©΄, `em.persist()`κ° μ€νλ  λ λ°μ΄ν°λ² μ΄μ€μ μ μ₯λ κ² μλκ³ 

μμμ± μ»¨νμ€νΈμ 1μ°¨ μΊμμ λ¨Όμ  μ μ₯λμ΄μλ μνμμ `em.find()`κ° μ€νλκΈ° λλ¬Έμ΄λ€.

`EntityManager`λ μ ν΄μ§ κ·μΉλλ‘ λ°μ΄ν°λ² μ΄μ€μ μ κ·ΌνκΈ° μ  1μ°¨ μΊμλ₯Ό λ¨Όμ  λ€μ‘κ³ , 1μ°¨ μΊμμμ μλ³μλ₯Ό ν΅ν΄ μλ§μ κ°μ²΄λ₯Ό μ°Ύμ κ°μ Έμ¨ κ²μ΄λ€.

λ°λΌμ `selectμΏΌλ¦¬`λ₯Ό λ°μ΄ν°λ² μ΄μ€μ λ λ¦΄ νμκ° μκ² λ κ²μ΄λ€.

κ·Έ ν `tx.commit()`μ΄ μ€νλλ μμ μμμΌ `flush`κ° νΈμΆλλ©° λ°μ΄ν°λ² μ΄μ€μ `insertμΏΌλ¦¬`λ₯Ό λ λ¦° κ²μ΄λ€.

`EntityTransaction` κ°μ²΄μ `commit()` λ©μλλ₯Ό μ΄ν΄λ³΄λ©΄ μ€μ λ‘ λ΄λΆμ `flush` λ©μλκ° ν¬ν¨λμ΄ μμμ μ μ μλ€.

```
@Override
public void commit() {
    errorIfInvalid();
    getTransactionCoordinatorOwner().flushBeforeTransactionCompletion(); // flush

    // we don't have to perform any before/after completion processing here.  We leave that for
    // the Synchronization callbacks
    jtaTransactionAdapter.commit();
}
```

λ€λ₯Έ μμ λ₯Ό λ³΄κ² λ€.

```
@Test
public void cashTest() {
    try {

        System.out.println("=================== new Member ===================");
        Member member = Member.builder()
                              .name("κ°λμ")
                              .age(30)
                              .build();
        System.out.println("=================== em.persist ===================");
        em.persist(member);
        System.out.println("==================================================");

        em.flush();
        em.clear();

        System.out.println("=================== em.find ===================");
        Member findMember = em.find(Member.class, member.getId());
        System.out.println("member = " + findMember);

        tx.commit();
    }
    catch(Exception e) {
        tx.rollback();
    }
}
```

```
=================== new Member ===================
=================== em.persist ===================
Hibernate: 
    call next value for hibernate_sequence
==================================================
Hibernate: 
    /* insert board.Member
        */ insert 
        into
            MEMBER
            (age, name, id) 
        values
            (?, ?, ?)
=================== em.find ===================
Hibernate: 
    select
        member0_.id as id1_1_0_,
        member0_.age as age2_1_0_,
        member0_.name as name3_1_0_ 
    from
        MEMBER member0_ 
    where
        member0_.id=?
member = Member(id=1, name=κ°λμ, age=30)
```

μ΄λ²μ μ€κ°μ `EntityManger`λ₯Ό ν΅ν΄ `μμμ± μ»¨νμ€νΈ(Persistence Context)`λ₯Ό κ°μ λ‘ `flush` β `clear` νλ€.

μ΄λ μμμ± μ»¨νμ€νΈμ μ μ₯λΌμλ `Entityλ₯Ό` λͺ¨λ λ°μ΄ν°λ² μ΄μ€μ `λκΈ°ν(flush)`νκ³ ,

μμμ± μ»¨νμ€νΈμ λͺ¨λ  `Entity`λ₯Ό `μ κ±°(clear)`νμμ μλ―Ένλ€.

μ€μ  μΏΌλ¦¬κ° λκ°λ μμλ₯Ό λ³΄λ©΄ `flush` β `clear` ν  λ `insertμΏΌλ¦¬`κ° μ€νλκ³ ,

κ·Έ ν `em.find()` λ©μλκ° μ€νλλ©° 1μ°¨ μΊμλ₯Ό λ€μ‘μ§λ§

μλ§μ `Entity`μ λ³΄λ₯Ό μ°Ύμ§ λͺ»ν΄ κ·Έμ μΌ λ°μ΄ν°λ² μ΄μ€μ μ κ·Όνλ©° `selectμΏΌλ¦¬`κ° λκ°μ νμΈν  μ μλ€.

---

## π λμΌμ± λ³΄μ₯

1μ°¨ μΊμμ μ°κ΄λ λ΄μ©μ΄λ€.

κ·Έλ₯ κ°λ¨νκ² 1μ°¨ μΊμλ₯Ό ν΅ν΄ λͺ¨λ  μμμ μ²λ¦¬νκΈ° λλ¬Έμ κ°μ²΄μ λμΌμ± **_(λ©λͺ¨λ¦¬ μ£Όμκ° κ°μμ μλ―Ένλ€)_** μ΄ λ³΄μ₯λλ€λ μ΄μΌκΈ°λ€.

κ°λ¨νκ² μ½λλ‘ λ³΄μ.

```
@Test
public void cashTest() {
    try {

        System.out.println("=================== new Member ===================");
        Member member = Member.builder()
                              .name("κ°λμ")
                              .age(30)
                              .build();
        System.out.println("=================== em.persist ===================");
        em.persist(member);

        System.out.println("=================== em.find ===================");
        Member findMember1 = em.find(Member.class, member.getId());
        Member findMember2 = em.find(Member.class, member.getId());
        System.out.println("compare = " + (findMember1 == findMember2));

        tx.commit();
    }
    catch(Exception e) {
        tx.rollback();
    }
}
```

```
=================== new Member ===================
=================== em.persist ===================
Hibernate: 
    call next value for hibernate_sequence
=================== em.find ===================
compare = true
Hibernate: 
    /* insert board.Member
        */ insert 
        into
            MEMBER
            (age, name, id) 
        values
            (?, ?, ?)
```

`em.persist(member)`κ° μ€νλλ©° 1μ°¨ μΊμμ member `Entity`μ μ λ³΄κ° μ μ₯λμκ³  **_(μμ μν)_**,

```
Member findMember1 = em.find(Member.class, member.getId());
Member findMember2 = em.find(Member.class, member.getId());
```

λ₯Ό ν΅ν΄ 1μ°¨ μΊμμμ κ°μ `Entity`κ°μ²΄λ₯Ό λ λ² κ°μ Έμ κ°κ° λ€λ₯Έ λ νΌλ°μ€ λ³μμ μ£Όμλ₯Ό ν λΉνλ€.

λ°λΌμ λ κ°μ²΄λ κ°μ μ£Όμλ₯Ό κ°μ§λ―λ‘ μλ²½νκ² λμΌνκΈ° λλ¬Έμ

> compare = true

λΌλ κ²°κ³Όκ° λμ¨λ€.

λ§μ§λ§μΌλ‘ `tx.commit()` λ©μλκ° μ€νλλ©° `insertμΏΌλ¦¬`κ° λκ°λ€.

1μ°¨ μΊμλ₯Ό μ λλ‘ μ΄ν΄νλ€λ©΄ λλ¬΄λ λΉμ°ν μ΄μΌκΈ°λ€.

---

## π μ°κΈ° μ§μ°, μ§μ° λ‘λ©, λ³κ²½ κ°μ§

μ­μ λͺ¨λ 1μ°¨ μΊμμ κ΄λ ¨λ λ΄μ©μ΄λ€.

μ°κΈ° μ§μ°μ μ μ½λμ κ°μ΄ `insertμΏΌλ¦¬`κ° `flush`κ° νΈμΆλλ μμ μ λ°μνλ κ²μ μλ―Ένλ©°,

μ§μ° λ‘λ© λν λ§μ°¬κ°μ§λ‘ μ μ½λμ κ°μ΄ μ€μ  μμμ΄ μ€νλλ μκ°μ `selectμΏΌλ¦¬`κ° λκ°λ νμμ λ§νλ€.

κ° μμλ€μ 1μ°¨ μΊμμ μ μ₯ν΄ λκ³  μ΄λ₯Ό ν λ²μ μμν΄μ ν¨μ¨μ μ¬λ¦¬κ² λ€λ μ€κ³ μλλ‘ λ³΄μΈλ€.

λ³κ²½ κ°μ§μ κ²½μ° JPAμλ `update` κ΄λ ¨ APIκ° λ°λ‘ μ‘΄μ¬νμ§ μλλ° μ΄μ λν λ΄μ©μ΄λ€.

`EntityManager`κ° `μμμ± μ»¨νμ€νΈ(Persistence Context)`λ₯Ό ν΅ν΄ κ΄λ¦¬νλ `Entity`λ κ°μ²΄μ μ λ³΄κ° λ³κ²½λ  κ²½μ° `EntityManager`κ° μ΄λ₯Ό κ°μ§νκ³ 

`updateμΏΌλ¦¬`λ₯Ό μλμΌλ‘ λ λ €μ€λ€.

μ΄λ `μμμ± μ»¨νμ€νΈ(Persistence Context)` λ΄λΆμ κ° `Entity` κ°μ²΄μ λν `μ€λμ·(Snap-Shot)`μ΄ μ‘΄μ¬νκΈ° λλ¬Έμ κ°λ₯ν μΌμ΄λ€.

μ­μ μ½λλ₯Ό λ³΄μ.

```
@Test
public void cashTest() {
    try {
        System.out.println("=================== new Member ===================");
        Member member = Member.builder()
                              .name("κ°λμ")
                              .age(30)
                              .build();
        System.out.println("=================== em.persist ===================");
        em.persist(member);

        System.out.println("=================== em.find ===================");
        Member findMember = em.find(Member.class, member.getId());

        System.out.println("=================== em.update ===================");
        findMember.setName("κ°λμμλ");

        tx.commit();
    }
    catch(Exception e) {
        tx.rollback();
    }
}
```

```
=================== new Member ===================
=================== em.persist ===================
Hibernate: 
    call next value for hibernate_sequence
=================== em.find ===================
=================== em.update ===================
Hibernate: 
    /* insert board.Member
        */ insert 
        into
            MEMBER
            (age, name, id) 
        values
            (?, ?, ?)
Hibernate: 
    /* update
        board.Member */ update
            MEMBER 
        set
            age=?,
            name=? 
        where
            id=?
```

λ λμ μ»κ³  λ€μ λ΄λ μ μ½λ μ΄λμλ `em.update(member) κ°μ` μ½λλ λ³΄μ΄μ§ μλλ€

κ·ΈλΌμλ λΆκ΅¬νκ³  μ€μ λ‘ `updateμΏΌλ¦¬`κ° λ μκ°λ€.

νμ§λ§ κ°λ§ λ³΄λ©΄ μ΄μ§ μ΄μν λΆλΆμ΄ λ³΄μΈλ€.

`insertμΏΌλ¦¬`κ° λ¨Όμ  νλ² λκ° ν `updateμΏΌλ¦¬`κ° λκ°λ€.

μ΄λ JPAκ° λμνλ λ΄λΆμ μΈ λ°©μμΌλ‘ μΈν νμμΈλ°.

`em.persist(member)`κ° μ€νλ  λ `μμμ± μ»¨νμ€νΈ(Persistence Context)`μ memberκ°μ²΄κ° μ μ₯λλ©°

μ΄λμ μν(μ΅μ΄ μν)λ₯Ό λ°λ‘ μ μ₯νλλ° μ΄λ₯Ό λ°λ‘ `μ€λμ·(Snap-Shot)`μ΄λΌ νλ€.

κ·Έλ¦¬κ³  `EntityManager`λ νΈλμ­μμ΄ `commit` λλ μμ μ νμ¬ `μμμ± μ»¨νμ€νΈ(Persistence Context)`μ μ λ³΄μ `μ€λμ·(Snap-Shot)`μ μ λ³΄λ₯Ό λΉκ΅νμ¬ update μΏΌλ¦¬λ₯Ό μμ±νκΈ° λλ¬Έμ μ΅μ΄μ insert μΏΌλ¦¬κ° ν λ² λ μκ° ν λΉκ΅λ₯Ό ν΅ν΄ μμ±λ `updateμΏΌλ¦¬`κ° νλ² λ λ μκ°λ κ²μ΄λ€.

---

## π κ²μ¦

μλμ μμ λ `@GeneratedValue(strategy = GenerationType.IDENTITY)`κ° μλ, `@GeneratedValue(strategy = GenerationType.SEQUENCE)`λ‘ μ§ννμλ€.

```
@Test
public void persistenceContextTest() throws Exception {
    try {
        Member member = Member.builder()
                              .name("νκΈΈλ")
                              .age(30)
                              .build();

        System.out.println("=============== persist() ===============");
        em.persist(member);

        System.out.println("=============== detach() ===============");
        em.detach(member);

        System.out.println("=============== commit() ===============");
        tx.commit();

    }
    catch(Exception e) {
        System.out.println("EXCEPTION -> " + e.getMessage());
        tx.rollback();
    }
}
```

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcETBt8%2FbtqWXaFkCf3%2F83HweDUe1gWMR6AlYZewyK%2Fimg.png)

`em.persist()`κ° μ€νλλ μμ μ μ°κΈ° μ§μ° μ μ₯μμ `insertμΏΌλ¦¬`λ₯Ό μμ±νκΈ° μν΄

`hibernate_sequence`μ μ±λ²μ μμ²­νμμ μ μ μλ€.

> Hibernate: call next value for hibernate\_sequence

ν΄λΉ μμ μ 1μ°¨ μΊμμλ `hibernate_sequence`μμ μ»μ΄μ¨ μλ³μ κ°μΌλ‘

memberμ μ λ³΄μ memberμ `insertμΏΌλ¦¬`κ° λ€μ΄μλ μνμ΄λ€.

μ§ν memberλ `detach` λ©μλλ‘ μΈν΄ `μ€μμ μν`κ° λμμΌλ―λ‘

1μ°¨μ μΌλ‘ 1μ°¨ μΊμμμ memberμ μ λ³΄κ° μ­μ λκ³ ,

2μ°¨μ μΌλ‘ μ°κΈ° μ§μ° SQL μ μ₯μμ μ μ₯λμ΄μλ `insertμΏΌλ¦¬`λ μ­μ λμλ€.

λ°λΌμ μ»€λ° μμ μ μλ¬΄λ° μΏΌλ¦¬λ λκ°μ§ μμλ€.

---

```
@Test
public void persistenceContextTest() throws Exception {
    try {
        Member member = Member.builder()
                              .name("νκΈΈλ")
                              .age(30)
                              .build();

        System.out.println("=============== persist() ===============");
        em.persist(member);

        System.out.println("=============== flush() ===============");
        em.flush();

        System.out.println("=============== detach() ===============");
        em.detach(member);

        System.out.println("=============== merge() -> update ===============");
        Member mergeMember = em.merge(member);
        System.out.println("λ³ν© μμ μ μ΄λ¦μ = " + mergeMember.getName());
        mergeMember.setName("νκΈΈλ μλλ€");
        System.out.println("μμ  ν μ΄λ¦μ = " + mergeMember.getName());

        System.out.println("=============== commit() ===============");
        tx.commit();

    }
    catch(Exception e) {
        System.out.println("EXCEPTION -> " + e.getMessage());
        tx.rollback();
    }
}
```

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FWzylL%2FbtqWUpwWMO2%2FPOphoiEdZOQsWMas8tvRz1%2Fimg.png)

μ€μμ μνμ λΉμμ μνκ° λ€λ₯Έ μ μ μλ³μμ μ λ¬΄λΌκ³  νμλ€.

λΉμμ μνλ μλ³μκ° μμ μλ μκ³  μμ μλ μλ€.

μ€μμ μνλ λΉμμ μνμ κ±°μ λμΌνμ§λ§ νλ² μμ μνμλ€κ° μμ μνκ° μλκ² λ κ²½μ°μ΄λ―λ‘

μ€μμ μνλ λ°λμ μλ³μ κ°μ κ°μ§κ³  μλ€.

μ μ½λμ μ€ν νλ¦μ λ³΄λ©΄ μ²μ member κ°μ²΄λ

νκΈΈλμ΄λΌλ μ΄λ¦μΌλ‘ λΉμμ μνκ° λμκ³  `persist` λμ΄ `μμ μν`κ° λμλ€.

κ·Έλ¦¬κ³  `flush`κ° νΈμΆλλ©° ν΄λΉ μ λ³΄λ λ°μ΄ν°λ² μ΄μ€μ λ°μλλ©° `insertμΏΌλ¦¬`κ° λκ°λ€.

μ§ν `detach` μ€ν ν `μ€μμ μν`λ‘ λ³κ²½λλ©° μμμ± μ»¨νμ€νΈμμ ν΄λΉ κ°μ²΄κ° μ­μ λ μνμμ

λ€μ λ°λ‘ `merge`κ° νΈμΆλκ³ , λ°μ΄ν°λ² μ΄μ€μμ λκΈ°νλ₯Ό μν΄ κ°μ²΄κ° λ€κ³  μλ μλ³μλ‘ `selectμΏΌλ¦¬`λ₯Ό λ λ¦¬λ κ±Έ νμΈν  μ μλ€.

```
λ³ν© μμ μ μ΄λ¦μ = νκΈΈλ
μμ  ν μ΄λ¦μ = νκΈΈλ μλλ€
```

κ·Έλμ μμ κ°μ λ©μμ§κ° μ½μμ μΆλ ₯λμκ³ 

μ΄ν `μμμ± μ»¨νμ€νΈ(Persistence Context)`κ° λ³κ²½ κ°μ§λ₯Ό ν΅ν΄ `updateμΏΌλ¦¬`λ₯Ό λ°μμν¨λ€.

---

```
@Test
public void persistenceContextTest() throws Exception {
    try {
        System.out.println("=============== member μΈμ€ν΄μ€ μμ± ===============");
        Member member = Member.builder()
                              .name("νκΈΈλ")
                              .age(30)
                              .build();
        System.out.println("em.contains(member) = " + em.contains(member));

        System.out.println("=============== persist ===============");
        em.persist(member);
        System.out.println("em.contains(member) = " + em.contains(member));

        System.out.println("=============== detach ===============");
        em.detach(member);
        System.out.println("em.contains(member) = " + em.contains(member));

        System.out.println("=============== commit ===============");
        tx.commit();
    }
    catch(Exception e) {
        System.out.println("EXCEPTION -> " + e.getMessage());
        tx.rollback();
    }
}
```

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fvtuqz%2FbtqWXbxta4n%2FHpNlNNiGaIUw8xQejBq5G0%2Fimg.png)

`em.contains` λ©μλλ ν΄λΉ `Entity`κ° `μμμ± μ»¨νμ€νΈ(Persistence Context)`μ μ‘΄μ¬νλ κ°μ²΄μΈμ§ μλμ§λ₯Ό boolean κ°μΌλ‘ μΆλ ₯ν΄μ£Όλ λ©μλλ€.

member κ°μ²΄κ° μ²μ μμ±λ  λ(λΉμμ μν) containsλ `false`λ₯Ό λ°ννλ€.

μ΄ν `persist λμ΄` μμ μνκ° λ μμ μλ `true`λ₯Ό λ°ννλ€.

λ€μ `detach λμ΄` μ€μμ μνκ° λ μμ μλ `false`λ₯Ό λ°ννλ κ²μ νμΈν  μ μλ€.

---

```
@Test
public void persistenceContextTest() throws Exception {
    try {
        System.out.println("=============== member μΈμ€ν΄μ€ μμ± ===============");
        Member member = Member.builder()
                              .id(1L)
                              .name("νκΈΈλ")
                              .age(30)
                              .build();
        System.out.println("em.contains(member) = " + em.contains(member));

        System.out.println("=============== merge ===============");
        Member mergeMember = em.merge(member);
        System.out.println("em.contains(member) = " + em.contains(member));
        System.out.println("em.contains(mergeMember) = " + em.contains(mergeMember));

        System.out.println("=============== commit ===============");
        tx.commit();
    }
    catch(Exception e) {
        System.out.println("EXCEPTION -> " + e.getMessage());
        tx.rollback();
    }
}
```

member κ°μ²΄λ `λΉμμ μν`μ΄μ§λ§ **_μλ³μ κ°μ κ°κ³  μκΈ° λλ¬Έ_** μ

`em.merge(member)κ°` λ¨Ήνλ©° `μμμ± μ»¨νμ€νΈ(Persistence Context)`λ member κ°μ²΄κ° `merge` λλ μμ μ

λ°μ΄ν°λ² μ΄μ€μμ λκΈ°νλ₯Ό μν΄ member κ°μ²΄κ° κ°μ§κ³  μλ μλ³μ κ°μΌλ‘ `selectμΏΌλ¦¬`λ₯Ό λ λ Έκ³ ,

λ°μ΄ν°λ² μ΄μ€μλ id=1μ ν΄λΉνλ μ λ³΄κ° μκΈ° λλ¬Έμ

μμμ± μ»¨νμ€νΈμ λ±λ‘νκΈ° μν΄ μ±λ²μ μμ²­νμμμ νμΈν  μ μλ€.

```
Hibernate: 
    call next value for hibernate_sequence
```

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdytvDH%2FbtqWVQ8RU3H%2Ftus6257JrNREeekbtMcAMk%2Fimg.png)

κ·Έλ¦¬κ³  ν κ°μ§ λ ν₯λ―Έλ‘μ΄ μ μ΄ μλ€.

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FofayM%2FbtqWUM0bhKE%2FjrRsEGcFXNwyUglzDaKJh1%2Fimg.png)

`merge`λ μ€μμ κ°μ²΄ μμ²΄λ₯Ό λ°λ‘ `μμμ± μ»¨νμ€νΈ(Persistence Context)`μ μ¬λ¦¬λ κ²μ΄ μλκ³ 

μλ‘μ΄ κ°μ²΄λ₯Ό λ§λ€μ΄μ μμμ± μ»¨νμ€νΈμ μ¬λ¦°λ€λ κ²μ μμ λ‘κ·Έλ‘ νμΈν  μ μλ€.

κ·Έλ¦¬κ³  μμμ± μ»¨νμ€νΈμλ μ λ³΄κ° μμ§λ§ λ°μ΄ν°λ² μ΄μ€μλ μ λ³΄κ° μμΌλ―λ‘ μ΄ λν λκΈ°νλ₯Ό μν΄

μ»€λ°λλ μμ μ merge λ member κ°μ²΄μ μ λ³΄λ₯Ό

λ°μ΄ν°λ² μ΄μ€μ `insertμΏΌλ¦¬`λ₯Ό ν΅ν΄ λ±λ‘νλ λͺ¨μ΅μ νμΈν  μ μλ€.

κ·Έλ¬λ―λ‘ `merge`λ μ€μ λ‘ CRUD μμμ

`Create` λλ `Update` κΈ°λ₯μ λμμ μνν  μ μλ€κ³  λ³Ό μ μκ² λ€.

---

μ΄λ° κ°λ¨ν κ²μ¦μ€νμ ν΅ν΄ μ μ μλ κ²μ

`μμμ± μ»¨νμ€νΈ(Persistence Context)`λ μ νλ¦¬μΌμ΄μκ³Ό λ°μ΄ν°λ² μ΄μ€ μ¬μ΄μμ κ°μμ λ°μ΄ν°λ² μ΄μ€ μ­ν μ μννλ€λ κ²μ΄λ€.

κ·Έλμ νΈλμ­μμ΄ `commit λκ±°λ` `flush`κ° μ§μ  νΈμΆλλ λλ μμ μ

λͺ¨λ  μλ³μ κ°μ ν΅ν΄ μμμ± μ»¨νμ€νΈμ λ΄μ©μ μ€μ  λ°μ΄ν°λ² μ΄μ€μ λ΄μ©κ³Ό λκΈ°νμν¨λ€λ μ μ΄λ€.

```
@Test
public void persistenceContextTest() throws Exception {
    try {
        System.out.println("=============== findById ===============");
        Member member1 = em.find(Member.class, 1L);
        Member member2 = em.find(Member.class, 2L);
        Member member3 = em.find(Member.class, 3L);

        System.out.println("=============== member1 update ===============");
        member1.setName("λ νκΈΈλ μλλ€");
        member1.setAge(1);

        System.out.println("=============== member2 update ===============");
        member2.setName("λλ°©μ­");
        member2.setAge(9999);

        System.out.println("=============== commit ===============");
        tx.commit();
    }
    catch(Exception e) {
        System.out.println("EXCEPTION -> " + e.getMessage());
        tx.rollback();
    }
}
```

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FsSlx6%2FbtqWVRzxUyi%2FGAhJ4mMyaHILNvDqpEhZgk%2Fimg.png)

`em.find()λ₯Ό` ν΅ν΄ 3κ°μ§ κ°μ²΄μ λν μ λ³΄λ₯Ό κ°μ Έμ€λ―λ‘ 3λ²μ `selectμΏΌλ¦¬`κ° λ°μνκ³ 

κ·Έμ€ 2κ°μ§ κ°μ²΄μ μ λ³΄λ₯Ό λ³κ²½νμμΌλ―λ‘ 2λ²μ `updateμΏΌλ¦¬`κ° λ°μνλ€.

μ΄λ₯Ό μ΄λ―Έμ§λ₯Ό ν΅ν΄ λ³΄λ©΄ μλμ κ°λ€.

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FEAuNE%2FbtqWXbjZeim%2FZtefOIORznYkSE48qJZXJK%2Fimg.png)

---

JPAλ₯Ό μ¬μ©ν  λ κ°μ₯ μ€μνκ² μκ°ν΄μΌ ν  μ μ `μμμ± μ»¨νμ€νΈ(Persistence Context)`μ λν μ΄ν΄μ΄λ€.

`μμμ± μ»¨νμ€νΈ(Persistence Context)`λ μ νλ¦¬μΌμ΄μκ³Ό λ°μ΄ν°λ² μ΄μ€μ μ¬μ΄μ μ‘΄μ¬νλ **_κ°μμ λ°μ΄ν°λ² μ΄μ€_** μ΄λ€.

μ΄ μ μ λͺμ¬ν΄μΌ ν  κ²μ΄λ€.
