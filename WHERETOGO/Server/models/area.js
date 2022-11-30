import db from "../config/dbConnection.js";

export const getBigContent = (areacode, result) => { 
    db.query("select aName from AreaCodeTBL where aCode = ?;", [areacode], (err, results) => {             
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

export const getSmallContent = (bigarea, smallarea, result) => { 
    db.query("select aDName from AreaCodeDetailTBL where aCode = ? and aDCode = ?;", [bigarea, smallarea], (err, results) => {             
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

export const getListContent = (areacode, result) => { 
    db.query("select aDCode, aDName from AreaCodeDetailTBL where aCode = ?;", [areacode], (err, results) => {             
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

export const getBigListContent = (result) => { 
    db.query("select aCode, aName from AreaCodeTBL;", (err, results) => {             
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