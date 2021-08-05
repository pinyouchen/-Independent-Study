<?php
$serverName="localhost";
$dbName="id17301288_employee_management_system";
$userName="id17301288_louis321321";
$password="Louis037662103";

$conn = mysqli_connect($serverName,$userName,$password,$dbName) or die('Unable to connect');

if(isset($_GET['key'])){
    $key=$_GET['key'];
    $query="SELECT * FROM recipe WHERE name LIKE '%$key%'";
    $result=mysqli_query($conn,$query);
        $response=array();
        while($row=mysqli_fetch_assoc($result)){
            array_push($response,
            array(
                'id'=>$row['id'],
                'name'=>$row['name'],
                'content'=>$row['content'],
                'message'=>$row['message']
            ));
        }
        echo json_encode($response);
}else{
    $query="SELECT * FROM recipe";
    $result=mysqli_query($conn,$query);
    $response=array();
    while($row=mysqli_fetch_assoc($result)){
        array_push($response,
            array(
                'id'=>$row['id'],
                'name'=>$row['name'],
                'content'=>$row['content'],
                'message'=>$row['message']
            ));
    }
    echo json_encode($response);
}

mysqli_close($conn);
?>