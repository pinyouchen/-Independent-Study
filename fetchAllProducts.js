var express = require('express');
var router = express.Router();

//增加引用函式
const products = require('./utility/products');

//接收GET請求
router.get('/', function(req, res, next) {
    products.fetchAllProducts().then(d => {
        if (d.data.length > 0){
            res.render('fetchAllProducts', {items:d.data});  //將資料傳給顯示頁面
        }else{
            res.render('notFound');  //導向找不到頁面
        }  
    })
});

module.exports = router;