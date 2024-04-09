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

    $.post("Login", {email: email, password: password}, function(data){
        console.log("outcome" + data)
        let outcome = data.outcome[0];
        if(!outcome) {
            let error = document.getElementById("error-message");
            error.innerHTML = "Email or password entered are incorrect!";
        }
    });

}

function logout() {
    $.post("Logout", {}, function(data){

    });
}