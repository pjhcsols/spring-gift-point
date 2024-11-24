# spring-gift-point

# 추가 구현 기능
## 웹소켓 구현 및 순서도 

클라이언트가 /ws 엔드포인트를 통해 WebSocket 연결을 설정합니다.
<br>
클라이언트는 /v1/sub/chat/rooms/{chatRoomId}/list 경로를 구독하여 초기 메시지 목록을 조회할 수 있습니다.
<br>
클라이언트는 /v1/sub/chat/rooms/{chatRoomId} 경로를 구독하여 채팅방의 실시간 메시지를 수신할 준비를 합니다.
<br>
채팅 메시지를 전송할 때 /v1/pub/chat/{chatRoomId} 또는 /v1/pub/chat/{chatRoomId}/file 경로로 메시지를 서버에 보냅니다.
<br>



채팅룸 기능
1. 유저의 채팅방 전체 조회 : 특정 유저의 채팅룸 ID와 제목을 가져오는 API (GET /v1/chat/rooms/user/{userId})
2. 채팅방 제목 설정 및 수정: 받은 채팅룸 ID와 해당되는 유저 ID가 일치한다면 제목을 설정하는 API (Patch /v1/chat/rooms/{chatRoomId}/title)

채팅 기능

/v1/chat/rooms/{chatRoomId}/messages

// 특정 채팅방의 메시지를 시간 순서대로 반환
->실시간 성 보장 x 필요할때 사용


웹소켓 기능
<-실시간 보장

/chat/{chatRoomId} - 메시지 전송

/chat/{chatRoomId}/file - 파일 전송

/chat/rooms/{chatRoomId}/list 채팅방에 속한 메시지를 시간순으로 가져옴

# step3
Feat: 포인트 기능 구현 [박한솔]
* Point 도메인 클래스 생성 및 포인트 충전, 사용, 적립 기능 구현
* PointService 클래스에서 포인트 관련 서비스 메소드 추가
* PointRepository를 통해 사용자에 대한 포인트 정보를 조회 및 저장
* 사용자 등록 시 포인트 자동 생성 기능 추가
  
Fix: 포인트 데이터 누락 오류 수정 [박한솔]
* 사용자 생성 시 포인트 데이터가 누락되는 문제 수정
* 사용자 생성 시 Point 객체를 자동으로 생성하여 PointRepository에 저장
* 포인트 데이터 초기화 로직 개선
  PointService 클래스 개선
* 포인트 충전 및 사용 기능 개선
* 불필요한 로직 제거 및 코드 정리
* 포인트 충전 최소 금액 조건 추가 (10,000원 이상 충전 필요)
  
Refactor: KakaoService에서 포인트 사용 및 적립 로직 추가 & Order 포인트 사용 기능 추가 [박한솔]
* createOrder 메소드에서 포인트 사용 및 적립 기능 추가
* 주문 시 포인트 차감 및 적립 처리
* 잔여 포인트에 대한 로직 개선 및 테스트 추가
* OrderRequest 클래스에 포인트 사용 필드 (pointsToUse) 추가
* 주문 생성 시 포인트 사용 및 적립 로직 추가

# step2
- AWS ec2 배포 
- 지속적인 배포를 위한 배포 스크립트(sh)를 작성한다.
- 클라이언트와 API 연동 시 발생하는 보안 문제에 대응한다.
  
# step1
- 요구사항 명세에 따른 컨트롤러 엔드포인트 수정 & 스웨거 API 문서 설정
## Refactor: 회원 기능 API 수정

- **회원 가입 API 구현**  
  Endpoint: `/api/members/register`

- **로그인 API 구현**  
  Endpoint: `/api/members/login`

## Refactor: 카테고리 기능 API 수정

- **카테고리 생성 API 구현**  
  Endpoint: `/api/categories`

- **카테고리 수정 API 구현**  
  Endpoint: `/api/categories/{categoryId}`

- **카테고리 목록 조회 API 구현**  
  Endpoint: `/api/categories`

## Refactor: 상품 기능 API 수정

- **상품 생성 API 구현**  
  Endpoint: `/api/products`

- **상품 조회 API 구현**  
  Endpoint: `/api/products/{productId}`

- **상품 수정 API 구현**  
  Endpoint: `/api/products/{productId}`

- **상품 삭제 API 구현**  
  Endpoint: `/api/products/{productId}`

- **상품 목록 조회 API 구현 (페이지네이션 적용)**  
  Endpoint: `/api/products?page=0&size=10&sort=name,asc&categoryId=1`

## Refactor: 상품 옵션 기능 API 수정

- **상품 옵션 추가 API 구현**  
  Endpoint: `/api/products/{productId}/options`

- **상품 옵션 수정 API 구현**  
  Endpoint: `/api/products/{productId}/options/{optionId}`

- **상품 옵션 삭제 API 구현**  
  Endpoint: `/api/products/{productId}/options/{optionId}`

- **상품 옵션 목록 조회 API 구현**  
  Endpoint: `/api/products/{productId}/options`

## Refactor: 위시 리스트 기능 API 수정

- **위시 리스트 상품 추가 API 구현**  
  Endpoint: `/api/wishes`

- **위시 리스트 상품 삭제 API 구현**  
  Endpoint: `/api/wishes/{wishId}`

- **위시 리스트 상품 조회 API 구현 (페이지네이션 적용)**  
  Endpoint: `/api/wishes?page=0&size=10&sort=createdDate,desc`

## Refactor: 주문 기능 API 수정

- **주문하기 API 구현**  
  Endpoint: `/api/orders`

- **주문 목록 조회 API 구현 (페이지네이션 적용)**  
  Endpoint: `/api/orders?page=0&size=10&sort=orderDateTime,desc`
