// import express
import express from "express";

// import function from controller
import {getSearch} from "../controllers/searchController.js";

// init express router
const searchRouter = express.Router();
  

eventRouter.get('/', getSearch);

export default searchRouter;