import db from "../config/dbConnection.js";

export const getSavedEvent = ([uid], result) => {
    db.query("select * from userSavedTBL where userID = ?;",[uid], (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, results);
        }
    });   
}

export const addSavedEvent = (uid, eid, result) => {
    db.query("insert into userSavedTBL (userID, eventID) VALUES (?,?);",[uid, eid], (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, results);
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