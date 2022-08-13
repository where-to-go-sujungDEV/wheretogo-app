import db from "../config/dbConnection.js";

export const getUserKeywordByID = (uid, result) => {
    db.query("select content from keywordTBL where userID = ? ;", [uid], (err, results) => {             
        if(err) {
            console.log(err);
            result({
                code : 500,
                isSuccess : false,
                err
            }, null);
        } else {
            result(null, {
                code : 200,
                isSuccess : true,
                results});
        }
    });   
};
  

export const putUserKeywordByID = (uid, data, result) => {
    db.query("select * from keywordTBL where userID = ? and content = ?;", [uid, data], (err, count) => {           
        if(count.length >= 1) {
            result({
                code : 202,
                isSuccess : false,
                msg : "이미 등록된 키워드입니다."
            }, null);
        } else {
            db.query("insert into keywordTBL (userID, content) VALUES (?,?);", [uid, data], (err, results) => {             
                if(err) {
                    console.log(err);
                    result({
                        code : 500,
                        isSuccess : false,
                        err
                    }, null);
                } else {
                    result(null, {
                        code : 201,
                        isSuccess : true,
                        msg : "성공적으로 키워드가 등록되었습니다."
                    });
                }
            });
        }
    });
};

export const deleteUserKeywordByID = (uid, data, result) => {
    db.query("select * from keywordTBL where userID = ? and content = ?;", [uid, data], (err, count) => {          
        if(count.length < 1) {
            result({
                code : 202,
                isSuccess : false,
                msg : "등록되지 않은 키워드를 삭제하려 하고 있습니다."
            }, null);
        } else {
            db.query("delete from keywordTBL where userID =? and content = ?;", [uid, data], (err, results) => {             
                if(err) {
                    console.log(err);
                    result({
                        code : 500,
                        isSuccess : false,
                        err
                    }, null);
                } else {
                    result(null, {
                        code : 201,
                        isSuccess : true,
                        msg : "성공적으로 키워드가 삭제되었습니다."
                    });
                }
            });
        }
    });
};

