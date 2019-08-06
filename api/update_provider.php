<?php
require_once("class.user.php");
$auth_user = new USER();

 
if($_SERVER['REQUEST_METHOD']=='POST')
{
 $fname=$_POST['fname'];
	$lname=$_POST['lname'];
	$pass=$_POST['pass'];
	$contact=$_POST['contact'];    
	$Ocontact=$_POST['othercontact'];    
    $desc=$_POST['des'];
    $address=$_POST['address'];
    $user_id=$_POST['user_id'];
$response = array(); 
	
if($auth_user->runFun("UPDATE `user` SET `firstname`='$fname', `lastname`='$lname',`password`='$pass', `address`='$address',`phone`='$contact' where id = '$user_id'"))
{
	
	if($auth_user->runFun("UPDATE provider SET description='$desc',phone2='$Ocontact' where user_id = '$user_id'"))
	{
	http_response_code(201);
	$response['error'] = true; 
	$response['message'] = 'Account updated Successfully';
	}
	else
	{
	http_response_code(200);
	$response['error'] = false; 
	$response['message'] = 'Some thing Wrong';
	}
}
}
else{
	$response['error']=false;
	$response['message']='Invalid Request POST Request Allowed';
	http_response_code(404);
}
echo json_encode($response);
?>