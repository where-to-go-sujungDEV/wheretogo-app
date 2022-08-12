import db from "../config/dbConnection.js";

export const getMainBoardContents = (result) => {
    db.query("Select mainEventID, ment, CONCAT('http://localhost:3000/asset/mainEvent/', prePic, '.jpg') as Img from mainEventTBL;", (err, results) => {             
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
    db.query("select eventID,eventName,startDate, endDate, savedNum, CONCAT('http://localhost:3000/asset/event/', pic, '.jpg') as Img, hashtag1, hashtag2, hashtag3 from eventTBL ORDER BY savedNum DESC LIMIT 5;", (err, results) => {             
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
  


  