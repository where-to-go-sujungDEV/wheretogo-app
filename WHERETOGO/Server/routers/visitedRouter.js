// import express
import express from "express";

// import function from controller
import {getVisited, setVisited, deleteVisited} from "../controllers/visitedController.js";

// init express router
const visitedRouter = express.Router();
  

visitedRouter.get('/:userID', getVisited);
  
visitedRouter.post('/:userID/:eventID/:assess', setVisited);
  
visitedRouter.delete('/:userID/:eventID', deleteVisited);

export default visitedRouter;