<br />
<div align="center">
  <a href="https://github.com/wafflestudio21-5/team2-android">
    <img src="https://raw.githubusercontent.com/wafflestudio21-5/team2-android/main/app/src/main/res/mipmap-xxxhdpi/ic_main.png" alt="Logo" width="70" height="70">
  </a>
  <h2 align="center">바니바니</h2>
  <p align="center">
    <h4>Wafflestudio 21.5기 루키 Team 2 토이프로젝트: 당근마켓 클론코딩</h4>
    <div style="padding: 1rem">
    <img src="https://img.shields.io/badge/Android-34A853?style=for-the-badge&logo=android&logoColor=white"/>
    <img src="https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white"/> <br/>
    <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/>
    <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
    <img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
    <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/>
    <img src="https://img.shields.io/badge/Redis-EE3445?style=for-the-badge&logo=redis&logoColor=white"/> <br/>
    <img src="https://img.shields.io/badge/Amazon_EC2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white"/>
    <img src="https://img.shields.io/badge/Amazon_S3-569A31?style=for-the-badge&logo=amazons3&logoColor=white"/>
    </div>
  </p>
</div>

## Github 저장소
- 안드로이드: https://github.com/wafflestudio21-5/team2-android

- 서버(스프링): https://github.com/wafflestudio21-5/team2-server

## 기능 + 스크린샷
- 사용자
  - 회원가입/로그인(Email+PW)
  - 소셜로그인(카카오)
  - 사용자별 지역 설정 (행정동 단위), 프로필 사진 설정
- 물품 게시글
  - 활성 지역 및 거리에 따른 메인화면 게시글 목록 조회(무한스크롤)
  - 나눔, 판매, 경매 게시글 작성, 사진 첨부
  - 게시글 상세 정보 확인, 수정 및 삭제
  - 게시글 검색
- 동네생활(커뮤니티)
  - 커뮤니티 게시글 목록/개별 조회, 수정 및 삭제
  - 커뮤니티 게시글 작성, 사진 첨부
  - 댓글, 대댓글 달기
- 채팅
  - WebSocket을 이용한 판매자와 구매자 간 1:1 채팅
- 나의 당근
  - 사용자 프로필 정보
  - 매너 온도, 받은 거래 후기 목록 조회
  - 내가 관심 누른 물품 목록 조회
- 기타
  - API 서버 https://banibani.shop 에 HTTPS 적용
  - S3 Bucket을 이용한 통합 이미지 업로드 API 구축

## 팀원 및 역할

|클라이언트(Android)||
|:---:|:---:|
|김도연|에러 핸들링, 나의 당근|
|손영준|채팅, 로그인/회원가입|
|안시영|게시글 목록/조회, 커뮤니티, 검색|

|백엔드(Spring)||
|:---:|:---:|
|심영인|사용자(Spring Security, JWT), 지역, 경매|
|이한나|동네생활(커뮤니티)|
|임찬영|물품 게시글, 지역 정보 처리, 이미지 업로드(S3), 캐싱?|
|임혜진|채팅(WebSocket)|

## 작업 컨벤션
- Notion에 각 스프린트별 칸반 보드 만들어 활용
- 일자별 통합, 서버, 안드로이드 Slack 스레드 만들어 소통
- Git commit type과 Task 번호 구분
- PR merge시 squash 안함
