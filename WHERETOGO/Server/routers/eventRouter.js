// import express
import express from "express";

// import function from controller
import {getMainBoard, getTopEvents, getUserTopEvents, getEventById} from "../controllers/eventController.js";

// init express router
const eventRouter = express.Router();
  

eventRouter.get('/main', getMainBoard);
  
eventRouter.get('/top', getTopEvents);

eventRouter.get('/userTop/:userID', getUserTopEvents);
  
eventRouter.get('/:eventID', getEventById);

export default eventRouter;