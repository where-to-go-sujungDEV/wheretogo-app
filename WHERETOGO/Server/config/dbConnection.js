import mysql from "mysql2";

// create the connection to database
/*const db = mysql.createConnection({
  host: "10.0.40.143",
  user: "1f99904e21cab5bd",
  port : "13307",
  password: "fdfdca066bc917d1"
 // database: "where2goDB",
});
*/
const db = mysql.createConnection({
  host: "127.0.0.1",
  user: "root",
  password: "MySQLsujin!",
  database: "where2goDB",
});
export default db;