import db from "../config/dbConnection.js";

export const getMainBoardContents = (result) => {
    db.query("Select mainEventID, ment, prePic, eventID from MainEventTBL;", (err, results) => {             
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
    db.query("Select  eventID, eventName, (select cName from CategoryTBL where CategoryTBL.cCode = EventTBL.kind) as kind, startDate, endDate,  pic, (select count(*) from UserSavedTBL where UserSavedTBL.eventID = EventTBL.eventID) as totalSavedNum,(select count(*) from UserVisitedTBL where UserVisitedTBL.eventID = EventTBL.eventID)as visitedNum from EventTBL ORDER BY totalSavedNum DESC LIMIT 5;", (err, results) => {             
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
    var qr = 'select eventID, eventName, startDate, endDate, (select count(*) from UserVisitedTBL where UserVisitedTBL.eventID = EventTBL.eventID)as visitedNum, (select count(*) from UserSavedTBL where UserSavedTBL.eventID = EventTBL.eventID) as savedNum, (select cName from CategoryTBL where CategoryTBL.cCode = EventTBL.kind) as kind, pic, (select count(*) from UserSavedTBL where UserSavedTBL.eventID = EventTBL.eventID ';  
          
    if(sex == 'w'){
        qr += ' and UserSavedTBL.userID in (select userID from UserTBL where sex = "w" ';
        if(age == 1)qr += ' and age = "1" ';
         else if (age == 2)qr += ' and age = "2" ';
         else if (age == 3)qr += ' and age = "3" ';
         else if (age == 4)qr += ' and age = "4" ';
         else if (age == 6)qr += ' and age = "6"';
         qr += ' ) ';
    }
            else if (sex == 'm') {
                qr += ' and UserSavedTBL.userID in (select userID from UserTBL where sex = "m" ';
                if(age == 1)qr += ' and age = "1" ';
                else if (age == 2)qr += ' and age = "2" ';
                else if (age == 3)qr += ' and age = "3" ';
                else if (age == 4)qr += ' and age = "4" ';
                else if (age == 6)qr += ' and age = "6"';
                qr += ' ) ';
            }

            qr += ' ) as userTopNum from EventTBL ORDER BY userTopNum DESC;' 


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
                    db.query("select eventID, eventName, startDate, endDate, (select count(*) from UserVisitedTBL where UserVisitedTBL.eventID = EventTBL.eventID)as visitedNum, (select count(*) from UserSavedTBL where UserSavedTBL.eventID = EventTBL.eventID) as savedNum, (select cName from CategoryTBL where CategoryTBL.cCode = EventTBL.kind) as kind, pic, (select count(*) from UserSavedTBL where UserSavedTBL.eventID = EventTBL.eventID ) as userTopNum from EventTBL ORDER BY userTopNum DESC LIMIT 5;", (err, results) => {             
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
                    db.query("select sex, age from UserTBL where userID = ?;",[uid], (err, userInfo) => {             
                        if(err) {
                            result(500, err, null);
                        } else { 
                            var qr = 'select eventID, eventName, startDate, endDate, (select count(*) from UserVisitedTBL where UserVisitedTBL.eventID = EventTBL.eventID)as visitedNum, (select count(*) from UserSavedTBL where UserSavedTBL.eventID = EventTBL.eventID) as savedNum, (select cName from CategoryTBL where CategoryTBL.cCode = EventTBL.kind) as kind, pic, (select count(*) from UserSavedTBL where UserSavedTBL.eventID = EventTBL.eventID ';  
                            console.log(userInfo[0]);
                            if(userInfo[0].sex == 'w'){
                                qr += ' and UserSavedTBL.userID in (select userID from UserTBL where sex = "w" ';
                                if(userInfo[0].age == 1)qr += ' and age = "1" ';
                                else if (userInfo[0].age == 2)qr += ' and age = "2" ';
                                else if (userInfo[0].age == 3)qr += ' and age = "3" ';
                                else if (userInfo[0].age == 4)qr += ' and age = "4" ';
                                else if (userInfo[0].age == 6)qr += ' and age = "6"';
                                qr += ' ) ';
                            }
                            else if (userInfo[0].sex == 'm') {
                                qr += ' and UserSavedTBL.userID in (select userID from UserTBL where sex = "m" ';
                                if(userInfo[0].age == 1)qr += ' and age = "1" ';
                                else if (userInfo[0].age == 2)qr += ' and age = "2" ';
                                else if (userInfo[0].age == 3)qr += ' and age = "3" ';
                                else if (userInfo[0].age == 4)qr += ' and age = "4" ';
                                else if (userInfo[0].age == 6)qr += ' and age = "6"';
                                qr += ' ) ';
                            }
                            
                
                            qr += ' ) as userTopNum from EventTBL ORDER BY userTopNum DESC LIMIT 5;' 
                
                
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
    db.query("select sex, age from UserTBL where userID = ?;",[uid], (err, userInfo) => {             
        if(err) {
            result(500, err, null);
        } else { 
            var qr = 'select eventID, eventName, startDate, endDate, (select count(*) from UserVisitedTBL where UserVisitedTBL.eventID = EventTBL.eventID)as visitedNum, (select count(*) from UserSavedTBL where UserSavedTBL.eventID = EventTBL.eventID) as savedNum, (select cName from CategoryTBL where CategoryTBL.cCode = EventTBL.kind) as kind, pic, (select count(*) from UserSavedTBL where UserSavedTBL.eventID = EventTBL.eventID ';  
            console.log(userInfo[0]);
            if(userInfo[0].sex == 'w'){
                qr += ' and UserSavedTBL.userID in (select userID from UserTBL where sex = "w" ';
                if(userInfo[0].age == 1)qr += ' and age = "1" ';
                else if (userInfo[0].age == 2)qr += ' and age = "2" ';
                else if (userInfo[0].age == 3)qr += ' and age = "3" ';
                else if (userInfo[0].age == 4)qr += ' and age = "4" ';
                else if (userInfo[0].age == 6)qr += ' and age = "6"';
                qr += ' ) ';
            }
            else if (userInfo[0].sex == 'm') {
                qr += ' and UserSavedTBL.userID in (select userID from UserTBL where sex = "m" ';
                if(userInfo[0].age == 1)qr += ' and age = "1" ';
                else if (userInfo[0].age == 2)qr += ' and age = "2" ';
                else if (userInfo[0].age == 3)qr += ' and age = "3" ';
                else if (userInfo[0].age == 4)qr += ' and age = "4" ';
                else if (userInfo[0].age == 6)qr += ' and age = "6"';
                qr += ' ) ';
            }
            

            qr += ' ) as userTopNum from EventTBL ORDER BY userTopNum DESC LIMIT 5;' 


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
    db.query("select EventTBL.eventID,EventTBL.eventName,EventTBL.startDate, (select count(*) from UserSavedTBL where UserSavedTBL.eventID = EventTBL.eventID) as savedNum,(select count(*) from UserVisitedTBL where UserVisitedTBL.eventID = EventTBL.eventID)as visitedNum , EventTBL.endDate, CategoryTBL.cName as kind, EventTBL.pic, AreaCodeTBL.aName, AreaCodeDetailTBL.aDName, EventTBL.addr1 as place, EventTBL.addr2 as detailedPlace, EventTBL.mapx,EventTBL.mapy,EventTBL.mlevel,tel, EventTBL.homepage, EventTBL.overview, EventTBL.agelimit, EventTBL.eventtime, EventTBL.eventplace,EventTBL.bookingplace, EventTBL.subevent, EventTBL.price from EventTBL, CategoryTBL,AreaCodeTBL,AreaCodeDetailTBL where EventTBL.kind = CategoryTBL.cCode and EventTBL.areacode = AreaCodeTBL.aCode and AreaCodeDetailTBL.aDCode = EventTBL.sigungucode and eventID = ?;", [id], (err, results) => {             
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
    db.query("select * from UserVisitedTBL where userID = ? and eventID = ?;", [uid, eid],(err, cntV) => {             
        if(err) {
            result(500, {
                 isSuccess : false,
                msg : "오류가 발생하였습니다.",
                code : 500, 
                err
             }, null);
        }
        else {
            db.query("select * from UserSavedTBL where userID = ? and eventID = ?;", [uid, eid],(err, cntS) => {             
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