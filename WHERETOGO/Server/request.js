import request from 'request';
import db from "./config/dbConnection.js";
import fs from 'fs';
//419개까지 넣음 / 22-09-07 업데이트
//446개까지 넣음 / 22-09-14 업데이트
const serviceKey ="QNnTJy6f3sstORUG9MRvZBkU7%2F3vsnIy%2BAgmf%2FKQpuzsI9iC%2FWV7SHiDqrfUrYfDLoJTDX5TAPIQpUD0mGwwFA%3D%3D";
const numOfRows = 1;


const pageNo = 423;

let basic="INSERT INTO eventTBL (eventID, eventName, startDate, endDate, addr1, addr2, kind, pic, mapx, mapy, mlevel, areacode, sigungucode, tel, homepage, overview, eventplace,bookingplace, subevent, price, agelimit, eventtime) VALUES ( "+'\n', qr = "", dqr ="", eqr = "";

global.basic = basic, global.qr = qr, global.dqr = dqr;
var eventID, eventName, startDate, endDate; // NOT NULL

var addr1,addr2,kind ,pic, mapx, mapy , mlevel , areacode , sigungucode , tel , homepage, overview, eventplace , bookingplace , subevent , price, agelimit, eventtime;  

function getLastAmount(){
  return new Promise((res, rej) => {
    db.query("select count(*) as count from eventTBL;", (err, results) => {             
      if(err) {
          res("failed");
      } else {
          res (results[0].count);
      }
  });  
  });
}

var getTotal = {
  'method': 'GET',
  "rejectUnauthorized": false, 
  'url': 'https://apis.data.go.kr/B551011/KorService/searchFestival?serviceKey=' + serviceKey +'&numOfRows=1&pageNo=1&MobileOS=AND&MobileApp=wheretogo&_type=json&eventStartDate=20220817&arrange=D',
  'headers': {
  },
  form: {

  }
};

var getInfo = {
  'method': 'GET',
  "rejectUnauthorized": false, 
  'url': 'https://apis.data.go.kr/B551011/KorService/searchFestival?serviceKey=' + serviceKey +'&numOfRows=1&MobileOS=AND&MobileApp=wheretogo&_type=json&eventStartDate=20220817&arrange=D&pageNo=',
  'headers': {
  },
  form: {

  }
};

var getDetailedInfo = { 
  'method': 'GET',
  "rejectUnauthorized": false, 
  'url': 'https://apis.data.go.kr/B551011/KorService/detailIntro?serviceKey=' + serviceKey +'&numOfRows=1&pageNo=1&MobileOS=AND&MobileApp=wheretogo&_type=json&contentTypeId=15&contentId=',
  'headers': {
  },
  form: {

  }
};

var getExplainInfo = {
  'method': 'GET',
  "rejectUnauthorized": false, 
  'url' : 'https://apis.data.go.kr/B551011/KorService/detailCommon?MobileOS=AND&MobileApp=wheretogo&serviceKey=' + serviceKey + '&_type=json&defaultYN=Y&overviewYN=Y&contentId=',
  'headers': {
  },
  form: {

  }
}

function getTotalNum(){
  return new Promise((res, rej) => {
    request(getTotal, function (error, response, body) {
      if (error) throw new Error(error);
      let info = JSON.parse(body);
      const totalNum = info['response']['body']['totalCount'];
      console.log(totalNum);
      res (totalNum);
      });
  });
}


function makeQr(i){
  return new Promise((res, rej) => {
  getInfo.url = 'https://apis.data.go.kr/B551011/KorService/searchFestival?serviceKey=' + serviceKey +'&numOfRows=1&MobileOS=AND&MobileApp=wheretogo&_type=json&eventStartDate=20220817&arrange=D&pageNo=';
  getInfo.url += i;

  request(getInfo, function (error, response, body) {
  if (error) throw new Error(error);
  let info = JSON.parse(body);

  let infoRes = info['response']['body']['items']['item'];

    qr = "";

    eventID = infoRes[0]['contentid'];
    eventName = infoRes[0]['title'];
    startDate = infoRes[0]['eventstartdate'];
    endDate = infoRes[0]['eventenddate'];

    addr1 = infoRes[0]['addr1'];
    addr2 = infoRes[0]['addr2'];
    kind = infoRes[0]['cat3'];
    pic = infoRes[0]['firstimage'];
    mapx = infoRes[0]['mapx'];
    mapy = infoRes[0]['mapy'];
    mlevel = infoRes[0]['mlevel'];
    areacode = infoRes[0]['areacode'];
    sigungucode = infoRes[0]['sigungucode'];
    tel = infoRes[0]['tel'];

    qr += eventID; qr += " , '";
    qr += eventName;qr += "' , ";
    qr += startDate;qr += " , ";
    qr += endDate;qr += " , ";

    eventName = eventName.replace(/'/g, '\'');
    eventName = eventName.replace(/"/g, '\"');

    addr1 = addr1.replace(/'/g, '\'');
    addr1 = addr1.replace(/"/g, '\"');

    addr2 = addr2.replace(/'/g, '\'');
    addr2 = addr2.replace(/"/g, '\"');

    tel = tel.replace(/'/g, '\'');
    tel = tel.replace(/"/g, '\"');

    if(addr1.length){ qr += "'"+addr1+"'";} else {qr += "NULL";}qr += ", ";
    if(addr2.length){ qr += "'"+addr2+"'";} else {qr += "NULL";}qr += ", ";
    if(kind.length){ qr += "'"+kind+"'";} else {qr += "\'A02081300\'";}qr += ", ";
    if(pic.length){ qr += "'"+pic+"'";} else {qr += "NULL";}qr += ", ";
    if(mapx.length){ qr += mapx;} else {qr += "NULL";}qr += ", ";
    if(mapy.length){ qr += mapy;} else {qr += "NULL";}qr += ", ";
    if(mlevel.length){qr += mlevel;} else {qr += "NULL";}qr += ", ";
    if(areacode.length){ qr += areacode;} else {qr += "100";}qr += ", ";
    if(sigungucode.length){ qr += sigungucode;} else {qr += "100";}qr += ", ";
    if(tel.length){ qr += "'"+tel+"'";} else {qr += "NULL";}qr += ", ";

    res({qr, eventID});
  });
});
}
    

function makeEqr(eventID){
  return new Promise((res, rej) => {
  getExplainInfo.url = 'https://apis.data.go.kr/B551011/KorService/detailCommon?MobileOS=AND&MobileApp=wheretogo&serviceKey=' + serviceKey + '&_type=json&defaultYN=Y&overviewYN=Y&contentId=';
      getExplainInfo.url += eventID;
      request(getExplainInfo, function (err, response, bd) {
      if (err) throw new Error(err);
      let Einfo = JSON.parse(bd);

      eqr = "";

      let EinfoRes = Einfo['response']['body']['items']['item'];

      homepage = EinfoRes[0]['homepage'];
      overview = EinfoRes[0]['overview'];

    homepage = homepage.replace(/'/g, '\'');

    overview = overview.replace(/'/g, '\'');
    overview = overview.replace(/"/g, '\"');

      if(homepage.length){ eqr += "\'"+homepage+"\'";} else {eqr += "NULL";}eqr += ", ";
      if(overview.length){ eqr += "\""+overview+"\"";} else {eqr += "NULL";}eqr += ", ";

      res(eqr);

      });   
    }); 
} 


function makeDqr(eventID){
  return new Promise((res, rej) => {
  getDetailedInfo.url = 'https://apis.data.go.kr/B551011/KorService/detailIntro?serviceKey=' + serviceKey +'&numOfRows=1&pageNo=1&MobileOS=AND&MobileApp=wheretogo&_type=json&contentTypeId=15&contentId=';
        getDetailedInfo.url += eventID;
        request(getDetailedInfo, function (err, response, dbd) {
          if (err) throw new Error(err);
          let Dinfo = JSON.parse(dbd);
          dqr = "";
    
          let DinfoRes = Dinfo['response']['body']['items']['item'];
    
          eventplace = DinfoRes[0]['eventplace'];
          bookingplace = DinfoRes[0]['bookingplace'];
          subevent = DinfoRes[0]['subevent'];
          price = DinfoRes[0]['usetimefestival'];
          agelimit = DinfoRes[0]['agelimit'];
          eventtime = DinfoRes[0]['spendtimefestival'];

          eventplace = eventplace.replace(/'/g, '\'');
          eventplace = eventplace.replace(/"/g, '\"');

          bookingplace = bookingplace.replace(/'/g, '\'');
          bookingplace = bookingplace.replace(/"/g, '\"');

          subevent = subevent.replace(/'/g, '\'');
          subevent = subevent.replace(/"/g, '\"');

          price = price.replace(/'/g, '\'');
          price = price.replace(/"/g, '\"');

          agelimit = agelimit.replace(/'/g, '\'');
          agelimit = agelimit.replace(/"/g, '\"');

          eventtime = eventtime.replace(/'/g, '\'');
          eventtime = eventtime.replace(/"/g, '\"');

          if(eventplace.length){ dqr += "'"+eventplace+"'";} else {dqr += "NULL";}dqr += ", ";
          if(bookingplace.length){ dqr += "'"+bookingplace+"'";} else {dqr += "NULL";}dqr += ", ";
          if(subevent.length){ dqr += "'"+subevent+"'";} else {dqr += "NULL";}dqr += ", ";
          if(price.length){ dqr += "'"+price+"'";} else {dqr += "NULL";}dqr += ",";
          if(agelimit.length){ dqr += "'"+agelimit+"'";} else {dqr += "NULL";}dqr += ", ";
          if(eventtime.length){ dqr += "'"+eventtime+"'";} else {dqr += "NULL";}dqr += ");";

          res(dqr);
        });
      }); 
}

async function getEveryEvent(){
  const totalN = await getTotalNum();

  const lastIdx = await getLastAmount(); //현재 DB의 총 이벤트 수 

  const amount = totalN - lastIdx;

  var i = pageNo;

  for (; i <= totalN; i++){
    const basic = "INSERT INTO eventTBL (eventID, eventName, startDate, endDate, addr1, addr2, kind, pic, mapx, mapy, mlevel, areacode, sigungucode, tel, homepage, overview, eventplace,bookingplace, subevent, price, agelimit, eventtime) VALUES ( ";

    const r1 = await makeQr(i);

    var keys = Object.keys(r1);
    const qr = r1[keys[0]];
    const eID = r1[keys[1]];
    
    const eqr = await makeEqr(eID);
    const dqr = await makeDqr(eID);
    
    fs.appendFile('event.txt', basic+ '\n'+ qr + '\n'+ eqr+ '\n'+ dqr + '\n'+ '\n', function (err) {
      if (err) throw err;
      console.log('The '+ (i - 1)  +'th'+ ' was appended to file!');
    });
  }

}

getEveryEvent();


