<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.yoru.model.Entity.Prodotto"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dettaglio Prodotto</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/Item.css" rel="stylesheet">
   
   
</head>
<body>

    <%@ include file="/header.jsp" %>
<%@page import="java.util.Collection"%>
<% 

Prodotto prodotto = (Prodotto) request.getAttribute("item");
if (prodotto == null) {
	response.sendRedirect("./GetItem");
	return;
}
%>
<div class="container dettagli-prodotto">
    <div class="row justify-content-center">
        <!-- Sezione immagine del prodotto -->
        <div class="col-md-5 text-center"> <!-- Riduciamo la larghezza della colonna e centriamo -->
            <img src="files/images/<%=prodotto.getSKU() %>.jpg" class="product-img img-fluid" alt="${prodotto.nome}" onerror="this.src='files/images/err.jpeg'">
        </div>

        <!-- Sezione informazioni prodotto -->
        <div class="col-md-5">
            <h2 class="titolo-prodotto"><c:out value="${prodotto.nome}"/></h2>
            <p class="prezzo-prodotto">€<fmt:formatNumber value="<%=prodotto.getPrezzo() %>" minFractionDigits="2" maxFractionDigits="2" /></p>

            <p><b>Disponibilità:</b> <%= prodotto.getQuantità()>0 ? "Disponibile" : "Non disponibile" %></p>
            <p><b>Categoria:</b> <%= prodotto.getItemType() %></p>

            <form action="CartServlet" method="POST">
                <input type="hidden" name="productId" value="<%= prodotto.getSKU() %>">

                <!-- Quantità -->
                <label for="quantity">Quantità:</label>
                <input type="number" id="quantity" name="quantity" class="form-control quantity-box" min="1" value="1">

                <!-- Pulsanti per aggiungere al carrello o comprare subito -->
                <button type="submit" class="btn btn-primary btn-block mt-3">Aggiungi al carrello</button>
                <button type="submit" class="btn buy-btn btn-block mt-3">Compra Subito</button>
            </form>

            <!-- Descrizione del prodotto -->
            <div class="descrizione-prodotto">
                <h4>Descrizione</h4>
                <p><%= prodotto.getDescrizione() %></p>
            </div>
        </div>
    </div>
</div>

    <%@ include file="/html/footer.html" %>

    <!-- Bootstrap JS, Popper.js, and jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
