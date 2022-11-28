import db from "../config/dbConnection.js";

export const getCalendarEvents = (uid, result) => {
    db.query("Select eventID, eventName, startDate, endDate from eventTBL where eventID in (SELECT eventID from userSavedTBL where userID = ?);",[uid], (err, results) => {             
        if(err) {
            console.log(err);
            result(500, {
                msg : "오류가 발생하였습니다.",
                code : 500, 
                err
            }, null);
        } 
        else if (!results.length){
            result(200, null, {
                msg : "저장한 이벤트가 없습니다.",
                code : 204,
                userID: uid,
                isSuccess : true
            })
        }
        else {
            result(200, null, {
                msg : "사용자가 저장한 이벤트를 불러옵니다",
                code : 200,
                isSuccess : true,
                userID : uid,
                results});
        }
    });   
}