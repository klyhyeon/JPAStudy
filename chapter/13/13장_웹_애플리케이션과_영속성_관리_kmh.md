### OSIV

OSIV(Open Session In View)는 영속성 컨텍스트를 뷰까지 열어둔다는 뜻이다.  영속성 컨텍스트가 살아있으면 엔티티는 영속상태로 유지된다.  따라서 뷰에서도 지연 로딩을 사용할 수 있다.

```
spring:
  jpa:
    open-in-view: true // 기본값
```

OSIV가 true이면 뷰에서도 다음과 같이 지연 로딩을 할 수 있다.

```
 @GetMapping
 public Member findById() {
     Member member = memberService.findById();
     Team team = member.getTeam();
     System.out.println(team);
     
     ...
 }
```

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FxQPVv%2FbtrcNMvMuIf%2Fe3br3jdzBNmwGp5lssKyT1%2Fimg.png)

하지만 OSIV를 false로 주게되면 다음과 같이 에러가 발생한다.  View까지 영속성 컨텍스트가 유지되지 않기 때문이다.

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fb0AK6p%2FbtrcMGiL9xl%2Fu33ipHJJzSZXWjEjCvPxL1%2Fimg.png)

### OSIV 동작 원리

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FRrJ28%2FbtrcNMJjL4G%2FfonOLiSiwwORZDD5w9Q8r0%2Fimg.png)

1.  클라이언트 요청이 들어오면 서블릿 필터나 스프링 인터셉터에서 영속성 컨텍스트를 생성한다.
2.  서비스 계층에서 @Transactional로 트랜잭션을 시작할 때 1번에서 미리 생성해둔 영속성 컨텍스트를 찾아와서 트랜잭션을 시작한다.
3.  서비스 계층이 끝나면 트랜잭션을 커밋하고 영속성 컨텍스트를 플러시한다.  이때 트랜잭션은 끝내지만 영속성 컨텍스트는 종료하지 않는다.
4.  컨트롤러와 뷰까지 영속성 컨텍스트가 유지되므로 조회한 엔티티는 영속 상태를 유지한다.
5.  서블릿 필터나 스프링 인터셉터로 요청이 돌아오면 영속성 컨텍스트를 종료한다.  이때 플러시를 호출하지 않고 바로 종료한다.

### 트랜잭션 없이 읽기

영속성 컨텍스트를 통한 모든 변경은 트랜잭션 안에서 이루어져야 한다.  엔티티를 변경하지 않고 단순히 조회만 할 때는 트랜잭션 없어도 되는데 이를 트랜잭션 없이 읽기라 한다.  프록시를 초기화하는 지연로딩도 조회 기능이므로 트랜잭션 없이 읽기가 가능하다.

스프링이 제공하는 OSIV를 사용하면 프레젠테이션 계층에서는 트랜잭션이 없으므로 엔티티를 수정할 수 없다.

-   영속성 컨텍스트를 프레젠테이션 계층까지 유지한다.
-   프레젠테이션 계층에는 트랜잭션이 없으므로 엔티티를 수정할 수 없다.
-   프레젠테이션 계층에는 트랜잭션이 없지만 트랜잭션 없이 읽기를 사용해 지연로딩을 할 수 있다.

> 컨트롤러에서 조회된 엔티티의 값은 수정할 수 있지만 더티체킹이 발생하지 않아 DB에 update쿼리가 나가지는 않는다.

### 스프링 OSIV 주의사항

스프링 OSIV를 사용하면 프레젠테이션 계층에서 엔티티를 수정해도 수정 내용을 데이터베이스에 반영하지 않는다.  단 예외가 있는데 프레젠테이션 계층에서 엔티티를 수정한 직후 트랜잭션을 시작하는 서비스 계층을 호출하면 문제가 발생한다.

```
 @GetMapping
 public Member findById() {
     Member member = memberService.findById();
     member.setAge(40);
     memberService.doSomeThing();
     
     ...
 }
```

```
@Transactional
public void doSomeThing() {

}
```

위 코드를 보면 컨트롤러에서 member를 가져와 age를 40으로 바꾸었다.  그리고 트랜잭션이 걸려있는 서비스 계층의 메소드를 호출하게 되면 트랜잭션에서 변경감지가 일어나 update 쿼리가 나가게 된다.

![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbXywiF%2FbtrcOeMlb9x%2Fn3Sisrqsue2h1hhuTg7VkK%2Fimg.png)

과거에 프로젝트를 하며 이 케이스때문에 디비에 있는 데이터를 일부 날려먹었던 경험이 있다.  그렇기에 개인적으로 하나의 트랜잭션에서 모든 로직을 처리하는 것을 선호하며 컨트롤러단에 엔티티를 리턴하지 않는다.  즉 View 계층에서 처리해야할 데이터 매핑 로직을 서비스 계층에서 해서 리턴하는 것을 선호한다.