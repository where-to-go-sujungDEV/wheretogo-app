// import express
import express from "express";
// import cors
import cors from "cors";
import createError from "http-errors";
import path from "path";

import multer from "multer";

// import routes
import eventRouter from "./routers/eventRouter.js";
import userRouter from "./routers/userRouter.js";
import keywordRouter from "./routers/keywordRouter.js";
import searchRouter from "./routers/searchRouter.js";
import visitedRouter from "./routers/visitedRouter.js";
import savedRouter from "./routers/savedRouter.js";
import calendarRouter from "./routers/calendarRouter.js";
import testRouter from "./routers/testRouter.js";

import dotenv from "dotenv";


dotenv.config();

// init express
const app = express();

// use express json
app.use(express.json());

app.use(cors());

app.use(express.urlencoded({ extended: true }));

// use router
app.use("/event", eventRouter);
app.use("/user", userRouter);
app.use("/search", searchRouter);
app.use("/keyword", keywordRouter);
app.use("/visited", visitedRouter);
app.use("/saved", savedRouter);
app.use("/calendar", calendarRouter);
app.use("/test", testRouter);

app.use('/asset', express.static('asset'));


//app.use("/comm", commRouter);
//app.use("/foll", followRouter);
//app.use("/cs", CSRouter);
//app.use("/", indexRouter);
//app.use("/rank", rankRouter);
//app.use("/mypage", MPRouter);
//app.use("/like", likeRouter);
//app.use("/report", reportRouter);
//app.use("/commLike", commLikeRouter);

// Handling Errors
app.use((err, req, res, next) => {
  // console.log(err);
  err.statusCode = err.statusCode || 500;
  err.message = err.message || "Internal Server Error";
  res.status(err.statusCode).json({
    message: err.message,
  });
});

app.listen(3000, () => console.log("Server running at http://localhost:3000"));