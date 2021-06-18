var express = require('express');
var router = express.Router();

const {addD} = require('./utility/diary');

router.post('/', function(req,res,next){
  var title = req.body.title;
  var text = req.body.text;
  var pic = req.body.pic;
  var date = req.body.date;

  var newData={
    title:title,
    text:text,
    pic:pic,
    date:date
} 

  console.log(title,text,pic,date)
  addD(title,text,pic,date).then(d =>{
    if(d.data.length > 0){
      res.render('addform');
    }else{
      res.render('error');
    }
  })
});

module.exports = router;
