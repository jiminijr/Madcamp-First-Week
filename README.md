# '카이스트 맛집, 도대체 어디?': 카이맛(Kaimat)
### 카이맛은 어쩌구 저쩌구 

## 1. 프로젝트 소개 
[카이스트 몰입캠프 2023W 1주차]

### a. 개발 팀원
-  성균관대학교 최지민
-  카이스트 박종모

### b. 프로젝트 주제
> 공통과제 I : 탭 구조를 활용한 안드로이드 앱 제작
>
>
> 목적: 서로 함께 공통의 과제를 함으로써, 개발에 빠르게 익숙해지기
>
>
> 결과물: 다음과 같은 세 개의 탭이 존재하는 안드로이드 앱
>
> * Tab 1)
>  나의 연락처 구축.
> 휴대폰의 연락처 데이터를 활용하거나, JSON 형식을 이용해서 임의의 연락처 데이터를 구축.
>
> * Tab 2)
>  나만의 이미지 갤러리 구축.
>  대략 20개 이상의 이미지 필요.
>
> * Tab 3)
>  자유 주제

## 2. 개발 환경

Android Studio 

compileSDKversion :
 
buildToolsversion :

Firebase Version : 

## 3. 구현 기능 
### 내부 데이터 관리
---
- **restaurants.json**을 사용하여 모든 탭 간 데이터 연동

> ex.
> 
>  "restaurant": [
>    {
>      "id":"1",
>      "out_img": "out1",
>      "food_img": "food1",
>      "menu": [{"menu_name":"미박삼겹살", "menu_img":"menu_1_1", "menu_price":"13000"},
>        {"menu_name":"특목살", "menu_img":"menu_1_2", "menu_price":"13000"},
>        {"menu_name":"갈비본살", "menu_img":"menu_1_3", "menu_price":"19000"}],
>      "name": "목구멍",
>      "address": "봉명동 626-1",
>      "number": "010-7503-9399",
>      "info": "봉명동 삼겹살 맛집!",
>      "tag": "#고기",
>      "lat": "36.358297",
>      "lon": "127.345035",
>      "link": "www.moggumung.com/"
>    },
>
> 

- **내부 저장소 이용**을 위해 아래 경로의 userdata.json 등 json 파일을 저장 후 데이터로 사용
  
> /data/data/com.example.navigation/files


### Splash
---

### Tab 1 - Restaurants
---

### Tab 2 - Tags
---

### Tab 3 - Map
---
