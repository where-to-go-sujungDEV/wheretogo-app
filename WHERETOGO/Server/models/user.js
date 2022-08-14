import db from "../config/dbConnection.js";

export const updateUserInfo = (uid, data, result) => {
    db.query("select * from userTBL where userID = ?;", uid, (err, count) => {             
        if (err) {
            console.log(err);
            result({
                msg : "회원정보 갱신을 실패하였습니다.", 
                code : 500, 
                isSuccess : false,
                err}, null);
        } 
        else if(count.length <= 0) {
            result(null,{
                msg : "존재하지 않는 사용자입니다.",
                code : 204,
                isSuccess : false
            });
        } 
        else {
            if((!data.nickName)&&(!data.password)){
                result(null, {
                    msg : "변경할 데이터가 입력되지 않았습니다. 변경사항이 없습니다.",
                    code : 204,
                    isSuccess : true
                });
            }
            else if((!data.nickName)&&(data.password)){
                db.query("update userTBL set password = ? where userID = ?;",[data.password, uid], (err, results) => {             
                    if(err) {
                        console.log(err);
                        result({
                            msg : "오류가 발생하였습니다.",
                            code : 500, 
                            err
                        }, null);
                    }
                    else {
                        result(null, {
                            msg : "회원정보 갱신을 완료하였습니다.",
                            code : 200,
                            isSuccess : true});
                    }
                }); 
            }
            else if((data.nickName)&&(!data.password)){
                db.query("update userTBL set nickName = ? where userID = ?;",[data.nickName, uid], (err, results) => {             
                    if(err) {
                        console.log(err);
                        result({
                            msg : "오류가 발생하였습니다.",
                            code : 500, 
                            err
                        }, null);
                    }
                    else {
                        result(null, {
                            msg : "회원정보 갱신을 완료하였습니다.",
                            code : 200,
                            isSuccess : true});
                    }
                }); 
            }
            else{
                db.query("update userTBL set nickName = ?, password = ? where userID = ?;",[data.nickName, data.password, uid], (err, results) => {             
                    if(err) {
                        console.log(err);
                        result({
                            msg : "오류가 발생하였습니다.",
                            code : 500, 
                            err
                        }, null);
                    }
                    else {
                        result(null, {
                            msg : "회원정보 갱신을 완료하였습니다.",
                            code : 200,
                            isSuccess : true});
                    }
                }); 
            }
        }
    });  
}



export const deleteUserInfo = (uid, result) => {
    db.query("select * from userTBL where userID = ?;", uid, (err, count) => {             
        if (err) {
            console.log(err);
            result({
                msg : "회원탈퇴를 실패하였습니다.", 
                code : 500, 
                isSuccess : false,
                err}, null);
        } 
        else if(count.length <= 0) {
            result(null,{
                msg : "존재하지 않는 사용자입니다.",
                code : 204,
                isSuccess : false
            });
        } 
        else {
            db.query("delete from userTBL where userID = ?;", uid, (err, results) => {             
                if(err) {
                    console.log(err);
                    result({
                        code : 500,
                        isSuccess : false,
                        msg : "회원탈퇴를 실패하였습니다.",
                        err}, null);
                } else {
                    result(null, {
                        msg : "회원탈퇴를 완료하였습니다.",
                        code : 200,
                        isSuccess : true
                    });
                }
            }); 
        }
    });  
}