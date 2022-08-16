const serviceKey ="uvWSjr%2FzpkFHh3b1a%2BLoq5yJvQB2PzfjAnZXCZAYZ%2FXL3ueaey8pYRWcodh9Qw1PT%2BCPrhXF5VNltjX3%2BJmI8g%3D%3D";
var tourArea = "목포";
var tourCategory = "축제";
const numOfRows = 10;

import request from 'request';
var options = {
  'method': 'GET',
  "rejectUnauthorized": false, 
  'url': 'https://apis.data.go.kr/6460000/tourInfo/getTourlnfoList?serviceKey=' + serviceKey + '&tourArea=' + encodeURI(tourArea) + '&tourCategory='+ encodeURI(tourCategory) + '&numOfRows=' + numOfRows,
  'headers': {
  },
  form: {

  }
};

request(options, function (error, response) {
  if (error) throw new Error(error);
  console.log(response.body[pageIndex]);


  //var info = JSON.parse(response);

  //console.log(info);
/*
  console.log("ㅅ자아아아악");
  for (i in info['response']['body']['items']['item']) {
    console.log('관광명 : ' + info['response']['body']['items']['item'][i]['tourName']);
    console.log("\n");
    console.log('분류 : ' + info['response']['body']['items']['item'][i]['tourCategory1']);
    console.log(
        '지역 : ' + info['response']['body']['items']['item'][i]['tourArea']
    );
    console.log('주소 : ' + info['response']['body']['items']['item'][i]['tourAddr']);
    console.log('연락처 : ' + info['response']['body']['items']['item'][i]['tourPhone']);
    console.log(" ")
}*/
});
