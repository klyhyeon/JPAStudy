### 프록시

프록시
- 영속성 컨텍스트에 엔티티가 없을 대 DB에 엔티티 생성을 요청해 실제 엔티티에 대한 참조를
멤버변수에 보관합니다. 

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


