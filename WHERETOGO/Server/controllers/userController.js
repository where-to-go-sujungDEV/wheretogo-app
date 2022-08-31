import {updateUserNInfo, updateUserPInfo, deleteUserInfo, registerUserInfo, loginUserInfo, doAutoLogin} from "../models/user.js";


  
export const changeUserNInfo = (req, res) => {
    const uid = req.params.userID;
    const data = req.body;
    updateUserNInfo(uid, data,(stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}

export const changeUserPInfo = (req, res) => {
    const uid = req.params.userID;
    const data = req.body;
    updateUserPInfo(uid, data,(stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}
  

export const deleteUser = (req, res) => {
    const uid = req.params.userID;
    deleteUserInfo(uid, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}


export const registerUser = (req, res) => {
    const data = req.body;
    registerUserInfo(data, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}



export const loginUser = (req, res) => {
    const data = req.body;
    loginUserInfo(data, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}


export const autoLogin = (req, res) => {
    const head = req.headers;
    doAutoLogin(head, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}