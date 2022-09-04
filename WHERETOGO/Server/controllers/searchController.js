import {getSearchResults, getHotSearchResults, updateSearchCount} from "../models/search.js";

export const getSearch = (req, res) => {
    const data = req.query;
    getSearchResults(data, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}

export const getHotSearch = (req, res) => {
    getHotSearchResults((stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}

export const updateSearch = (req, res) => {
    const data = req.query;
    updateSearchCount(data, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}