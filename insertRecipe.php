<?php
$serverName="localhost";
$dbName="id17301288_employee_management_system";
$userName="id17301288_louis321321";
$password="J(*h>m>d)J^=8/UP";

$conn = mysqli_connect($serverName,$userName,$password,$dbName);

$user = $_POST["user_id"];
$category = $_POST["category_id"];
$cooking = $_POST["cooking_id"];
$name = $_POST["recipe_name"];
$quantity = $_POST["quantity"];
$reTime = $_POST["release_time"];
$vegetarian = $_POST["vegetarian"];
$intro = $_POST["introduction"];
$cookTime = $_POST["cooking_time"];
$privacy = $_POST["privacy"];


     
$sql = "INSERT INTO recipe
        (user_id,category_id,cooking_id,recipe_name,quantity,release_time,vegetarian,degree,introduction,cooking_time,likes,privacy)
        VALUES ('$user','$category','$cooking','$name','$quantity','$reTime','$vegetarian','0','$intro','$cookTime','0','$privacy')";
     
$result = mysqli_query($conn,$sql);
     
if($result){
    echo "Data Inserted";
}
else{
    echo "Failed";
}
mysqli_close($conn);
?>