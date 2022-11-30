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
    db.query("Select  eventID, eventName, (select cName from CategoryTBL where CategoryTBL.cCode = EventTBL.kind) as kind, startDate, endDate,  pic, (select count(*) from userSavedTBL where UserSavedTBL.eventID = EventTBL.eventID) as totalSavedNum,(select count(*) from UserVisitedTBL where UserVisitedTBL.eventID = EventTBL.eventID)as visitedNum from eventTBL ORDER BY totalSavedNum DESC LIMIT 5;", (err, results) => {             
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

export const getRecommandEventsInfos = (sex, age, result) => {        
    var qr = 'select eventID, eventName, startDate, endDate, (select count(*) from UserVisitedTBL where UserVisitedTBL.eventID = EventTBL.eventID)as visitedNum, (select count(*) from userSavedTBL where UserSavedTBL.eventID = EventTBL.eventID) as savedNum, (select cName from CategoryTBL where CategoryTBL.cCode = EventTBL.kind) as kind, pic, (select count(*) from userSavedTBL where UserSavedTBL.eventID = EventTBL.eventID ';  
          
    if(sex == 'w'){
        qr += ' and UserSavedTBL.userID in (select userID from userTBL where sex = "w" ';
        if(age == 1)qr += ' and age = "1" ';
         else if (age == 2)qr += ' and age = "2" ';
         else if (age == 3)qr += ' and age = "3" ';
         else if (age == 4)qr += ' and age = "4" ';
         else if (age == 6)qr += ' and age = "6"';
         qr += ' ) ';
    }
            else if (sex == 'm') {
                qr += ' and UserSavedTBL.userID in (select userID from userTBL where sex = "m" ';
                if(age == 1)qr += ' and age = "1" ';
                else if (age == 2)qr += ' and age = "2" ';
                else if (age == 3)qr += ' and age = "3" ';
                else if (age == 4)qr += ' and age = "4" ';
                else if (age == 6)qr += ' and age = "6"';
                qr += ' ) ';
            }

            qr += ' ) as userTopNum from eventTBL ORDER BY userTopNum DESC;' 


            db.query(qr, (err, results) => {             
                if(err) {
                    result(500, err, null);
                } else {
                    result(200, null, {
                        code : 200,
                        isSuccess : true,
                        sex: sex,
                        age : age,
                        results}
                        );
                    }
            });
}

export const getUserTopContents = (uid, result) => { 
    
        db.query("select count(*) as exist from UserTBL where userID =?;", [uid], (err, uexist) => {
            if(err) {
                result(500, err, null);
            } else {
                if (uexist[0].exist == 0){
                    db.query("select eventID, eventName, startDate, endDate, (select count(*) from UserVisitedTBL where UserVisitedTBL.eventID = EventTBL.eventID)as visitedNum, (select count(*) from userSavedTBL where UserSavedTBL.eventID = EventTBL.eventID) as savedNum, (select cName from CategoryTBL where CategoryTBL.cCode = EventTBL.kind) as kind, pic, (select count(*) from userSavedTBL where UserSavedTBL.eventID = EventTBL.eventID ) as userTopNum from eventTBL ORDER BY userTopNum DESC LIMIT 5;", (err, results) => {             
                        if(err) {
                            result(500, err, null);
                        }
                        else{
                            result(200, null, {
                                code : 200,
                                isSuccess : true,
                                userInfo: [
                                    {
                                        sex: "n",
                                        age: 0
                                    }
                                ],
                                results}
                                );
                            }
                    });

                }
                else{
                    db.query("select sex, age from userTBL where userID = ?;",[uid], (err, userInfo) => {             
                        if(err) {
                            result(500, err, null);
                        } else { 
                            var qr = 'select eventID, eventName, startDate, endDate, (select count(*) from UserVisitedTBL where UserVisitedTBL.eventID = EventTBL.eventID)as visitedNum, (select count(*) from userSavedTBL where UserSavedTBL.eventID = EventTBL.eventID) as savedNum, (select cName from CategoryTBL where CategoryTBL.cCode = EventTBL.kind) as kind, pic, (select count(*) from userSavedTBL where UserSavedTBL.eventID = EventTBL.eventID ';  
                            console.log(userInfo[0]);
                            if(userInfo[0].sex == 'w'){
                                qr += ' and UserSavedTBL.userID in (select userID from userTBL where sex = "w" ';
                                if(userInfo[0].age == 1)qr += ' and age = "1" ';
                                else if (userInfo[0].age == 2)qr += ' and age = "2" ';
                                else if (userInfo[0].age == 3)qr += ' and age = "3" ';
                                else if (userInfo[0].age == 4)qr += ' and age = "4" ';
                                else if (userInfo[0].age == 6)qr += ' and age = "6"';
                                qr += ' ) ';
                            }
                            else if (userInfo[0].sex == 'm') {
                                qr += ' and UserSavedTBL.userID in (select userID from userTBL where sex = "m" ';
                                if(userInfo[0].age == 1)qr += ' and age = "1" ';
                                else if (userInfo[0].age == 2)qr += ' and age = "2" ';
                                else if (userInfo[0].age == 3)qr += ' and age = "3" ';
                                else if (userInfo[0].age == 4)qr += ' and age = "4" ';
                                else if (userInfo[0].age == 6)qr += ' and age = "6"';
                                qr += ' ) ';
                            }
                            
                
                            qr += ' ) as userTopNum from eventTBL ORDER BY userTopNum DESC LIMIT 5;' 
                
                
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
            }
         });
/*
    db.query("select sex, age from userTBL where userID = ?;",[uid], (err, userInfo) => {             
        if(err) {
            result(500, err, null);
        } else { 
            var qr = 'select eventID, eventName, startDate, endDate, (select count(*) from UserVisitedTBL where UserVisitedTBL.eventID = EventTBL.eventID)as visitedNum, (select count(*) from userSavedTBL where UserSavedTBL.eventID = EventTBL.eventID) as savedNum, (select cName from CategoryTBL where CategoryTBL.cCode = EventTBL.kind) as kind, pic, (select count(*) from userSavedTBL where UserSavedTBL.eventID = EventTBL.eventID ';  
            console.log(userInfo[0]);
            if(userInfo[0].sex == 'w'){
                qr += ' and UserSavedTBL.userID in (select userID from userTBL where sex = "w" ';
                if(userInfo[0].age == 1)qr += ' and age = "1" ';
                else if (userInfo[0].age == 2)qr += ' and age = "2" ';
                else if (userInfo[0].age == 3)qr += ' and age = "3" ';
                else if (userInfo[0].age == 4)qr += ' and age = "4" ';
                else if (userInfo[0].age == 6)qr += ' and age = "6"';
                qr += ' ) ';
            }
            else if (userInfo[0].sex == 'm') {
                qr += ' and UserSavedTBL.userID in (select userID from userTBL where sex = "m" ';
                if(userInfo[0].age == 1)qr += ' and age = "1" ';
                else if (userInfo[0].age == 2)qr += ' and age = "2" ';
                else if (userInfo[0].age == 3)qr += ' and age = "3" ';
                else if (userInfo[0].age == 4)qr += ' and age = "4" ';
                else if (userInfo[0].age == 6)qr += ' and age = "6"';
                qr += ' ) ';
            }
            

            qr += ' ) as userTopNum from eventTBL ORDER BY userTopNum DESC LIMIT 5;' 


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
    });*/  
}
  
export const getEventByEventID = (id, result) => { 
    db.query("select eventTBL.eventID,eventTBL.eventName,eventTBL.startDate, (select count(*) from userSavedTBL where UserSavedTBL.eventID = EventTBL.eventID) as savedNum,(select count(*) from UserVisitedTBL where UserVisitedTBL.eventID = EventTBL.eventID)as visitedNum , eventTBL.endDate, categoryTBL.cName as kind, eventTBL.pic, areaCodeTBL.aName, areaCodeDetailTBL.aDName, eventTBL.addr1 as place, eventTBL.addr2 as detailedPlace, eventTBL.mapx,eventTBL.mapy,eventTBL.mlevel,tel, eventTBL.homepage, eventTBL.overview, eventTBL.agelimit, eventTBL.eventtime, eventTBL.eventplace,eventTBL.bookingplace, eventTBL.subevent, eventTBL.price from eventTBL, categoryTBL,areaCodeTBL,areaCodeDetailTBL where eventTBL.kind = categoryTBL.cCode and eventTBL.areacode = areaCodeTBL.aCode and areaCodeDetailTBL.aDCode = eventTBL.sigungucode and eventID = ?;", [id], (err, results) => {             
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