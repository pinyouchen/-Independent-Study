'use strict';

//引用操作資料庫的物件
const query = require('./asyncDB');

var fetchAllProducts = async function(){
    var result={};
	
    await query('SELECT * FROM recipe')
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
		
    return result;
}


var addR = async function(newData){
    var result;
	
    await query('insert into recipe set ?',newData)
        .then((data) => {            
            result = 0; 
        }, (error) => {
            result = -1;
        });
		
    return result;
}

var searchR = async function(keyword){
    var result={};
	
    await query('SELECT * FROM recipe WHERE id like ?','%' + keyword + '%')
        .then((data) => {
            result = {code:0, data:data};  
        }, (error) => {
            result = {code:-1};
        });
		
    return result;
}

var deleteR = async function(id){
    var result={};
	
    await query('DELETE FROM recipe WHERE id = ?', id)
        .then((data) => {
        result = data.affectedRows;  
        }, (error) => {
        result = -1;
        });
    
    return result;
}

var updateR = async function(newData, id){
    var result;

    await query('UPDATE recipe SET ? WHERE id = ?', [newData, id])
        .then((data) => {
            result = data.affectedRows;  
        }, (error) => {
            result = -1;
        });
		
    return result;
}


//匯出
module.exports = {fetchAllProducts,addR, searchR, deleteR, updateR};
