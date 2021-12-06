<?php

    $connection = mysqli_connect("localhost","id17301288_louis321321","J(*h>m>d)J^=8/UP","id17301288_employee_management_system");
    
    // $id =@$_POST["id"];
    // $id ="4";
 
     
     $sql = "DELETE recipe,recipe_picture,recipe_steps,recipe_message,likes FROM recipe 
     INNER JOIN recipe_picture,recipe_steps,recipe_message,likes Where recipe_picture.recipe_id =recipe.id and 
     recipe_steps.recipe_id =recipe.id and recipe_message.recipe_id=recipe.id and likes.recipe_id =recipe.id and
     recipe.id='4'";
     
     $result = mysqli_query($connection,$sql);
     
     if($result){
         echo "Data Deleted";
     }else{
         echo "Failed".mysqli_error($connection);
     }
     mysqli_close($connection);
     


?>