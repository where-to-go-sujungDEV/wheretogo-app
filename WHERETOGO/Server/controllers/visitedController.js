import {getVisitedEvent, addVisitedEvent, deleteVisitedEvent, getIfVisited} from "../models/visited.js";


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

  
export const setVisited = (req, res) => {
    const uid = req.params.userID;
    const eid = req.params.eventID;
    const ass = req.params.assess;
    addVisitedEvent(uid, eid, ass,(err, results) => {
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

export const checkVisited = (req, res) => {
    const uid = req.params.userID;
    const eid = req.params.eventID;
    getIfVisited(uid, eid, (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}