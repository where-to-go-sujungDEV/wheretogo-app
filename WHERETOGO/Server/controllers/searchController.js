import {getSearchResults, getHotSearchResults, updateSearchCount} from "../models/search.js";

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

export const getHotSearch = (req, res) => {
    getHotSearchResults((err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}

export const updateSearch = (req, res) => {
    const data = req.body;
    updateSearchCount(data, (err, results) => {
        if (err){
            res.send(err);
        }else{
            res.json(results);
        }
    });
}