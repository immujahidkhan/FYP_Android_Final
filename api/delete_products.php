<?php
require_once("class.user.php");
$auth_user = new USER();

$product_id=$_GET['product_id'];
$response = array(); 
	
	if($auth_user->runFun("DELETE from `orders` WHERE `product_id`='$product_id'"))
	{
	http_response_code(201);
	$response['error'] = true; 
	$response['message'] = 'Updated Successfully';
	}
	else
	{
	http_response_code(200);
	$response['error'] = false; 
	$response['message'] = 'Some thing Wrong';
	}
echo json_encode($response);
?>