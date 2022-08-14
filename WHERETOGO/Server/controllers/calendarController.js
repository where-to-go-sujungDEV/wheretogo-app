import {getCalendarEvents} from "../models/calendar.js";


export const getCalendarEvent = (req, res) => {
    const uid = req.params.userID;
    getCalendarEvents(uid, (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}