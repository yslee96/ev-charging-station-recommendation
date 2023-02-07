# EV-Charging-Station-Recommendation

[외부 API(카카오 주소 검색 API](https://developers.kakao.com/docs/latest/ko/local/dev-guide))와 [공공 데이터(전기차 충전소 현황 정보)](https://www.data.go.kr/data/15102458/fileData.do)를 활용해 입력된 주소 근처의 전기차 충전소 최대 3곳을 추천하는 서비스이다.

추천하는 전기차 충전소의 길안내는 [카카오 지도 및 로드뷰 바로가기 URL](https://apis.map.kakao.com/web/guide/#routeurl)로 제공 된다.

## 요구 및 구현 사항

- 전기차 충전소 찾기 서비스 요구사항
  - 충전소 현황 데이터(공공 데이터)는 위도 경도의 위치 정보 데이터를 가지고 있다.
  - 해당 서비스로 주소 정보를 입력하여 요청하면 위치 기준에서 가까운 충전소 3 곳을 추출 한다.
  - 주소는 도로명 주소 또는 지번을 입력하여 요청 받는다.
    - 정확한 주소를 입력 받기 위해 [Kakao 우편번호 서비스](https://postcode.map.daum.net/guide) 사용한다.   
  - 주소는 정확한 상세 주소(동, 호수)를 제외한 주소 정보를 이용하여 추천 한다.   
    - ex) 서울 성북구 종암로 10길
  - 입력 받은 주소를 위도, 경도로 변환 하여 기존 약국 데이터와 비교 및 가까운 전기차 충전소를 찾는다.     
    - 두 위 경도 좌표 사이의 거리를 [haversine formula](https://en.wikipedia.org/wiki/Haversine_formula)로 계산한다.  
    - 지구가 완전한 구형이 아니 므로 아주 조금의 오차가 있다.   
  - 입력한 주소 정보에서 정해진 반경(10km) 내에 있는 약국만 추천 한다.   
  - 추출한 충전소 데이터는 길안내 URL 및 로드뷰 URL로 제공 한다.   
  - 길안내 URL은 고객에게 제공 되기 때문에 가독성을 위해 shorten url로 제공 한다.
  
## Feature List     
- 카카오 주소검색 API 연동하여 주소를 위도, 경도로 변환하기   
- 추천 결과를 카카오 지도 URL로 연동하여 제공하기   
- 공공 데이터를 활용하여 개발하기
- Spring Data JPA를 이용한 CRUD 메서드 구현하기
- redis를 이용하여 성능 최적화하기 
- Handlebars를 이용한 간단한 View 만들기   
- 도커를 사용하여 다중 컨테이너 애플리케이션 만들기   
- 애플리케이션을 클라우드 서비스에 배포하기   
- base62를 이용한 shorten url 개발하기 (길안내 URL)   
- Spring retry를 이용한 재처리 구현하기 (카카오 API의 네트워크 오류 등에 대한 재처리)   

## Tech Stack   

- JDK 11
- Spring Boot 2.7.8
- Spring Data JPA
- Gradle
- Handlebars
- Lombok
- Github
- Docker
- AWS EC2
- Redis
- MariaDB
- Spock   
- Testcontainers 

## Result
![스크린샷 2023-02-07 오전 11 21 30](https://user-images.githubusercontent.com/77106988/217218010-84c01020-1fd4-471a-8f51-86167d0d36b2.png)
<img width="1440" alt="스크린샷 2023-02-07 오후 3 39 36" src="https://user-images.githubusercontent.com/77106988/217218565-3d925ed1-f5bc-4a77-92cd-a19a640cc31f.png">
<img width="1440" alt="스크린샷 2023-02-07 오후 3 39 28" src="https://user-images.githubusercontent.com/77106988/217217839-acd88c3a-0c89-42f5-ab79-be39cedafc88.png">
<img width="1440" alt="스크린샷 2023-02-07 오후 3 39 48" src="https://user-images.githubusercontent.com/77106988/217217944-93473e6a-20ad-429d-85e7-35a66f09e721.png">



