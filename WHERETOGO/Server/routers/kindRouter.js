// import express
import express from "express";

// import function from controller
import {getKind} from "../controllers/kindController.js";

// init express router
const kindRouter = express.Router();
  

kindRouter.get('/:kind', getKind);

export default kindRouter;