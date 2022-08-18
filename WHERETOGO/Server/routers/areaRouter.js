// import express
import express from "express";

// import function from controller
import {getBigName, getSmallName, getList} from "../controllers/areaController.js";

// init express router
const areaRouter = express.Router();
  

areaRouter.get('/name/:areacode', getBigName);
areaRouter.get('/name/:bigarea/:smallarea', getSmallName);
areaRouter.get('/:areacode', getList);
export default areaRouter;