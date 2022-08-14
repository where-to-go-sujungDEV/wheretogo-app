import db from "../config/dbConnection.js";

export const getSavedEvent = ([uid], result) => {
    db.query("Select eventID, eventName, genre, kind, theme, startDate, endDate, pic, w1+w2+w3+w4+w6+m1+m2+m3+m4+m6 as savedNum from eventTBL where eventID in (SELECT eventID from userSavedTBL where userID = ?);",[uid], (err, results) => {             
        if(err) {
            console.log(err);
            result({
                msg : "오류가 발생하였습니다.",
                code : 500, 
                err
            }, null);
        } 
        else if (!results.length){
            result(null, {
                msg : "저장한 이벤트가 없습니다.",
                code : 204,
                isSuccess : true
            })
        }
        else {
            result(null, {
                msg : "사용자가 저장한 이벤트를 불러옵니다",
                code : 200,
                isSuccess : true,
                userID : uid,
                results});
        }
    });   
}

export const addSavedEvent = (uid, eid, result) => {
    db.query("select * from userSavedTBL where userID = ? and eventID = ?;", [uid, eid], (err, count) => {             
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
                msg : "이미 담긴 이벤트이거나 존재하지 않는 사용자입니다.",
                code : 204,
                isSuccess : false
            });
        } 
        else {
            db.query("insert into userSavedTBL (userID, eventID) VALUES (?,?);",[uid, eid], (err, results) => {             
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
  

export const deleteSavedEvent = (uid, eid, result) => {
    db.query("select * from userSavedTBL where userID = ? and eventID = ?;", [uid, eid], (err, count) => {             
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
            db.query("delete from userSavedTBL where userID = ? and eventID = ?;",[uid, eid], (err, results) => {             
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

export const getIfSaved = (uid, eid, result) => {
    db.query("select * from userSavedTBL where userID = ? and eventID = ?;", [uid, eid],(err, count) => {             
        if(err) {
            console.log(err);
            result({
                isSuccess : false,
                msg : "오류가 발생하였습니다.",
                code : 500, 
                err
            }, null);

        } else if (!count.length){
            result(null, {
                isSaved : false,
                code : 200,
                isSuccess : true
            })
        }
        else {
            result(null, {
                isSaved : true,
                code : 200,
                isSuccess : true});
        }
    });    
}