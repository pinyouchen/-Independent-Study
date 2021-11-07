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
    $sql = "SELECT * FROM recipe_steps,recipe 
            where recipe_id = '$ID' and recipe_steps.recipe_id=recipe.id 
            order by  recipe_steps.step_number " ;
    $result = $conn->query($sql);
    $response['data']=array();
    // if ($result->num_rows>0) {
        
    // } else {
    //     echo "No Results Found.";
    // }
    while($row=mysqli_fetch_assoc($result)) {
            array_push($response['data'],
                array(
                    'id'=>$row['recipe_id'],
                    'intr'=>$row['step_description'],
                    'IMG'=>$row['step_picture']
                ));
        }
    echo json_encode($response);
    $conn->close();
?>