<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

// include database and object files
require_once("class.user.php");
$auth_user = new USER();

$perpage = 10;
//$perpage = $_GET["perpage"];

if(isset($_GET["page"])){
$page = intval($_GET["page"]);
}
else {
$page = 1;
}
$calc = $perpage * $page;
$start = $calc - $perpage;
//rand()
$cats = $auth_user->runFun("select * from cart");
$num = $cats->rowCount();
    // products array
    $products_arr=array();
    $products_arr["OrdersList"]=array();
	 while ($row = $cats->fetch(PDO::FETCH_ASSOC)){
      	
		 $catsData = $auth_user->runFun("select * from product where product_id=$row[product_id]");
		 $catsRow = $catsData->fetch(PDO::FETCH_ASSOC);
		 
		$product_item=array(
            "id" => $row['cart_id'],
            "user_id" => $catsRow['price'],
            "name" => $catsRow['name']
        );
 
        array_push($products_arr["OrdersList"], $product_item);
    }
 
    echo json_encode($products_arr);
	
?>