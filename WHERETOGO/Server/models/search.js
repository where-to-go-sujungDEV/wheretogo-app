import db from "../config/dbConnection.js";

export const getSearchResults = (result) => {
    db.query("select * from eventTBL;", (err, results) => {             
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


/*export const getSearchResults = (data, result) => {
    db.query("select * from eventTBL where si = ? and dou = ?;", [data.si, data.dou], (err, results) => {             
        if(err) {
            console.log(err);
            result(err, null);
        } else {
            result(null, results);
        }
    });   
}*/