// import express
import express from "express";

// import function from controller
import {getSearch, getHotSearch, updateSearch} from "../controllers/searchController.js";

// init express router
const searchRouter = express.Router();
  

searchRouter.post('/', getSearch);

searchRouter.get('/hot', getHotSearch);

searchRouter.post('/update', updateSearch);

export default searchRouter;