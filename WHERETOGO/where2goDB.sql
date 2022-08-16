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
    `pw`    varchar(200)    NOT NULL, 
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
    `link`       text      NOT NULL, 
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
INSERT INTO `eventTBL` (eventName, dou, si, genre, kind, theme, startDate, endDate, pic, place, link, content) 
VALUES ('서울 프린지 페스티벌', '경기도', '서울특별시', '전시', '미술', '감동', '2022-08-11', '2022-08-28', 'http://tong.visitkorea.or.kr/cms/resource/39/2503539_image2_1.png', "서울시 마포구 및 서대문구 일대", "https://www.seoulfringefestival.net:5632/", '예술가의 자유로운 시도와 그들의 주체성을 지지하는 예술축제, 서울프린지페스티벌 서울프린지페스티벌은 1998년 대학로에서 열린 ‘독립예술제’로 시작되어 매년 여름, 연극, 무용, 음악, 퍼포먼스, 미술, 영상 등 다양한 분야의 예술가들이 참여하는 축제이다. 예술가나 작품에 대한 심사나 선정이 없는 자유참가에 원칙을 두고 있으며, 모두에게 참여의 기회를 개방하고 있다. 정형화된 틀에서 벗어나 공간을 실험하고, 장르와 형식을 넘나드는 시도와 도전이 가능하다.');

INSERT INTO `eventTBL` (eventName, dou, si, genre, kind, theme, startDate, endDate, pic, place, link, content) 
VALUES ('Groove In Gwanak STREETDANCE FESTIVAL', '경기도', '서울특별시', '축제', '춤', '신남', '2022-07-16', '2022-08-31', 'http://tong.visitkorea.or.kr/cms/resource/39/2503539_image2_1.png', "신림역 인근 별빛내린천, S1472관천로문화플랫폼", "https://korean.visitkorea.or.kr/detail/fes_detail.do?cotid=a1fc3498-0dd7-41e0-a78b-440ea52971d7&big_category=undefined&mid_category=undefined&big_area=undefined&referrer=https://search.naver.com/search.naver?where=nexearch&sm=tab_etc&mra=bk00&pkid=110&os=28426284&qvt=0&query=Groove%20In%20Gwanak%20STREETDANCE%20FESTIVAL", '관악을 움직이다 - Groove in Gwanak
특별히 열정적인 댄서들이 모인 스트릿댄스 페스티벌이 2022년 여름, 관악구 도림천(별빛내린천)을 꾸민다. 한때 청년들의 거리문화로 치부되었던 스트릿댄스 장르는 최근 방송 매체와 여러 플랫폼을 통해 그 대중성과 예술성을 인정받고 있다. 이에 발 맞추어 관악에서 처음으로 스트릿댄스를 독립적인 현대 문화 예술로 바라보고 그들의 열정을 마음껏 표현할 수 있는 축제를 마련했다.
2022년 7월 16일 토요일, 그 첫 무대가 여러분을 찾아간다.');

INSERT INTO `eventTBL` (eventName, dou, si, genre, kind, theme, startDate, endDate, pic, place, link, content) 
VALUES ('기억의 숲', '경기도', '서울특별시', '연극', '서사', '공포', '2022-07-15', '2022-08-25', 'http://tong.visitkorea.or.kr/cms/resource/39/2503539_image2_1.png', "대학로 유니플렉스 3관", "없음", '어둡고 칙칙한 취조실. 한 남자와 범인이 마주 앉아 있다. 17명을 잔인하게 살해하고 마치 영안실에 시체를 안치하듯 자신의 집 지하실 사물함에 보관한 희대의 살인마는 종신형을 선고받게 된다.');

INSERT INTO `eventTBL` (eventName, dou, si, genre, kind, theme, startDate, endDate, pic, place, link, content) 
VALUES ('너의 목소리가 들려', '경기도', '서울특별시', '연극', '서사', '재미', '2018-12-06', '2023-12-12', 'http://tong.visitkorea.or.kr/cms/resource/39/2503539_image2_1.png', "봄날 아트홀 2관", "없음", '재개발 지역에 발생한 갑작스러운 화재로 많은 사람들이 죽고 수지는 엄마를 잃게 된다. 방화범으로 몰린 수지는 교도소에 가게 되고... 10년의 수감생활을 마친 수지는 엄마와의 추억이 깃든 옛 동네로 발걸음을 옮긴다.');

INSERT INTO `eventTBL` (eventName, dou, si, genre, kind, theme, startDate, endDate, pic, place, link, content) 
VALUES ('부산국제매직페스티벌', '경상남도', '부산광역시', '축제', '놀이', '재미', '2022.06.01', '2022.11.13', 'http://tong.visitkorea.or.kr/cms/resource/39/2503539_image2_1.png', "행사별 상이", "https://www.hibimf.org/", '부산국제매직페스티벌은 2006년부터 시작되어 문화콘텐츠 산업의 떠오르는 블루오션인 ‘매직’을 테마로 한 국내 100만 매직 매니아의 꿈의 축제인 국내 유일 세계 최대 규모의 마술 페스티벌이다. 올해 11월까지 진행되는 제17회 부산국제매직페스티벌에는 1년 내내 마술로, 매직컨벤션, 제4회 국제매직버스킹챔피언십, 매직위크 등 다양한 행사들이 준비되어있다.');



INSERT INTO `mainEventTBL` VALUES (1,'어디가?는 처음이신가요?', 'http://tong.visitkorea.or.kr/cms/resource/39/2503539_image2_1.png', NULL);
INSERT INTO `mainEventTBL` VALUES (2,'여름에는 물총놀이지! 워터밤 가보자고', 'http://tong.visitkorea.or.kr/cms/resource/39/2503539_image2_1.png',2);
INSERT INTO `mainEventTBL` VALUES (3,'미친 더위 공포로 이겨내자 ~ 공포 이벤트 모음집 ~', 'http://tong.visitkorea.or.kr/cms/resource/39/2503539_image2_1.png',3);
INSERT INTO `mainEventTBL` VALUES (4,'나랑 꽃보러 갈래?', 'http://tong.visitkorea.or.kr/cms/resource/39/2503539_image2_1.png',3);
INSERT INTO `mainEventTBL` VALUES (5,'방학이잖아 축제가야지', 'http://tong.visitkorea.or.kr/cms/resource/39/2503539_image2_1.png',2);
INSERT INTO `mainEventTBL` VALUES (6,'연인끼리 가기좋은 8월 서울 행사', 'http://tong.visitkorea.or.kr/cms/resource/39/2503539_image2_1.png',4);



INSERT INTO `keywordTBL` VALUES (1, '밤');
INSERT INTO `keywordTBL` VALUES (1,'야시장');
INSERT INTO `keywordTBL` VALUES (1,'먹거리');
INSERT INTO `keywordTBL` VALUES (1,'진흙');
INSERT INTO `keywordTBL` VALUES (1,'축제');

INSERT INTO `userVisitedTBL` VALUES (1, 1, 'g');
INSERT INTO `userVisitedTBL` VALUES (1, 2, 'b');
INSERT INTO `userVisitedTBL` VALUES (3, 3, 's');
INSERT INTO `userVisitedTBL` VALUES (5, 4, 'g');
INSERT INTO `userVisitedTBL` VALUES (12, 5, 'b');
INSERT INTO `userVisitedTBL` VALUES (11, 4, 'g');
INSERT INTO `userVisitedTBL` VALUES (11, 2, 'b');
INSERT INTO `userVisitedTBL` VALUES (34, 3, 's');
INSERT INTO `userVisitedTBL` VALUES (25, 4, 'g');
INSERT INTO `userVisitedTBL` VALUES (41, 5, 'b');
INSERT INTO `userVisitedTBL` VALUES (17, 1, 'g');
INSERT INTO `userVisitedTBL` VALUES (41, 2, 'b');
INSERT INTO `userVisitedTBL` VALUES (35, 3, 's');
INSERT INTO `userVisitedTBL` VALUES (45, 4, 'g');
INSERT INTO `userVisitedTBL` VALUES (19, 5, 'b');

INSERT INTO `userSavedTBL` VALUES (1, 1);
INSERT INTO `userSavedTBL` VALUES (11, 2);
INSERT INTO `userSavedTBL` VALUES (3, 3);
INSERT INTO `userSavedTBL` VALUES (5, 4);
INSERT INTO `userSavedTBL` VALUES (13, 5);
INSERT INTO `userSavedTBL` VALUES (1, 2);
INSERT INTO `userSavedTBL` VALUES (21, 2);
INSERT INTO `userSavedTBL` VALUES (34, 3);
INSERT INTO `userSavedTBL` VALUES (25, 4);
INSERT INTO `userSavedTBL` VALUES (41, 5);
INSERT INTO `userSavedTBL` VALUES (17, 1);
INSERT INTO `userSavedTBL` VALUES (41, 2);
INSERT INTO `userSavedTBL` VALUES (35, 3);
INSERT INTO `userSavedTBL` VALUES (45, 4);
INSERT INTO `userSavedTBL` VALUES (19, 5);

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