// import express
import express from "express";

// import function from controller
import {getUserKeyword, putUserKeyword, deleteUserKeyword } from "../controllers/keywordController.js";

// init express router
const keywordRouter = express.Router();
  

keywordRouter.get('/:userID', getUserKeyword);
  
keywordRouter.post('/put', putUserKeyword);

keywordRouter.delete('/delete', deleteUserKeyword);


export default keywordRouter;