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
            msg: '이미 등록된 유저입니다'
          });
        } else {
          // username is available
          bcrypt.hash(req.body.password, 10, (err, hash) => {
            if (err) {
              return res.status(500).send({
                msg: '비밀번호 해시실패'
              });
            } else {
              // has hashed pw => add to database
              db.query(
                `INSERT INTO userTBL (nickName, email, password, sex, age) VALUES ('${req.body.nickName}', ${db.escape(
                  req.body.email
                  )}, ${db.escape(hash)},'${req.body.sex}', '${req.body.age}')`,
                  (err, result) => {
                    if (err) {
                      throw err;
                      return res.status(400).send({
                        msg: err
                      });
                    }
                    return res.status(201).send({
                      msg: 'The user has been registerd with us!'
                    });
                  }
                  );}
        });
      }}
      );
});

router.post('/login',  [
  check('email', 'Please include a valid email').isEmail().normalizeEmail({ gmail_remove_dots: true }),
  check('password', 'Password must be 6 or more characters').isLength({ min: 1 })
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
          msg: 'Email or password is incorrect!'
        });
      }
      // check password
      bcrypt.compare(
        req.body.password,
        result[0]['password'],
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
                msg: 'Logged in!',
                token,
                user: result[0]
              });
            }
            return res.status(401).send({
              msg: 'email or password is incorrect!'
            });
          }
          );
        }
        );
});

router.post('/get-user',  [
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
              message: "로그인이 필요합니다.",
            });
          }

        const theToken = req.headers.authorization.split(' ')[1];
    const decoded = jwt.verify(theToken, 'the-super-strong-secrect');
    db.query(
      `SELECT * FROM userTBL where email='${decoded.email}'`,function (error, results, fields) {
    if (error) throw error;
    return res.send({ error: false, data: results[0], message: '사용자 로그인 정보 확인되었습니다.' });
    });
    db.query(
      `SELECT userID FROM userTBL where email='${decoded.email}'`,function (error, results, fields) {
    if (error) throw error;
    return res.send({ error: false, data: results[0], message: '사용자id값 저장됨' });
    });
    });

export default router;