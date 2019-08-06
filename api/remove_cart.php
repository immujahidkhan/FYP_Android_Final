<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

// include database and object files
require_once("class.user.php");
$auth_user = new USER();
//
if(isset($_GET['id']))
{
$cart_id = $_GET['id'];   
$sql="delete from cart where cart_id=$cart_id";
$cats = $auth_user->runFun($sql);
	echo json_encode(array('response'=>"Successfully Removed"));
}else{
	echo json_encode(array('response'=>"Id not set"));
}
?>