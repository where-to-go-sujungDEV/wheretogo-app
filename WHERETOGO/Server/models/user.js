import db from "../config/dbConnection.js";
import bcrypt from 'bcryptjs';
import jwt from 'jsonwebtoken'; 

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
                code : 406,
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
            else if(data.password){
                bcrypt.hash(data.password, 10, (err, hash) => {
                    if (err) {
                      return res.status(500).send({
                        msg: '비밀번호 암호화에 실패하였습니다',
                        code : 500,
                        isSuccess : false
                      });
                    } else {
                        if(!data.nickName){
                            db.query(`update userTBL set pw = ${db.escape(hash)} where userID = ?;`,[uid], (err, results) => {             
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
                            db.query(`update userTBL set nickName = ?, pw = ${db.escape(hash)} where userID = ?;`,[data.nickName, uid], (err, results) => {             
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

export const registerUserInfo = (data, result) => {
    db.query("SELECT * FROM userTBL WHERE LOWER(email) = LOWER(?);", data.email ,(err, cnt) => {
            if(err){
                result(500, {
                    code : 5000,
                    isSuccess : false,
                    msg : "회원가입에 실패하였습니다",
                    err}, null);
            }
            else if (cnt.length > 0) {
              result(200, null, {
                code : 4090,
                isSuccess : false,
                msg : '이미 등록된 유저입니다'
                });
            } else {
              bcrypt.hash(data.password, 10, (err, hash) => {
                if (err) {
                    result(500, {
                        code : 5000,
                        isSuccess : false,
                        msg : "회원가입에 실패하였습니다.",
                        err}, null
                    );
                } else {
                  // has hashed pw => add to database
                  db.query(`INSERT INTO userTBL (email, pw, nickName, sex, age) VALUES (?, ${db.escape(hash)},?, ?, ?);`, [data.email, data.nickName, data.sex, data.age] ,
                      (err, results) => {
                        if (err) {
    
                            result(500, {
                                code : 5000,
                                isSuccess : false,
                                msg : "회원가입에 실패하였습니다.",
                                err}, null
                            );
                        }
                        else {
                            result(201, null, {
                                code : 2010,
                                isSuccess : true,
                                msg : "회원가입에 성공하였습니다."
                                }
                            );
                        }
                      }
                      );}
            });
          }}
          );
}


export const loginUserInfo = (data, result) => {
    db.query("SELECT * FROM userTBL WHERE LOWER(email) = LOWER(?);", data.email ,(err, cnt) => {
            if(err){
                result(500, {
                    code : 500,
                    isSuccess : false,
                    msg : "로그인에 실패하였습니다",
                    err}, null);
            }
            else if (!cnt.length) {
              result(200, null, {
                code : 401,
                isSuccess : false,
                msg : '이메일이 올바르지 않거나, 등록되지않은 유저입니다.'
                });
            } else {
              bcrypt.compare(data.password, cnt[0].pw, (bErr, bResult) => {
                if (bErr) {
                    result(500, {
                        code : 500,
                        isSuccess : false,
                        msg : "오류가 발생하였습니다.",
                        err}, null
                    );
                } else if(!bResult){
                    result(200,null,{
                        code : 401,
                        isSuccess : false,
                        msg : "비밀번호가 틀렸습니다.",
                    });
                }
                else {
                    const token = jwt.sign({id : cnt[0].userID},'the-super-strong-secret',{ expiresIn: '1h' });
   
                  db.query(`UPDATE userTBL SET last_login = now() WHERE email = ?`, [cnt[0].email] ,(err, results) => {
                        if (err) {
                            result(500, {
                                code : 5000,
                                isSuccess : false,
                                msg : "로그인에 실패하였습니다.",
                                err}, null
                            );
                        }
                        else {
                            result(201, null, {
                                code : 200,
                                isSuccess : true,
                                msg : "로그인에 성공하였습니다.",
                                token : token,
                                user : result[0]
                                }
                            );
                        }
                      }
                      );}
            });
          }}
          );
}