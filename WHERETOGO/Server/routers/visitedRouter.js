// import express
import express from "express";

// import function from controller
import {getVisited, setVisited, deleteVisited, checkVisited} from "../controllers/visitedController.js";

// init express router
const visitedRouter = express.Router();
  

visitedRouter.get('/:userID', getVisited);
  
visitedRouter.post('/:userID/:eventID', setVisited);
  
visitedRouter.delete('/:userID/:eventID', deleteVisited);

visitedRouter.get('/check/:userID/:eventID', checkVisited);

export default visitedRouter;