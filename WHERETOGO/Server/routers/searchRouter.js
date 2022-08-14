// import express
import express from "express";

// import function from controller
import {getSearch, getHotSearch} from "../controllers/searchController.js";

// init express router
const searchRouter = express.Router();
  

searchRouter.get('/', getSearch);

searchRouter.get('/hot', getHotSearch);

export default searchRouter;