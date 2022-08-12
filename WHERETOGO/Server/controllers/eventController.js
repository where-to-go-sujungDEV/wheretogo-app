
import {getMainBoardContents, getTopContents, getEventByEventID} from "../models/event.js";


export const getMainBoard = (req, res) => {
    getMainBoardContents((err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}
  
export const getTopEvents = (req, res) => {
    const uid = req.params.userID;
    getTopContents(uid, (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}
  

export const getEventById = (req, res) => {
    const id = req.params.eventID;
    getEventByEventID(id, (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}
