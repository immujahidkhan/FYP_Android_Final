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
$cats = $auth_user->runFun("select * from provider where status='Approved' ORDER BY views desc limit $start,$perpage");
$num = $cats->rowCount();
    // products array
    $products_arr=array();
    $products_arr["Providers"]=array();
	 while ($row = $cats->fetch(PDO::FETCH_ASSOC)){
      	
		 $catsData = $auth_user->runFun("select * from user where type='provider' and id = '$row[user_id]'");
		 $catsRow = $catsData->fetch(PDO::FETCH_ASSOC);
		 
		 $serviceData = $auth_user->runFun("SELECT * FROM `service` where  service_id = '$row[service]'");
		 $serviceRow = $serviceData->fetch(PDO::FETCH_ASSOC);
		 
		$product_item=array(
            "id" => $row['id'],
            "user_id" => $row['user_id'],
            "name" => $catsRow['firstname'] ." ".$catsRow['lastname'],
            "views" => ucwords($row['views']),
            "service" => ucwords($row['service']),
            "serviceTitle" => ucwords($serviceRow['name']),
            "description" => ucwords($row['description']),
            "address" => ucwords($catsRow['address']),
            "phone2" => ucwords($row['phone2']),
            "status" => ucwords($row['status']),
        );
 
        array_push($products_arr["Providers"], $product_item);
    }
 
    echo json_encode($products_arr);
	
?>