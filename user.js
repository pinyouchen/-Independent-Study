'use strict';

//引用操作資料庫的物件
const query = require('./asyncDB');

//------------------------------------------
//執行資料庫動作的函式-傳回所有產品資料
//------------------------------------------
var addUser = async function(newData){
	var result;
	
	await query('insert into user set ?',newData)
	.then((data) => {
		result = 0;
	}, (error) => {
		result = -1;
	});
	return result;
}

//匯出
module.exports = {addUser};  