<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="noopener">
    <link rel="stylesheet" href="./css/login.css" rel="noopener">
    <title>Yoru Login Page</title>
</head>

<body>

 

    <div class="container" id="container">
        <div class="form-container sign-up">
            <form>
                <h1>Crea un account</h1>
                <div class="social-icons">
                    <a href="#" class="icon" rel="noopener"><i class="fa-brands fa-facebook-f"></i></a>
                    <a href="#" class="icon" rel="noopener"><i class="fa-brands fa-google-plus-g" ></i></a>
                    <a href="#" class="icon" rel="noopener"><i class="fa-brands fa-github"></i></a>
                    <a href="#" class="icon" rel="noopener"><i class="fa-brands fa-linkedin-in"></i></a>
                </div>
                <span>o usa la tua e-mail per la registrazione</span>
                <input type="text" placeholder="Nome">
                <input type="email" placeholder="Email">
                <input type="password" placeholder="Password">
                <div >
        			<input class="submit" type="button" value="Registrati" onclick="signUP()">
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
                <span>o usa la tua e-mail e password</span>
                <input id="email" type="email" name="email" placeholder="Email">
                <input id="password" type="password"  name="passwrod" placeholder="Password">
                <input id="rememberMe" type="checkbox" name="rememberMe" value="false">Resta connesso
                <a href="#" rel="noopener">Password dimenticata?</a>
                <div >
        			<input class="submit" type="button" value="Login" onclick="login()">
      			</div>
            </form>
        </div>
        <div class="toggle-container">
            <div class="toggle">
                <div class="toggle-panel toggle-left">
                    
 					<a href="${pageContext.request.contextPath}/home.jsp"><img class="logo" src="./images/LOGO_bianco2.2.png"></a>
                    <p>Inserisci i tuoi dati personali per utilizzare tutte le funzionalità del sito</p>
                    <button class="hidden" id="login">Accedi</button>
                </div>
                <div class="toggle-panel toggle-right">
                    
 					<a href="${pageContext.request.contextPath}/home.jsp"><img class="logo" src="./images/LOGO_bianco2.2.png"></a>
                    <p>Registrati con i tuoi dati personali per utilizzare tutte le funzionalità del sito</p>
                    <button class="hidden" id="register">Registrati</button>
                </div>
            </div>
        </div>
    </div>
    
    <div class="error">
      <p id="error-message"></p>
      <hr>
    </div>

	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script src="./js/login.js"></script>
</body>

</html>