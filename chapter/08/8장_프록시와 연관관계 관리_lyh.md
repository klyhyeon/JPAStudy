### 프록시

프록시
- 영속성 컨텍스트에 엔티티가 없을 대 DB에 엔티티 생성을 요청해 실제 엔티티에 대한 참조를
멤버변수에 보관합니다. 
- 프록시를 잘 사용하면 DB 접근을 줄일 수 있습니다.  

지연로딩 
- 연관 엔티티를 호출하지 않아도 되는 상황에서 DB에 함께 조회해 두는 것은 
효율적이지 않습니다. 이를 해결하기 위해 조회를 지연하는 방법이 지연 로딩입니다.
- 지연로딩을 사용하려면 DB 조회를 지연할 수 있는 가짜 객체가 필요합니다. 이것을 프록시 객체라고 합니다.

```java
    Member member = em.getReference(Member.class, "member1");
```

- getReference() 메서드를 호출할 땐 DB를 조회하지 않고 실제 엔티티 객체도 생성하지 않습니다.
대신 DB 접근을 위한 프록시 객체를 반환합니다. 
  
프록시의 특징
- 실제 객체를 상속하기 때문에 겉 모양이 같습니다. 

![프록시 위임](https://user-images.githubusercontent.com/61368705/125005879-d26d6400-e097-11eb-9848-177018eb459f.png)

프록시는 실제 엔티티 객체에 대한 참조를 보관합니다. 그리고 프록시 객체의 메소드를 호출하면 실제 객체의 메소드를 호출합니다.

```java
//ch08.MemberProxy.class
    Member member = em.getReference(Member.class, "id1");
    member.getName(); //1. getName()
```
![프록시 초기화](https://user-images.githubusercontent.com/61368705/125153335-f01cf500-e18d-11eb-89a3-f88e2f3da77d.png)

## 즉시 로딩과 지연 로딩

- 즉시 로딩(EAGER) : 엔티티를 조회할 때 연관된 엔티티도 함께 조회합니다. 
    - 즉시 로딩 때 SQL이 2번 실행되는데, 대부분의 JPA 구현체는 즉시 로딩을 최적화하기 위해 가능하면 조인 쿼리를 사용합니다.  
- 지연 로딩(LAZY) : 연관된 엔티티를 실제 사용할 때 조회합니다.

```java
    Member member = em.find(Member.class, "member1");
```

## JPA 기본 Fetch 전략

@ManyToOne, @OneToOne : 즉시 로딩(FetchType.EAGER)
@OneTomany, @ManyToMany : 지연 로딩(FetchType.LAZY)

즉시 로딩의 리스크는 특정 회원이 연관된 컬렉션에 데이터를 수만 건 등록했는데, 설정한 Fetch 전략이
즉시 로딩이면 해당 회원을 로딩하는 순간 수만 건의 데이터도 함께 로딩된다는 것입니다.

추천하는 방법은 모든 연관관게에 지연 로딩을 사용하는 것입니다. 

## 영속성 전이 : CASCADE

특정 엔티티를 영속성 컨텍스트로 만들 때 연관된 엔티티도 함께 영속 상태로 만들고 싶을 때 사용합니다.

```java
    @Entity
    public class Parent {
        ...
        @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST)
        private List<Child> children = new ArrayList<Child>();
    }
    
    child1.setParent(parent);
    child2.setParent(parent);
    
    em.persist(parent);
```

## 고아 객체
부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 DB에서 삭제하는 기능입니다.
- orphanRemoval = true;

## 정리 

프록시의 동작 원리와 즉시 로딩, 지연 로딩에 대해서 알아보았습니다.

- JPA는 객체 그래프(엔티티)를 마음껏 탐색할 수 있도록 지원합니다. 이 때 프록시 기술을 사용합니다.
- 객체를 조회할 때 연관된 객체를 즉시 로딩하는 방법을 즉시 로딩이라 합니다. 지연해서 로딩하는 방법을 지연 로딩이라 합니다.
- 객체를 저장하거나 삭제할 때 연관된 객체를 함께 처리하는 것을 영속성 전이라고 합니다.
- 부모 엔티티와 관계가 끊어진 자식 엔티티를 자동으로 삭제하려면 고아 객체 기능을 사용하면 됩니다. 





