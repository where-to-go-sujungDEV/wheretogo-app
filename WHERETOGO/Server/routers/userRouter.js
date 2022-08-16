// import express
import express from "express";

// import function from controller
import {changeUserInfo, deleteUser, registerUser, loginUser} from "../controllers/userController.js";

// init express router
const userRouter = express.Router();
  
  
userRouter.patch('/change/:userID', changeUserInfo);
  
userRouter.delete('/unregister/:userID', deleteUser);

userRouter.post('/sign-up', registerUser);

userRouter.post('/login', loginUser);

export default userRouter;