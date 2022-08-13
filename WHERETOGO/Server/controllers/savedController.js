import {getSavedEvent, addSavedEvent, deleteSavedEvent} from "../models/saved.js";


export const getSaved = (req, res) => {
    const uid = req.params.userID;
    getSavedEvent(uid, (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}

 
export const setSaved = (req, res) => {
    const uid = req.params.userID;
    const eid = req.params.eventID;
    addSavedEvent(uid, eid, (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}
  

export const deleteSaved = (req, res) => {
    const uid = req.params.userID;
    const eid = req.params.eventID;
    deleteSavedEvent(uid, eid, (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}