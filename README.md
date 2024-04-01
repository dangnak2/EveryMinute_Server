# 학교 소식 뉴스피드
<br>

## Tech Stack
### Backend
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens"> <img src="https://img.shields.io/badge/spring data jpa-6DB33F?style=for-the-badge&logoColor=white"> <img src="https://img.shields.io/badge/querydsl-6DB33F?style=for-the-badge&logoColor=white"> <img src="https://img.shields.io/badge/hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white">

### DB
<img src="https://img.shields.io/badge/amazon rds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white"> <img src="https://img.shields.io/badge/amazon s3-569A31?style=for-the-badge&logo=amazons3&logoColor=white"> 

### Deploy
<img src="https://img.shields.io/badge/amazon ec2-FF9900?style=for-the-badge&logo=amazon ec2&logoColor=white">  <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/amazon api gateway-FF4F8B?style=for-the-badge&logo=amazonapigateway&logoColor=white"> <img src="https://img.shields.io/badge/aws lambda-FF9900?style=for-the-badge&logo=awslambda&logoColor=white"> 

### Develop Tool
<img src="https://img.shields.io/badge/intelliJ-000000?style=for-the-badge&logo=intellij idea&logoColor=white"> <img src="https://img.shields.io/badge/swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> 

### Etc.
<img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white"/> <img src="https://img.shields.io/badge/Jira-0052CC?style=for-the-badge&logo=jira&logoColor=white"/>
<img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white"/> <img src="https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=Figma&logoColor=white"/>

<br> 
<br>

## System Architecture
<details>
<summary>Target Architecture</summary>

![image](https://github.com/dangnak2/EveryMinute_Server/assets/80161984/8ceefad3-8797-4373-b599-d073fe8b4c6f)
<br>


</details>

<details>
<summary>Final Architecture</summary>

![image](https://github.com/dangnak2/EveryMinute_Server/assets/80161984/3080bfb8-aa3d-40f4-aa56-e509abfafea2)
<br>
  

</details>
<br>

## Project Structure

<details>
<summary>Details</summary>

```jsx
src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── example
    │   │           └── everyminute
    │   │               ├── EveryminuteApplication.java
    │   │               ├──user
    │   │               │   ├── controller
    │   │               │   │   └── UserController.java
    │   │               │   ├── dto
    │   │               │   │   ├── request
    │   │               │   │   │   ├── JoinReq.java
    │   │               │   │   │   └── LoginReq.java
    │   │               │   │   └── response
    │   │               │   │       ├── LoginRes.java
    │   │               │   │       └── TokenDto.java
    │   │               │   └──  entity
    │   │               │   │   ├── User.java
    │   │               │   │   └──  Role.java
    │   │               │   ├── repository
    │   │               │   │   └── UserRepository.java
    │   │               │   └── service
    │   │               │   │   └── Userservice.java
    │   │               ├── school
    │   │               │   ├── controller
    │   │               │   │   └── SchoolController.java
    │   │               │   ├── dto
    │   │               │   │   ├── request
    │   │               │   │   │   └──  RegisterSchoolReq.java
    │   │               │   ├── entity
    │   │               │   │   ├── School.java
    │   │               │   ├── repository
    │   │               │   │   └── SchoolRepository.java
    │   │               │   └── service
    │   │               │       └── SchoolService.java
    │   │               ├── subscribe
    │   │               │   ├── controller
    │   │               │   │   └── SubscribeController.java
    │   │               │   ├── dto
    │   │               │   │   ├── response
    │   │               │   │   │   └── GetSubscriptionsRes.java
    │   │               │   ├── entity
    │   │               │   │   └── Subscribe.java
    │   │               │   ├── repository
    │   │               │   │   ├── SubscribeCustom.java
    │   │               │   │   ├── SubscribeRepository.java
    │   │               │   │   └── SubscribeRepositoryImpl.java
    │   │               │   └── service
    │   │               │       └── SubscribeService.java
    │   │               ├── news
    │   │               │   ├── controller
    │   │               │   │   └── NewsController.java
    │   │               │   ├── dto
    │   │               │   │   ├── request
    │   │               │   │   │   ├── PostNewsReq.java
    │   │               │   │   │   └── UpdateNewsReq.java
    │   │               │   │   └── response
    │   │               │   │       └── SchoolNewsRes.java
    │   │               │   ├── entity
    │   │               │   │   ├── News.java
    │   │               │   ├── repository
    │   │               │   │   ├── NewsCustom.java
    │   │               │   │   ├── NewsRepository.java
    │   │               │   │   └── NewsRepositoryImpl.java
    │   │               │   └── service
    │   │               │       └── NewsService.java
    │   │               └── global
    │   │                   ├── config
    │   │                   │   ├── AwsS3ImageUrlUtil.java
    │   │                   │   ├── QuerydslConfig.java
    │   │                   │   ├── RedisConfig.java
    │   │                   │   ├── SwaggerConfig.java
    │   │                   │   └── WebConfig.java
    │   │                   ├── entity
    │   │                   │   ├── BaseEntity.java
    │   │                   ├── exception
    │   │                   │   ├── BaseException.java
    │   │                   │   ├── BaseResponseCode.java
    │   │                   │   ├── ExceptionHandlerAdvice.java
    │   │                   ├── resolver
    │   │                   │   ├── Account.java
    │   │                   │   ├── LoginResolver.java
    │   │                   └── response
    │   │                   │   └── ResponseCustom.java
    │   │                   │
    │   │                   ├── utils
    │   │                   │   ├── DateTimeUtil.java
    │   │                   │   ├── JwtUtil.java
    │   │                   │   └── RedisUtil.java
    │   │                   ├── Constants.java
    │   │                   └── CustomPage.java
    │   └── resources
    │       └── application.yml
    └── test
        ├── java
            └── com
                └── example
                    └── everyminute
                        ├── EveryminuteApplicationTests.java
                        ├── user
                        │   └── service
                        │   │   └── UserServiceTest.java
                        │   └── dto
                        │   │   └── TestUserDto.java
                        ├── subscribe
                        │   └── service
                        │   │   └── SubscribeServiceTest.java
                        │   └── dto
                        │   │   └── TestSubscribeDto.java
                        ├── global
                        │   ├── ControllerTestSupport.java
                        │   └── IntegrationTestSupport.java
                        ├── school
                        │   └── service
                        │   │   └── SchoolServiceTest.java
                        │   └── dto
                        │   │   └── TestSchoolDto.java
                        ├── news
                        │   └── service
                        │   │   └── NewsServiceTest.java
                        │   └── dto
                            └── TestNewsDto.java
```
<br>
</details>

## DB 
<details>
<summary>Schema</summary>

![image](https://github.com/dangnak2/EveryMinute_Server/assets/80161984/1162722d-ffac-4f80-8f98-9ba90d2048d8)
</details>

## Develop Convention
**Commit**
> [지라번호] feat: 설명
```
[PDS-5] feat: 회의실 목록 조회 API 생성
```

- feat: 새로운 주요 기능 추가
- fix: 일반적인 수정 사항
- chore: config, 라이브러리, 빌드 관련 파일 수정
- refactor: 코드 리팩토링
- rename: 파일, 클래스, 변수명 등 이름 변경
- docs: 문서 수정
- comment: 주석 추가 및 수정
- hotfix: 긴급한 수정 사항
- test: 테스트 코드 작성

<br>

**Pull Request**
> [feat] PR설명
```
[feat] 뉴스피드 조회 API
```

<br>

**Branch**
> feat/#{이슈번호}-메소드명(카멜 케이스)
```
feat/#15-users, fix/#16-users
```

- main
    - 사용자가 사용하는 브랜치, 언제나 실행가능한 상태
- master
    - 개발자가 사용하는 브랜치
    - 실행 가능한 상태를 만들어 가는 과정
- feat
    - 새로운 기능 브랜치
- test
    - 테스트가 필요한 코드용 브랜치
- fix
    - 버그 발생 시 버그 수정
- hotfix
    - 긴급하게 버그를 수정하는 브랜치
- setting
  - 프로젝트 설정을 위한 브랜치
- refactor
  - 코드의 구조를 수정 또는 변경하는 브랜치

<br>
