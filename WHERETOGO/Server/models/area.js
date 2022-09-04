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

export const getSmallContent = (bigarea, smallarea, result) => { 
    db.query("select aDName from areaCodeDetailTBL where aCode = ? and aDCode = ?;", [bigarea, smallarea], (err, results) => {             
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
    db.query("select aDCode, aDName from areaCodeDetailTBL where aCode = ?;", [areacode], (err, results) => {             
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
    db.query("select aCode, aName from areaCodeTBL;", (err, results) => {             
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