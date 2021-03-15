# 일상제주 - Back
2021.01.09 ~ 2021.02.15

## 프로젝트 소개
한 곳의 사이트에서 제주도의 관광명소와 주변 맛집을 소개하고, 회원 추천식당, 회원 일정 공유, 교통편 정보 등을 통하여 다양한 정보를 공유하고 자신만의 일정을 기록하여 여행객의 수월한 관광을 돕는 웹 사이트

## Team
* **PL** 김상희<br/>
* **Front-end** 김소연, 안현서, 양국현, 양호준<br/>
* **Back-end** 김상희, 성석우, 오상근<br/>

## Stack
* **Front-end** [Front-end 저장소](https://github.com/KkukYang/jeju-front "front")
* **Back-end** : Spring boot, Java, MySQL, Mybatis, Maven
* **Open API** : Jeju Visit api, 공공 데이터 포털(기상청), 제주도 렌터카 api
* **Tool** : Eclipse, InteilliJ, AWS, RDS, EC2, FileZilla, TortoiseGit, SourceTree, MySQL workbench, Apache Tomcat 9.0, Postman

## 기능
### 회원
#### getList
```
GET / member/list
```
* Response
```
[{
  "num":"2",
  "id":"sanghee",
  "provider":"no",
  "pass":"1234",
  "name":"김상희",
  "gender":"여자",
  "birth":"1997-08-08",
  "photo":"jeju20210215012923.jpg",
  "address":"경기 안양시 만안구 안양동",
  "addrdetail":"석수빌라",
  "email":"ssss9708",
  "email2":"gmail.com",
  "hp":"010-1111-2512",
  "gaipday":"2021-02-15 10:29"},
  ...
]
```

#### Login
```
POST / member/login
```
* Request
```
{
  "num":"2",
  "id":"sanghee",
  "provider":"no",
  "pass":"1234",
  "name":"김상희",
  "gender":"여자",
  "birth":"1997-08-08",
  "photo":"jeju20210215012923.jpg",
  "address":"경기 안양시 만안구 안양동",
  "addrdetail":"석수빌라",
  "email":"ssss9708",
  "email2":"gmail.com",
  "hp":"010-1111-2512",
  "gaipday":"2021-02-15 10:29"
}
```
* Response
```
{
  true
}
```

#### getTotalCount
```
GET / member/count
```
* Response
```
{
  "num": 42
}
```

#### insert
```
POST / member/insert
```
* Request
```
{
  "id": "hahaha",
  "provider": "no",
  "pass: "1234",
  "name": "하하하",
  "gender": "여자",
  "birth": "2020-01-03",
  "photo": "no",
  "address": "경기도 안양시",
  "addrdetail": "만안구",
  "email": "haha",
  "email2": "gmail.com",
  "hp": "010-1111-1111",
  "gaipday": "2021-01-01 10:29"
}
```

#### delete
```
GET / member/delete
```
* Request
```
{
  "num": 2
}
```

#### update
```
POST / member/update
```
* Request
```
{
  "id": "hahaha",
  "pass: "1234",
  "name": "하하하",
  "gender": "여자",
  "birth": "2020-01-03",
  "address": "경기도 안양시",
  "addrdetail": "만안구",
  "email": "haha",
  "email2": "gmail.com",
  "hp": "010-1111-1661"
}
```
