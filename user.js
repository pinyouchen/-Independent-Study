'use strict';

//引用操作資料庫的物件
const query = require('./asyncDB');

var login = async function(email,password){
    var result = [];
    await query('select * from user where email=? and password=?',[email,password])
    .then((data) => {
        result = {code:0, data:data};
    }, (error) => {
        result = {code:-1};
    });

    console.log(result)
    return result;
}
module.exports = {login};