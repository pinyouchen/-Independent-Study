'use strict';

//引用操作資料庫的物件
const query = require('./asyncDB');

var category_name = async function(name){
    var result = [];
    await query('select * from category where category_name=?',[category_name])
    .then((data) => {
        result = {code:0, data:data};
    }, (error) => {
        result = {code:-1};
    });

    console.log(result)
    return result;
}
module.exports = {category_name};