# 롤 커뮤니티 서비스
## 1. 프로젝트 개요

### 1-1. 목표
+ 사용자 간에 자유롭게 의사소통 할 수 있는 롤 커뮤니티 서비스 개발

### 1.2. 기능
+ 자유 게시판 / 신고 게시판 기능
   1. 자유 게시판
      - 등급에 따른 차등 게시글 기본 조회
      - 유형 별 분류, 등급 별 조회 카테고리 제공 (등급 : 롤 티어 및 관리자 계정 등급을 의미)
   2. 신고 게시판
      - 유형 별 분류 카테고리 제공
   3. 게시글에 대한 댓글 기능


## 2. 기술 스택
**[Front-end]**
+ HTML
+ CSS
+ Javascript
  

**[Back-end]**
+ 스프링 부트
+ 타임리프
+ 스프링 데이터 JPA

**[Database]**
+ MySQL

***************************************
<img src='https://github.com/HoJun0415/springboot-lol-community/blob/main/%EC%8A%A4%ED%83%9D.PNG'>

## 3. 패키지 구조
- 도메인과 계층형을 섞은 하이브리드 구조 택

```bash
.
├── main
│   ├── generated
│   ├── java
│   │   └── com
│   │       └── lol
│   │           └── community
│   │               ├── LolCommunityApplication.java
│   │               ├── board
│   │               │   ├── controller
│   │               │   │   ├── BoardApiController.java
│   │               │   │   └── BoardController.java
│   │               │   ├── domain
│   │               │   │   ├── Board.java
│   │               │   │   ├── BoardReaction.java
│   │               │   │   └── BoardType.java
│   │               │   ├── dto
│   │               │   │   ├── BoardSearch.java
│   │               │   │   ├── request
│   │               │   │   │   ├── BoardRequest.java
│   │               │   │   │   └── BoardSearchRequest.java
│   │               │   │   └── response
│   │               │   │       ├── BoardBaseResponse.java
│   │               │   │       ├── BoardMainResponse.java
│   │               │   │       ├── BoardMainView.java
│   │               │   │       └── BoardResponse.java
│   │               │   ├── repository
│   │               │   │   ├── BoardReactionRepository.java
│   │               │   │   └── BoardRepository.java
│   │               │   └── service
│   │               │       ├── BoardReactionService.java
│   │               │       ├── BoardService.java
│   │               │       └── BoardServiceImpl.java
│   │               ├── category
│   │               │   ├── domain
│   │               │   │   └── Category.java
│   │               │   ├── dto
│   │               │   │   └── response
│   │               │   │       └── CategoryResponse.java
│   │               │   ├── repository
│   │               │   │   └── CategoryRepository.java
│   │               │   └── service
│   │               │       └── CategoryService.java
│   │               ├── comment
│   │               │   ├── Comment.java
│   │               │   ├── repository
│   │               │   │   └── CommentRepository.java
│   │               │   └── service
│   │               │       └── CommentService.java
│   │               ├── global
│   │               │   ├── BaseEntity.java
│   │               │   ├── config
│   │               │   │   ├── JpaAuditingConfig.java
│   │               │   │   ├── SwaggerConfig.java
│   │               │   │   ├── WebConfig.java
│   │               │   │   └── WebSecurityConfig.java
│   │               │   └── exception
│   │               │       └── ExceptionType.java
│   │               └── user
│   │                   ├── controller
│   │                   │   ├── LoginController.java
│   │                   │   └── UserController.java
│   │                   ├── domain
│   │                   │   ├── Grade.java
│   │                   │   ├── GradeCode.java
│   │                   │   └── User.java
│   │                   ├── dto
│   │                   ├── form
│   │                   │   ├── LoginForm.java
│   │                   │   ├── UserEditForm.java
│   │                   │   ├── UserListForm.java
│   │                   │   └── UserSaveForm.java
│   │                   ├── login
│   │                   │   ├── Login.java
│   │                   │   ├── LoginMemberArgumentResolver.java
│   │                   │   ├── SessionConst.java
│   │                   │   └── SessionValue.java
│   │                   ├── repository
│   │                   │   ├── UserRepository.java
│   │                   │   └── UserRepositoryImpl.java
│   │                   └── service
│   │                       ├── LoginService.java
│   │                       ├── UserService.java
│   │                       └── UserServiceImpl.java
│   └── resources
│       ├── application.yml
│       ├── static
│       │   ├── css
│       │   │   ├── addUserForm.css
│       │   │   ├── article.css
│       │   │   ├── editUserForm.css
│       │   │   ├── loginForm.css
│       │   │   ├── newArticle.css
│       │   │   ├── normalize.css
│       │   │   └── userAuth.css
│       │   ├── img
│       │   │   ├── arrow.png
│       │   │   ├── authBtn.png
│       │   │   ├── banner.png
│       │   │   ├── btn_write.png
│       │   │   ├── footer_img.png
│       │   │   ├── footer_img_dohan.png
│       │   │   ├── lock_img.png
│       │   │   ├── logo.png
│       │   │   ├── logo_top.png
│       │   │   ├── pencil.png
│       │   │   ├── plus.png
│       │   │   ├── search.png
│       │   │   ├── user.png
│       │   │   └── user_img.png
│       │   └── js
│       │       └── article.js
│       └── templates
│           ├── board
│           │   ├── articles.html
│           │   └── newArticle.html
│           ├── layout
│           │   ├── footer.html
│           │   ├── head.html
│           │   └── header.html
│           ├── login
│           │   └── loginForm.html
│           └── users
│               ├── addUserForm.html
│               ├── editUserForm.html
│               ├── list.html
│               ├── listEditUser.html
│               └── userAuth.html
└── test
    └── java
        └── com
            └── lol
                └── community
                    ├── LolCommunityApplicationTests.java
                    ├── board
                    │   └── controller
                    │       └── BoardApiControllerTest.java
                    └── user
                        └── service
                            └── UserServiceImplTest.java
```

## 4. 데이터베이스 모델링(ER Diagram)
- :link: [ERD 클라우드 링크](https://www.erdcloud.com/d/DhiTdXvz6iMsy2tsu)

<img src="https://github.com/www-author/springboot-lol-community/assets/148677085/7255b2a6-6b9a-4e56-890d-e244cb974419" alt="erd" >


## 5. 화면 구성
- :link: [피그마 링크](https://www.figma.com/file/Ttj3FgTYKYZbHHF5fWPYOZ/%EB%A1%A4-%EC%A0%95%EB%B3%B4-%EC%BB%A4%EB%AE%A4%EB%8B%88%ED%8B%B0?type=design&node-id=0-1&mode=design&t=HWHVkapKhksFJNS5-0)

## 6. API 설계서 
- :link: [API 설계 문서](https://www.notion.so/oreumi/API-19069c3d780c4b65b5d11cb6297b2102)
<img src="https://github.com/www-author/springboot-lol-community/assets/148677085/b89d3c76-0999-42b2-887e-81c4154c6184" width="700"  alt="api설계서">



## 7. 프로젝트 운영 관리 
- :link: [노션 링크 참고](https://www.notion.so/oreumi/15-b6ded3552c1441279f51d1f049f63d8d)
