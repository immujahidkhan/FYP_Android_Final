<?php
require_once("class.user.php");
$auth_user = new USER();

//if($_SERVER['REQUEST_METHOD']=='GET'){
//$user_ID = $_GET['user_id'];

if($_SERVER['REQUEST_METHOD']=='POST'){
$user_ID = $_POST['user_id'];

$provider = $auth_user->runFun("SELECT * FROM `provider` WHERE user_id='$user_ID'");
$provider_view = $auth_user->runFun("UPDATE `provider` SET `views`=views+1 WHERE user_id='28'");
$num = $provider->rowCount();
$resultArr = array();

    $resultArr = array('success' => true, 'total' => $num);
    while($row = $provider->fetch(PDO::FETCH_ASSOC)){
        $resultArr['provider'][$row['id']] = array(
		'id' => $row['id'],
		'user_id' => $row['user_id'],
		'description' => $row['description'],
		'status' => $row['status'],
		'views' => $row['views'],
		'phone2' => $row['phone2']
		);
		$user = $auth_user->runFun("SELECT * FROM user WHERE id='$row[user_id]' && type='provider'");
		$num_user = $user->rowCount();
		$user_row = $user->fetch(PDO::FETCH_ASSOC);
		$resultArr['provider'][$row['user_id']]['user']['email'] = $user_row['email'];
		$resultArr['provider'][$row['user_id']]['user']['address'] = $user_row['address'];
		$resultArr['provider'][$row['user_id']]['user']['phone'] = $user_row['phone'];
		$resultArr['provider'][$row['user_id']]['user']['name'] = $user_row['firstname']." ".$user_row['lastname'];
		//
		$product = $auth_user->runFun("SELECT * FROM product WHERE provider_id='$row[id]'");
		$num_product = $product->rowCount();
		while($row_product = $product->fetch(PDO::FETCH_ASSOC)){
        $resultArr['provider'][$row['id']]['user']['product'] = $row_product['product_id'];
	
	}
		
    }
    $resultArr['provider'] = array_values($resultArr['provider']);
echo json_encode($resultArr);   
		 
	}
?>