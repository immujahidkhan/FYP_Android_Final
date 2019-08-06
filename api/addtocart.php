<?php
if(isset($_GET['id'])){
$product_id = $_GET['id'];
include '../../db1.php';    
$sql="select * from product where product_id=$product_id";
$result=mysqli_query($con,$sql);
$row=mysqli_fetch_assoc($result);
$sql3="select * from provider where id=$row[provider_id]";
$result3=mysqli_query($con,$sql3);
$row3=mysqli_fetch_assoc($result3);
$sql2="insert into cart(product_id,provider_id)values('$product_id','$row3[user_id]')";
$response = "";
if(mysqli_query($con,$sql2)){ 
$response = 'Added Successfully';
}else{
$response = 'Error';
}
echo json_encode($response);
}
?>