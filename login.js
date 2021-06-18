var express = require('express');
var router = express.Router();

const {login} = require('./utility/user');

router.post('/', function(req,res,next){
  var email = req.body.email;
  var password = req.body.password;
  console.log(email,password)
  login(email,password).then(d =>{
    if(d.data.length > 0){
      res.render('addform');
    }else{
      res.render('error');
    }
  })
});

module.exports = router;
