# '카이스트 맛집, 도대체 어디?': 카이맛(Kaimat)
### 맛집 찾기 어려운 카이스트 학생 다 모여라! 카이스트 주변 맛집 검색 어플, 카이맛(Kaimat)

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

- IDE : Android Studio 2022.3.1 


- OS : Android


- Language : Java


- Target Device : Galaxy s8


- compileSDKversion : 34


- minSdk = 26


- targetSdk = 33


## 3. 구현 기능 
### 내부 데이터 관리
---
- **restaurants.json**을 사용하여 모든 탭 간 맛집 데이터 연동
  
```ex.
"restaurant": [
    {
      "id":"1",
      "out_img": "out1",
      "food_img": "food1",
      "menu": [{"menu_name":"미박삼겹살", "menu_img":"menu_1_1", "menu_price":"13000"},
        {"menu_name":"특목살", "menu_img":"menu_1_2", "menu_price":"13000"},
        {"menu_name":"갈비본살", "menu_img":"menu_1_3", "menu_price":"19000"}],
      "name": "목구멍",
      "address": "봉명동 626-1",
      "number": "010-7503-9399",
      "info": "봉명동 삼겹살 맛집!",
      "tag": "#고기",
      "lat": "36.358297",
      "lon": "127.345035",
      "link": "www.moggumung.com/"
    }
```
 

- **내부 저장소 이용**을 위해 아래 경로의 userdata.json 등 json 파일을 저장 후 데이터로 사용
  
``` /data/data/com.example.navigation/files ```


### Splash
---
> 앱 실행 시 나타나는 로딩 화면

<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/b9541ae4-0f1e-4cff-a7e1-0161c8fca370" width="200" height="400"/>
<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/3d39ec9e-f624-4bca-a2a6-91b15d2ad232" width="200" height="400"/>
<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/e02ae75f-ea66-4405-aeb4-f61a4a5ec582" width="200" height="400"/>



- 로고 제작 후 svg 포맷으로 저장
- build.gradle.kts (Module :app) 에 ```implementation ("com.airbnb.android:lottie:6.3.0")``` 추가 후 lottie 사용
- SplashActivity에서 5초 간 로딩화면 지속하도록 구현


### Tab 1 - Restaurants
---
> Tab 1의 기본 화면 (검색, 찜 아이콘)

<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/6d15636d-edf6-433e-8d31-cc66bd1ea63d" width="200" height="400"/>
<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/1ace2e39-81df-4e20-9642-08a53e5840a4" width="200" height="400"/>
<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/8a95cb81-b5ad-4047-a177-acba675e92ca" width="200" height="400"/>
<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/924081db-2a25-4640-9675-e49d2026cdc9" width="200" height="400"/>




- ConstraintLayout, CardView, RecyclerView 등을 사용하여 구현
- 맛집 목록을 나타내며, 검색 아이콘을 누를 시 상호명 및 주소를 기준으로 검색 가능
- 개별 하트 아이콘을 누르면 '찜'으로 설정되어 우측 하단 하트 버튼을 누르면 찜한 맛집 목록으로 필터링 가능함
- 필터링 된 경우에 검색 아이콘을 누를 시 찜한 맛집 내에서 검색 기능이 작동함



> Tab 1의 개별 식당 상세 화면

<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/44af5dab-7efa-4440-a685-c7c6575cd363" width="200" height="400"/>
<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/6d87b141-2f39-4009-8a04-cb03aa1051e2" width="200" height="400"/>
<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/cd1b8b23-4c7c-4965-a898-57ecf5fca5df" width="200" height="400"/>

- ConstraintLayout, CardView, RecyclerView 등 사용
- 개별 맛집 카드를 누르면, 판매하는 3가지 메뉴 정보와 가격이 나타남


> 통화 아이콘
<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/68ef5c03-9c54-4fd2-9559-11f1a28c1192" width="200" height="400"/>
<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/9e3769dc-0380-479f-be91-e9e384dfaf58" width="200" height="400"/>
<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/2ac3317b-d980-4940-9404-374d2134f38b" width="200" height="400"/>

- 통화 아이콘을 누르면 해당되는 맛집의 번호로 전화를 걸 수 있음



> 지도 아이콘

<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/d2a9d1f9-c6ca-40e1-abb7-173b07e553c2" width="200" height="400"/>
<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/4fbd6748-8d97-4531-80e0-e3cc06530c62" width="200" height="400"/>
<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/ab2c517f-466c-4596-88d9-fabadc3066bd" width="200" height="400"/>
<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/12c03e2f-764e-4dc7-9e3c-6fd05466f1c3" width="200" height="400"/>


- 지도 아이콘을 누르면 **tab 3의 Map*** 화면으로 이동하여 해당되는 맛집의 지도 마커로 이동함



  
- 리뷰 아이콘을 누르면 다른 사람의 리뷰가 나타나며 상단에는 '리뷰 등록' 아이콘과 '팝업 닫기' 아이콘이 존재함
- '리뷰 등록' 아이콘을 누르면 리뷰를 등록할 수 있는 창이 나타나고, 해당 창에는 별점 기능, 리뷰 작성칸, 카메라 및 갤러리를 통한 사진 첨부 기능이 있음


### Tab 2 - Tags
---
> 갤러리 뷰를 사용하여 태그를 통한 음식 추천 및 해당하는 음식점 안내


<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/7d4522b0-a916-4406-800e-4aa89a90df63" width="200" height="400"/>
<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/2f6f7c68-de5d-4e3a-8811-6159057ac462" width="200" height="400"/>
<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/03894742-fffe-4a8b-924e-654a0b43d1e3" width="200" height="400"/>
<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/38fa00a2-60f9-455c-8708-4ae284a66449" width="200" height="400"/>


- 앨범 형식으로 메뉴 추천 구현
- 각각의 앨범 항목을 클릭하면 해당 음식을 판매하는 맛집 안내
- tab 1과 연동하여 음식점 정보를 누를 시 tab 1과 동일하게 맛집 상세 정보 페이지 표출


### Tab 3 - Map
---
> Naver Map API를 사용하여 현재 위치, 맛집 위치, 맛집 정보가 나타나도록 구현

<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/d99abd69-7223-4903-9213-2ef6db63b57a" width="200" height="400"/>
<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/17e0e1f7-30ae-44ff-8e95-1a3270599e50" width="200" height="400"/>
<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/5b1dc1f4-5712-45e5-849d-e037917bb4cf" width="200" height="400"/>
<img src="https://github.com/jiminijr/Madcamp-First-Week/assets/95954633/6eee645a-25bf-4822-9f83-d0dea9acb05c" width="200" height="400"/>


- 확대, 축소가 가능함
- 현재 위치 아이콘을 누르면 현재 위치가 표시됨
- 지도 위 마커를 누르면 맛집 정보가 나타남



