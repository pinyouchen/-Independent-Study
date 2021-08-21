<?php
   $serverName="localhost";
   $dbName="id17301288_employee_management_system";
   $userName="id17301288_louis321321";
   $password="J(*h>m>d)J^=8/UP";
   
   $conn = mysqli_connect($serverName,$userName,$password,$dbName);
   
    if(isset($_POST['image']))
	{

	$target_dir = "recipe_image/";
	$image = $_POST["recipe_picture"];
	$imageStore = rand()."_".time().".jpg";
	$target_dir = $target_dir."/".$imageStore;
	$uri="https://louis32132118.000webhostapp.com/recipe_image/".$imageStore;
	file_put_contents($target_dir, base64_decode($image));
    $recipe=$_POST["recipe_id"];

    $qry="SELECT * FROM recipe,recipe_picture WHERE recipe.id=recipe_picture.recipe_id";
    
	$select= "INSERT into recipe_picture(recipe_id,recipe_picture) VALUES ('$recipe','$uri')";
	
    $result = mysqli_query($conn,$qry);
    $num_rows = mysqli_num_rows($result);
    
    if($num_rows > 0){
    echo "Data Inserted";
    mysqli_query($conn,$select);
    }else{
    echo "!!!";
    }

    mysqli_close($conn);
}
	
	
?>