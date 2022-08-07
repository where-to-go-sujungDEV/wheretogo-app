// import express
import express from "express";

// import function from controller
import {putKeyword, getUserKeyword, putUserKeyword, deleteUserKeyword, getKeywordExist } from "../controllers/keywordController.js";

// init express router
const keywordRouter = express.Router();
  
keywordRouter.post('/insert', putKeyword);

keywordRouter.get('/:userID', getUserKeyword);
  
keywordRouter.post('/:userID/:keywordID', putUserKeyword);

keywordRouter.delete('/:userID/:keywordID', deleteUserKeyword);

keywordRouter.get('/exist', getKeywordExist);

export default keywordRouter;