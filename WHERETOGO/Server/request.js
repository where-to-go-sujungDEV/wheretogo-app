import request from 'request';

const serviceKey ="QNnTJy6f3sstORUG9MRvZBkU7%2F3vsnIy%2BAgmf%2FKQpuzsI9iC%2FWV7SHiDqrfUrYfDLoJTDX5TAPIQpUD0mGwwFA%3D%3D";
const numOfRows = 1;
const pageNo = 11;

let qr = "", dqr ="";

global.qr = qr, global.dqr = dqr;
var eventID, eventName, startDate, endDate; // NOT NULL

var addr1,addr2,kind ,firstimage, firstimage2 , mapx, mapy , mlevel , areacode , sigungucode , tel , sponsor1 , sponsor1tel, sponsor2, sponsor2tel , playtime , eventplace , eventhomepage , agelimit , bookingplace , placeinfo, subevent , program , usetimefestival , discountinfofestival , spendtimefestival;  

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

request(getInfo, function (error, response, body) {
  if (error) throw new Error(error);
  //console.log(body);
  let info = JSON.parse(body);

  //console.log("info" + info);

  let infoRes = info['response']['body']['items']['item'];

  //console.log("infoRes" + infoRes[0]['title']);

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
    firstimage = infoRes[i]['firstimage'];
    firstimage2 = infoRes[i]['firstimage2'];
    mapx = infoRes[i]['mapx'];
    mapy = infoRes[i]['mapy'];
    mlevel = infoRes[i]['mlevel'];
    areacode = infoRes[i]['areacode'];
    sigungucode = infoRes[i]['sigungucode'];
    tel = infoRes[i]['tel'];

    qr += "INSERT INTO eventTBL (eventID, eventName, startDate, endDate, addr1, addr2, kind, firstimage, firstimage2, mapx, mapy, mlevel, areacode, sigungucode, tel, sponsor1, sponsor1tel, sponsor2, sponsor2tel, playtime, eventplace, eventhomepage, agelimit, bookingplace, placeinfo, subevent, program, usetimefestival, discountinfofestival, spendtimefestival) VALUES ( ";

    qr += eventID; qr += " , '";
    qr += eventName;qr += "' , ";
    qr += startDate;qr += " , ";
    qr += endDate;qr += " , ";

    if(addr1.length){ qr += "'"+addr1+"'";} else {qr += "NULL";}qr += ", ";
    if(addr2.length){ qr += "'"+addr2+"'";} else {qr += "NULL";}qr += ", ";
    if(kind.length){ qr += "'"+kind+"'";} else {qr += "NULL";}qr += ", ";
    if(firstimage.length){ qr += "'"+firstimage+"'";} else {qr += "NULL";}qr += ", ";
    if(firstimage2.length){ qr += "'"+firstimage2+"'";} else {qr += "NULL";}qr += ", ";
    if(mapx.length){ qr += mapx;} else {qr += "NULL";}qr += ", ";
    if(mapy.length){ qr += mapy;} else {qr += "NULL";}qr += ", ";
    if(mlevel.length){qr += mlevel;} else {qr += "NULL";}qr += ", ";
    if(areacode.length){ qr += areacode;} else {qr += "NULL";}qr += ", ";
    if(sigungucode.length){ qr += sigungucode;} else {qr += "NULL";}qr += ", ";
    if(tel.length){ qr += "'"+tel+"'";} else {qr += "NULL";}qr += ", ";

    getDetailedInfo.url += eventID;
    //console.log(getDetailedInfo.url);
    console.log(qr);


    request(getDetailedInfo, function (err, response, bd) {
      if (err) throw new Error(err);
      //console.log(bd);
      let Dinfo = JSON.parse(bd);

      dqr = "";

      let DinfoRes = Dinfo['response']['body']['items']['item'];

     // console.log("==========eventID" +DinfoRes[0]['contentid'] +"=============");

      sponsor1 = DinfoRes[0]['sponsor1'];
      sponsor1tel = DinfoRes[0]['sponsor1tel'];
      sponsor2 = DinfoRes[0]['sponsor2'];
      sponsor2tel = DinfoRes[0]['sponsor2tel']; 
      playtime = DinfoRes[0]['playtime'];
      eventplace = DinfoRes[0]['eventplace'];
      eventhomepage = DinfoRes[0]['eventhomepage'];
      agelimit = DinfoRes[0]['agelimit'];
      bookingplace = DinfoRes[0]['bookingplace'];
      placeinfo= DinfoRes[0]['placeinfo'];
      subevent = DinfoRes[0]['subevent'];
      program = DinfoRes[0]['program'];
      usetimefestival = DinfoRes[0]['usetimefestival'];
      discountinfofestival = DinfoRes[0]['discountinfofestival'];
      spendtimefestival = DinfoRes[0]['spendtimefestival'];

      if(sponsor1.length){ dqr += "'"+sponsor1+"'";} else {dqr += "NULL";}dqr += ", ";
      if(sponsor1tel.length){ dqr += "'"+sponsor1tel+"'";} else {dqr += "NULL";}dqr += ", ";
      if(sponsor2.length){ dqr += "'"+sponsor2+"'";} else {dqr += "NULL";}dqr += ", ";
      if(sponsor2tel.length){ dqr += "'"+sponsor2tel+"'";} else {dqr += "NULL";}dqr += ", ";
      if(playtime.length){ dqr += "'"+playtime+"'";} else {dqr += "NULL";}dqr += ", ";
      if(eventplace.length){ dqr += "'"+eventplace+"'";} else {dqr += "NULL";}dqr += ", ";
      if(eventhomepage.length){ dqr += "'"+eventhomepage+"'";} else {dqr += "NULL";}dqr += ", ";
      if(agelimit.length){ dqr += "'"+agelimit+"'";} else {dqr += "NULL";}dqr += ", ";
      if(bookingplace.length){ dqr += "'"+bookingplace+"'";} else {dqr += "NULL";}dqr += ", ";
      if(placeinfo.length){ dqr += "'"+placeinfo+"'";} else {dqr += "NULL";}dqr += ", ";
      if(subevent.length){ dqr += "'"+subevent+"'";} else {dqr += "NULL";}dqr += ", ";
      if(program.length){ dqr += "'"+program+"'";} else {dqr += "NULL";}dqr += ", ";
      if(usetimefestival.length){ dqr += "'"+usetimefestival+"'";} else {dqr += "NULL";}dqr += ", ";
      if(discountinfofestival.length){ dqr += "'"+discountinfofestival+"'";} else {dqr += "NULL";}dqr += ", ";
      if(spendtimefestival.length){ dqr += "'"+spendtimefestival+"'";} else {dqr += "NULL";}dqr += ");";

      console.log(dqr);

    });
    getDetailedInfo.url = 'https://apis.data.go.kr/B551011/KorService/detailIntro?serviceKey=' + serviceKey +'&numOfRows=1&pageNo=1&MobileOS=AND&MobileApp=wheretogo&_type=json&contentTypeId=15&contentId='
}
});
