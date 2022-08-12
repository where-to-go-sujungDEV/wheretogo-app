import db from "../config/dbConnection.js";

export const getMainBoardContents = (result) => {
    db.query("Select mainEventID, ment, CONCAT('http://localhost:3000/asset/mainEvent/', prePic) as Img from mainEventTBL;", (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, results);
        }
    });   
}

export const getTopContents = (uid, result) => { 
    db.query("select eventID,eventName,startDate, endDate, savedNum, CONCAT('http://localhost:3000/asset/event/', pic) as Img, hashtag1, hashtag2, hashtag3 from eventTBL ORDER BY savedNum DESC LIMIT 5;", (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, results);
        }
    });   
}
  

export const getEventByEventID = (id, result) => {
    db.query("select * from eventTBL where eventID = ?;", [id], (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, results[0]);
        }
    });   
}
  


  