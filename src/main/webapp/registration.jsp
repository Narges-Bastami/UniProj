<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=0.9">
	<title>Sign Up</title>

	<!-- Font Icon -->
	<link rel="stylesheet"
		  href="fonts/material-icon/css/material-design-iconic-font.min.css">

	<!-- Main css -->
	<link rel="stylesheet" href="css/style.css">
</head>
<body>

<input type="hidden" id="status" value="<%= request.getAttribute("status") %>">

<div class="main">

	<!-- Sign up form -->
	<section class="signup">
		<div class="container">
			<div class="signup-content" >
				<div class="signup-form">
					<h2 class="form-title">Sign Up</h2>

					<form method="post" action="register" class="register-form"
						  id="register-form">
						<div class="form-group">
							<label for="name"><i
									class="zmdi zmdi-account material-icons-name"></i></label> <input
								type="text" name="name" id="name" placeholder="Name" />
						</div>
						<div class="form-group">
							<label for="email"><i class="zmdi zmdi-email"></i></label> <input
								type="email" name="email" id="email" placeholder="Email" />
						</div>
						<div class="form-group">
							<label for="pass"><i class="zmdi zmdi-lock"></i></label> <input
								type="password" name="pass" id="pass" placeholder="Password" />
						</div>
						<div class="form-group">
							<label for="re_pass"><i class="zmdi zmdi-lock-outline"></i></label>
							<input type="password" name="re_pass" id="re_pass"
								   placeholder="Repeat Password" />
						</div>
						<div class="form-group">
							<label for="contact"><i class="zmdi zmdi-lock-outline"></i></label>
							<input type="text" name="contact" id="contact"
								   placeholder="Phone Number"  />
						</div>
						<div class="form-group">
							<label for="question"><i class="zmdi zmdi-lock-outline"></i></label>
							<input type="text" name="question" id="question"
								   placeholder="Your favourite dessert? (SQ)"  />
						</div>
						<div class="form-group form-button">
							<input type="submit" name="signup" id="signup"
								   class="form-submit" value="Register" />
						</div>
					</form>
				</div>
				<div class="signup-image">
					<figure>
						<img src="images/signup-image.jpg" alt="sing up image" width="290" height="290" >
					</figure>
					<a href="login.jsp" class="signup-image-link"> Already a member? </a>
				</div>
			</div>
		</div>
	</section>

</div>

<!-- JS -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="js/main.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<script type="text/javascript">

	var status = document.getElementById('status').value;
	if (status == "success") {
		swal("Congrats", "Account Created Successfully!", "success");
	}
	if (status == "invalidName") {
		swal("Hold on!", "Please Enter Your Name!", "error");
	}
	if (status == "invalidEmail") {
		swal("Hold on!", "Please Enter a Valid Email!", "error");
	}
	if (status == "invalidUpwd") {
		swal("Hold on!", "Please Enter a Valid Password!", "error");
	}
	if (status == "invalidConfirmPassword") {
		swal("Hold on!", "Passwords Don't Match!", "error");
	}
	if (status == "invalidMobile") {
		swal("Hold on!", "Enter a Valid Mobile Number!", "error");
	}
	if (status == "invalidAnswer") {
		swal("Hold on!", "Enter a Security Answer!", "error");
	}
	if (status == "uExist") {
		swal("Hold on!", "User Already Exists!", "error");
	}

</script>

</body>
</html>
