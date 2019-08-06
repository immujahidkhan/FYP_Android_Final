<?php
require_once("class.user.php");
$auth_user = new USER();

if($_SERVER['REQUEST_METHOD']=='POST'){
$user_ID = $_POST['user_id'];

$provider = $auth_user->runFun("SELECT * FROM `provider` WHERE user_id='$user_ID'");
$provider_view = $auth_user->runFun("UPDATE `provider` SET `views`=views+1 WHERE user_id='$user_ID'");
$num = $provider->rowCount();
$resultArr = array();
$resultArr = array('success' => true, 'total' => $num);
$row = $provider->fetch(PDO::FETCH_ASSOC);		
		$user = $auth_user->runFun("SELECT * FROM user WHERE id='$row[user_id]' && type='provider'");
		$user_row = $user->fetch(PDO::FETCH_ASSOC);
		//service
		$service = $auth_user->runFun("select * from service where service_id='$row[service]'");
		$service_row = $service->fetch(PDO::FETCH_ASSOC);		
        $resultArr['provider'][$row['id']] = array(
		'provider_id' => $row['id'],
		'user_id' => $row['user_id'],
		'description' => $row['description'],
		'service_name' => $service_row['name'],
		'service_id' => $service_row['service_id'],
		'status' => $row['status'],
		'views' => $row['views'],
		'email' => $user_row['email'],
		'address' => $user_row['address'],
		'name' => $user_row['firstname']." ".$user_row['lastname'],
		'phone' => $user_row['phone'],
		'phone2' => $row['phone2']
		);
		$products = $auth_user->runFun("select * from product where provider_id=$row[id]");
		 while($products_row = $products->fetch(PDO::FETCH_ASSOC)){
		    $resultArr['provider'][$row['id']]['products'][] = array(
			'product_id' => $products_row['product_id'],
			'product_name' => $products_row['name'],
			'product_description' => $products_row['description'],
			'product_price' => $products_row['price'],
			'provider_id' => $products_row['provider_id'],
			'service_id' => $products_row['service_id'],
			'img' => "".$products_row['image'],
			);
		  }
$resultArr['provider'] = array_values($resultArr['provider']);
echo json_encode($resultArr);   
}
?>