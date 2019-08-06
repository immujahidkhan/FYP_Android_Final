<?php
require_once("class.user.php");
$auth_user = new USER();

 
if($_SERVER['REQUEST_METHOD']=='POST')
{
 $fname=$_POST['fname'];
	$lname=$_POST['lname'];
	$email=$_POST['email'];
	$pass=$_POST['pass'];
	$contact=$_POST['contact'];    
	$Ocontact=$_POST['othercontact'];    
    $desc=$_POST['des'];
    $address=$_POST['address'];
    $service=$_POST['service'];
    $status="Not Approved";
$response = array(); 
	
$data = $auth_user->runFun("SELECT * FROM `user` WHERE email = '$email'");
if($data->rowCount() >0)
{
	$response['error'] = false; 
	$response['message'] = 'Already exist';
}else{
//if($auth_user->runFun("INSERT INTO `user`(`email`) VALUES ('$email')"))
if($auth_user->runFun("INSERT INTO `user`(`firstname`, `lastname`, `email`, `password`, `type`, `address`, `phone`) VALUES ('$fname','$lname','$email','$pass','provider','$address','$contact')"))
{
	$serviceData = $auth_user->runFun("SELECT * FROM user where email='$email'");
	$serviceRow = $serviceData->fetch(PDO::FETCH_ASSOC);
	
	if($auth_user->runFun("INSERT INTO provider(user_id,service,description,phone2,status,views)  VALUES('$serviceRow[id]','$service','$desc','$Ocontact','$status','0')"))
	{
	http_response_code(201);
	$response['error'] = true; 
	$response['message'] = 'Account Created Successfully';
	}
	else
	{
	http_response_code(200);
	$response['error'] = false; 
	$response['message'] = 'Some thing Wrong';
	}
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