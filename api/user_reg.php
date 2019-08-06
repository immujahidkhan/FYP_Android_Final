<?php
require_once("class.user.php");
$auth_user = new USER();

 
if($_SERVER['REQUEST_METHOD']=='POST')
{
 $fname=$_POST['fname'];
	$lname=$_POST['lname'];
	$email=$_POST['email'];
	$mobile=$_POST['mobile'];
	$address=$_POST['address'];    
$response = array(); 
	
$data = $auth_user->runFun("SELECT * FROM `user` WHERE email = '$email'");
if($data->rowCount() >0)
{
	$response['error'] = false; 
	$response['message'] = 'Email Already exist';
}else{
	$cats = $auth_user->runFun("select * from cart");
if($auth_user->runFun("INSERT INTO `user`(`firstname`, `lastname`, `email`, `phone`, `type`, `address`) VALUES ('$fname','$lname','$email','$mobile','user','$address')"))
{
	
	$auth_user->runFun("delete from cart");
	$user = $auth_user->runFun("select * from user where email='$email'");
	$rowUser = $user->fetch(PDO::FETCH_ASSOC);
	$cats = $auth_user->runFun("select * from cart");
   	while($row = $cats->fetch(PDO::FETCH_ASSOC))
    {
       $sql3="INSERT INTO orders(product_id,status,user_id,provider_id) VALUES('$row[product_id]','Pending','$rowUser[id]','$row[provider_id]')";
	   $auth_user->runFun($sql3);
    }
	//
	
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
else{
	$response['error']=false;
	$response['message']='Invalid Request POST Request Allowed';
	http_response_code(404);
}
echo json_encode($response);
?>