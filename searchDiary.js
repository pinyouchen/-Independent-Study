var express = require('express');
var router = express.Router();

const {searchD} = require('./utility/diary');

router.get('/:text', function(req, res, next) {
  
  var text = req.params.text;   

      searchD(text).then(d => {
      if (d.data.length > 0){
          res.render('searchD', {items:d.data});  
      }else{
          res.render('notFound');  
      }  
  })
});

module.exports = router;
