'use strict';

//引用函式
const {fetchAllProducts, fetchOneProduct, fetchRangeOfProducts, addProduct, deleteProduct, updateProduct} = require('./utility/products');

//執行函式
fetchAllProducts().then(d => {
    console.log(d);
})