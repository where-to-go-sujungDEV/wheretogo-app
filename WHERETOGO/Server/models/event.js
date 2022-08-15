import db from "../config/dbConnection.js";

export const getMainBoardContents = (result) => {
    db.query("Select mainEventID, ment, prePic, eventID from mainEventTBL;", (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, {
                code : 200,
                isSuccess : true,
                results});
        }
    });   
}

export const getTopContents = (result) => { 
    db.query("select eventID,eventName,startDate, w1+w2+w3+w4+w6+m1+m2+m3+m4+m6 as totalSavedNum, endDate, pic, genre, kind, theme from eventTBL ORDER BY totalSavedNum DESC LIMIT 5;", (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, {
                code : 200,
                isSuccess : true,
                results});
        }
    });   
}

export const getUserTopContents = (uid, result) => { 
    db.query("select sex, age from userTBL where userID = ?;",[uid], (err, userInfo) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {            
            if(userInfo[0].sex == 'w'){
                if(userInfo[0].age == 1){
                    db.query("select eventID,eventName,startDate, w1 as savedNum , endDate, genre, kind, theme, pic from eventTBL ORDER BY savedNum DESC LIMIT 5;", (err, results) => {             
                        if(err) {
                            console.log(err);
                            result(err, null);
                        } else {
                            result(null, {
                                code : 200,
                                isSuccess : true,
                                userInfo,
                                results}
                                );
                            }
                    });
                }
                else if (userInfo[0].age == 2){
                    db.query("select eventID,eventName,startDate, w2 as savedNum, endDate, genre, kind, theme, pic from eventTBL ORDER BY savedNum DESC LIMIT 5;", (err, results) => {             
                        if(err) {
                            console.log(err);
                            result(err, null);
                        } else {
                            result(null, {
                                code : 200,
                                isSuccess : true,
                                userInfo,
                                results});
                            }
                        });
                }
                else if (userInfo[0].age == 3){
                    db.query("select eventID,eventName,startDate, w3 as savedNum, endDate, genre, kind, theme, pic  from eventTBL ORDER BY savedNum DESC LIMIT 5;", (err, results) => {             
                        if(err) {
                            console.log(err);
                            result(err, null);
                        } else {
                            result(null, {
                                code : 200,
                                isSuccess : true,
                                userInfo,
                                results});
                            }
                        });
                }
                else if (userInfo[0].age == 4){
                    db.query("select eventID,eventName,startDate, w4 as savedNum , endDate, genre, kind, theme, pic  from eventTBL ORDER BY savedNum  DESC LIMIT 5;", (err, results) => {             
                        if(err) {
                            console.log(err);
                            result(err, null);
                        } else {
                            result(null, {
                                code : 200,
                                isSuccess : true,
                                userInfo,
                                results});
                            }
                        });
                }
                else if (userInfo[0].age == 6){
                    db.query("select eventID,eventName,startDate, w6 as savedNum , endDate, genre, kind, theme, pic  from eventTBL ORDER BY savedNum DESC LIMIT 5;", (err, results) => {             
                        if(err) {
                            console.log(err);
                            result(err, null);
                        } else {
                            result(null, {
                                code : 200,
                                isSuccess : true,
                                uid : [uid],
                                userInfo,
                                results});
                            }
                        });
                }
                else {
                    db.query("select eventID,eventName,startDate, w1+w2+w3+w4+w6 as savedNum , endDate, genre, kind, theme, pic  from eventTBL ORDER BY savedNum DESC LIMIT 5;", (err, results) => {             
                        if(err) {
                            console.log(err);
                            result(err, null);
                        } else {
                            result(null, {
                                code : 200,
                                isSuccess : true,
                                userInfo,
                                results}
                                );
                            }
                    });
                }
            }
            else if (userInfo[0].sex == 'm') {
                if(userInfo[0].age == 1){
                    db.query("select eventID,eventName,startDate, m1 as savedNum, endDate, genre, kind, theme, pic  from eventTBL ORDER BY savedNum DESC LIMIT 5;", (err, results) => {             
                        if(err) {
                            console.log(err);
                            result(err, null);
                        } else {
                            result(null, {
                                code : 200,
                                isSuccess : true,
                                userInfo,
                                results});
                            }
                        });
                }
                else if (userInfo[0].age == 2){
                    db.query("select eventID,eventName,startDate, m2 as savedNum, endDate, genre, kind, theme, pic  from eventTBL ORDER BY savedNum DESC LIMIT 5;", (err, results) => {             
                        if(err) {
                            console.log(err);
                            result(err, null);
                        } else {
                            result(null, {
                                code : 200,
                                isSuccess : true,
                                userInfo,
                                results});
                            }
                        });
                }
                else if (userInfo[0].age == 3){
                    db.query("select eventID,eventName,startDate, m3 as savedNum, endDate, genre, kind, theme, pic  from eventTBL ORDER BY savedNum DESC LIMIT 5;", (err, results) => {             
                        if(err) {
                            console.log(err);
                            result(err, null);
                        } else {
                            result(null, {
                                code : 200,
                                isSuccess : true,
                                userInfo,
                                results});
                            }
                        });
                }
                else if (userInfo[0].age == 4){
                    db.query("select eventID,eventName,startDate, m4 as savedNum, endDate, genre, kind, theme, pic  from eventTBL ORDER BY savedNum DESC LIMIT 5;", (err, results) => {             
                        if(err) {
                            console.log(err);
                            result(err, null);
                        } else {
                            result(null, {
                                code : 200,
                                isSuccess : true,
                                userInfo,
                                results});
                            }
                        });
                }
                else if (userInfo[0].age == 6){
                    db.query("select eventID,eventName,startDate, m6 as savedNum, endDate, genre, kind, theme, pic  from eventTBL ORDER BY savedNum DESC LIMIT 5;", (err, results) => {             
                        if(err) {
                            console.log(err);
                            result(err, null);
                        } else {
                            result(null, {
                                code : 200,
                                isSuccess : true,
                                userInfo,
                                results});
                            }
                        });
                }
                else {
                    db.query("select eventID,eventName,startDate, m1+m2+m3+m4+m6 as savedNum , endDate, genre, kind, theme, pic  from eventTBL ORDER BY savedNum DESC LIMIT 5;", (err, results) => {             
                        if(err) {
                            console.log(err);
                            result(err, null);
                        } else {
                            result(null, {
                                code : 200,
                                isSuccess : true,
                                userInfo,
                                results}
                                );
                            }
                    });
                }
            }
            else {
                db.query("select eventID,eventName,startDate, w1+w2+w3+w4+w6+m1+m2+m3+m4+m6 as savedNum , endDate, genre, kind, theme, pic  from eventTBL ORDER BY savedNum DESC LIMIT 5;", (err, results) => {             
                    if(err) {
                        console.log(err);
                        result(err, null);
                    } else {
                        result(null, {
                            code : 200,
                            isSuccess : true,
                            userInfo,
                            results}
                            );
                        }
                });
            }
        }
    });  
}
  

export const getEventByEventID = (id, result) => {
    db.query("select eventID,eventName,startDate, w1+w2+w3+w4+w6+m1+m2+m3+m4+m6 as savedNum , endDate, genre, kind, theme, pic, dou, si, time, place, link, cost, content from eventTBL where eventID = ?;", [id], (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, {
                code : 200,
                isSuccess : true,
                results});
        }
    });   
}
  

export const getEventUserInfos= (uid, eid, result) => {
    db.query("select * from userVisitedTBL where userID = ? and eventID = ?;", [uid, eid],(err, cntV) => {             
        if(err) {
            console.log(err);
            result({
                 isSuccess : false,
                msg : "오류가 발생하였습니다.",
                code : 500, 
                    err
             }, null);
        }
        else {
            db.query("select * from userSavedTBL where userID = ? and eventID = ?;", [uid, eid],(err, cntS) => {             
                if(err) {
                    console.log(err);
                    result({
                         isSuccess : false,
                        msg : "오류가 발생하였습니다.",
                        code : 500, 
                            err
                     }, null);
            
                }
                else {
                    if ((!cntV.length)&&(!cntS.length)){
                        result(null, {
                           isVisited : false,
                           isSaved : false,
                           code : 200,
                           isSuccess : true
                        });
                     }
                     else if ((cntV.length)&&(!cntS.length)){
                        result(null, {
                           isVisited : true,
                           isSaved : false,
                           code : 200,
                           isSuccess : true
                        });
                     }
                     else if ((!cntV.length)&&(cntS.length)){
                        result(null, {
                           isVisited : false,
                           isSaved : true,
                           code : 200,
                           isSuccess : true
                        });
                     }
                     else {
                        result(null, {
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