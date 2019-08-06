<?php
require_once("class.user.php");
$auth_user = new USER();

	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		$image = $_POST['image'];
		$service_id = $_POST['service_id'];
		$provider_id = $_POST['provider_id'];
		$name = $_POST['name'];
		$price = $_POST['price'];
		$description = $_POST['description'];
		
		$sql = "SELECT * FROM product ORDER BY product_id ASC";
		
		$cats = $auth_user->runFun($sql);
		$id = 0;
		function generateRandomString($length = 10) {
		$characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
		$charactersLength = strlen($characters);
		$randomString = '';
		for ($i = 0; $i < $length; $i++) {
        $randomString .= $characters[rand(0, $charactersLength - 1)];
		}
		return $randomString;
		}
		while ($row = $cats->fetch(PDO::FETCH_ASSOC)){
				$id = $row['product_id'];
		}
		
		$file = $id.generateRandomString().".jpg";
		$path1 = "../img/".$file;
		$sql = "INSERT INTO `product`(`name`, `description`, `price`, `provider_id`, `image`, `service_id`) VALUES ('$name','$description','$price','$provider_id','$file','$service_id')";
		
		if($auth_user->insert($sql)){
			file_put_contents($path1,base64_decode($image));
			echo json_encode(array('response'=>"Successfully Uploaded"));
		}
		
	}else{
		echo json_encode(array('response'=>"Error"));
	}
?>