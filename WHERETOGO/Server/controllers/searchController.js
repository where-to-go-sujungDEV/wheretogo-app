import {getSearchResults} from "../models/search.js";

export const getSearch = (req, res) => {
    getSearchResults((err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}
/*
export const getSearch = (req, res) => {
    const data = req.body;
    getSearchResults(data, (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}
*/