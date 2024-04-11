const container = document.getElementById('container');
const registerBtn = document.getElementById('register');
const loginBtn = document.getElementById('login');

registerBtn.addEventListener('click', () => {
    container.classList.add("active");
});

loginBtn.addEventListener('click', () => {
    container.classList.remove("active");
});

$.ajaxsetup({timeout:30000})

function login() {


    let email = document.getElementById("email").value;
    //let password = btoa(document.getElementById("password").children[1].value); //Base64
    let password = document.getElementById("password").value;

    var jqxhr = $.post("Login", {email: email, password: password}, function(data){
        console.log("risposta" + data.outcome[0])
        const home =  "http://localhost:8080/Yoru/home.jsp";
        let outcome = data.outcome[0];
        if(outcome){
			window.location.href = home
		}
      	else {
            let error = document.getElementById("error-message");
            error.innerHTML = "Email or password entered are incorrect!";
        }
    });
    
    jqxhr.fail(function(_jqxhr, textStatus, errorThrow){
		if(textStatus == "timeout"){
			alert("Problemi nell'esecuzione della richiesta: nella risposta nel tempo limite");
		}else{
			alert("Problemi nell'esecuzione della richiesta: " + errorThrow);
		}
	});

}

function logout() {
    $.post("Logout", {}, function(data){

    });
}