
import {getMainBoardContents, getTopContents, getUserTopContents, getEventByEventID, getEventUserInfos} from "../models/event.js";


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
    getTopContents((err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}

export const getUserTopEvents = (req, res) => {
    const uid = req.params.userID;
    getUserTopContents(uid, (err, results) => {
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

export const getEventUserInfo = (req, res) => {
    const eid = req.params.eventID;
    const uid = req.params.userID;
    getEventUserInfos(uid, eid, (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}