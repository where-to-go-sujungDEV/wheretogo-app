
import { getUserKeywordByID, putUserKeywordByID, deleteUserKeywordByID} from "../models/keyword.js";


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
    const data = req.body.keyword;
    putUserKeywordByID(uid, data, (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}

export const deleteUserKeyword = (req, res) => {
    const uid = req.params.userID;
    const data = req.body.keyword;
    deleteUserKeywordByID(uid, data, (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}

