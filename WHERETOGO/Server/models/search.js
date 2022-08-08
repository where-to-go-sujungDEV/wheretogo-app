import db from "../config/dbConnection.js";

export const getSearchResults = (data, result) => {
    db.query("select * from eventTBL where si = ? and do = ?;", [data.si, data.do], (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, results);
        }
    });   
}