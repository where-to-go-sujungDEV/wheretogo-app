// import express
import express from "express";

// import function from controller
import {changeUserInfo, deleteUser, registerUser} from "../controllers/userController.js";

// init express router
const userRouter = express.Router();
  
  
userRouter.patch('/change/:userID', changeUserInfo);
  
userRouter.delete('/unregister/:userID', deleteUser);

userRouter.post('/sign-up', registerUser);

export default userRouter;