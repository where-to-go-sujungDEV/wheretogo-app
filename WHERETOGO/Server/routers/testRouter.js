import express from 'express';
const testRouter = express.Router();
import db from '../config/dbConnection.js';

//import { hashSync, genSaltSync, compareSync } from "bcrypt";
import bcrypt from 'bcryptjs';
import { check, validationResult } from 'express-validator';


testRouter.post('/pw', (req, res, next) => {
          // username is available
          bcrypt.hash(req.body.password, 10, (err, hash) => {
            if (err) {
              return res.status(500).send({
                msg: '비밀번호 암호화에 실패하였습니다',
                code : 500,
                isSuccess : false
              });
            } 
            else {
              // has hashed pw => add to database
                    return res.status(201).send({
                      msg: '성공',
                      code : 201,
                      pw : hash,
                      isSuccess : true

                    });
            }
        });
});

export default testRouter;