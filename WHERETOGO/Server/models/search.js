import db from "../config/dbConnection.js";

export const getSearchResults = (data, result) => {
    db.query("select * from eventTBL where si = ? and dou = ?;", [data.si, data.dou], (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, results);
        }
    });   
}