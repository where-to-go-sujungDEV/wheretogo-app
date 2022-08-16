
import { getUserKeywordByID, putUserKeywordByID, deleteUserKeywordByID} from "../models/keyword.js";


export const getUserKeyword = (req, res) => {
    const uid = req.params.userID;
    getUserKeywordByID(uid, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}
  

export const putUserKeyword = (req, res) => {
    const uid = req.params.userID;
    const data = req.body.keyword;
    putUserKeywordByID(uid, data, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}

export const deleteUserKeyword = (req, res) => {
    const uid = req.params.userID;
    const data = req.params.keyword;
    deleteUserKeywordByID(uid, data, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}

