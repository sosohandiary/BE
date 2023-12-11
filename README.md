## 📕 우리들만의 소소한 다이어리, US!

### 👉 프로젝트 소개
- US는 친구들과 공유해 다이어리를 꾸밀 수 있는 **웹 다이어리 꾸미기 SNS 서비스** 
- 여러 가지 스티커들과 다양한 글씨체를 통해 다이어리 꾸미는 재미
- 전체 공개 다이어리, 친구 공개 다이어리를 설정
- 다이어리를 공유할 친구는 최대 8명까지 초대 가능
- 그리기 기능이 있어 자신만의 감성으로 꾸미기 가능

### 👉 주요 기능
| **다이어리 유형 구분** | **친구 초대** | **다이어리 꾸미기** |
| :---: | :---: | :---: |
| <img src ="https://user-images.githubusercontent.com/122272525/232973469-75665e2e-bc51-4aec-8ca7-8e266244a828.gif" col="4" /> | <img src ="https://user-images.githubusercontent.com/122272525/232975637-d069e728-5f9a-4175-b5f0-9c4dd6a83e48.gif" col="4" /> | <img src ="https://user-images.githubusercontent.com/122272525/232975769-8a2bbc40-3fce-47e3-ab1d-a1ed8dc26f08.gif" col="4" /> |

**US를 방문해 보세요!** : https://us-diary.vercel.app/login

## ⚙ 아키텍쳐
![소소한 우리들만의 다이어리 _ 어스-002](https://user-images.githubusercontent.com/122272525/232308813-85a3aefb-81f6-4d67-8b6f-90638a2f85ac.png)

### 📑 아키텍쳐 도입 배경
**Spring Boot :** 국내에서는 백엔드 언어로 Java 사용이 많이 사용되고 있고, 웹 프로그램을 쉽고 빠르게 만들 수 있도록 도와주는 프레임워크  

**Spring Data Jpa :** 객체 지향 프로그래밍의 장점을 활용하면서도 데이터베이스와의 연동 작업을 간편하게 처리할 수 있게 하기 위해 도입.

**NGINX :** 오픈 소스로서 certbot을 이용하여 해당 도메인에 대한 인증서가 발급해(.pem 파일) Https로 보안을 강화.

**MySql :** 무료 오픈 소스로서 도메인 주도 개발에 있어서 RDBMS를 필요하다고 생각이 들고 그 중 가장 접하기 쉽고 익숙하다고 판단

**AWS EC2 :** 합리적인 비용과 AWS의 보안성, 유연한 서버관리(서버 실행, 종료 등)의 장점으로 도입.

**AWS S3 :** 합리적인 비용과 AWS의 보안성, EC2 서버와 연동이 쉬움 등의 장점으로 도입.


## 🔧 Core Tools
 <img src="https://img.shields.io/badge/Spring-green?style=for-the-badge&logo=Spring&logoColor=#6DB33F"> <img src="https://img.shields.io/badge/Spring Boot-green?style=for-the-badge&logo=Spring Boot&logoColor=#6DB33F"> <img src="https://img.shields.io/badge/Spring Security-green?style=for-the-badge&logo=Spring Security&logoColor=#6DB33F">
<br/>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"/>  <img src="https://img.shields.io/badge/Amazon RDS-527FFF?style=for-the-badge&logo=Amazon RDS&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon%20S3-FF9900?style=for-the-badge&logo=Amazon%20S3&logoColor=white"/> 


## 📐 ERD 설계
![어스다이어리-최종erd](https://user-images.githubusercontent.com/122272525/232308744-510a9916-2c27-452c-b26f-bdc1edc4e5a0.PNG)


## ❓ Trouble Shooting
**JPA 사용관련 : 다이어리 삭제**

**문제 파악 :** 해당하는 다이어리를 삭제하기 위해서 spring data JPA가 제공하는 deleteById를 사용할려고 하였는데, 삭제가 되지않고 에러가 발생

**해결 방안 :** spring data JPA에 공식문서를 확인하여 기본으로 제공하는 deleteById는 참조변수가 ID 타입을 이용한다는 것을 확인하였고, 해당하는 메서드를 오버로딩하여 Long타입으로 전환하여 사용

**문제 해결** : 해당하는 메서드를 Long타입으로 오버로딩하여 사용하여 문제 해결

<br>

**JPA 사용관련 : 회원 탈퇴**

**문제 파악 :** 회원탈퇴를 진행시 다음과 같은 에러가 뜨고 delete쿼리가 발생하지 않았고, 구글링하여 찾아보니 memberId를 외래키로 참조하고 있는 많은 엔티티들이 있기 때문에 회원탈퇴를 할때 다른 엔티티에 외래키 필드 또한 침해되므로 문제가 발생하는 것을 확인. 

**해결 방안 :** 

1안 : member와 연관된 엔티티의 연관관계를 양방향으로 만들기

2안 : DB에 외래 키 무결성을 관리하도록 설정을 해줘야 하므로 @OnDelete 어노테이션을 member와 연관된 테이블에 적용

**문제 해결 :** 1안은 코드에 변화를 많이 주기에 2안은 member와 연관된 테이블에 @OnDelete 어노테이션을 붙임으로써 해결 가능 하므로 2안을 선택

<br>

**JPA 사용관련 : find와 exist**

**문제 파악 :** 프론트와 연결한 이후 친구 수락 할 시 중복 수락이 되는 점을 파악했고,  
’boolean findByFriendIdAndMemberIdAndStatus(Long friendId, Long memberId, StatusFriend Status);’ 해당 메서드로 친구 수락 연결을 진행. 해당 메서드가 중복해서 수락이 가능하여 다른 JPA문법으로 찾는 방법으로 선회

**해결 방안 :** findBy 메서드가 맞지않는 이유는 nullPointerException을 발생시키서 해당 검색 조건을 만족하는 엔티티가 존재하지 않는 경우 검색 결과가 null이 된다.

이에 반해 existsBy 메서드는 해당 검색 조건을 만족하는 엔티티가 존재하는지 여부를 boolean 타입으로 반환하기 때문에, 검색 결과가 null이 되지 않는다.

**문제 해결** :  boolean existsByFriendIdAndMemberIdAndStatus(Long friendId, Long memberId, StatusFriend Status); 로 변경

<br>


## 👫 팀원 정보 및 개발 블로그 주소

| 주특기 | 이름 | 개발블로그 / 깃허브 |
|:---:| :---: | :---: |
| BE | 황원준 |https://velog.io/@potenter11 / https://github.com/1juuun |
| BE | 강혜광 |https://www.notion.so/java-spring-jpa-study/0d8e097dc39b461eabc30905a6f4fb5f?v=c99e67357f6342d1a37af5693d2f7f79 / https://github.com/kingaser |
| BE | 함동진 |https://eastjin.tistory.com / https://github.com/eastjin |
| FE | 최승호 |https://a-potato.tistory.com / https://github.com/boompeak |
| FE | 곽세령 |https://kuromi-dev.tistory.com / https://github.com/seryoungk |
| FE | 이주애 |https://www.notion.so/juae-world-8bf6f88c53544eb5a5656e2527749f35 / https://github.com/leejuae1020 |
| FE | 한지윤 |https://velog.io/@icedlatte / https://github.com/JellyKingdom |
| DE | 김채연 | |

