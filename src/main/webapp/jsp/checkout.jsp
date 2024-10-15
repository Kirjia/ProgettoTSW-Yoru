<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.yoru.model.Entity.Prodotto"%>
<%@ page import="com.yoru.model.Entity.CartItem"%>
<%@ page import="com.yoru.model.Entity.User"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
    <link rel="stylesheet" href="../css/Checkout.css">
</head>
<body>
	<%@include file="/html/header.html"%>
	<div class="container">

    <!-- Sezione dettagli utente -->
    <div class="order-details">
        <h2>Dati dell'Utente</h2>

        <%
            User utente = (User) session.getAttribute("user");
            if (utente != null) {
        %>
        <div class="form-group">
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" value="<%= utente.getNome() %>" readonly>
        </div>
        <div class="form-group">
            <label for="cognome">Cognome:</label>
            <input type="text" id="cognome" name="cognome" value="<%= utente.getCognome() %>" readonly>
        </div>
        <div class="form-group">
            <label for="indirizzo">Indirizzo:</label>
            <input type="text" id="indirizzo" name="indirizzo" value="<%= utente.getEmail() %>" readonly>
        </div>
        <div class="form-group">
            <label for="numero">Numero di Telefono:</label>
            <input type="text" id="numero" name="numero" value="<%= utente.getTelefono() %>" readonly>
        </div>
        <% } else { %>
        <p>Utente non autenticato.</p>
        <% } %>

        <!-- Sezione metodo di pagamento -->
        <h2>Metodo di Pagamento</h2>
        <form action="ConfermaOrdineServlet" method="post">
            <div class="form-group">
                <input type="radio" id="contrassegno" name="metodoPagamento" value="Contrassegno" checked>
                <label for="contrassegno">Pagamento alla Consegna</label>
            </div>
            <div class="form-group">
                <input type="radio" id="bonifico" name="metodoPagamento" value="Bonifico">
                <label for="bonifico">Paga con Bonifico</label>
            </div>

            <!-- Sezione prodotti nel carrello -->
            <h2>Prodotti nel Carrello</h2>
            <div class="cart-items">
                <c:forEach var="prodotto" items="${carrello.prodotti}">
                    <div class="cart-item">
                        <span>${prodotto.nome} (x${prodotto.quantita})</span>
                        <span>€${prodotto.subTotale}</span>
                    </div>
                </c:forEach>
            </div>
    </div>

    <!-- Sezione laterale con il riepilogo e totale -->
    <div class="cart-summary">
        <h2>Riepilogo Ordine</h2>
        <div class="total-section">
            Totale: €${carrello.totale}
        </div>
        <button type="submit" class="confirm-button">Conferma Ordine</button>
        </form>
    </div>

</div>
	<%@include file="/html/footer.html" %>
</body>
</html>