import {getSavedEvent, addSavedEvent, deleteSavedEvent, checkIfSaved} from "../models/saved.js";


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

export const checkSaved = (req, res) => {
    const uid = req.params.userID;
    const eid = req.params.eventID;
    checkIfSaved(uid, eid, (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results[0]);
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