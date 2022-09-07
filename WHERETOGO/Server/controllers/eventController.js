
import {getMainBoardContents, getTopContents, getRecommandEventsInfos, getUserTopContents, getEventByEventID, getEventUserInfos} from "../models/event.js";


export const getMainBoard = (req, res) => {
    getMainBoardContents((stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}
  
export const getTopEvents = (req, res) => {
    getTopContents((stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}

export const getUserTopEvents = (req, res) => {
    const uid = req.params.userID;
    getUserTopContents(uid, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}
  

export const getEventById = (req, res) => {
    const id = req.params.eventID;
    getEventByEventID(id, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}

export const getEventUserInfo = (req, res) => {
    const eid = req.params.eventID;
    const uid = req.params.userID;
    getEventUserInfos(uid, eid, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}

export const getRecommandEvents = (req, res) => {
    const sex = req.params.sex;
    const age = req.params.age;
    getRecommandEventsInfos(sex, age, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}