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
    db.query("select eventTBL.eventID, eventTBL.eventName, eventTBL.startDate, eventTBL.w1+eventTBL.w2+eventTBL.w3+eventTBL.w4+eventTBL.w6+eventTBL.m1+eventTBL.m2+eventTBL.m3+eventTBL.m4+eventTBL.m6 as totalSavedNum, eventTBL.endDate, categoryTBL.cName as kind, eventTBL.pic from eventTBL, categoryTBL where categoryTBL.cCode = eventTBL.kind ORDER BY totalSavedNum DESC LIMIT 5;", (err, results) => {             
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
            var qr = 'select eventTBL.eventID, eventTBL.eventName, eventTBL.startDate, eventTBL.endDate, categoryTBL.cName as kind, eventTBL.pic, ';  
          
            if(userInfo[0].sex == 'w'){
                if(userInfo[0].age == 1)qr += ' eventTBL.w1 ';
                else if (userInfo[0].age == 2)qr += ' eventTBL.w2 ';
                else if (userInfo[0].age == 3)qr += ' eventTBL.w3 ';
                else if (userInfo[0].age == 4)qr += ' eventTBL.w4 ';
                else if (userInfo[0].age == 6)qr += ' eventTBL.w6 ';
                else qr += ' eventTBL.w1+eventTBL.w2+eventTBL.w3+eventTBL.w4+eventTBL.w6 ';
            }
            else if (userInfo[0].sex == 'm') {
                if(userInfo[0].age == 1)qr += ' eventTBL.m1 ';
                else if (userInfo[0].age == 2)qr += ' eventTBL.m2 ';
                else if (userInfo[0].age == 3)qr += ' eventTBL.m3 ';
                else if (userInfo[0].age == 4)qr += ' eventTBL.m4 ';
                else if (userInfo[0].age == 6)qr += ' eventTBL.m6 ';
                else qr += ' eventTBL.m1+eventTBL.m2+eventTBL.m3+eventTBL.m4+eventTBL.m6 ';
            }
            else qr += ' eventTBL.w1+eventTBL.w2+eventTBL.w3+eventTBL.w4+eventTBL.w6+eventTBL.m1+eventTBL.m2+eventTBL.m3+eventTBL.m4+eventTBL.m6 ';

            qr += ' as savedNum from eventTBL, categoryTBL where eventTBL.kind = categoryTBL.cCode ORDER BY savedNum DESC LIMIT 5;' 

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
    db.query("select eventTBL.eventID,eventTBL.eventName,eventTBL.startDate, eventTBL.w1+eventTBL.w2+eventTBL.w3+eventTBL.w4+eventTBL.w6+eventTBL.m1+eventTBL.m2+eventTBL.m3+eventTBL.m4+eventTBL.m6 as savedNum , eventTBL.endDate, categoryTBL.cName as kind, eventTBL.pic, areaCodeTBL.aName, areaCodeDetailTBL.aDName, eventTBL.addr1 as place, eventTBL.addr2 as detailedPlace, eventTBL.mapx,eventTBL.mapy,eventTBL.mlevel,tel, eventTBL.telname, eventTBL.homepage, eventTBL.overview, eventTBL.eventplace,eventTBL.bookingplace, eventTBL.subevent, eventTBL.price from eventTBL, categoryTBL,areaCodeTBL,areaCodeDetailTBL where eventTBL.kind = categoryTBL.cCode and eventTBL.areacode = areaCodeTBL.aCode and areaCodeDetailTBL.aDCode = eventTBL.sigungucode and eventID = ?;", [id], (err, results) => {             
        if(err) {
            result(500, err, null);
        } else {
            result(200, null, {
                code : 200,
                isSuccess : true,
                results : results[0]});
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