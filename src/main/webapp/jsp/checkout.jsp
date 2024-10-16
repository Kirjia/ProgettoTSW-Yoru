<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.yoru.model.Entity.Prodotto"%>
<%@ page import="com.yoru.model.Entity.CartItem"%>
<%@ page import="com.yoru.model.Entity.User"%>
<%@ page import="javax.servlet.http.HttpSession"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Checkout</title>
 <link href="${pageContext.request.contextPath}/css/Checkout.css" rel="stylesheet"> 
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  
</head>
<body>
	<%@ include file="/jsp/header.jsp"%>
	<div class="container">

		<!-- Sezione dettagli utente -->
		<div class="order-details">
			<h2>Dati dell'Utente</h2>

			<%
			User utente = (User) session.getAttribute("user");
			if (utente != null) {
			%>
			<div class="form-group">
				<label for="nome">Nome:</label> <input type="text" id="nome"
					name="nome" value="<%=utente.getNome()%>" readonly>
			</div>
			<div class="form-group">
				<label for="cognome">Cognome:</label> <input type="text"
					id="cognome" name="cognome" value="<%=utente.getCognome()%>"
					readonly>
			</div>
			<div class="form-group">
				 <!-- Menu a tendina per la selezione dell'indirizzo -->
        <label for="addressDropdown">Seleziona l'indirizzo:</label>
        <select id="addressDropdown" name="selectedAddress">
            <!-- Le opzioni verranno popolate dinamicamente -->
        </select>

			</div>
			<div class="form-group">
				<label for="numero">Numero di Telefono:</label> <input type="text"
					id="numero" name="numero" value="<%=utente.getTelefono()%>"
					readonly>
			</div>
			<%
			} else {
			%>
			<p>Utente non autenticato.</p>
			<%
			}
			%>

			<!-- Sezione metodo di pagamento -->
			<h2>Metodo di Pagamento</h2>
			<form action="ConfermaOrdineServlet" method="post">
				<div class="form-group">
					<input type="radio" id="contrassegno" name="metodoPagamento"
						value="Contrassegno" checked> <label for="contrassegno">Pagamento alla Consegna</label>
				</div>

				<!-- Sezione prodotti nel carrello -->
				<h2>Prodotti nel Carrello</h2>
				<div class="cart-items">
					<c:forEach var="prodotto" items="${carrello.prodotti}">
						<div class="cart-item">
							<span>${prodotto.nome} (x${prodotto.quantita})</span> <span>€${prodotto.subTotale}</span>
						</div>
					</c:forEach>
				</div>
		</div>

		<!-- Sezione laterale con il riepilogo e totale -->
		<div class="cart-summary">
			<h2>Riepilogo Ordine</h2>
			<div class="total-section">Totale: €${carrello.totale}</div>
			<button type="submit" class="confirm-button">Conferma Ordine</button>
			</form>
		</div>

	</div>
	   <script type="text/javascript">
        // Funzione per caricare gli indirizzi tramite l'email dell'utente
      function loadAddresses(email) {
    $.ajax({
        url: '${pageContext.request.contextPath}/Indirizzi', // Chiamata alla servlet Indirizzi.java
        type: 'POST', // Usiamo POST come specificato nella servlet
        data: { email: email }, // Inviamo l'email come parametro
        dataType: 'json',
        success: function(response) {
            var addresses = response.indirizzi;
            var select = $('#addressDropdown');
            select.empty();
            
            addresses.forEach(function(address) {
                var option = $('<option>', {
                    value: address.id,
                    text: address.via + ', ' + address.city + ', ' + address.provincia + ' ' + address.CAP
                });
                select.append(option);
            });
        },
        error: function(error) {
            console.log('Errore nel recupero degli indirizzi:', error.responseText); // Mostra il testo dell'errore
        }
    });
}

        // Carichiamo gli indirizzi quando la pagina viene caricata
        $(document).ready(function() {
            var userEmail = '<%= utente.getEmail() %>'; // Recuperiamo l'email dalla sessione JSP
            if (userEmail) {
                loadAddresses(userEmail); // Passiamo l'email alla funzione per caricare gli indirizzi
            } else {
                console.log('Email utente non disponibile');
            }
        });
    </script>
	<%@include file="/html/footer.html"%>
</body>
</html>