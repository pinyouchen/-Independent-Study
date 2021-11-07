<?php
    $serverName="localhost";
    $dbName="id17301288_employee_management_system";
    $userName="id17301288_louis321321";
    $password="J(*h>m>d)J^=8/UP";

    $conn = mysqli_connect($serverName,$userName,$password,$dbName);
    $ID = $_POST['ID'];
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }
    $sql =" SELECT * FROM recipe,user,recipe_picture,category,cooking,international
            where recipe.id='$ID' and recipe.userid=user.id and recipe.id=recipe_picture.recipe_id and recipe.category_id=category.category and recipe.cooking_id=cooking.cooking and recipe.international=international.inter";
    $result = $conn->query($sql);
    $response['data']=array();
    // if ($result->num_rows>0) {
        
    // } else {
    //     echo "No Results Found.";
    // }
    while($row=mysqli_fetch_assoc($result)) {
            array_push($response['data'],
                array(
                    'recipe'=>$row['recipe_name'],
                    'username'=>$row['name'],
                    'time'=>$row['release_time'],
                    'category'=>$row['catePic'],
                    'cookIMG'=>$row['cookPic'],
                    'cooking'=>$row['cooking_id'],
                    'inter'=>$row['interIMG'],
                    'intro'=>$row['introduction'],
                    'people'=>$row['quantity'],
                    'vege'=>$row['vegetarian'],
                    'star'=>$row['all_star'],
                    'rating'=>$row['rating'],
                    'cookingtime'=>$row['cooking_time'],
                    'privacy'=>$row['privacy'],
                    'picture'=>$row['recipe_picture']
                ));
        }
    echo json_encode($response);
    $conn->close();
?>