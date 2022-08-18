import db from "../config/dbConnection.js";

export const getBigContent = (areacode, result) => { 
    db.query("select aName from areaCodeTBL where aCode = ?;", [areacode], (err, results) => {             
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