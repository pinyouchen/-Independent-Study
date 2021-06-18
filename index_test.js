var express = require('express');
var mysql = require('mysql');
var bodyParser = require('body-parser');

var con = mysql.createConnection({
  host:'localhost',
  user:"root",
  password:'Louis321321',
  database:'study_recipe'
});

var app = express();
var publicDir =(__dirname + "/public");
app.use(express.static(publicDir));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended:true}));

app.get("/person",(req,res,next) =>{
  con.query('SELECT * FROM person',function(error,result,fields){
    con.on('error',function(err){
        console.log('[MYSQL]ERROR',err);
    });
    if(result && result.length)
    {
      res.end(JSON.stringify(result));
    }
    else
    {
      res.end(JSON.stringify('no person here'));
    }
  });
});

app.post("/search",(req,res,next) =>{

  var post_data = req.body;
  var name_search = post_data.search;

  var query = "SELECT * FROM person WHERE name LIKE '%"+name_search+"%'";

  con.query(query,function(error,result,fields){
    con.on('error',function(err){
        console.log('[MYSQL]ERROR',err);
    });
    if(result && result.length)
    {
      res.end(JSON.stringify(result));
    }
    else
    {
      res.end(JSON.stringify('no person here'));
    }
  });
});

app.listen(3000,()=>{
  console.log('EDMTDev Search API running on port 3000');
})