import admin from "firebase-admin";
import serviceAccount from "./firebase-admin.json" assert {type: "json"};

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

export const pushAlarm = async function (req, res){

    //디바이스의 토큰 값
     let deviceToken =`ehK_zofqHlI:APA91bEbOAhJWTI9gfYHBt3w9uziywNiGFS5XbaFAwP5SU9AuDtwT7AABHNDYcXWigbLNw8q1CnN_FPxd-6xSPw1sZl6UZe_jJ_tGQ6WdbYIYKRHmfkj0G3FfkahDSm7lA4KC2LAu74l`
       
     let message = {
       notification: {
         title: '키워드 알림',
         body: '이벤트가 등록되었습니다',
       },
       token: deviceToken,
     }
   
     admin
       .messaging()
       .send(message)
       .then(function (response) {
         console.log('Successfully sent message: : ', response)
         return res.status(200).json({success : true})
       })
       .catch(function (err) {
           console.log('Error Sending message!!! : ', err)
           return res.status(400).json({success : false})
       });
   
   }

   pushAlarm();