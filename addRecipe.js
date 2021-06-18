var express = require('express');
var router = express.Router();

const {addR} = require('./utility/recipe');
const {category_name} = require('./utility/category');

router.post('/', function(req,res,next){
  var name = req.body.name;
  var content = req.body.content;
  var quantity = req.body.quantity;
  var time = req.body.time;
  var pic = req.body.pic;
  var category_name = req.body.category_name;

  var newData={
    name:name,
    content:content,
    quantity:quantity,
    time:time,
    date:date,
    pic:pic,
    category_name:category_name
} 

  console.log(name,content,quantity,time,date,pic,category_name)
  addR(name,content,quantity,time,date,pic,category_name).then(d =>{
    if(d.data.length > 0){
      res.render('addform');
    }else{
      res.render('error');
    }
  })
});

module.exports = router;
