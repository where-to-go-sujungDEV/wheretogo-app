const serviceKey ="QNnTJy6f3sstORUG9MRvZBkU7%2F3vsnIy%2BAgmf%2FKQpuzsI9iC%2FWV7SHiDqrfUrYfDLoJTDX5TAPIQpUD0mGwwFA%3D%3D";
var areaCode = 39; 
const numOfRows = 10;
const pageNo = 1;
import request from 'request';
var options = {
  'method': 'GET',
  "rejectUnauthorized": false, 
  'url': 'https://apis.data.go.kr/B551011/KorService/searchFestival?serviceKey=QNnTJy6f3sstORUG9MRvZBkU7%2F3vsnIy%2BAgmf%2FKQpuzsI9iC%2FWV7SHiDqrfUrYfDLoJTDX5TAPIQpUD0mGwwFA%3D%3D&numOfRows=' + numOfRows + '&pageNo='+pageNo+'&MobileOS=AND&MobileApp=wheretogo&_type=json&eventStartDate=20220817',
  'headers': {
  },
  form: {

  }
};

request(options, function (error, response, body) {
  if (error) throw new Error(error);

  let info = JSON.parse(body);

  var qr = "";

  for (var i = 0; i <  info['response']['body']['numOfRows'] ; i++) {
    qr += "INSERT INTO eventTBL(addr1,addr2,cat2,cat3,eventID,startDate,endDate,firstimage,firstimage2,mapx,mapy,mlevel,areacode,sigungucode,tel,eventName) VALUES ( '";
    qr += info['response']['body']['items']['item'][i]['addr1'];
    qr += "', '";
    qr += info['response']['body']['items']['item'][i]['addr2'];
    qr += "', '";
    qr += info['response']['body']['items']['item'][i]['cat2'];
    qr += "', '";
    qr += info['response']['body']['items']['item'][i]['cat3'];
    qr += "', ";
    qr += info['response']['body']['items']['item'][i]['contentid'];
    qr += " , '";
    qr += info['response']['body']['items']['item'][i]['eventstartdate'];
    qr += "', '";
    qr += info['response']['body']['items']['item'][i]['eventenddate'];
    qr += "', '";
    qr += info['response']['body']['items']['item'][i]['firstimage'];
    qr += "', '";
    qr += info['response']['body']['items']['item'][i]['firstimage2'];
    qr += "', ";
    qr += info['response']['body']['items']['item'][i]['mapx'];
    qr += ", ";
    qr += info['response']['body']['items']['item'][i]['mapy'];
    qr += ", ";
    qr += info['response']['body']['items']['item'][i]['mlevel'];
    qr += ", ";
    qr += info['response']['body']['items']['item'][i]['areacode'];
    qr += ", ";
    qr += info['response']['body']['items']['item'][i]['sigungucode'];
    qr += ", '";
    qr += info['response']['body']['items']['item'][i]['tel'];
    qr += "', '";
    qr += info['response']['body']['items']['item'][i]['title'];
    qr += "' ); ";
    console.log(qr);
    console.log(' ');
}
console.log('총 개수 : ' + info['response']['body']['totalCount']);
console.log('출력된 개수 : ' + info['response']['body']['numOfRows']);
});
