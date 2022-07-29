// import express
import express from "express";
  
// import function from controller
import { showUsers, showUserById, createUser, updateUser, deleteUser } from "../controllers/usersController.js";
  
// init express router
const UserRouter = express.Router();
  
// Get All User
UserRouter.get('/users', showUsers);
  
// Get certain User
UserRouter.get('/users/:id', showUserById);
  
// Create New User
UserRouter.post('/users', createUser);
  
// Update User
UserRouter.put('/users/:id', updateUser);
  
// Delete User
UserRouter.delete('/users/:id', deleteUser);
  
// export default router
export default UserRouter;