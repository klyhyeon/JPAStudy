### 컬렉션
- persist, 엔티티를 영속성 상태로 변경하면 하이버네이트가 제공하는 PersistentBag로 전환됩니다. 
- 컬렉션 종류
  - **Collection, List**
  - **Set: 중복 허용 X**
    - equals() + hashcode()로 검증합니다.
    - Set은 엔티티를 추가할 때 중복 엔티티가 있는지 확인해야하기 때문에 지연 로딩된 컬렉션을 초기화 합니다.
    - 
  - **List + @OrderColumn**
    - List로 받을 때 순서가 정해진 상태로 리턴됩니다. 
    - 하지만 @OrderColumn은 연관 엔티티 중 FK가 있는 엔티티에만 종속적이고 제한적인 부분이
    있기 때문에 `@OrderBy`를 권장합니다.

###**@Converter**
  - 컨버터를 사용하면 엔티티의 데이터 타입을 변환해서 테이블에 저장할 수 있습니다. 그 반대도 가능합니다.
  - 글로벌 설정 `@Converter(autoApply=true)`을 추가하면 일일이 어노테이션을 지정하지 않아도 해당 타입이 변환됩니다.
    
### 리스너
  - JPA 리스너 기능을 사용하면 엔티티의 생명주기에 따른 이벤트를 설정할 수 있습니다.
  - 사용할 수 있는 이벤트 종류는 아래 그림과 같습니다.
    ![image](https://user-images.githubusercontent.com/61368705/130981316-1e57b114-23fb-493e-abde-cdaaa7ca5e11.png)
