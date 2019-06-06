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

2\. PostsRepository.java
  - ibatis/MyBatis에서 Dao로 불리는 DB Layer 접근자
  - JPA에서는 Repository 라고 부르며 인터페이스로 생성
  - 인터페이스 생성 후 **JpaRepository<Entity클래스, PK타입>** 상속하면 기본적인 CRUD 메소드 자동생성
```java
public interface PostRepository extends JpaRepository<Posts, Long> { }
```

<hr>

### Controller
1. WebRestController.java
* ``` Spring Bean 생성 방법 ``` 
  - @Autowired(비권장)
  - setter
  - **생성자(권장)**
  
* ``` @AllArgsConstructor ```
  - Lombok, 모든 필드를 인자값으로 하는 생성자 생성
  - 의존성 관계가 변경될때마다 생성자 코드를 계속 수정하지 않아도 됨

<hr>

### DTO
1. PostsSaveRequestDto.java
* ``` @Setter ```
  - controller에서 ``` @RequestBody ```로 외부에서 데이터를 받는 경우 **기본생성자 + set 메소드** 로만 값이 할당 됨
  - 이때만 ``` @Setter ``` 허용
  - **테이블과 매핑되는 entity class와 controller DTO를 분리하자**
     - entity class 변경시 여러 클래스에 영향을 줄 수 있다
     - request/response용 DTO는 자주 변경 되므로 분리가 필요함


<hr>

### test
1. PostsRepositoryTest.java
* ``` given ```
  - 테스트 기반 환경 구축
  - ``` @builder ``` 사용법도 같이 확인
* ``` when ```
  - 테스트 하고자 하는 행위 선언
  - 여기서는 Posts가 DB에 insert 되는 것 확인용
* ``` then ```
  - 테스트 결과 검증
  - DB에 insert 되었는지 조회를 통해 값 확인
  
<hr>

### Dependency
1. h2
![image](https://t1.daumcdn.net/cfile/tistory/998D714C5A44FA5A0E)
* 사용방법<br>
  1\) Application.java를 실행시키고, 브라우저에서 http://localhost:8080/h2-console 로 접속<br>
  2\) JDBC URL : jdbc:h2:mem:testdb 입력 후 connect 버튼을 통해 접속<br>


<hr>

### ETC
1. application.properties -> application.yml
* ```yml``` 
  - properties에 비해 상대적으로 유연한 구조
  - 상위 계층에 대한 표현, List 등을 완전하게 표현 가능

2.JPA Auditing
  - createDate, modifyDate 를 따로 추가하지 않고 자동으로 추가 해줌
* ```LocalDateTime```
  - Java8 부터 등장
  - 기본 날짜 타입인 Date의 문제점 수정
* ```@MappedSuperclass```
  - JPA Entity 클래스들이 BaseTimeEntity을 상속할 경우 필드들(createdDate, modifiedDate)도 컬럼으로 인식하도록 함
* ```@EntityListeners(AuditingEntityListener.class)```
  - BaseTimeEntity클래스에 Auditing 기능을 포함
* ```@EnalbeJpaAuditing```
  - JPA Auditing 어노테이션들을 모두 활성화시키는 어노테이션
  - application run하는 메소드에 추가(ex. Application.java)