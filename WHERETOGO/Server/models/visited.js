import db from "../config/dbConnection.js";

export const getVisitedEvent = ([uid], result) => {
    db.query("Select userVisitedTBL.assessment, eventTBL.eventID, eventTBL.eventName, categoryTBL.cName as kind, eventTBL.startDate, eventTBL.endDate, eventTBL.pic, eventTBL.w1+eventTBL.w2+eventTBL.w3+eventTBL.w4+eventTBL.w6+eventTBL.m1+eventTBL.m2+eventTBL.m3+eventTBL.m4+eventTBL.m6 as savedNum from eventTBL, categoryTBL, userVisitedTBL where eventTBL.eventID = userVisitedTBL.eventID and eventTBL.kind = categoryTBL.cCode and userID = ?;",[uid], (err, results) => {             
        if(err) {
            console.log(err);
            result(500, {
                msg : "오류가 발생하였습니다.",
                code : 500, 
                err
            }, null);
        } else if (!results.length){
            result(200, null, {
                msg : "방문한 이벤트가 없습니다.",
                code : 204,
                isSuccess : true
            })
        }
        else {
            result(200, null, {
                msg : "사용자가 방문한 이벤트를 불러옵니다",
                code : 200,
                isSuccess : true,
                userID : uid,
                results});
        }
    });    
}

export const addVisitedEvent = (uid, eid, ass, result) => {
    db.query("select * from userVisitedTBL where userID = ? and eventID = ?;", [uid, eid], (err, count) => {             
        if (err) {
            console.log(err);
            result(500, {
                msg : "오류가 발생하였습니다", 
                code : 500, 
                isSuccess : false,
                err}, null);
        } 
        else if(count.length > 0) {
            result(200, null,{
                msg : "이미 방문함에 담긴 이벤트이거나 존재하지 않는 사용자입니다.",
                code : 204,
                isSuccess : false
            });
        } 
        else {
            db.query("insert into userVisitedTBL (userID, eventID, assessment) VALUES (?,?,?);",[uid, eid, ass], (err, results) => {             
                if(err) {
                    console.log(err);
                    result(500, {
                        code : 500,
                        isSuccess : false,
                        msg : "이벤트 담기를 실패하였습니다.",
                        err}, null);
                } else {
                    result(200, null, {
                        msg : "이벤트가 담겼습니다.",
                        code : 200,
                        isSuccess : true
                    });
                }
            }); 
        }
    });  
}
  

export const deleteVisitedEvent = (uid, eid, result) => {
    db.query("select * from userVisitedTBL where userID = ? and eventID = ?;", [uid, eid], (err, count) => {             
        if (err) {
            console.log(err);
            result(500, {
                msg : "오류가 발생하였습니다", 
                code : 500, 
                isSuccess : false,
                err}, null);
        } 
        else if(count.length <= 0) {
            result(200, null,{
                msg : "담지 않은 이벤트를 담기 취소하려 하고 있거나, 존재하지 않는 사용자입니다.",
                code : 204,
                isSuccess : false
            });
        } 
        else {
            db.query("delete from userVisitedTBL where userID = ? and eventID = ?;", [uid, eid], (err, results) => {             
                if(err) {
                    console.log(err);
                    result(500, {
                        code : 500,
                        isSuccess : false,
                        msg : "이벤트 담기 취소를 실패하였습니다.",
                        err}, null);
                } else {
                    result(200, null, {
                        msg : "이벤트 담기를 취소하였습니다.",
                        code : 200,
                        isSuccess : true
                    });
                }
            }); 
        }
    });  
}


export const getIfVisited = (uid, eid, result) => {
    db.query("select * from userVisitedTBL where userID = ? and eventID = ?;", [uid, eid],(err, count) => {             
        if(err) {
            console.log(err);
            result(500, {
                isSuccess : false,
                msg : "오류가 발생하였습니다.",
                code : 500, 
                err
            }, null);

        } else if (!count.length){
            result(200, null, {
                isVisited : false,
                code : 200,
                isSuccess : true
            })
        }
        else {
            result(200, null, {
                isVisited : true,
                code : 200,
                isSuccess : true});
        }
    });    
}