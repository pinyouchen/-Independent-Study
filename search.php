<?php 

	$connection = mysqli_connect("localhost","id17301288_louis321321","J(*h>m>d)J^=8/UP","id17301288_employee_management_system");
	
	$result = array();
	$result['user'] = array();
	$select= "select*from user where name='".$index['name']."'":"SELECT *from user";
	$responce = mysqli_query($connection,$select);
	
	while($row = mysqli_fetch_array($responce))
		{
			$index['id']      = $row['0'];
			$index['name']    = $row['1'];
			$index['email']   = $row['2'];
			$index['password'] = $row['3'];
		
			
			array_push($result['user'], $index);
		}
			
			$result["success"]="1";
			echo json_encode($result);
			mysqli_close($connection);

 ?>
