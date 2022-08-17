const serviceKey ="QNnTJy6f3sstORUG9MRvZBkU7%2F3vsnIy%2BAgmf%2FKQpuzsI9iC%2FWV7SHiDqrfUrYfDLoJTDX5TAPIQpUD0mGwwFA%3D%3D";
var areaCode = 39; 
const numOfRows = 31;

import request from 'request';
var options = {
  'method': 'GET',
  "rejectUnauthorized": false, 
  'url': 'https://apis.data.go.kr/B551011/KorService/areaCode?serviceKey=' + serviceKey + '&numOfRows=' + numOfRows + '&MobileOS=AND&MobileApp=wheretogo&_type=json&areaCode='+areaCode,
  'headers': {
  },
  form: {

  }
};

request(options, function (error, response, body) {
  if (error) throw new Error(error);

  console.log(response.body);

  let info = JSON.parse(body);

  var qr = "";

  for (var i = 0; i <  info['response']['body']['numOfRows'] ; i++) {
    qr += 'INSERT INTO areaCodeDetailTBL(aCode,aDCode,aDName) VALUES ( ';
    qr += areaCode;
    qr += ", "
    qr += info['response']['body']['items']['item'][i]['code'];
    qr += " , '";
    qr += info['response']['body']['items']['item'][i]['name'];
    qr += "' ); ";
    console.log(qr);
    qr = "";
}
console.log('총 개수 : ' + info['response']['body']['totalCount']);
console.log('출력된 개수 : ' + info['response']['body']['numOfRows']);
});
