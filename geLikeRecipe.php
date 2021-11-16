<?php
    $serverName="localhost";
    $dbName="id17301288_employee_management_system";
    $userName="id17301288_louis321321";
    $password="J(*h>m>d)J^=8/UP";

    $conn = mysqli_connect($serverName,$userName,$password,$dbName);

    $userID=$_POST['user_id'];
    $query="SELECT * FROM recipe,user,recipe_picture,category,cooking,international,likes
            where privacy = 1 and likes.user_id="$userID" and recipe.userid=user.id and recipe.id=recipe_picture.recipe_id and
             recipe.category_id=category.category and recipe.cooking_id=cooking.cooking and 
             recipe.international=international.inter and recipe.id=likes.recipe_id
            Order by recipe.id";
    $result=mysqli_query($conn,$query);
    $response['data']=array();
    while($row=mysqli_fetch_assoc($result)){
        array_push($response['data'],
            array(
                    'id'=>$row['id'],
                    'recipename'=>$row['recipe_name'],
                    'username'=>$row['name'],
                    'time'=>$row['release_time'],
                    'category'=>$row['catePic'],
                    'cooking'=>$row['cookPic'],
                    'cookingid'=>$row['cooking_id'],
                    'people'=>$row['quantity'],
                    'inter'=>$row['interIMG'],
                    'intro'=>$row['introduction'],
                    'cookingtime'=>$row['cooking_time'],
                    'privacy'=>$row['privacy'],
                    'picture'=>$row['recipe_picture'],
                    'likes'=>$row['likes']
                ));
    }
    echo json_encode($response);


    mysqli_close($conn);
?>