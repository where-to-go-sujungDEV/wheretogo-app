import {getKindContent} from "../models/kind.js";


export const getKind = (req, res) => {
    const kind = req.params.kind;
    getKindContent(kind, (stat, err, results) => {
        if (err){
            res.status(stat).send(err);
        }else{
            res.status(stat).json(results);
        }
    });
}