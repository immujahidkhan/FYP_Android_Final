<?php
require_once("class.user.php");
$auth_user = new USER();

if($_SERVER['REQUEST_METHOD']=='GET'){
$provider_id = $_GET['provider_id'];

$cats = $auth_user->runFun("SELECT * FROM `product` WHERE `provider_id`='$provider_id'");
$num = $cats->rowCount();
$products_arr=array();
$products_arr["Products"]=array();
//if($num>0){
	 while ($row = $cats->fetch(PDO::FETCH_ASSOC)){
		 
		$product_item=array(
            "response" => $num,
            "product_id" => $row['product_id'],
            "provider_id" => $row['provider_id'],
            "provider_name" => $row['name'],
            "provider_description" => $row['description'],
            "provider_image" => $row['image'],
            "provider_price" => $row['price'],
            "provider_service_id" => $row['service_id'],
        );
 
        array_push($products_arr["Products"], $product_item);
    }
 
    echo json_encode($products_arr);
/*}else{
	$product_item=array("response" => $num);
	array_push($products_arr["Products"], $product_item);
	echo json_encode($products_arr);
}*/
}
?>