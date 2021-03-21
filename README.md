
1. 개발 프레임웍크 
 :  특별한 프레임워크 미사용. 디비의 경우 ibatis 로 구현
 
2. 3개의 테이블 생성 : 채번관리테이블, 카드결제테이블 , 카드결제전송테이블

create table SEQ_NRTB	(
	TYPE_CLS VARCHAR2(10) not null ,
	INPT_YMD VARCHAR2(8) not null ,
	SEQ_NR   NUMBER(10) not null ,
	FST_INPT_YMD VARCHAR2(8) not null ,
	FST_INPT_TOD VARCHAR2(6) not null ,
	DELETE_FLAG VARCHAR2(1) not null ,
	constraint PK_SEQ_NRTB primary key (TYPE_CLS, INPT_YMD)
	);
	comment on table SEQ_NRTB is '채번관리테이블';
	//입력 타입구분
	//입력일자
	//순번
	//최초입력일
	//최초입력시간
	//삭제플레그
	
 create table CARD_PAY01TB	(
	MNG_NO      VARCHAR2(20) not null ,
	DATA_TYPE   VARCHAR2(10) not null ,
	CARD_INFO   VARCHAR2(300) not null ,
	MON_PLAN    VARCHAR2(2) not null ,
	AMT   DECIMAL(15) not null ,
	VAT   DECIMAL(15) not null ,
	BEF_MNG_NO      VARCHAR2(20)  ,
	FST_INPT_YMD VARCHAR2(8) not null ,
	FST_INPT_TOD VARCHAR2(6) not null ,
	DELETE_FLAG VARCHAR2(1) not null ,
	constraint PK_CARD_PAY01TB primary key (MNG_NO, DATA_TYPE)
	);
	comment on table CARD_PAY01TB is '카드결제테이블';
	//관리번호
	//결제취소구분
	//암호화카드정보
	//할부개월
	//금액
	//부가세
	//원 관리번호
	//최초입력일
	//최초입력시간
	//삭제플레그
		
create table CARD_PAY02TB	(
	MNG_NO      VARCHAR2(20) not null ,
	SEND_DATA   VARCHAR2(450) not null ,
	FST_INPT_YMD VARCHAR2(8) not null ,
	FST_INPT_TOD VARCHAR2(6) not null ,
	DELETE_FLAG VARCHAR2(1) not null ,
	constraint PK_CARD_PAY02TB primary key (MNG_NO)
	);
	comment on table CARD_PAY02TB is '카드결제전송테이블';
	//관리번호
	//결제전문	
	//최초입력일
	//최초입력시간
	//삭제플레그	
  
3. 문제해결 전략
 가) 업무요건 분석으로 인-아웃 데이타 확인 
 나) 분석된 요건으로 데이타 객체 및 디비 생성
 다) 인터페이스 도출 및 필요기능(내부적으로 사용 할) 도출
   
4. 빌드 및 실행 방법
 가) junit 테스트 케이스를 통한검증.
 나) 선택문제 - 부분취소 내용 구현됨.
 다) 디비 연결 부분 미 구현으로 디비관련 주석처리함.
	
