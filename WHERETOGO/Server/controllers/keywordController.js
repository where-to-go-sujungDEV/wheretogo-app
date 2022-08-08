
import {insertKeyword, getUserKeywordByID, putUserKeywordByID, deleteUserKeywordByID, getIfKeywordExist} from "../models/keyword.js";

export const putKeyword = (req, res) => {
    const kw = req.body.keyword;
    insertKeyword(kw, (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}


export const getUserKeyword = (req, res) => {
    const uid = req.params.userID;
    getUserKeywordByID(uid, (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}
  

export const putUserKeyword = (req, res) => {
    const uid = req.params.userID;
    const kid = req.params.keywordID;
    putUserKeywordByID([uid,kid], (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}

export const deleteUserKeyword = (req, res) => {
    const uid = req.params.userID;
    const kid = req.params.keywordID;
    deleteUserKeywordByID([uid,kid], (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}

export const getKeywordExist = (req, res) => {
    const kw = req.body.keyword;
    getIfKeywordExist(kw, (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}