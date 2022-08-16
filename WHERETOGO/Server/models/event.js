import db from "../config/dbConnection.js";

export const getMainBoardContents = (result) => {
    db.query("Select mainEventID, ment, prePic, eventID from mainEventTBL;", (err, results) => {             
        if(err) {
            result(500, err, null);
        } else {
            result(200, null, {
                code : 200,
                isSuccess : true,
                results});
        }
    });   
}

export const getTopContents = (result) => { 
    db.query("select eventID,eventName,startDate, w1+w2+w3+w4+w6+m1+m2+m3+m4+m6 as totalSavedNum, endDate, pic, genre, kind, theme from eventTBL ORDER BY totalSavedNum DESC LIMIT 5;", (err, results) => {             
        if(err) {
            result(500, err, null);
        } else {
            result(200, null, {
                code : 200,
                isSuccess : true,
                results});
        }
    });   
}

export const getUserTopContents = (uid, result) => { 
    db.query("select sex, age from userTBL where userID = ?;",[uid], (err, userInfo) => {             
        if(err) {
            result(500, err, null);
        } else { 
            var qr = 'select eventID,eventName,startDate, endDate, genre, kind, theme, pic, ';  
          
            if(userInfo[0].sex == 'w'){
                if(userInfo[0].age == 1)qr += ' w1 ';
                else if (userInfo[0].age == 2)qr += ' w2 ';
                else if (userInfo[0].age == 3)qr += ' w3 ';
                else if (userInfo[0].age == 4)qr += ' w4 ';
                else if (userInfo[0].age == 6)qr += ' w6 ';
                else qr += ' w1+w2+w3+w4+w6 ';
            }
            else if (userInfo[0].sex == 'm') {
                if(userInfo[0].age == 1)qr += ' m1 ';
                else if (userInfo[0].age == 2)qr += ' m2 ';
                else if (userInfo[0].age == 3)qr += ' m3 ';
                else if (userInfo[0].age == 4)qr += ' m4 ';
                else if (userInfo[0].age == 6)qr += ' m6 ';
                else qr += ' m1+m2+m3+m4+m6 ';
            }
            else qr += ' w1+w2+w3+w4+w6+m1+m2+m3+m4+m6 ';

            qr += ' as savedNum from eventTBL ORDER BY savedNum DESC LIMIT 5;' 

            db.query(qr, (err, results) => {             
                if(err) {
                    result(500, err, null);
                } else {
                    result(200, null, {
                        code : 200,
                        isSuccess : true,
                        userInfo,
                        results}
                        );
                    }
            });
        }
    });  
}
  

export const getEventByEventID = (id, result) => {
    db.query("select eventID,eventName,startDate, w1+w2+w3+w4+w6+m1+m2+m3+m4+m6 as savedNum , endDate, genre, kind, theme, pic, dou, si, time, place, link, cost, content from eventTBL where eventID = ?;", [id], (err, results) => {             
        if(err) {
            result(500, err, null);
        } else {
            result(200, null, {
                code : 200,
                isSuccess : true,
                results});
        }
    });   
}
  

export const getEventUserInfos= (uid, eid, result) => {
    db.query("select * from userVisitedTBL where userID = ? and eventID = ?;", [uid, eid],(err, cntV) => {             
        if(err) {
            result(500, {
                 isSuccess : false,
                msg : "오류가 발생하였습니다.",
                code : 500, 
                err
             }, null);
        }
        else {
            db.query("select * from userSavedTBL where userID = ? and eventID = ?;", [uid, eid],(err, cntS) => {             
                if(err) {
                    result(500, {
                         isSuccess : false,
                        msg : "오류가 발생하였습니다.",
                        code : 500, 
                            err
                     }, null);
            
                }
                else {
                    if ((!cntV.length)&&(!cntS.length)){
                        result(200, null, {
                           isVisited : false,
                           isSaved : false,
                           code : 200,
                           isSuccess : true
                        });
                     }
                     else if ((cntV.length)&&(!cntS.length)){
                        result(200, null, {
                           isVisited : true,
                           isSaved : false,
                           code : 200,
                           isSuccess : true
                        });
                     }
                     else if ((!cntV.length)&&(cntS.length)){
                        result(200, null, {
                           isVisited : false,
                           isSaved : true,
                           code : 200,
                           isSuccess : true
                        });
                     }
                     else {
                        result(200, null, {
                           isVisited : true,
                           isSaved : true,
                           code : 200,
                           isSuccess : true
                        });
                     }
                 }
            });
         }
    });    
}