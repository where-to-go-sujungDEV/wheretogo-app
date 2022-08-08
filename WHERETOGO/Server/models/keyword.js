import db from "../config/dbConnection.js";

export const insertKeyword = (kw, result) => {
    db.query("insert into keywordTBL (content) VALUES (?);", [kw], (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, results);
        }
    });   
}

export const getUserKeywordByID = (uid, result) => {
    db.query("select * from keywordTBL where userID = ? ;", [uid], (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, results);
        }
    });   
}
  

export const putUserKeywordByID = (uid, kid, result) => {
    db.query("insert into userKeywordTBL (userID, keywordID) VALUES (?,?);", [uid, kid], (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, results);
        }
    });   
}

export const deleteUserKeywordByID = (uid, kid, result) => {
    db.query("delete from userKeywordTBL where userID = ? and keywordID = ?;", [uid, kid], (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, results);
        }
    });   
}

export const getIfKeywordExist = (kw, result) => {
    db.query("select * from keywordTBL where content = '?';", [kw], (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, results);
        }
    });   
}
