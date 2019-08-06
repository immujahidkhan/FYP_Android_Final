<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

// include database and object files
require_once("class.user.php");
$auth_user = new USER();
$email=$_POST['email'];
$pass=$_POST['pass'];
$cats = $auth_user->runFun("SELECT * FROM user where type='provider' and email='$email' and password='$pass'");
if($cats->rowCount()==1){
	$row = $cats->fetch(PDO::FETCH_ASSOC);
		 $catsData = $auth_user->runFun("select * from provider where user_id = '$row[id]'");
		 $catsRow = $catsData->fetch(PDO::FETCH_ASSOC);
		 
		 $serviceData = $auth_user->runFun("SELECT * FROM `service` where  service_id = '$catsRow[service]'");
		 $serviceRow = $serviceData->fetch(PDO::FETCH_ASSOC);
		
		$product_item=array(
			'response'=>"Found",
            "id" => $row['id'],
            "email" => $row['email'],
            "provider_id" => $catsRow['id'],
            "name" => $row['firstname'] ." ".$row['lastname'],
            "address" => ucwords($row['address']),
            "phone" => ucwords($row['phone']),
            "type" => ucwords($row['type']),
            "service_id" => ucwords($serviceRow['service_id']),
            "service_name" => ucwords($serviceRow['name']),
            "service" => ucwords($catsRow['service']),
            "views" => ucwords($catsRow['views']),
        );
		echo json_encode($product_item);
}
else{
	echo json_encode(array('response'=>"Not Found"));
}	
	
?>