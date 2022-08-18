-- Where to go : App project DataBase 
-- team members : Sujin Kang, Gahyun Son, Sihyun Jeon, 
-- Host:     Database: where2goDB
-- Made by Sujin Kang
-- ------------------------------------------------------


DROP database IF EXISTS `where2goDB`;
CREATE database where2goDB;
use where2goDB;

CREATE TABLE areaCodeTBL
(
    `aCode`  integer       NOT NULL, 
    `aName`  VARCHAR(7)    NOT NULL, 
     PRIMARY KEY (aCode)
);


ALTER TABLE areaCodeTBL COMMENT '광역시/도 단위 지역 코드 테이블. 지역코드 저장 테이블';


CREATE TABLE areaCodeDetailTBL
(
    `aCode`   integer        NOT NULL, 
    `aDCode`  integer        NOT NULL, 
    `aDName`  VARCHAR(10)    NOT NULL, 
     PRIMARY KEY (aCode, aDCode)
);

ALTER TABLE areaCodeDetailTBL COMMENT '시/군/구 단위 지역코드 테이블. 세부 지역코드 저장 테이블';

ALTER TABLE areaCodeDetailTBL
    ADD CONSTRAINT FK_areaCodeDetailTBL_aCode_areaCodeTBL_aCode FOREIGN KEY (aCode)
        REFERENCES areaCodeTBL (aCode) ON DELETE RESTRICT ON UPDATE RESTRICT;

CREATE TABLE categoryTBL(
   cCode VARCHAR(20) NOT NULL PRIMARY KEY
  ,cName VARCHAR(20) NOT NULL
);


CREATE TABLE userTBL
(
    `userID`      BIGINT          NOT NULL    AUTO_INCREMENT, 
    `email`       varchar(50)     NOT NULL, 
    `nickName`    varchar(50)     NOT NULL, 
    `pw`    varchar(200)    NOT NULL, 
    `sex`         varchar(1)      NULL        DEFAULT NULL, 
    `age`         int(1)          NULL        DEFAULT NULL, 
    `last_login`  datetime        NOT NULL    DEFAULT '2001-03-26', 
     PRIMARY KEY (userID)
);

ALTER TABLE userTBL COMMENT '회원 테이블';


CREATE TABLE eventTBL
(
    `eventID`         BIGINT           NOT NULL, 
    `eventName`       TEXT       		NOT NULL, 
	`startDate`  	  DATE              NOT NULL, 
    `endDate`    	  DATE              NOT NULL, 
    
    `addr1`           TEXT       NULL  DEFAULT NULL,
    `addr2`           TEXT        NULL DEFAULT NULL,
    `kind`            VARCHAR(20)         NULL DEFAULT NULL,
    `firstimage`      TEXT       NULL DEFAULT NULL,
    `firstimage2`     TEXT       NULL	DEFAULT NULL,
    `mapx`            NUMERIC(14,10)    NULL	DEFAULT NULL, 
    `mapy`            NUMERIC(13,10)    NULL	DEFAULT NULL, 
    `mlevel`          integer           NULL	DEFAULT NULL, 
    `areacode`        integer           NULL	DEFAULT NULL, 
    `sigungucode`     integer           NULL	DEFAULT NULL, 
    `tel`             TEXT      		NULL	DEFAULT NULL, 
	`sponsor1`        TEXT 				NULL	DEFAULT NULL,
    `sponsor1tel`     TEXT				NULL	DEFAULT NULL,
    `sponsor2`        TEXT				NULL	DEFAULT NULL,
	`sponsor2tel`     TEXT				NULL	DEFAULT NULL,
	`playtime`        TEXT				NULL	DEFAULT NULL,
	`eventplace`      TEXT				NULL	DEFAULT NULL,
  `eventhomepage`     TEXT				NULL	DEFAULT NULL,
  `agelimit`          TEXT				NULL	DEFAULT NULL,
  `bookingplace`      TEXT				NULL	DEFAULT NULL,
  `placeinfo`         TEXT				NULL	DEFAULT NULL,
  `subevent`          TEXT				NULL	DEFAULT NULL,
  `program`           TEXT				NULL	DEFAULT NULL,
  `usetimefestival`   TEXT				NULL	DEFAULT NULL,
  `discountinfofestival` TEXT				NULL	DEFAULT NULL,
  `spendtimefestival`   TEXT				NULL	DEFAULT NULL,
    `w1`       BIGINT    NULL        DEFAULT 0 COMMENT '10~19 ; w', 
    `m1`       BIGINT    NULL        DEFAULT 0 COMMENT '10~19 ; m', 
    `w2`       BIGINT    NULL        DEFAULT 0 COMMENT '20~29 ; w', 
    `m2`       BIGINT    NULL        DEFAULT 0 COMMENT '20~29 ; m', 
    `w3`       BIGINT    NULL        DEFAULT 0 COMMENT '30~39 ; w', 
    `m3`       BIGINT    NULL        DEFAULT 0 COMMENT '30~39 ; m', 
    `w4`       BIGINT    NULL        DEFAULT 0 COMMENT '40~59 w', 
    `m4`       BIGINT    NULL        DEFAULT 0 COMMENT '40~59m' , 
    `w6`       BIGINT    NULL        DEFAULT 0 COMMENT '60~ ; w', 
    `m6`       BIGINT    NULL        DEFAULT 0 COMMENT '60~ ; m', 
     PRIMARY KEY (eventID)
);
 /* 
ALTER TABLE eventTBL
    ADD CONSTRAINT FK_eventTBL_areacode_areaCodeTBL_aCode FOREIGN KEY (areacode)
        REFERENCES areaCodeTBL (aCode) ON DELETE RESTRICT ON UPDATE CASCADE;
 
 ALTER TABLE eventTBL
    ADD CONSTRAINT FK_eventTBL_kind_categoryTBL_cCode FOREIGN KEY (kind)
        REFERENCES categoryTBL (cCode) ON DELETE RESTRICT ON UPDATE RESTRICT;
        
 /*       
 ALTER TABLE eventTBL
    ADD CONSTRAINT FK_eventTBL_sigungucode_areaCodeDetailTBL_aDCode FOREIGN KEY (sigungucode)
        REFERENCES areaCodeDetailTBL (aDCode) ON DELETE RESTRICT ON UPDATE CASCADE;*/
        


CREATE TABLE userVisitedTBL
(
    `userID`      BIGINT        NOT NULL, 
    `eventID`     BIGINT        NOT NULL, 
    `assessment`  varchar(1)    NOT NULL, 
     PRIMARY KEY (userID, eventID)
);

ALTER TABLE userVisitedTBL COMMENT '사용자가 방문한 이벤트 테이블';

ALTER TABLE userVisitedTBL
    ADD CONSTRAINT FK_userVisitedTBL_eventID_eventTBL_eventID FOREIGN KEY (eventID)
        REFERENCES eventTBL (eventID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE userVisitedTBL
    ADD CONSTRAINT FK_userVisitedTBL_userID_userTBL_userID FOREIGN KEY (userID)
        REFERENCES userTBL (userID) ON DELETE RESTRICT ON UPDATE RESTRICT;
        
        


CREATE TABLE mainEventTBL
(
    `mainEventID`  BIGINT         NOT NULL    AUTO_INCREMENT, 
    `ment`         varchar(80)    NOT NULL, 
    `prePic`       text           NOT NULL, 
    `eventID`     BIGINT         NULL, 
     PRIMARY KEY (mainEventID)
);

ALTER TABLE mainEventTBL COMMENT '메인 이벤트 테이블 (홈 상단)';

ALTER TABLE mainEventTBL
    ADD CONSTRAINT FK_mainEventTBL_eventID_eventTBL_eventID FOREIGN KEY (eventID)
        REFERENCES eventTBL (eventID) ON DELETE CASCADE ON UPDATE RESTRICT;




CREATE TABLE searchTBL
(
    `searchID`  BIGINT         NOT NULL    AUTO_INCREMENT, 
    `word`      VARCHAR(45)    NOT NULL, 
    `count`     BIGINT         NOT NULL    DEFAULT 0, 
     PRIMARY KEY (searchID)
);

ALTER TABLE searchTBL COMMENT '검색어 테이블';




CREATE TABLE keywordTBL
(
    `userID`     BIGINT         NOT NULL, 
    `content`    varchar(10)    NOT NULL, 
     PRIMARY KEY (userID, content)
);

ALTER TABLE keywordTBL COMMENT '사용자가 등록한 키워드 테이블';

ALTER TABLE keywordTBL
    ADD CONSTRAINT FK_keywordTBL_userID_userTBL_userID FOREIGN KEY (userID)
        REFERENCES userTBL (userID) ON DELETE RESTRICT ON UPDATE RESTRICT;



CREATE TABLE userSavedTBL
(
    `userID`   BIGINT    NOT NULL, 
    `eventID`  BIGINT    NOT NULL, 
     PRIMARY KEY (userID, eventID)
);

ALTER TABLE userSavedTBL COMMENT '사용자가 담아둔 이벤트 테이블';

ALTER TABLE userSavedTBL
    ADD CONSTRAINT FK_userSavedTBL_eventID_eventTBL_eventID FOREIGN KEY (eventID)
        REFERENCES eventTBL (eventID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE userSavedTBL
    ADD CONSTRAINT FK_userSavedTBL_userID_userTBL_userID FOREIGN KEY (userID)
        REFERENCES userTBL (userID) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- 지역 코드 입력 ---------
INSERT INTO areaCodeTBL(aCode,aName) VALUES (1,'서울');
INSERT INTO areaCodeTBL(aCode,aName) VALUES (2,'인천');
INSERT INTO areaCodeTBL(aCode,aName) VALUES (3,'대전');
INSERT INTO areaCodeTBL(aCode,aName) VALUES (4,'대구');
INSERT INTO areaCodeTBL(aCode,aName) VALUES (5,'광주');
INSERT INTO areaCodeTBL(aCode,aName) VALUES (6,'부산');
INSERT INTO areaCodeTBL(aCode,aName) VALUES (7,'울산');
INSERT INTO areaCodeTBL(aCode,aName) VALUES (8,'세종특별자치시');
INSERT INTO areaCodeTBL(aCode,aName) VALUES (31,'경기도');
INSERT INTO areaCodeTBL(aCode,aName) VALUES (32,'강원도');
INSERT INTO areaCodeTBL(aCode,aName) VALUES (33,'충청북도');
INSERT INTO areaCodeTBL(aCode,aName) VALUES (34,'충청남도');
INSERT INTO areaCodeTBL(aCode,aName) VALUES (35,'경상북도');
INSERT INTO areaCodeTBL(aCode,aName) VALUES (36,'경상남도');
INSERT INTO areaCodeTBL(aCode,aName) VALUES (37,'전라북도');
INSERT INTO areaCodeTBL(aCode,aName) VALUES (38,'전라남도');
INSERT INTO areaCodeTBL(aCode,aName) VALUES (39,'제주도');


-- 지역 상세 코드 입력 ------------
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,1,'강남구'); /*서울*/
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,2,'강동구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,3,'강북구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,4,'강서구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,5,'관악구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,6,'광진구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,7,'구로구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,8,'금천구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,9,'노원구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,10,'도봉구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,11,'동대문구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,12,'동작구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,13,'마포구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,14,'서대문구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,15,'서초구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,16,'성동구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,17,'성북구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,18,'송파구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,19,'양천구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,20,'영등포구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,21,'용산구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,22,'은평구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,23,'종로구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,24,'중구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (1,25,'중랑구');

INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (2,1,'강화군');/*인천*/
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (2,2,'계양구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (2,3,'미추홀구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (2,4,'남동구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (2,5,'동구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (2,6,'부평구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (2,7,'서구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (2,8,'연수구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (2,9,'옹진군');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (2,10,'중구');

INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (3,1,'대덕구'); /*대전*/
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (3,2,'동구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (3,3,'서구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (3,4,'유성구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (3,5,'중구');

INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (4,1,'남구');/*대구*/
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (4,2,'달서구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (4,3,'달성군');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (4,4,'동구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (4,5,'북구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (4,6,'서구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (4,7,'수성구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (4,8,'중구');

INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (5,1,'광산구');/*광주*/
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (5,2,'남구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (5,3,'동구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (5,4,'북구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (5,5,'서구');

INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (6,1,'강서구');/*부산*/
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (6,2,'금정구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (6,3,'기장군');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (6,4,'남구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (6,5,'동구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (6,6,'동래구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (6,7,'부산진구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (6,8,'북구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (6,9,'사상구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (6,10,'사하구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (6,11,'서구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (6,12,'수영구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (6,13,'연제구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (6,14,'영도구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (6,15,'중구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (6,16,'해운대구');

INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (7,1,'중구');/*울산*/
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (7,2,'남구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (7,3,'동구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (7,4,'북구');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (7,5,'을주군');

INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (8,1,'세종특별자치시');/*세종특별자치시*/

INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,1, '가평군');/*경기도*/
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,2,'고양시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,3,'과천시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,4,'광명시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,5,'광주시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,6,'구리시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,7,'군포시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,8,'김포시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,9,'남양주시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,10,'동두천시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,11,'부천시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,12,'성남시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,13,'수원시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,14,'시흥시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,15,'안산시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,16,'안성시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,17,'안양시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,18,'양주시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,19,'양평군');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,20,'여주시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,21,'연천군');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,22,'오산시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,23,'용인시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,24,'의왕시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,25,'의정부시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,26,'이천시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,27,'파주시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,28,'평택시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,29,'포천시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,30,'하남시');
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES (31,31,'화성시');

INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 32, 1 , '강릉시' ); /*강원도*/
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 32, 2 , '고성군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 32, 3 , '동해시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 32, 4 , '삼척시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 32, 5 , '속초시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 32, 6 , '양구군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 32, 7 , '양양군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 32, 8 , '영월군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 32, 9 , '원주시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 32, 10 , '인제군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 32, 11 , '정선군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 32, 12 , '철원군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 32, 13 , '춘천시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 32, 14 , '태백시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 32, 15 , '평창군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 32, 16 , '홍천군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 32, 17 , '화천군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 32, 18 , '횡성군' );

INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 33, 1 , '괴산군' ); /*충청북도*/
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 33, 2 , '단양군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 33, 3 , '보은군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 33, 4 , '영동군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 33, 5 , '옥천군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 33, 6 , '음성군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 33, 7 , '제천시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 33, 8 , '진천군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 33, 9 , '청원군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 33, 10 , '청주시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 33, 11 , '충주시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 33, 12 , '증평군' );

INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 34, 1 , '공주시' ); /*충청남도*/
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 34, 2 , '금산군' ); 
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 34, 3 , '논산시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 34, 4 , '당진시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 34, 5 , '보령시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 34, 6 , '부여군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 34, 7 , '서산시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 34, 8 , '서천군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 34, 9 , '아산시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 34, 11 , '예산군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 34, 12 , '천안시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 34, 13 , '청양군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 34, 14 , '태안군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 34, 15 , '홍성군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 34, 16 , '계룡시' );

INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 1 , '경산시' ); /*경상북도*/
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 2 , '경주시' ); 
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 3 , '고령군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 4 , '구미시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 5 , '군위군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 6 , '김천시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 7 , '문경시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 8 , '봉화군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 9 , '상주시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 10 , '성주군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 11 , '안동시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 12 , '영덕군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 13 , '영양군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 14 , '영주시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 15 , '영천시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 16 , '예천군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 17 , '울릉군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 18 , '울진군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 19 , '의성군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 20 , '청도군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 21 , '청송군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 22 , '칠곡군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 35, 23 , '포항시' );

INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 36, 1 , '거제시' ); /*경상남도*/
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 36, 2 , '거창군' ); 
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 36, 3 , '고성군' ); 
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 36, 4 , '김해시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 36, 5 , '남해군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 36, 6 , '마산시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 36, 7 , '밀양시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 36, 8 , '사천시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 36, 9 , '산청군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 36, 10 , '양산시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 36, 12 , '의령군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 36, 13 , '진주시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 36, 14 , '진해시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 36, 15 , '창녕군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 36, 16 , '창원시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 36, 17 , '통영시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 36, 18 , '하동군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 36, 19 , '함안군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 36, 20 , '함양군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 36, 21 , '합천군' );

INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 37, 1 , '고창군' ); /*전라북도*/
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 37, 2 , '군산시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 37, 3 , '김제시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 37, 4 , '남원시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 37, 5 , '무주군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 37, 6 , '부안군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 37, 7 , '순창군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 37, 8 , '완주군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 37, 9 , '익산시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 37, 10 , '임실군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 37, 11 , '장수군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 37, 12 , '전주시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 37, 13 , '정읍시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 37, 14 , '진안군' );

INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 1 , '강진군' );  /*전라남도*/
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 2 , '고흥군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 3 , '곡성군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 4 , '광양시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 5 , '구례군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 6 , '나주시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 7 , '담양군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 8 , '목포시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 9 , '무안군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 10 , '보성군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 11 , '순천시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 12 , '신안군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 13 , '여수시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 16 , '영광군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 17 , '영암군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 18 , '완도군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 19 , '장성군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 20 , '장흥군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 21 , '진도군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 22 , '함평군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 23 , '해남군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 38, 24 , '화순군' );

INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 39, 1 , '남제주군' ); /*제주도*/
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 39, 2 , '북제주군' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 39, 3 , '서귀포시' );
INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( 39, 4 , '제주시' );

-- 카테고리 데이터 입력 ---------------------------------------------------------------------------
INSERT INTO categoryTBL(cCode,cName) VALUES ('A02070100','문화관광축제'); /*축제*/
INSERT INTO categoryTBL(cCode,cName) VALUES ('A02070200','일반축제');
INSERT INTO categoryTBL(cCode,cName) VALUES ('A02080100','전통공연');/*공연/행사*/
INSERT INTO categoryTBL(cCode,cName) VALUES ('A02080200','연극');
INSERT INTO categoryTBL(cCode,cName) VALUES ('A02080300','뮤지컬');
INSERT INTO categoryTBL(cCode,cName) VALUES ('A02080400','오페라');
INSERT INTO categoryTBL(cCode,cName) VALUES ('A02080500','전시회');
INSERT INTO categoryTBL(cCode,cName) VALUES ('A02080600','박람회');
INSERT INTO categoryTBL(cCode,cName) VALUES ('A02080700','컨벤션');
INSERT INTO categoryTBL(cCode,cName) VALUES ('A02080800','무용');
INSERT INTO categoryTBL(cCode,cName) VALUES ('A02080900','클래식음악회');
INSERT INTO categoryTBL(cCode,cName) VALUES ('A02081000','대중콘서트');
INSERT INTO categoryTBL(cCode,cName) VALUES ('A02081100','영화');
INSERT INTO categoryTBL(cCode,cName) VALUES ('A02081200','스포츠경기');
INSERT INTO categoryTBL(cCode,cName) VALUES ('A02081300','기타행사');


-- 사용자 데이터 입력 -------------------------------------
-- 10대 여성 --
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('bear@gmail.com', '곰돌이', "$2a$10$sm454B3JeElqcatMKRVhweISBzb4Eng4Huzxkf857xhCzy2yW9MuG", 'w', 1, NOW());  /*pw : bearbear*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('sugar@gmail.com', '사탕', "$2a$10$iUq5jMK2jTFI7ZdHIoOUx.a8ApXX1B/.09ul29PhLOh4CbauSGZhq", 'w', 1, NOW());  /*pw : candysugar*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('doll@gmail.com', '인형좋아', "$2a$10$CTu/WGf6S2XFFUQMnDUVTuL61Cs92dsoi6rp9ptLEMNhxGDLiWq.K", 'w', 1, NOW());  /*pw : dollybabe*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('mypop@gmail.com', '마이팝', "$2a$10$fhmfCyLP7ucqrV8B27QN4.SksrOr7dHHd4MJugwfytpR0P8hr6W22", 'w', 1, NOW());  /*pw : mypopmypop*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('heyU@gmail.com', '너누', "$2a$10$/2Sisforynvo8spBRPXZHu8.8CEzxBLdPR9rx3VJnb0czYzn.abaG", 'w', 1, NOW());  /*pw : heyyouu*/

-- 20대 여성 -- 
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('sujin@gmail.com', '수지니', "$2a$10$pNZAd/ie7/Ipr6Go30ane.GhiRyflwGQgIXwQR7ux91jr.yK7IxrS", 'w', 2, NOW());  /*pw : heysusu*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('gahyun@gmail.com', '가가', "$2a$10$4RS0v3ayGHIbYVp1NmFS8uslgH8j4VL1ECcCq1.g0iqs.F7iTP4v6", 'w', 2, NOW());  /*pw : heygaga*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('sihyun@gmail.com', '셔니', "$2a$10$743m9YcvXtU8lH2YJYObbuJPdRCGbOq3dB7GKJLuZEUXOgsW908D2", 'w', 2, NOW());  /*pw : heysisi*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('sungshin@gmail.com', '성신', "$2a$10$WBYzbW2Yb9fefIx6ZqdK1.eY7.hysMAueYi8tXtus3CuEKkAV7xGG", 'w', 2, NOW());  /*pw : heysung*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('sooryong@gmail.com', '수룡이', "$2a$10$IZk4SMRabjcjD0WZfSHN7OtOPB6Qy4JWiWetOOJtiiDsXP3dN6oE2", 'w', 2, NOW());  /*pw : sooryong*/

-- 30대 여성 --
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('company@gmail.com', '회사싫어', "$2a$10$MndpzBlzUugqXuo72Gb.CedMLth30.fjkx0AS8oBL0ghYeCXZ/J2u", 'w', 3, NOW());  /*pw : company*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('leavenow@gmail.com', '떠나요', "$2a$10$lzvMsIEYuf0syToGzQC7KO0tCqgpC8tA.JuIqClZEv6K2LyEFmpGm", 'w', 3, NOW());  /*pw : leavenow*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('makemoney@gmail.com', '돈벌자', "$2a$10$VjE6THxyyasqSeY3mpaceeYLWdARNf7.aViyUg4ocni.Wm5yGC5oi", 'w', 3, NOW());  /*pw : makemoney*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('iamapple@gmail.com', '나는사과', "$2a$10$ZQLqFgzGnVBKc1SLdU4ZGOtiB.rTKBqPxxt.YegqD0tjmFEvO0HHG", 'w', 3, NOW());  /*pw : iamapple*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('whatever@gmail.com', '어쩌라고', "$2a$10$hOnQIXkKoBP5/JAosAH0Uug/potnK0FpI0BN4f3BXFDBZF/V2QDGe", 'w', 3, NOW());  /*pw : whatever*/

-- 40대~50대 여성 --
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('momcame@gmail.com', '엄마왔다', "$2a$10$ryLBMAAgKqcceZ1becAjse7UAswndYwQrxdTPPmhURsXLRPzDatOC", 'w', 4, NOW());  /*pw : momcame*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('jihunmom@gmail.com', '지훈맘', "$2a$10$HKWbSAKr/Wm9RraxPFoLB.5m4xQHf5m/U/tSHWRD4MDAXF5TuU1pG", 'w', 4, NOW());  /*pw : jihunmom*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('dorokdorok@gmail.com', '도록도록', "$2a$10$EYq3uYhdO7CFqvKRY7nfuuLLuac7tlkNCjNgXrNsjOHbEUxIGPLUm", 'w', 4, NOW());  /*pw : dorokdorok*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('greengreen@gmail.com', '연두새싹', "$2a$10$wbqdAMT8Jue6hxc.N0JDG.31tJ0lgtkFGAV/RVCUjj4Me6kFqt5Da", 'w', 4, NOW());  /*pw : greengreen*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('gogoworld@gmail.com', '덤벼라 세상아', "$2a$10$2x4Hui3nZgW9CbK.NQs0nOqS0yNn6RWi11AFqvgOrSAZB9wJ/wyaa", 'w', 4, NOW());  /*pw : gogoworld*/

-- 60대 이상 여성
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('hikinggo@gmail.com', '등산가자', "$2a$10$6wB7DkobECOkphcxL.k.4./JFmAeSomwB.z6/wcWF99vbFw0tBrm2", 'w', 6, NOW());  /*pw : hikinggo*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('coolerthan@gmail.com', '멋쟁이', "$2a$10$9A.NEBPNX/JgvTKtqFWaC.4ZyCbfdw4q9sfaoJOirLQY/l9D4wR3i", 'w', 6, NOW());  /*pw : coolerthan*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('jucyjucy@gmail.com', '주스', "$2a$10$CJJcH9OqBVEXUBdCrduq8.ZLj7TABoJRBwY9TX5U3YGf0xz1rQjN.", 'w', 6, NOW());  /*pw : jucyjucy*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('unnieunnie@gmail.com', '아는언니', "$2a$10$sb.3LfOCzIPTjIeHW/Lbi.8S6qp35tSGs8qJS1Aj9GqxctVbud77S", 'w', 6, NOW());  /*pw : unnieunnie*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('wavewave@gmail.com', '파도가 온다', "$2a$10$Wg1nUCjoVc6QluG4srTrtO6bdKVcsAw70SWxmCLhyas3ndBPyE4E2", 'w', 6, NOW());  /*pw : wavewave*/

-- 10대 남성
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('gamegame@gmail.com', '겜돌이', "$2a$10$hgfTj/sdiNIOvhL8TUNqROf0xakUiJNAT/uEEdgg/zYTOHG4rK98G", 'm', 1, NOW());  /*pw : gamegame*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('sohotdude@gmail.com', '아 더워', "$2a$10$zNylvHhE95Pci7eU7SH7D.RkZliqCYTc1mft.2ciYx0/aiBEEFe7K", 'm', 1, NOW());  /*pw : sohotdude*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('television@gmail.com', '어쩔티비', "$2a$10$TChJQ4LadWKUZ7.0nYEBZOO9/z.MEqwUhkOP2n4fB1oe0NzQEfnvy", 'm', 1, NOW());  /*pw : television*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('pccafegogo@gmail.com', '피방가자', "$2a$10$Gk2Pkb7aFUsJc.cp.FRyrO1iyFWmq7fHMvMLntPTvCkkIsNDfgCVO", 'm', 1, NOW());  /*pw : pccafegogo*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('youngboy@gmail.com', '어린이', "$2a$10$GZ.qlrQhinIkoeO4EwSI9O6lQYNrJONRnRqPnjEWGyFo8rDvZmjMa", 'm', 1, NOW());  /*pw : youngboy*/

-- 20대 남성
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('dusickk@gmail.com', '두식', "$2a$10$65ecGxcxrnKHgphfbIR.QO5l.PFpHqqSFv34nVF8eiI1LVu03r.Hm", 'm', 2, NOW());  /*pw : dusickk*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('chulsoo@gmail.com', '철수', "$2a$10$89M65jt8BOiUEPzItMorX.Wi4HASaI3KOyAUbBwZ3H.vgiZfFZjbG", 'm', 2, NOW());  /*pw : chulsoo*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('zzanggu@gmail.com', '짱구', "$2a$10$S1OW2ztFG.kTpG16KczwzuNAX/ia05t9ficvzacWI0M7/Tpgrj9xe", 'm', 2, NOW());  /*pw : zzanggu*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('thisishyg@gmail.com', '형이야', "$2a$10$/J6Ix70uZim0E.T.tYKOdeH6yJPbl0j1bv8X9KvGeJkWfnPrgCFei", 'm', 2, NOW());  /*pw : thisishyg*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('daehakyes@gmail.com', '대학어제', "$2a$10$SAFRlzPAHYmvL5EUrqvutekfn.mCYxWk3VISB96pH63Es.A/J8MXK", 'm', 2, NOW());  /*pw : daehakyes*/

-- 30대 남성
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('dontenvy@gmail.com', '부럽지가 않아', "$2a$10$L2MHrBcbSk5Bzg3cG8Z2vuYiZasXA3eSsgB88DPSh/73LIsSCIvDW", 'm', 3, NOW());  /*pw : dontenvy*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('vennomm@gmail.com', '베놈', "$2a$10$tsFJwaCoPV1aJzIMWlBgsevNkCF8/ncrZRqX4uSp2fKHTWDLWeC1K", 'm', 3, NOW());  /*pw : vennomm*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('yerinsdad@gmail.com', '예린이아빠', "$2a$10$8ChbC1LE9UI.6lIKqfSlHeWLLBMxkzLFTVeLdWRDhivfgI32T27c6", 'm', 3, NOW());  /*pw : yerinsdad*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('golfgolf@gmail.com', '골프치자', "$2a$10$YaW7ZrSxpG69gq1maZ/UcOeLreB3Jn89bGKYAQD3oimnr1D8Duhzi", 'm', 3, NOW());  /*pw : golfgolf*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('goodboy@gmail.com', '멋진형', "$2a$10$npmhD7j8fw.S5JKa.z9SK.eUlh9mlFFWCMRLeR2zcr5x63/25XdHW", 'm', 3, NOW());  /*pw : goodboy*/

-- 40대 ~50대 남성
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('hwahwa@gmail.com', '화려강산', "$2a$10$NHkFJNPh0J2UQpYOfWajkeWawpsKGMGOjhFNLjXxsf6qMsJ/.Q6kS", 'm', 4, NOW());  /*pw : hwahwa*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('mugoong@gmail.com', '무궁화', "$2a$10$EvrvKsyQgDNreoXaIWMyweU92.RGmUYl7S2ngkIR5oZ0oao2Wk2Y.", 'm', 4, NOW());  /*pw : mugoong*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('samcheon@gmail.com', '삼천리 자전거', "$2a$10$xF70yNffWATnuGUF11SY1eugdivxhdn6k/Xbv7ruDrws.b.NtOUdS", 'm', 4, NOW());  /*pw : samcheon*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('daehann@gmail.com', '대한사람', "$2a$10$8/NGlgnK/6gcutoZu3MC9O3evGlhAaf9fDnkMLxB6fwAOYFF/TYFi", 'm', 4, NOW());  /*pw : daehann*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('taegukki@gmail.com', '태극기', "$2a$10$OwkSaHoyc0jl/Iv0eMyj0eeR1wyAhfmWiS6V4WJEa.nIu1qVcXeUu", 'm', 4, NOW());  /*pw : taegukki*/

-- 60대 이상 남성
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('together@gmail.com', '님과함께', "$2a$10$hXLIFtfWsl.g0jBPjMnj1OP0QBbKdMYdPOdgYWs5aIpMFQg4Je8Ge", 'm', 6, NOW());  /*pw : together*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('songgain@gmail.com', '송가인이 나라를 구한다', "$2a$10$cwLta.5vhtJu3PrMqeSs0eViK/yfAfqVIuCaXxSdc0dF.LR4KsTTS", 'm', 6, NOW());  /*pw : songgain*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('jaokkaa@gmail.com', '자옥아', "$2a$10$.IfIHNMf0cppjQ2UCygNC.9zhnJq4xjJvI8vokUggw04c6SERvGAq", 'm', 6, NOW());  /*pw : jaokkaa*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('noreason@gmail.com', '무조건이야', "$2a$10$Csn4CC27mcH6JjqX3WzbIO2Irbq0Ow/t6ZH/3iuccaFfIKX.2JHrW", 'm', 6, NOW());  /*pw : noreason*/
INSERT INTO `userTBL` (email, nickName, pw, sex, age, last_login) VALUES ('jinjinja@gmail.com', '진진자라', "$2a$10$SffPDhslr3.3K0n/RefgKu4U6wI2YPx0uXN3PetVrb5pRfIx1opKG", 'm', 6, NOW());  /*pw : jinjinja*/



-- 이벤트 정보 입력 ------------------------------------------------------------------------------

INSERT INTO eventTBL (eventID, eventName, startDate, endDate, addr1, addr2, kind, firstimage, firstimage2, mapx, mapy, mlevel, areacode, sigungucode, tel, sponsor1, sponsor1tel, sponsor2, sponsor2tel, playtime, eventplace, eventhomepage, agelimit, bookingplace, placeinfo, subevent, program, usetimefestival, discountinfofestival, spendtimefestival) VALUES ( 2819403 , '강남디자인위크' , 20220826 , 20220904 , '서울특별시 강남구 논현동', '논현동 가구거리', 'A02080600', 'http://tong.visitkorea.or.kr/cms/resource/81/2838381_image2_1.jpg', 'http://tong.visitkorea.or.kr/cms/resource/81/2838381_image3_1.jpg', 127.0225157989, 37.5112531257, 6, 1, NULL, '02-3423-5532',
'강남구', '02-3423-5532', '강남구', NULL, '11:00∼18:00', '논현동 가구거리 및 청담명품거리 일대', NULL, NULL, NULL, NULL, NULL, NULL, '무료', NULL, NULL);

INSERT INTO eventTBL (eventID, eventName, startDate, endDate, addr1, addr2, kind, firstimage, firstimage2, mapx, mapy, mlevel, areacode, sigungucode, tel, sponsor1, sponsor1tel, sponsor2, sponsor2tel, playtime, eventplace, eventhomepage, agelimit, bookingplace, placeinfo, subevent, program, usetimefestival, discountinfofestival, spendtimefestival) VALUES ( 2713558 , '강원세계산림엑스포' , 20230504 , 20230606 , '강원도 고성군 토성면 잼버리로 244', '', 'A02080600', 'http://tong.visitkorea.or.kr/cms/resource/24/2804924_image2_1.jpg', 'http://tong.visitkorea.or.kr/cms/resource/24/2804924_image2_1.jpg', 128.5001657397, 38.2236839691, 6, 32, NULL, '033-818-2158',
'(재)강원세계산림엑스포조직위원회', '033-818-2158', NULL, NULL, NULL, '강원도세계잼버리수련장', NULL, NULL, NULL, NULL, NULL, NULL, '보통권 :<br>일반(만 19세 ~ 64세) 10,000원(예매 8,000원)<br>청소년(만 13세 ~ 18세) 7,000원(예매 6,000원)<br>어린이(만 7세 ~ 12세) 5,000원(예매 4,000원)<br><br>단체권 :<br>일반(만 19세 ~ 64세) 8,000원<br>청소년(만 13세 ~ 18세) 6,000원<br>어린이(만 7세 ~ 12세) 5,000원<br>※ 내국인 :<br>20명 이상 / 외국인 : 10명 이상, 예매요금 동일, 동시입장<br><br>우대권 :<br>일반(만 19세 ~ 64세) 5,000원(예매 5,000원)<br>청소년(만 13세 ~ 18세) 3,500원(예매 3,500원)<br>어린이(만 7세 ~ 12세) 2,500원(예매 2,500원)<br>※ 만 65세 ~74세, 경증(4~6급)장애인, 현역군경(의무복무자), 강원도민<br><br>가족권 : 24,000원(예매 20,000원)<br><br>※ 무료입장 : 국가(독립)유공자, 국민기초생활수급자, 중증(1~3)급  장애인 및 보호자 1명, 만 75세 이상, 만 7세 미만, 공무수행자, 국빈외교사절단 및 수행자, 단체인솔자(20명당 1인), 학교단체 인솔 교사', NULL, NULL);

INSERT INTO eventTBL (eventID, eventName, startDate, endDate, addr1, addr2, kind, firstimage, firstimage2, mapx, mapy, mlevel, areacode, sigungucode, tel, sponsor1, sponsor1tel, sponsor2, sponsor2tel, playtime, eventplace, eventhomepage, agelimit, bookingplace, placeinfo, subevent, program, usetimefestival, discountinfofestival, spendtimefestival) VALUES ( 2829875 , '강원일자리박람회' , 20220914 , 20220928 , '강원도 횡성군 문예로 75 횡성 문화체육공원', '', 'A02080600', 'http://tong.visitkorea.or.kr/cms/resource/74/2829874_image2_1.png', 'http://tong.visitkorea.or.kr/cms/resource/74/2829874_image3_1.png', 127.9782824672, 37.4907202341, 6, 32, NULL, '강원일자리재단 : 033-256-9602<br>운영사무국 : 1644-4845',
'강원도', '강원일자리재단 : 033-256-9602<br>운영사무국 : 1644-4845', '강원일자리재단', NULL, '온라인 메타버스 09.15(목) ~17(토)<br>온라인 10:00 ~ 18:00<br>횡성 09.14(수)<br>강릉 09.19(월)<br>춘천 09.27(화)~28(수)<br>원주 09.28(수)', '온라인 메타버스 박람회 : 2022.09.15(목) ~ 09.17(토)<br>오프라인 박람회<br>횡성 국민 체육센터 - 09.14(수)<br>강릉 아이스아레나 - 09.19(월)<br>춘천 춘천농협농수산물종합유통센터 - 09.23(금)<br>원주 국민체육센터 - 09.28(수)', NULL, NULL, NULL, NULL, NULL, NULL, '무료', NULL, NULL);

INSERT INTO eventTBL (eventID, eventName, startDate, endDate, addr1, addr2, kind, firstimage, firstimage2, mapx, mapy, mlevel, areacode, sigungucode, tel, sponsor1, sponsor1tel, sponsor2, sponsor2tel, playtime, eventplace, eventhomepage, agelimit, bookingplace, placeinfo, subevent, program, usetimefestival, discountinfofestival, spendtimefestival) VALUES ( 141105 , '경남고성공룡세계엑스포' , 20221001 , 20221030 , '경상남도 고성군 당항만로 1116', '', 'A02070200', 'http://tong.visitkorea.or.kr/cms/resource/38/2828038_image2_1.jpg', 'http://tong.visitkorea.or.kr/cms/resource/38/2828038_image3_1.jpg', 128.3915388600, 35.0533471834, 6, 36, NULL, '055)670-3814',
'경상남도 고성군', '055)670-3814', '(재)경남고성공룡세계엑스포조직위원회', NULL, '일~목: 09:00 ~ 18:00<br>금,토, 10/2, 10/9 : 09:00 ~ 22:00', '고성군 당항포관광지', NULL, NULL, NULL, NULL, NULL, NULL, '대인(만 19세 ~ 만 64세) 18,000원 (사전예매 12,000원)<br>소인(만 3세 ~ 만 18세) 12,000원 (사전예매 6,000원)<br>※ 무료: 만 3세미만 어린이, 국가(독립)유공자 및 유족, 중증(구 1~3급) 장애인, 교육기관 인솔교사(사전등록필요), 단체관람객 유치자', NULL, NULL);

INSERT INTO eventTBL (eventID, eventName, startDate, endDate, addr1, addr2, kind, firstimage, firstimage2, mapx, mapy, mlevel, areacode, sigungucode, tel, sponsor1, sponsor1tel, sponsor2, sponsor2tel, playtime, eventplace, eventhomepage, agelimit, bookingplace, placeinfo, subevent, program, usetimefestival, discountinfofestival, spendtimefestival) VALUES ( 2809281 , '고창농촌영화제' , 20221028 , 20221030 , '전라북도 고창군 녹두로 1265 고창군농산물유통센타', '', 'A02081300', 'http://tong.visitkorea.or.kr/cms/resource/80/2809280_image2_1.jpg', 'http://tong.visitkorea.or.kr/cms/resource/80/2809280_image2_1.jpg', 126.6846960753, 35.4337773663, 6, 37, NULL, '010-3568-7907',
'고창군', '010-3568-7907', '고창농촌영화제 조직위원회', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '무료', NULL, NULL);

INSERT INTO eventTBL (eventID, eventName, startDate, endDate, addr1, addr2, kind, firstimage, firstimage2, mapx, mapy, mlevel, areacode, sigungucode, tel, sponsor1, sponsor1tel, sponsor2, sponsor2tel, playtime, eventplace, eventhomepage, agelimit, bookingplace, placeinfo, subevent, program, usetimefestival, discountinfofestival, spendtimefestival) VALUES ( 2612274 , '관악강감찬축제' , 20221014 , 20221016 , '서울특별시 관악구', '(봉천동)', 'A02070200', 'http://tong.visitkorea.or.kr/cms/resource/16/2667216_image2_1.jpg', 'http://tong.visitkorea.or.kr/cms/resource/16/2667216_image2_1.jpg', 126.9514981970, 37.4782084678, 6, 1, NULL, '02) 828-5763',
'관악구', '02) 828-5763', '관악문화재단', '02) 828-5763', NULL, '비대면 온라인 중심', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO eventTBL (eventID, eventName, startDate, endDate, addr1, addr2, 
kind, firstimage, firstimage2, mapx, mapy, mlevel, areacode, sigungucode, tel, sponsor1,
 sponsor1tel, sponsor2, sponsor2tel, playtime, eventplace, eventhomepage, agelimit, bookingplace, placeinfo, subevent, 
 program, usetimefestival, discountinfofestival, spendtimefestival) VALUES ( 2786391 , '광안리 M(Marvelous) 드론 라이트쇼' , 20220402 
 , 20231231 , '부산광역시 수영구 광안해변로 219', '(광안동)', 
 'A02081300', 'http://tong.visitkorea.or.kr/cms/resource/20/2822720_image2_1.jpg',
 'http://tong.visitkorea.or.kr/cms/resource/20/2822720_image3_1.jpg', 129.1185505648, 35.1538269450,NULL , 6,
 NULL, '051-610-4884','부산광역시 수영구', '051-610-4884', NULL, NULL, 
 '*연중상설 매주 토요일<br>동절기(10월~2월)19:00,21:00 <br>하절기(그외)20:00,21:00', '광안리해변 일원', 
 NULL, NULL, NULL, NULL, NULL, NULL, '무료', NULL, '10분 내외');

INSERT INTO eventTBL (eventID, eventName, startDate, endDate, addr1, addr2, kind, firstimage, firstimage2, mapx, mapy, mlevel, areacode, sigungucode, tel, sponsor1, sponsor1tel, sponsor2, sponsor2tel, playtime, eventplace, eventhomepage, agelimit, bookingplace, placeinfo, subevent, program, usetimefestival, discountinfofestival, spendtimefestival) VALUES ( 506545 , '광안리어방축제' , 20221014 , 20221016 , '부산광역시 수영구 광안동', '', 'A02070100', 'http://tong.visitkorea.or.kr/cms/resource/34/2689834_image2_1.png', 'http://tong.visitkorea.or.kr/cms/resource/34/2689834_image2_1.png', 129.1187283431, 35.1532381253, 6, 6, NULL, '051-610-4062',
'수영구', '051-610-4062', '부산광역시 수영구 축제위원회', '051-610-4062', '홈페이지 참조', '광안리해변 및 수영사적공원 일원', NULL, '전연령 가능', NULL, NULL, NULL, NULL, '무료', NULL, '기간내 자유');

INSERT INTO eventTBL (eventID, eventName, startDate, endDate, addr1, addr2, kind, firstimage, firstimage2, mapx, mapy, mlevel, areacode, sigungucode, tel, sponsor1, sponsor1tel, sponsor2, sponsor2tel, playtime, eventplace, eventhomepage, agelimit, bookingplace, placeinfo, subevent, program, usetimefestival, discountinfofestival, spendtimefestival) VALUES ( 629718 , '광주 왕실도자기축제' , 20220826 , 20220828 , '경기도 광주시 곤지암읍 경충대로 727', '', 'A02070200', '', '', 127.3266121626, 37.3510216352, 6, 31, NULL, '031-760-1714',
'광주시', '031-760-1714', '광주시문화재단', NULL, '2022.08.26. (14:00~22:00)<br>2022.08.07.~2022.08.28. (11:00~22:00)', '곤지암도자공원 일대', NULL, NULL, NULL, NULL, NULL, NULL, '홈페이지 참조', NULL, NULL);

INSERT INTO eventTBL (eventID, eventName, startDate, endDate, addr1, addr2, kind, firstimage, firstimage2, mapx, mapy, mlevel, areacode, sigungucode, tel, sponsor1, sponsor1tel, sponsor2, sponsor2tel, playtime, eventplace, eventhomepage, agelimit, bookingplace, placeinfo, subevent, program, usetimefestival, discountinfofestival, spendtimefestival) VALUES ( 2561750 , '광주비엔날레' , 20230407 , 20230709 , '광주광역시 북구 비엔날레로 111', '', 'A02081300', 'http://tong.visitkorea.or.kr/cms/resource/48/2561748_image2_1.JPG', 'http://tong.visitkorea.or.kr/cms/resource/48/2561748_image2_1.JPG', 126.8902647155, 35.1825648257, 6, 5, NULL, '062-608-4114',
'(재)광주비엔날레', '062-608-4114', '광주광역시', '062-608-4114', NULL, '광주비엔날레 전시관, 국립광주박물관, 광주극장, 호랑가시나무 아트폴리곤', NULL, '전연령 가능', '현장판매 : 광주비엔날레전시관, 국립아시아문화전당전시관', NULL, NULL, NULL, '홈페이지 참고', NULL, '기간내 자유');

INSERT INTO `mainEventTBL` VALUES (1,'어디가?는 처음이신가요?', 'http://localhost:3000/asset/mainEvent/1.jpg', NULL);
INSERT INTO `mainEventTBL` VALUES (2,'산림에서 힐링하자', 'http://tong.visitkorea.or.kr/cms/resource/24/2804924_image2_1.jpg',2713558);
INSERT INTO `mainEventTBL` VALUES (3,'낭만적인 드론 라이트 쇼!', 'http://tong.visitkorea.or.kr/cms/resource/20/2822720_image2_1.jpg',2786391);
INSERT INTO `mainEventTBL` VALUES (4,'나랑 공룡보러 갈래?', 'http://tong.visitkorea.or.kr/cms/resource/38/2828038_image2_1.jpg',141105);
INSERT INTO `mainEventTBL` VALUES (5,'취업난 이겨내자 강원청년 모두모여~~!', 'http://tong.visitkorea.or.kr/cms/resource/74/2829874_image2_1.png',2829875);
INSERT INTO `mainEventTBL` VALUES (6,'영화제를 한다구요...? 농촌에서요...?', 'http://tong.visitkorea.or.kr/cms/resource/80/2809280_image2_1.jpg',2809281);



INSERT INTO `keywordTBL` VALUES (1, '밤');
INSERT INTO `keywordTBL` VALUES (1,'야시장');
INSERT INTO `keywordTBL` VALUES (1,'먹거리');
INSERT INTO `keywordTBL` VALUES (1,'진흙');
INSERT INTO `keywordTBL` VALUES (1,'축제');

UPDATE eventTBL set w1 = 10 and w2 = 3 and w3 = 0 and w4 = 0 and w6 = 0 and m1 = 0 and m2 = 0 and m3 = 0 and m4 = 1 and m6 = 2 where eventID = 141105;
UPDATE eventTBL set w1 = 0  and w6 = 0 and m1 = 0 and m2 = 100 and m3 = 100 and m4 = 0 and m6 = 2 where eventID = 506545;
UPDATE eventTBL set w1 = 10 and w2 = 3 and w3 = 21 and w4 = 0 and w6 = 0 and m1 = 6 and m2 = 0 and m3 = 0 and m4 = 0 and m6 = 2 where eventID = 629718;
UPDATE eventTBL set w1 = 10 and w2 = 5 and w3 = 0 and w4 = 0 and w6 = 1 and m1 = 0 and m2 = 0 and m3 = 0 and m4 = 2 and m6 = 2 where eventID = 2561750;
UPDATE eventTBL set w1 = 0 and w2 = 3 and w3 = 0 and w4 = 1 and w6 = 0 and m1 = 4 and m2 = 0 and m3 = 9 and m4 = 0 and m6 = 2 where eventID = 2612274;
UPDATE eventTBL set w1 = 10 and w2 = 3 and w3 = 0 and w4 = 5 and w6 = 0 and m1 = 0 and m2 = 0 and m3 = 0 and m4 = 0 and m6 = 2 where eventID = 2713558;
UPDATE eventTBL set w1 = 10 and w2 = 3 and w3 = 7 and w4 = 0 and w6 = 0 and m1 = 0 and m2 = 0 and m3 = 0 and m4 = 0 and m6 = 2 where eventID = 2786391;
UPDATE eventTBL set w1 = 8 and w2 = 9 and w3 = 0 and w4 = 0 and w6 = 100 and m1 = 7 and m2 = 3 and m3 = 0 and m4 = 0 and m6 = 2 where eventID = 2809281;
UPDATE eventTBL set w1 = 1 and w2 = 3 and w3 = 5 and w4 = 0 and w6 = 0 and m1 = 0 and m2 = 0 and m3 = 0 and m4 = 0 and m6 = 2 where eventID = 2819403;
UPDATE eventTBL set w1 = 10 and w2 = 3 and w3 = 0 and w4 = 0 and w6 = 0 and m1 = 0 and m2 = 0 and m3 = 0 and m4 = 0 and m6 = 2 where eventID = 2829875;


INSERT INTO `userVisitedTBL` VALUES (1, 141105, 'g');
INSERT INTO `userVisitedTBL` VALUES (1, 506545, 'b');
INSERT INTO `userVisitedTBL` VALUES (1, 629718, 's');
INSERT INTO `userVisitedTBL` VALUES (1, 2561750, 'g');
INSERT INTO `userVisitedTBL` VALUES (1, 2612274, 'b');
INSERT INTO `userVisitedTBL` VALUES (11, 141105, 'g');
INSERT INTO `userVisitedTBL` VALUES (11, 506545, 'b');
INSERT INTO `userVisitedTBL` VALUES (34, 141105, 's');
INSERT INTO `userVisitedTBL` VALUES (25, 506545, 'g');
INSERT INTO `userVisitedTBL` VALUES (41, 141105, 'b');
INSERT INTO `userVisitedTBL` VALUES (17, 141105, 'g');
INSERT INTO `userVisitedTBL` VALUES (41, 2561750, 'b');
INSERT INTO `userVisitedTBL` VALUES (35, 2612274, 's');
INSERT INTO `userVisitedTBL` VALUES (45, 2612274, 'g');
INSERT INTO `userVisitedTBL` VALUES (19, 2612274, 'b');

INSERT INTO `userSavedTBL` VALUES (1, 2713558);
INSERT INTO `userSavedTBL` VALUES (1, 2786391);
INSERT INTO `userSavedTBL` VALUES (1, 2809281);
INSERT INTO `userSavedTBL` VALUES (1, 2819403);
INSERT INTO `userSavedTBL` VALUES (1, 2829875);
INSERT INTO `userSavedTBL` VALUES (12, 2713558);
INSERT INTO `userSavedTBL` VALUES (21, 2713558);
INSERT INTO `userSavedTBL` VALUES (34, 2713558);
INSERT INTO `userSavedTBL` VALUES (25, 2713558);
INSERT INTO `userSavedTBL` VALUES (41, 2713558);
INSERT INTO `userSavedTBL` VALUES (17, 2713558);
INSERT INTO `userSavedTBL` VALUES (41, 2786391);
INSERT INTO `userSavedTBL` VALUES (35, 2713558);
INSERT INTO `userSavedTBL` VALUES (45, 2713558);
INSERT INTO `userSavedTBL` VALUES (19, 2713558);

INSERT INTO `searchTBL` (word, count) VALUES ('워터밤', 10);
INSERT INTO `searchTBL` (word, count) VALUES ('물총', 8);
INSERT INTO `searchTBL` (word, count) VALUES ('음악', 7);
INSERT INTO `searchTBL` (word, count) VALUES ('백예린', 5);
INSERT INTO `searchTBL` (word, count) VALUES ('아이유', 10);
INSERT INTO `searchTBL` (word, count) VALUES ('재즈', 1);
INSERT INTO `searchTBL` (word, count) VALUES ('수영', 1);
INSERT INTO `searchTBL` (word, count) VALUES ('불꽃놀이', 4);
INSERT INTO `searchTBL` (word, count) VALUES ('수박', 2);
INSERT INTO `searchTBL` (word, count) VALUES ('야시장', 21);