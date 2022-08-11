import db from "../config/dbConnection.js";

export const getSavedEvent = ([uid], result) => {
    db.query("Select * from eventTBL where eventID in (SELECT eventID from userSavedTBL where userID = ?);",[uid], (err, results) => {             
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
    db.query("select count(*) from userSavedTBL where userID = ? and eventID = ?;", [uid, eid], (err, count) => {             
        if (err) {
            console.log(err);
            result({
                msg : "오류가 발생하였습니다", 
                code : 500, 
                err}, null);
        } 
        else if(count > 0) {
            result(null,{
                msg : "이미 담긴 이벤트 입니다.",
                code : 204,
                isSuccess : false
            });
        } 
        else {
            db.query("insert into userSavedTBL (userID, eventID) VALUES (?,?);",[uid, eid], (err, results) => {             
                if(err) {
                    console.log(err);
                    result({msg : "이벤트 담기를 실패하였습니다.",err}, null);
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
    db.query("delete from userSavedTBL where userID = ? and eventID = ?;", [uid, eid], (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, results);
        }
    });   
}

export const checkIfSaved = (uid, eid, result) => {
    db.query("select count(*) from userSavedTBL where userID = ? and eventID = ?;", [uid, eid], (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, results);
        }
    });   
}