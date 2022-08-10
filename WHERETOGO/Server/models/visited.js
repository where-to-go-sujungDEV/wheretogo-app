import db from "../config/dbConnection.js";

export const getVisitedEvent = ([uid], result) => {
    db.query("select * from userVisitedTBL where userID = ?;",[uid], (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, results);
        }
    });   
}

export const addVisitedEvent = ([uid, eid],result) => {
    db.query("insert into userVisitedTBL (userID, eventID) VALUES (?,?);",[uid, eid], (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, results);
        }
    });   
}
  

export const deleteVisitedEvent = ([uid, eid], result) => {
    db.query("delete from userVisitedTBL where userID = ? and eventID = ?;", [uid, eid], (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, results);
        }
    });   
}

export const checkIfVisited = ([uid, eid], result) => {
    db.query("select * from userVisitedTBL where userID = ? and eventID = ?;", [uid, eid], (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, results);
        }
    });   
}