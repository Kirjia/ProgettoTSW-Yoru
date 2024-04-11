<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="noopener">
    <link rel="stylesheet" href="css/login.css" rel="noopener">
    <title>Yoru Login Page</title>
</head>

<body>

    <div class="container" id="container">
        <div class="form-container sign-up">
            <form>
                <h1>Create Account</h1>
                <div class="social-icons">
                    <a href="#" class="icon" rel="noopener"><i class="fa-brands fa-facebook-f"></i></a>
                    <a href="#" class="icon" rel="noopener"><i class="fa-brands fa-google-plus-g" ></i></a>
                    <a href="#" class="icon" rel="noopener"><i class="fa-brands fa-github"></i></a>
                    <a href="#" class="icon" rel="noopener"><i class="fa-brands fa-linkedin-in"></i></a>
                </div>
                <span>or use your email for registeration</span>
                <input type="text" placeholder="Name">
                <input type="email" placeholder="Email">
                <input type="password" placeholder="Password">
                <div >
        			<input class="submit" type="button" value="Signup" onclick="signUP()">
      			</div>
            </form>
        </div>
        <div class="form-container sign-in">
            <form id="login-form" action="Login" method="post">
                <h1>Login</h1>
                <div class="social-icons">
                    <a href="#" class="icon" rel="noopener"><i class="fa-brands fa-google-plus-g"></i></a>
                    <a href="#" class="icon" rel="noopener"><i class="fa-brands fa-facebook-f"></i></a>
                    <a href="#" class="icon" rel="noopener"><i class="fa-brands fa-github"></i></a>
                    <a href="#" class="icon" rel="noopener"><i class="fa-brands fa-linkedin-in"></i></a>
                </div>
                <span>or use your email password</span>
                <input id="email" type="email" name="email" placeholder="Email">
                <input id="password" type="password"  name="passwrod" placeholder="Password">
                <a href="#" rel="noopener">Forget Your Password?</a>
                <div >
        			<input class="submit" type="button" value="Login" onclick="login()">
      			</div>
            </form>
        </div>
        <div class="toggle-container">
            <div class="toggle">
                <div class="toggle-panel toggle-left">
                    <h1>Welcome Back!</h1>
                    <p>Enter your personal details to use all of site features</p>
                    <button class="hidden" id="login">Sign In</button>
                </div>
                <div class="toggle-panel toggle-right">
                    <h1>Ciao Ornn!</h1>
                    <p>Register with your personal details to use all of site features</p>
                    <button class="hidden" id="register">Sign Up</button>
                </div>
            </div>
        </div>
    </div>
    
    <div class="error">
      <p id="error-message"></p>
      <hr>
    </div>

	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="js/login.js"></script>
</body>

</html>