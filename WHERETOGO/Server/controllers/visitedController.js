import {getVisitedEvent, addVisitedEvent, deleteVisitedEvent, checkIfVisited} from "../models/visited.js";


export const getVisited = (req, res) => {
    const uid = req.params.userID;
    getVisitedEvent(uid, (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}

export const checkVisited = (req, res) => {
    const uid = req.params.userID;
    const eid = req.params.eventID;
    checkIfVisited(uid, eid, (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results[0]);
        }
    });
}
  
export const setVisited = (req, res) => {
    const uid = req.params.userID;
    const eid = req.params.eventID;
    addVisitedEvent(uid, eid,(err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}
  

export const deleteVisited = (req, res) => {
    const uid = req.params.userID;
    const eid = req.params.eventID;
    deleteVisitedEvent(uid, eid, (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}
