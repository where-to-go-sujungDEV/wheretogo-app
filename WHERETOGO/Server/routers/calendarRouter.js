// import express
import express from "express";

// import function from controller
import {getCalendarEvent} from "../controllers/calendarController.js";

// init express router
const calendarRouter = express.Router();
  

calendarRouter.get('/:userID', getCalendarEvent);

export default calendarRouter;