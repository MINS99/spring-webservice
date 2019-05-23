# spring-webservice

spring boot study project <br>
https://jojoldu.tistory.com/250 를 참고하였습니당

<hr>

### domain
1. posts 
* ``` @Entity ```
  - 테이블과 링크될 클래스임을 나타냄
  - 언더스코어 네이밍(_)으로 이름을 매칭(SalesManager.java -> sales_manager table)
* ``` @Id ```
  - 테이블의 PK
* ``` @GeneratedValue ```
  - PK의 생성 규칙
  - 기본값 : AUTO, auto_increment(spring boot 2.0에선 옵션 추가 필요)
* ``` @Column ```
  - 선언 해주면 해당 클래스의 필드는 모두 컬럼이 됨
  - 기본값 외에 추가로 변경이 필요한 경우 선언
  - 문자열의 기본값 : VARCHAR(255)
> **TIP:** Entity의 PK는 Long 타입의 auto_increment를 추천 (MYSQL 기준 bigint)

* ``` @NoArgsConstructor ```
  - 기본 생성자 자동 추가
  - access = AccessLevel.PROTECTED : 기본 생성자의 접근 권한 protected
  - protected Posts() { } 와 같은 효과
  - Entity 클래스를 프로젝트 코드상에서 기본 생성자로 생성은 막고, JPA에서 Entity 클래스를 생성하는 것은 허용.
* ``` @Getter ```
  - Getter 메소드 자동 생성
* ``` @Builder ```
  - 해당 클래스의 빌더패턴 클래스 생성
  - 생성자 상단에 선언시 생성자에 포함된 필드만 빌더에 포함
  - 어느 필드에 어떤 값을 채워야 할지 명확하게 인지 가능(Example.builder().a(a).b(b).build();)
> **TIP:** 위의 세개의 어노테이션은 Lombok 라이브러리 어노테이션으로 코드 변경량을 최소화 시켜줌<br>
> Lombok은 의존성만 추가하여 IDE에서 사용할 수 없으므로 사용환경 구성이 필요

<br>

* ``` PostsRepository.java ```
  - ibatis/MyBatis에서 Dao로 불리는 DB Layer 접근자
  - JPA에서는 Repository 라고 부르며 인터페이스로 생성
  - 인터페이스 생성 후 **JpaRepository<Entity클래스, PK타입>** 상속하면 기본적인 CRUD 메소드 자동생성
```java
public interface PostRepository extends JpaRepository<Posts, Long> { }
```
