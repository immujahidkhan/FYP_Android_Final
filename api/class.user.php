<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
require_once('dbconfig.php');
class USER
{	

	private $conn;
	
	public function __construct()
	{
		$database = new Database();
		$db = $database->dbConnection();
		$this->conn = $db;
    }

	public function insert($sql)
{
	try
	{
		
		$stmt=$this->conn->prepare($sql);
		$stmt->execute();
		return $stmt;
	}
		catch (PDOException $e)
	{
		echo $e->getMessage();
		return false;
	}
	
}
	public function runFun($query)
	{
		try
		{
		$stmt = $this->conn->prepare($query);
		$stmt->execute();
		return $stmt;	
		}
		catch(PDOException $e)
		{
			echo $e->getMessage();	
			return false;
		}
		
	}
		public function apiLogin($email,$pass)
	{
		try
		{
			$stmt = $this->conn->prepare("SELECT * FROM users WHERE pass=:pass and email=:email ");
			$stmt->execute(array(':pass'=>$pass, ':email'=>$email));
			$userRow=$stmt->fetch(PDO::FETCH_ASSOC);
			if($stmt->rowCount() == 1)
			{
				$response = array();
				$id = $userRow['id'];
				$name = $userRow['name'];
				$email = $userRow['email'];
				$pass = $userRow['pass'];
				$message = "success";
				$code = "login_true";
				array_push($response,array("id"=>$id,"name"=>$name,"email"=>$email,"pass"=>$pass,"message"=>$message,"code"=>$code));
				echo json_encode(array("server_response"=>$response));
			}else{
				$response = array();
				$id = "failed";
				$name = "failed";
				$email = "failed";
				$pass = "failed";
				$message = "Email or Password does't match!";
				$code = "login_false";
				array_push($response,array("id"=>$id,"name"=>$name,"email"=>$email,"pass"=>$pass,"message"=>$message,"code"=>$code));
				echo json_encode(array("server_response"=>$response));
			}
		}
		catch(PDOException $e)
		{
			echo $e->getMessage();
		}
	}
	
	
}
?>