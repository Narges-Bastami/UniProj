<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Forgot Password</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<input type="hidden" id="status" value="<%= request.getAttribute("status") %>">

<div class="main">
    <section class="forgot-password">
        <div class="container">
            <div class="forgot-password-content">
                <h2 class="form-title">Forgot Password</h2>
                <form method="post" action="forgotpassword" class="forgot-form">
                    <div class="form-group">
                        <label for="email"><i class="zmdi zmdi-email"></i></label>
                        <input type="email" name="email" id="email" placeholder="Your Email" />
                    </div>
                    <div class="form-group">
                        <label for="securityAnswer"><i class="zmdi zmdi-shield-security"></i></label>
                        <input type="text" name="securityAnswer" id="securityAnswer" placeholder="What's your favourite dessert?" />
                    </div>
                    <div class="form-group">
                        <label for="newPwd"><i class="zmdi zmdi-lock"></i></label>
                        <input type="password" name="newPwd" id="newPwd" placeholder="New Password" />
                    </div>
                    <div class="form-group">
                        <label for="confirmNewPwd"><i class="zmdi zmdi-lock-outline"></i></label>
                        <input type="password" name="confirmNewPwd" id="confirmNewPwd" placeholder="Confirm New Password" />
                    </div>
                    <div class="form-group form-button">
                        <input type="submit" name="reset" id="reset" class="form-submit" value="Reset Password" />
                    </div>
                </form>
            </div>
        </div>
    </section>
</div>

<script src="vendor/jquery/jquery.min.js"></script>
<script src="js/main.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<script type="text/javascript">
    var status = document.getElementById('status').value;

    if (status == "emptyFields") {
        swal("Error", "All Fields are Required!", "error");
    } else if (status == "passwordMismatch") {
        swal("Error", "New Passwords Do Not Match!", "error");
    } else if (status == "userNotFound") {
        swal("Error", "No User Found with this Email or Security Answer!", "error");
    } else if (status == "updateFailed") {
        swal("Error", "Failed to Update Password. Try Again!", "error");
    } else if (status == "error") {
        swal("Error", "An Unexpected Error Occurred!", "error");
    }
</script>
</body>
</html>
