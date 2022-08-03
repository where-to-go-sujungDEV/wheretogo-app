import db from "../config/dbConnection.js";

export const getMainBoardContents = (result) => {
    db.query("select * from mainEventTBL;", (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, results);
        }
    });   
}

export const getTopContents = (result) => { //임시로 3개까지만 가져오게 해놓음
    db.query("select * from eventTBL ORDER BY savedNum DESC LIMIT 3;", (err, results) => {             
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
  


  