'use strict';

//引用操作資料庫的物件
const query = require('./asyncDB');

//------------------------------------------
// 傳回所有產品
//------------------------------------------
var fetchAllProducts = async function(){
    var result={};
	
    await query('SELECT * FROM product')
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
		
    return result;
}

//------------------------------------------
// 傳回單一商品
//------------------------------------------
var fetchOneProduct = async function(proNo){
    var result={};
	
    await query('SELECT * FROM product WHERE proNo = ?', proNo)
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
		
    return result;
}

//------------------------------------------
// 傳回指定範圍的產品
//------------------------------------------
var fetchRangeOfProducts = async function(start=0, nums=10){
    var result={};
	
    await query('SELECT * FROM product LIMIT ?, ?', [start, nums])
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
		
    return result;
}

//------------------------------------------
// 新增商品
//------------------------------------------
var addProduct = async function(newData){
    var result;

    await query('INSERT INTO product SET ?', newData)
        .then((data) => {
            result = 0;  
        }, (error) => {
            result = -1;
        });
		
    return result;
}

//------------------------------------------
// 刪除商品
//------------------------------------------
var deleteProduct = async function(proNo){
    var result;

    await query('DELETE FROM product WHERE proNo = ?', proNo)
        .then((data) => {
            result = data.affectedRows;  
        }, (error) => {
            result = -1;
        });
		
    return result;
}

//------------------------------------------
// 更新商品
//------------------------------------------
var updateProduct = async function(newData, proNo){
    var result;

    await query('UPDATE product SET ? WHERE proNo = ?', [newData, proNo])
        .then((data) => {
            result = data.affectedRows;  
        }, (error) => {
            result = -1;
        });
		
    return result;
}
//------------------------------------------

//匯出
module.exports = {fetchAllProducts, fetchOneProduct, fetchRangeOfProducts, addProduct, deleteProduct, updateProduct};