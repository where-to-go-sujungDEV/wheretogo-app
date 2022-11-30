import db from "../config/dbConnection.js";

export const getKindContent = (kind, result) => { 
    db.query("select cName from CategoryTBL where cCode = ?;", [kind], (err, results) => {             
        if(err) {
            result(500, err, null);
        } else {
            result(200, null, {
                code : 200,
                isSuccess : true,
                results});
        }
    });   
}