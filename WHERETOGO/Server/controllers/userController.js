import {updateUserInfo, deleteUserInfo, registerUserInfo} from "../models/user.js";


  
export const changeUserInfo = (req, res) => {
    const uid = req.params.userID;
    const data = req.body;
    updateUserInfo(uid, data,(err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}
  

export const deleteUser = (req, res) => {
    const uid = req.params.userID;
    deleteUserInfo(uid, (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}


export const registerUser = (req, res) => {
    const data = req.body;
    registerUserInfo(data, ( err, results) => {
        if (err){
            res.status(500).send(err);
        }else{
            res.status(201).json(results);
        }
    });
}