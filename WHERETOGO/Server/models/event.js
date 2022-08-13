import db from "../config/dbConnection.js";

export const getMainBoardContents = (result) => {
    db.query("Select mainEventID, ment, CONCAT('http://localhost:3000/asset/mainEvent/', prePic, '.jpg') as Img, event1ID, event2ID, event3ID, event4ID, event5ID from mainEventTBL;", (err, results) => {             
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
    db.query("select eventID,eventName,startDate, w1+w2+w3+w4+w6+m1+m2+m3+m4+m6 as totalSavedNum, endDate, CONCAT('http://localhost:3000/asset/event/', pic, '.jpg') as Img, genre, kind, theme from eventTBL ORDER BY totalSavedNum DESC LIMIT 5;", (err, results) => {             
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
  

export const getEventByEventID = (id, result) => {
    db.query("select * from eventTBL where eventID = ?;", [id], (err, results) => {             
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
  


  