// import express
import express from "express";

// import function from controller
import {getSaved, setSaved, deleteSaved, checkSaved} from "../controllers/savedController.js";

// init express router
const savedRouter = express.Router();
  

savedRouter.get('/:userID', getSaved);
  
savedRouter.post('/:userID/:eventID', setSaved);
  
savedRouter.delete('/:userID/:eventID', deleteSaved);

savedRouter.get('/check/:userID/:eventID', checkSaved);

export default savedRouter;