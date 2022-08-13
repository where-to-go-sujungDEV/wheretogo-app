import db from "../config/dbConnection.js";

export const getVisitedEvent = ([uid], result) => {
    db.query("select eventID.eventTBL, eventName.eventTBL, dou.eventTBL, si.eventTBL, genre.eventTBL, kind.eventTBL, theme.eventTBL, startDate.eventTBL, endDate.eventTBL, savedNum.eventTBL, pic, time.eventTBL, place.eventTBL,cost.eventTBL, assessment.userVisitedTBL from eventTBL INNER JOIN userVisitedTBL on eventTBL.eventID = userVisitedTBL.eventID where userID = ?;",[uid], (err, results) => {             
        if(err) {
            console.log(err);
            result({
                msg : "오류가 발생하였습니다.",
                code : 500, 
                err
            }, null);
        } else if (!results.length){
            result(null, {
                msg : "방문한 이벤트가 없습니다.",
                code : 204,
                isSuccess : true
            })
        }
        else {
            result(null, {
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
            result({
                msg : "오류가 발생하였습니다", 
                code : 500, 
                isSuccess : false,
                err}, null);
        } 
        else if(count.length > 0) {
            result(null,{
                msg : "이미 방문함에 담긴 이벤트이거나 존재하지 않는 사용자입니다.",
                code : 204,
                isSuccess : false
            });
        } 
        else {
            db.query("insert into userVisitedTBL (userID, eventID, assessment) VALUES (?,?,?);",[uid, eid, ass], (err, results) => {             
                if(err) {
                    console.log(err);
                    result({
                        code : 500,
                        isSuccess : false,
                        msg : "이벤트 담기를 실패하였습니다.",
                        err}, null);
                } else {
                    result(null, {
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
            result({
                msg : "오류가 발생하였습니다", 
                code : 500, 
                isSuccess : false,
                err}, null);
        } 
        else if(count.length <= 0) {
            result(null,{
                msg : "담지 않은 이벤트를 담기 취소하려 하고 있거나, 존재하지 않는 사용자입니다.",
                code : 204,
                isSuccess : false
            });
        } 
        else {
            db.query("delete from userVisitedTBL where userID = ? and eventID = ?;", [uid, eid], (err, results) => {             
                if(err) {
                    console.log(err);
                    result({
                        code : 500,
                        isSuccess : false,
                        msg : "이벤트 담기 취소를 실패하였습니다.",
                        err}, null);
                } else {
                    result(null, {
                        msg : "이벤트 담기를 취소하였습니다.",
                        code : 200,
                        isSuccess : true
                    });
                }
            }); 
        }
    });  
}