import {getBigContent, getSmallContent, getListContent} from "../models/area.js";


export const getBigName = (req, res) => {
    const areacode = req.params.areacode;
    getBigContent(areacode, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}

export const getSmallName = (req, res) => {
    const bigarea = req.params.bigarea;
    const smallarea = req.params.smallarea;
    getSmallContent(bigarea, smallarea, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}

export const getList = (req, res) => {
    const areacode = req.params.areacode;
    getListContent(areacode, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}