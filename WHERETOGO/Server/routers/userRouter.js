// import express
import express from "express";

// import function from controller
import {changeUserNInfo, changeUserPInfo, getUserPW, getUserNN, deleteUser, registerUser, loginUser, autoLogin} from "../controllers/userController.js";

// init express router
const userRouter = express.Router();
  
  
userRouter.patch('/changeN/:userID', changeUserNInfo);
  
userRouter.patch('/changeP/:userID', changeUserPInfo);

userRouter.delete('/unregister/:userID', deleteUser);

userRouter.get('/get-pw/:userID', getUserPW);

userRouter.get('/get-nickname/:userID', getUserNN);

userRouter.post('/sign-up', registerUser);

userRouter.post('/login', loginUser);

userRouter.post('/auto-login', autoLogin);

export default userRouter;