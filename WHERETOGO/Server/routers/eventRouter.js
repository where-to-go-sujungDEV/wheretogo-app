// import express
import express from "express";

// import function from controller
import {getMainBoard, getTopEvents, getEventById} from "../controllers/eventController.js";

// init express router
const eventRouter = express.Router();
  

eventRouter.get('/main', getMainBoard);
  
eventRouter.get('/top/:userID', getTopEvents);
  
eventRouter.get('/:eventID', getEventById);

export default eventRouter;