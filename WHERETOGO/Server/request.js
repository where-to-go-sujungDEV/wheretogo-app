import request from 'request';

const serviceKey ="QNnTJy6f3sstORUG9MRvZBkU7%2F3vsnIy%2BAgmf%2FKQpuzsI9iC%2FWV7SHiDqrfUrYfDLoJTDX5TAPIQpUD0mGwwFA%3D%3D";
const numOfRows = 1;
const pageNo = 2;

let qr = "", dqr ="", eqr = "";

global.qr = qr, global.dqr = dqr;
var eventID, eventName, startDate, endDate; // NOT NULL

var addr1,addr2,kind ,pic, thumbnail , mapx, mapy , mlevel , areacode , sigungucode , tel , telname , homepage, overview, eventplace , bookingplace , subevent , price, agelimit, eventtime;  

var getInfo = {
  'method': 'GET',
  "rejectUnauthorized": false, 
  'url': 'https://apis.data.go.kr/B551011/KorService/searchFestival?serviceKey=' + serviceKey +'&numOfRows=' + numOfRows + '&pageNo='+pageNo+'&MobileOS=AND&MobileApp=wheretogo&_type=json&eventStartDate=20220817',
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

request(getInfo, function (error, response, body) {
  if (error) throw new Error(error);
  let info = JSON.parse(body);

  let infoRes = info['response']['body']['items']['item'];


  console.log('총 개수 : ' + info['response']['body']['totalCount']);
console.log('출력된 개수 : ' + info['response']['body']['numOfRows']);

  qr = "";
  

  for (var i = 0; i <  info['response']['body']['numOfRows'] ; i++) {

    eventID = infoRes[i]['contentid'];
    eventName = infoRes[i]['title'];
    startDate = infoRes[i]['eventstartdate'];
    endDate = infoRes[i]['eventenddate'];

    addr1 = infoRes[i]['addr1'];
    addr2 = infoRes[i]['addr2'];
    kind = infoRes[i]['cat3'];
    pic = infoRes[i]['firstimage'];
    thumbnail = infoRes[i]['firstimage2'];
    mapx = infoRes[i]['mapx'];
    mapy = infoRes[i]['mapy'];
    mlevel = infoRes[i]['mlevel'];
    areacode = infoRes[i]['areacode'];
    sigungucode = infoRes[i]['sigungucode'];
    tel = infoRes[i]['tel'];

    qr += "INSERT INTO eventTBL (eventID, eventName, startDate, endDate, addr1, addr2, kind, pic, thumbnail, mapx, mapy, mlevel, areacode, sigungucode, tel, telname, homepage, overview, eventplace,bookingplace, subevent, price, agelimit, eventtime) VALUES ( ";

    qr += eventID; qr += " , '";
    qr += eventName;qr += "' , ";
    qr += startDate;qr += " , ";
    qr += endDate;qr += " , ";

    if(addr1.length){ qr += "'"+addr1+"'";} else {qr += "NULL";}qr += ", ";
    if(addr2.length){ qr += "'"+addr2+"'";} else {qr += "NULL";}qr += ", ";
    if(kind.length){ qr += "'"+kind+"'";} else {qr += "NULL";}qr += ", ";
    if(pic.length){ qr += "'"+pic+"'";} else {qr += "NULL";}qr += ", ";
    if(thumbnail.length){ qr += "'"+thumbnail+"'";} else {qr += "NULL";}qr += ", ";
    if(mapx.length){ qr += mapx;} else {qr += "NULL";}qr += ", ";
    if(mapy.length){ qr += mapy;} else {qr += "NULL";}qr += ", ";
    if(mlevel.length){qr += mlevel;} else {qr += "NULL";}qr += ", ";
    if(areacode.length){ qr += areacode;} else {qr += "NULL";}qr += ", ";
    if(sigungucode.length){ qr += sigungucode;} else {qr += "NULL";}qr += ", ";
    if(tel.length){ qr += "'"+tel+"'";} else {qr += "NULL";}qr += ", ";

    
    getExplainInfo.url += eventID;
    getDetailedInfo.url += eventID;
    //console.log(getDetailedInfo.url);
    //console.log('=====================qr============================\n');
    console.log(qr);

    request(getExplainInfo, function (err, response, bd) {
      if (err) throw new Error(err);
      //console.log(bd);
      let Einfo = JSON.parse(bd);

      eqr = "";

      let EinfoRes = Einfo['response']['body']['items']['item'];

     // console.log("==========eventID" +DinfoRes[0]['contentid'] +"=============");

      telname = EinfoRes[0]['telname'];
      homepage = EinfoRes[0]['homepage'];
      overview = EinfoRes[0]['overview'];

      if(telname.length){ eqr += "\""+telname+"\"";} else {eqr += "NULL";}eqr += ", ";
      if(homepage.length){ eqr += "\'"+homepage+"\'";} else {eqr += "NULL";}eqr += ", ";
      if(overview.length){ eqr += "\""+overview+"\"";} else {eqr += "NULL";}eqr += ", ";

      //console.log('=====================eqr============================\n');

      //console.log(eqr);

    });


    request(getDetailedInfo, function (err, response, bd) {
      if (err) throw new Error(err);
      //console.log(bd);
      let Dinfo = JSON.parse(bd);
      dqr = "";

      let DinfoRes = Dinfo['response']['body']['items']['item'];

      eventplace = DinfoRes[0]['eventplace'];
      bookingplace = DinfoRes[0]['bookingplace'];
      subevent = DinfoRes[0]['subevent'];
      price = DinfoRes[0]['usetimefestival'];
      agelimit = DinfoRes[0]['agelimit'];
      eventtime = DinfoRes[0]['spendtimefestival'];

      if(eventplace.length){ dqr += "'"+eventplace+"'";} else {dqr += "NULL";}dqr += ", ";
      if(bookingplace.length){ dqr += "'"+bookingplace+"'";} else {dqr += "NULL";}dqr += ", ";
      if(subevent.length){ dqr += "'"+subevent+"'";} else {dqr += "NULL";}dqr += ", ";
      if(price.length){ dqr += "'"+price+"'";} else {dqr += "NULL";}dqr += ",";
      if(agelimit.length){ dqr += "'"+agelimit+"'";} else {dqr += "NULL";}dqr += ", ";
      if(eventtime.length){ dqr += "'"+eventtime+"'";} else {dqr += "NULL";}dqr += ");";

    console.log(eqr);
    console.log(dqr);

    });
    getDetailedInfo.url = 'https://apis.data.go.kr/B551011/KorService/detailIntro?serviceKey=' + serviceKey +'&numOfRows=1&pageNo=1&MobileOS=AND&MobileApp=wheretogo&_type=json&contentTypeId=15&contentId=';
    getExplainInfo.url = 'https://apis.data.go.kr/B551011/KorService/detailCommon?MobileOS=AND&MobileApp=wheretogo&serviceKey=' + serviceKey + '&_type=json&defaultYN=Y&overviewYN=Y&contentId=';
}
});
