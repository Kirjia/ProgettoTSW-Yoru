const container = document.getElementById('container');
const registerBtn = document.getElementById('register');
const loginBtn = document.getElementById('login');

registerBtn.addEventListener('click', () => {
    container.classList.add("active");
});

loginBtn.addEventListener('click', () => {
    container.classList.remove("active");
});


function login() {


    let email = document.getElementById("email").value;
    //let password = btoa(document.getElementById("password").children[1].value); //Base64
    let password = document.getElementById("password").value;
    let checkBox = document.getElementById("rememberMe");
    
    let rememberMe;
    
    if(checkBox.checked == true)
    	rememberMe = "true";
	else
		rememberMe = "false";

    var jqxhr = $.post("./Login", {email: email, password: password, rememberMe: rememberMe}, function(data){
        console.log("risposta" + data.outcome[0]);
        const home =  "https://localhost/Yoru/home.jsp";
        let outcome = data.outcome[0];
        if(outcome){
			window.location.href = home;
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
			alert("Problemi nell'esecuzione della richiesta: " + textStatus + "error: " + errorThrow);
		}
	});

}

function signUp() {
    let name = document.querySelector('.sign-up input[placeholder="Nome"]').value;
    let surname = document.querySelector('.sign-up input[placeholder="Cognome"]').value;
    let email = document.querySelector('.sign-up input[placeholder="Email"]').value;
    let password = document.querySelector('.sign-up input[placeholder="Password"]').value;
    let phone = document.querySelector('.sign-up input[placeholder="Telefono"]').value; 

	if (!name || !surname || !email || !password || !phone) {
	    alert("Tutti i campi sono obbligatori!");
	    return;
	}
	
    $.post("./SignUp", { email: email, password: password, name: name, surname: surname, phone: phone }, function(data) {
        if (data.result) {
            alert("Registrazione completata con successo!");
            window.location.href = "./home.jsp";
        } else {
            alert("Registrazione fallita!");
        }
    }).fail(function(jqxhr, textStatus, errorThrown) {
        alert("Errore nella registrazione: " + textStatus + " " + errorThrown);
    });
}



function logout() {
    $.post("Logout", {}, function(data){

    });
}