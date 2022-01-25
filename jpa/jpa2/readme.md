# 2022.01.10, jpa - 이솔 구조

0. 위치 
1. 프로젝트 생성
2. jpa 를 위한 yml 설정
3. 클래스

## 0. 위치 
```https://github.com/jjuntang/example/tree/master/jpa/jpa2```

## 1. 프로젝트 생성
- dependancy : spring web, lombok, mariaDB
- 

## 2. jpa 를 위한 yml 설정
```yml
spring:
  jpa:
    hibernate.ddl-auto: none
    show-sql: true
    properties:
      hibernate.jdbc.time_zone: UTC
  datasource:
    url: jdbc:mariadb://3.37.225.152:3306/eve_csms
    username: eve-csms-ap
    password: ap3306##
    driver-class-name: org.mariadb.jdbc.Driver
  servlet.multipart:
      maxFileSize: 10MB
      maxRequestSize: 10MB
```

## 3. 클래스
- jpa
  1. ChangeAudit
  2. ChargerLastStatusDtl
  3. ChargerLastStatusDtlRepository
- controlloer
