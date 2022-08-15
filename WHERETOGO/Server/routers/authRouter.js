import express from 'express';
const router = express.Router();
import db from '../config/dbConnection.js';

//import { hashSync, genSaltSync, compareSync } from "bcrypt";
import bcrypt from 'bcryptjs';
import { check, validationResult } from 'express-validator';

import jwt from 'jsonwebtoken'; 

router.post('/sign-up', [
  check('nickName', 'nickName is requied').not().isEmpty(),
  check('email', 'Please include a valid email').isEmail().normalizeEmail({ gmail_remove_dots: true }),
  check('password', 'Password must be 6 or more characters').isLength({ min: 6 })
], (req, res, next) => {
  db.query(
    `SELECT * FROM userTBL WHERE LOWER(email) = LOWER(${db.escape(req.body.email)});`,
      (err, result) => {
        if (result.length > 0) {
          return res.status(409).send({
            msg: '이미 등록된 유저입니다',
            code : 409,
            isSuccess : false
          });
        } else {
          // username is available
          bcrypt.hash(req.body.password, 10, (err, hash) => {
            if (err) {
              return res.status(500).send({
                msg: '비밀번호 암호화에 실패하였습니다',
                code : 500,
                isSuccess : false
              });
            } else {
              // has hashed pw => add to database
              db.query(

                `INSERT INTO userTBL (email, pw, nickName, sex, age) VALUES (${db.escape(
                  req.body.email)}, ${db.escape(hash)},'${req.body.nickName}', ?, ?);`, [req.body.sex, req.body.age] ,
                  (err, result) => {
                    if (err) {
                      throw err;
                      return res.status(400).send({
                        msg: err
                      });
                    }
                    return res.status(201).send({
                      msg: '회원가입에 성공하였습니다',
                      code : 201,
                      isSuccess : true

                    });
                  }
                  );}
        });
      }}
      );
});

router.post('/login',  [
  check('email', 'Please include a valid email').isEmail().normalizeEmail({ gmail_remove_dots: true }),
  check('password', 'Password must be 6 or more characters').isLength({ min: 6 })
], (req, res, next) => {
  db.query(
    `SELECT * FROM userTBL WHERE email = ${db.escape(req.body.email)};`,
    (err, result) => {
      // user does not exists
      if (err) {
        throw err;
        return res.status(400).send({
          msg: err
        });
      }
      if (!result.length) {
        return res.status(401).send({
          msg: '이메일이나 비밀번호가 올바르지 않습니다',
          code : 401,
          isSuccess : false
        });
      }
      // check password
      bcrypt.compare(
        req.body.password,
        result[0]['pw'],
        (bErr, bResult) => {
          // wrong password
          if (bErr) {
            throw bErr;
            return res.status(401).send({
              msg: 'Email or password is incorrect!'
            });
          }
          if (bResult) {
            const token = jwt.sign({id:result[0].id},'the-super-strong-secrect',{ expiresIn: '1h' });
            db.query(
              `UPDATE userTBL SET last_login = now() WHERE email = '${result[0].email}'`
              );
              return res.status(200).send({
                msg: '로그인에 성공하였습니다',
                code : 200,
                isSuccess : true,
                token,
                user: result[0]
              });
            }
            return res.status(401).send({
              msg: '이메일이나 비밀번호가 올바르지 않습니다',
              code : 401,
              isSuccess : false
            });
          }
          );
        }
        );
});

router.post('/auto-login',  [
  check('nickName', 'nickName is requied').not().isEmpty(),
  check('email', 'Please include a valid email').isEmail().normalizeEmail({ gmail_remove_dots: true }),
  check('password', 'Password must be 6 or more characters').isLength({ min: 6 })
],  (req, res, next) => {
        if(
          !req.headers.authorization ||
          !req.headers.authorization.startsWith('Bearer') ||
          !req.headers.authorization.split(' ')[1])
          {
            return res.status(422).json({
              message: "token값을 제공해주세요.",
              code : 422,
              isSuccess : false
            });
          }

        const theToken = req.headers.authorization.split(' ')[1];
    const decoded = jwt.verify(theToken, 'the-super-strong-secrect');
    db.query(
      `SELECT * FROM userTBL where email='${decoded.email}'`,function (error, results, fields) {
    if (error) throw error;
    return res.send({ isSuccess: true, data: results[0], message: '사용자 로그인 정보 확인되었습니다.' });
    });
  });

export default router;