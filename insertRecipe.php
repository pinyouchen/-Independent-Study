<?php
    $serverName="localhost";
    $dbName="id17301288_employee_management_system";
    $userName="id17301288_louis321321";
    $password="J(*h>m>d)J^=8/UP";
    
    $conn = mysqli_connect($serverName,$userName,$password,$dbName);
    
    $user = $_POST["userid"];
    $category = $_POST["category_id"];
    $cooking = $_POST["cooking_id"];
    $name = $_POST["recipe_name"];
    $quantity = $_POST["quantity"];
    $reTime = $_POST["release_time"];
    $vegetarian = $_POST["vegetarian"];
    $degree = $_POST["degree"];
    $intro = $_POST["introduction"];
    $cookTime = $_POST["cooking_time"];
    $like = $_POST["likes"];
    $privacy = $_POST["privacy"];
    $inter = $_POST["international"];

    $target_dir = "recipe_image/";
	$image = $_POST["recipe_picture"];
	$imageStore = rand()."_".time().".jpg";
	$target_dir = $target_dir."/".$imageStore;
	$uri="https://louis32132118.000webhostapp.com/recipe_image/".$imageStore;
	file_put_contents($target_dir, base64_decode($image));
         
    $sql = "INSERT INTO recipe(userid,category_id,cooking_id,recipe_name,quantity,release_time,international,vegetarian,degree,introduction,cooking_time,likes,privacy) VALUES ('$user','$category','$cooking','$name','$quantity','$reTime','$inter','$vegetarian','$degree','$intro','$cookTime','$like','$privacy')";
    
    $result = $conn->query($sql);
     
    if($result){
        
        $recipe_id=$conn->insert_id;
        $select= "INSERT into recipe_picture(recipe_id,recipe_picture) VALUES ('$recipe_id','$uri')";
        $result = $conn->query($select);
        echo "Data Inserted";
        // header('Content-type: application/json');
        echo json_encode($recipe_id);
    }
    else{
        echo "Failed".mysqli_error($conn);
    }
    mysqli_close($conn);
?>