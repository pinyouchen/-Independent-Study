<?php
    $serverName="localhost";
    $dbName="id17301288_employee_management_system";
    $userName="id17301288_louis321321";
    $password="J(*h>m>d)J^=8/UP";
    
    $conn = mysqli_connect($serverName,$userName,$password,$dbName);

    $recipeID=$_POST["recipeID"];
    $quan=$_POST["quantity"];
    $ingre=$_POST["ingredient"];

    $sql = "SELECT * FROM ingredient WHERE Ingre='$ingre'";
    $in_ingre = "INSERT INTO ingredient(Ingre) VALUES ('$ingre')";
    $result=mysqli_query($conn,$sql);
    $row=mysqli_fetch_assoc($result);
    if (!mysqli_num_rows($result)) 
    { 
        $resultInsert=$conn->query($in_ingre);
        if($resultInsert){
            $ingre_id=$conn->insert_id;
            $select="INSERT INTO recipe_ingredient(ingredient_id,recipe_id,quantity) VALUES('$ingre_id','$recipeID','$quan')";
            $resultInsert=$conn->query($select);
            echo "new list Inserted";
        }
        else{
            echo "Failed".mysqli_error($conn);
        }
    } 
    else 
    { 
        $ingre_id=$row['id'];
        $resultInsert=$conn->query($in_ingre);
        if($resultInsert){
            $select="INSERT INTO recipe_ingredient(ingredient_id,recipe_id,quantity) VALUES('$ingre_id','$recipeID','$quan')";
            $resultInsert=$conn->query($select);
            echo "list Inserted";
        }
        else{
            echo "Failed".mysqli_error($conn);
        }
    }
    mysqli_close($conn); 
?>