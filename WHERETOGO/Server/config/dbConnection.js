import mysql from "mysql2";

// create the connection to database
const db = mysql.createConnection({
  host: "127.0.0.1",
  user: "root",
  password: "vmfhwprxm3!",
<<<<<<< HEAD
  password: "MySQLsujin!",
=======
  password: "vmfhwprxm3!",
>>>>>>> 20d82934b1debc8f1fd9466dde40a528f2fc64c2

database: "where2goDB",
  database: "where2goDB",
});

export default db;