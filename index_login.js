var crypto = require('crypto');
var uuid = require('uuid');
var express = require('express');
var mysql = require('mysql');
var bodyParser = require('body-parser');

var con = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password:'Louis321321',
  database:'DemoNodeJS'
});

var genRandomString = function(length){
  return crypto.randomBytes(Math.ceil(length/2))
  .toString('hex')
  .slice(0,length);
};

var sha512 = function(password,salt){
  var hash = crypto.createHmac('sha512',salt);
  hash.update(password);
  var value = hash.digest('hex');
  return{
    salt:salt,
    passwordHash:value
  };
};

function saltHashPassword(userPassword){
  var salt = genRandomString(16);
  var passwordData = sha512(userPassword,salt);
  return passwordData;
}

var app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

app.post('/register/',(req,res,next) =>{
  var post_data = req.body;
  var uid = uuid.v4();
  var plaint_password = post_data.password;
  var hash_data = saltHashPassword(plaint_password);
  var password = hash_data.passwordHash;
  var salt = hash_data.salt;
  var name = post_data.name;
  var email = post_data.email;

  con.query('SELECT * FROM user where email=?',[email],function(err,result,fields){
    con.on('error',function(err){
      console.log('[MYSQL ERROR]',err);
    });

    if(result && result.length)
    res.json('User already exists!');
    else
    {
      con.query('INSERT INTO `User`(`unique_id`, `name`, `email`, `encrypted_password`, `salt`, `created_at`, `updated_at`) VALUES (?,?,?,?,?,NOW(),NOW())',[uid,name,email,password,salt],function(err,result,fields){
        con.on('error',function(err){
          console.log('[MYSQL ERROR]',err);
          res.json('Rigister error:',err);
        });
        res.json('Rigister successful');
      })
    }
  });
})


// app.get("/",(req,res,next) =>{
//   console.log('Password: 123456');
//   var encrypt = saltHashPassword("123456");
//   console.log('Encrypt:' + encrypt.passwordHash);
//   console.log('Salt:' + encrypt.salt);
// })

 app.listen(3000, ()=>{
   console.log('on port 3000');
 })
