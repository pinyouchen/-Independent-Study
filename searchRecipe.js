var express = require('express');
var router = express.Router();

const {searchR} = require('./utility/recipe');

router.get('/:category_name', function(req, res, next) {
  
  var category_name = req.params.category_name;   

      searchR(category_name).then(d => {
      if (d.data.length > 0){
          res.render('searchR', {items:d.data});  
      }else{
          res.render('notFound');  
      }  
  })
});

module.exports = router;
