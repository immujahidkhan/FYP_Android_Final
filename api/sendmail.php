<?php
$email= $_GET['email'];;

require '../PHPMailer-master/PHPMailerAutoload.php';

            $mail = new PHPMailer();
            
            //Enable SMTP debugging.
            $mail->SMTPDebug = 1;
            //Set PHPMailer to use SMTP.
            $mail->isSMTP();
            //Set SMTP host name
            $mail->Host = "smtp.gmail.com";
            $mail->SMTPOptions = array(
                                'ssl' => array(
                                    'verify_peer' => false,
                                    'verify_peer_name' => false,
                                    'allow_self_signed' => true
                                )
                            );
            //Set this to true if SMTP host requires authentication to send email
            $mail->SMTPAuth = TRUE;
            //Provide username and password
            $mail->Username = "homedrivenapp@gmail.com";
            $mail->Password = "Admin@12345";
            //If SMTP requires TLS encryption then set it
            $mail->SMTPSecure = "false";
            $mail->Port = 587;
            //Set TCP port to connect to
            
            $mail->From = "homedrivenapp@gmail.com";
            $mail->FromName = "Home Driven";
            
            $mail->addAddress($email);
            
            $mail->isHTML(true);
                            
            $mail->Subject = "Order";
            $mail->Body = "<i>Loves Your order is under process you will recieved a call from service provider within a day OR few hours later  </i>";
            $mail->AltBody = "Successfully recived";
            if(!$mail->send())
            {
            //echo "Mailer Error: " . $mail->ErrorInfo;
            }
            else
            {
            echo "Message has been sent successfully";
            }
?>
