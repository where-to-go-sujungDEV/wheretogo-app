-- Where to go : App project DataBase 
-- team members : Sujin Kang, Gahyun Son, Sihyun Jeon, 
-- Host:     Database: where2goDB
-- Made by Sujin Kang
-- ------------------------------------------------------


DROP database IF EXISTS `where2goDB`;
CREATE database where2goDB;
use where2goDB;


CREATE TABLE userTBL
(
    `userID`      BIGINT          NOT NULL    AUTO_INCREMENT, 
    `email`       varchar(50)     NOT NULL, 
    `nickName`    varchar(50)     NOT NULL, 
    `password`    varchar(200)    NOT NULL, 
    `sex`         varchar(1)      NULL        DEFAULT NULL, 
    `age`         int(1)          NULL        DEFAULT NULL, 
    `last_login`  datetime        NOT NULL    DEFAULT '2001-03-26', 
     PRIMARY KEY (userID)
);

ALTER TABLE userTBL COMMENT '회원 테이블';



CREATE TABLE eventTBL
(
    `eventID`    BIGINT            NOT NULL    AUTO_INCREMENT, 
    `eventName`  varchar(80)       NOT NULL, 
    `dou`        varchar(10)       NOT NULL, 
    `si`         varchar(10)       NOT NULL, 
    `genre`      varchar(10)       NOT NULL, 
    `kind`       varchar(10)       NOT NULL, 
    `theme`      varchar(10)       NOT NULL, 
    `startDate`  date          	   NOT NULL, 
    `endDate`    date              NULL, 
    `pic`        text              NOT NULL, 
    `time`       varchar(20)       NULL, 
    `place`      varchar(40)       NOT NULL, 
    `link`       varchar(100)      NOT NULL, 
    `cost`       varchar(1000)     NULL, 
    `content`    varchar(10000)    NOT NULL,
	`w1`       BIGINT    NULL        DEFAULT 0 COMMENT '10~19 ; w' DEFAULT 0, 
    `m1`       BIGINT    NULL        DEFAULT 0 COMMENT '10~19 ; m' DEFAULT 0, 
    `w2`       BIGINT    NULL        DEFAULT 0 COMMENT '20~29 ; w' DEFAULT 0, 
    `m2`       BIGINT    NULL        DEFAULT 0 COMMENT '20~29 ; m' DEFAULT 0, 
    `w3`       BIGINT    NULL        DEFAULT 0 COMMENT '30~39 ; w' DEFAULT 0, 
    `m3`       BIGINT    NULL        DEFAULT 0 COMMENT '30~39 ; m' DEFAULT 0, 
    `w4`       BIGINT    NULL        DEFAULT 0 COMMENT '40~59 w' DEFAULT 0, 
    `m4`       BIGINT    NULL        DEFAULT 0 COMMENT '40~59m' DEFAULT 0, 
    `w6`       BIGINT    NULL        DEFAULT 0 COMMENT '60~ ; w' DEFAULT 0, 
    `m6`       BIGINT    NULL        DEFAULT 0 COMMENT '60~ ; m' DEFAULT 0, 
     PRIMARY KEY (eventID)
);

ALTER TABLE eventTBL COMMENT '이벤트 테이블';



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
    `event1ID`     BIGINT         NULL, 
    `event2ID`     BIGINT         NULL, 
    `event3ID`     BIGINT         NULL, 
    `event4ID`     BIGINT         NULL, 
    `event5ID`     BIGINT         NULL, 
     PRIMARY KEY (mainEventID)
);

ALTER TABLE mainEventTBL COMMENT '메인 이벤트 테이블 (홈 상단)';

ALTER TABLE mainEventTBL
    ADD CONSTRAINT FK_mainEventTBL_event1ID_eventTBL_eventID FOREIGN KEY (event1ID)
        REFERENCES eventTBL (eventID) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE mainEventTBL
    ADD CONSTRAINT FK_mainEventTBL_event2ID_eventTBL_eventID FOREIGN KEY (event2ID)
        REFERENCES eventTBL (eventID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE mainEventTBL
    ADD CONSTRAINT FK_mainEventTBL_event3ID_eventTBL_eventID FOREIGN KEY (event3ID)
        REFERENCES eventTBL (eventID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE mainEventTBL
    ADD CONSTRAINT FK_mainEventTBL_event4ID_eventTBL_eventID FOREIGN KEY (event4ID)
        REFERENCES eventTBL (eventID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE mainEventTBL
    ADD CONSTRAINT FK_mainEventTBL_event5ID_eventTBL_eventID FOREIGN KEY (event5ID)
        REFERENCES eventTBL (eventID) ON DELETE RESTRICT ON UPDATE RESTRICT;




CREATE TABLE searchTBL
(
    `searchID`  BIGINT         NOT NULL    AUTO_INCREMENT, 
    `word`      VARCHAR(45)    NOT NULL, 
    `count`     BIGINT         NOT NULL    DEFAULT 0, 
     PRIMARY KEY (searchID)
);

ALTER TABLE searchTBL COMMENT '검색어 테이블';




CREATE TABLE userKeywordTBL
(
    `userID`     BIGINT         NOT NULL, 
    `content`    varchar(10)    NOT NULL, 
     PRIMARY KEY (userID, content)
);

ALTER TABLE userKeywordTBL COMMENT '사용자가 등록한 키워드 테이블';

ALTER TABLE userKeywordTBL
    ADD CONSTRAINT FK_userKeywordTBL_userID_userTBL_userID FOREIGN KEY (userID)
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



-- Insert DATA
INSERT INTO `userTBL` VALUES (1,'bobo@gmail.com','보보','$2a$10$ToKR8fL9JgaOajZqxSomQeJpQlMgwpg4qtiS8aA2UTuaUwIyymQbC' , 'w', 2, default); /*pw : babaoba*/
INSERT INTO `userTBL` VALUES (2,'water@gmail.com','물마셔요', '$2a$10$A7lmvA8G03x84zylgs2/yel7r9JagASmdG2FBrp0vcAjVdAG4Ghsu', 'm', 3, default); /*pw : sujung*/
INSERT INTO `userTBL` VALUES (3,'ruru@naver.com','루루짱', '$2a$10$9fJS/X4QwiY.O6Jhff6sUeTt/w.1Wm9RdDoheJXZ2ywjjcyyVM73a', 'm', 5, default);  /*pw : test*/
INSERT INTO `userTBL` VALUES (4,'flower@gmail.com','꽃이조화', '$2a$10$sdeXENBV0HtI7486z/S.fOgG4z5FjnebsRcnMF0Jk9n0pXGAoA6je', 'm', 4, default); /*pw : play*/
INSERT INTO `userTBL` VALUES (5,'hot@gmail.com','더워죽겄다', '$2a$10$LjGParALQQ81lmwvq5a3A.xN.HdS9efhqXw.W3cn4pujR0SDGZLbC', 'w', 1, default); /*pw : bbuu*/


INSERT INTO `eventTBL` VALUES (1, '서울 밤도깨비 야시장','경기도','서울특별시','시장', '음식', '신남', '2021-08-12', NULL, 1, NULL, '반포한강공원 달빛광장', 'https://bamdokkaebi.org/', '무료', '올 여름 한강 야시장이 돌아옵니다!', 0, 0, 0, 10, 0, 2, 0, 5, 0, 0);
INSERT INTO `eventTBL` VALUES (2,'보령머드축제','충청남도','보령시','축제', '놀이', '신남', '2022-07-16 00:00:00', '2022-08-15', 2, '10:00 ~ 18:00', '2022보령해양머드박람회장 내 체험존', 'https://www.mudfestival.or.kr/intro/view','6000 ~ 12000', '2022보령해양머드박람회와 함께하는 제25회 보령머드축제가 3년 만에 정상 개최된다. 오는 22년 7월 16일부터 8월 15일까지 31일일간 대천해수욕장에서 진행될 예정이다. 지구촌 최대의 여름축제로 국적, 언어, 연령의 구분없이 모두가 하나가되어 즐기는 체험형 축제로 매년 많은 사랑을 받아오고 있으며 문화관광축제로도 선정되었다.', 0, 0,3, 0, 0, 0, 2, 0, 6, 0);
INSERT INTO `eventTBL` VALUES (3,'태백 해바라기 축제','강원도','태백시','축제', '자연', '평온', '2022-07-22 00:00:00', '2022-08-07',  3, NULL, '강원 태백시 구와우마을', 'http://www.sunflowerfestival.co.kr/', NULL, '제 18회 태백해바라기 축제를 개최한다. 2022년에는 7/22(금)~8/7(일)까지 자연스럽고 평화로운 구와우 마을에서 백만송이 해바라기꽃을 피워놓는다. 해바라기 군락이 마치 동화 속에 온 듯한 경험을 선사하는 곳, 태백 해바라기 축제', 0, 0, 0, 0,0, 1, 0, 0, 0, 1);
INSERT INTO `eventTBL` VALUES (4,'아시아프','경기도','서울특별시','전시', '미술', '평온', '2022-07-26 00:00:00', '2022-08-21',4, NULL, '홍익대학교 현대미술관(홍문관)', 'https://asyaaf.chosun.com/main/main.php',NULL, '국내 최대의 청년 미술축제 2022 아시아프가 7월 26일부터 8월 21일까지 홍익대학교 현대미술관에서 개최된다. 올해로 15회를 맞이한 아시아프는 참여작가 500명을 선정해 약 1000여점의 작품을 전시하게 된다. 만 20세~35세 청년작가들을 선발하는 아시아프 부문과 만 36세 이상 작가들에게 문호를 여는 히든 아티스트 부문으로 나뉘어 진행한다. 미술시장을 이끌어 갈 청년작가들의 다채로운 작품들을 관람하시는 것은 물론, 마음에 드는 작품을 직접 구매하는 것도 가능하다. 오프라인 전시가 종료된 후에는 아시아프 홈페이지에서 참여작가들의 작품을 온라인 구매할 수 있다.', 1,0, 1, 0, 1, 1, 0, 0, 0, 0);
INSERT INTO `eventTBL` VALUES (5,'국제 스페셜 뮤직&아트페스티벌','강원도','태백시','축제', '음악', '감동', '2022-07-26 00:00:00', '2022-07-29', 5, NULL, '강원도 평창 알펜시아 리조트', 'https://sokorea.or.kr/sok/culture.php', '무료', '매년 여름 평창에서 개최하고 있는 전 세계 유일의 발달장애 문화축제로써, 다양한 나라에서 모인 발달장애인 아티스트들과 비장애인들이 음악, 미술, 스포츠를 함께 즐기며 이를 통해 발달장애인들이 사회로 좀 더 포용 될 수 있도록 지원하는 국제적인 문화예술축제. 올해의 주제인 <우리, 여기, 다시 Again, We are here together>은 코로나19를 이겨내고 발달장애 아티스트 모두(우리)가 평창(여기)에서 (다시) 만나 축제의 장이 시작된다  라는 의미를 담았다. 3년 만에 대면 공연으로 개최되는 이번 페스티벌 콘서트에서 발달장애인 아티스트들의 음악적, 예술적 역량을 확인하시길 바란다.', 0, 0, 0, 2, 0, 100, 0, 0, 0, 0);



INSERT INTO `mainEventTBL` VALUES (1,'어디가?는 처음이신가요?', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `mainEventTBL` VALUES (2,'여름에는 물총놀이지! 워터밤 가보자고', 2, 1,2,3,4,5);
INSERT INTO `mainEventTBL` VALUES (3,'미친 더위 공포로 이겨내자 ~ 공포 이벤트 모음집 ~', 3,1,2,3,4,5);
INSERT INTO `mainEventTBL` VALUES (4,'나랑 꽃보러 갈래?', 4,1,2,3,4,5);
INSERT INTO `mainEventTBL` VALUES (5,'방학이잖아 축제가야지', 5,1,2,3,4,5);
INSERT INTO `mainEventTBL` VALUES (6,'연인끼리 가기좋은 8월 서울 행사', 6,1,2,3,4,5);



INSERT INTO `userKeywordTBL` VALUES (1, '밤');
INSERT INTO `userKeywordTBL` VALUES (1,'야시장');
INSERT INTO `userKeywordTBL` VALUES (1,'먹거리');
INSERT INTO `userKeywordTBL` VALUES (1,'진흙');
INSERT INTO `userKeywordTBL` VALUES (1,'축제');

INSERT INTO `userVisitedTBL` VALUES (1, 1, 'g');
INSERT INTO `userVisitedTBL` VALUES (1, 2, 'b');
INSERT INTO `userVisitedTBL` VALUES (1, 3, 's');
INSERT INTO `userVisitedTBL` VALUES (1, 4, 'g');
INSERT INTO `userVisitedTBL` VALUES (1, 5, 'b');

INSERT INTO `userSavedTBL` VALUES (1, 1);
INSERT INTO `userSavedTBL` VALUES (1, 2);
INSERT INTO `userSavedTBL` VALUES (1, 3);
INSERT INTO `userSavedTBL` VALUES (1, 4);
INSERT INTO `userSavedTBL` VALUES (1, 5);

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