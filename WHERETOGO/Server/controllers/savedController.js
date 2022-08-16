import {getSavedEvent, addSavedEvent, deleteSavedEvent, getIfSaved} from "../models/saved.js";


export const getSaved = (req, res) => {
    const uid = req.params.userID;
    getSavedEvent(uid, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}

 
export const setSaved = (req, res) => {
    const uid = req.params.userID;
    const eid = req.params.eventID;
    addSavedEvent(uid, eid, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}
  

export const deleteSaved = (req, res) => {
    const uid = req.params.userID;
    const eid = req.params.eventID;
    deleteSavedEvent(uid, eid, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}

export const checkSaved = (req, res) => {
    const uid = req.params.userID;
    const eid = req.params.eventID;
    getIfSaved(uid, eid, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}