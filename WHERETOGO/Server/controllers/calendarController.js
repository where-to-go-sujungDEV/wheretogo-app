import {getCalendarEvents} from "../models/calendar.js";


export const getCalendarEvent = (req, res) => {
    const uid = req.params.userID;
    getCalendarEvents(uid, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}