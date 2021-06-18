'use strict';

//引用操作資料庫的物件
const query = require('./asyncDB');

var fetchAllProducts = async function(){
    var result={};
	
    await query('SELECT * FROM dairy')
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
		
    return result;
}


var addD = async function(newData){
    var result;
	
    await query('INSERT INTO diary SET ?',newData)
        .then((data) => {            
            result = 0; 
        }, (error) => {
            result = -1;
        });
		
    return result;
}

var searchD = async function(keyword){
    var result={};
	
    await query('SELECT * FROM diary WHERE id like ?','%' + keyword + '%')
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
		
    return result;
}

var deleteD = async function(id){
    var result={};
	
    await query('DELETE FROM diary WHERE id = ?', id)
        .then((data) => {
        result = data.affectedRows;  
        }, (error) => {
        result = -1;
        });
    
    return result;
}

var updateD = async function(newData, id){
    var result;

    await query('UPDATE diary SET ? WHERE id = ?', [newData, id])
        .then((data) => {
            result = data.affectedRows;  
        }, (error) => {
            result = -1;
        });
		
    return result;
}


//匯出
module.exports = {fetchAllProducts,addD, searchD, deleteD, updateD};
