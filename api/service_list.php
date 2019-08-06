<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

// include database and object files
require_once("class.user.php");
$auth_user = new USER();


$cats = $auth_user->runFun("SELECT * FROM `service` ");
$num = $cats->rowCount();
    // products array
    $products_arr=array();
    $products_arr["Service_List"]=array();
	 while ($row = $cats->fetch(PDO::FETCH_ASSOC)){
      	
		 
		$product_item=array(
            "service_id" => $row['service_id'],
            "name" => $row['name']
        );
 
        array_push($products_arr["Service_List"], $product_item);
    }
 
    echo json_encode($products_arr);
	
?>