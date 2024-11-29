<%@page import="com.yoru.model.Entity.Gadgets"%>
<%@page import="com.yoru.model.Entity.Libro"%>
<%@page import="com.yoru.model.Entity.Autore"%>
<%@page import="java.util.*"%>
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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
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
        <%if(prodotto.getItemType()==Prodotto.ItemType.GADGET){%>
        <%Gadgets gadgets = (Gadgets) prodotto;
        List<String> materiali = gadgets.getMateriali();
        boolean isOutOfStock = gadgets.getQuantità() <= 0;
        %>
        <div class="col-md-5 text-center position-relative">
            <img src="files/images/<%= gadgets.getSKU() %>.png" 
                 class="product-img img-fluid <% if (isOutOfStock) { %> out-of-stock <% } %>" 
                 alt="${prodotto.nome}" 
                 onerror="this.src='files/images/err.png'">
                 
            <% if (isOutOfStock) { %>
                <div class="out-of-stock-overlay">Out of Stock</div>
            <% } %>
        </div>

        <!-- Sezione informazioni prodotto -->
        <div class="col-md-5">
            <h2 class="titolo-prodotto"><%=gadgets.getNome()%></h2>
            <p class="prezzo-prodotto">€<fmt:formatNumber value="<%=gadgets.getPrezzo() %>" minFractionDigits="2" maxFractionDigits="2" /></p>


            <p><b>Disponibilità:</b> <%= gadgets.getQuantità()>0 ? "Disponibile" : "Non disponibile" %></p>
            
            
            
            <p><b>Categoria:</b> <%= gadgets.getItemType() %></p>
            <p><b>Modello:</b>	<%= gadgets.getModello() %></p>
			<p><b>Marchio:</b>  <%= gadgets.getMarchio() %></p>
			
			<p><b>Materiale:</b>
			<%    for (String materiale : materiali) { %>
			 <%=materiale%> 
			<%} %></p>
            <form id="addToCart">
                <input type="hidden" id="sku" name="productId" value="<%= gadgets.getSKU() %>">
                
                
                
				<p><b>Quantità:</b></p>
               <div class="d-flex align-items-center">
   				 
   				 <input type="number" id="quantity" name="quantity" class="form-control quantity-box me-2" min="1" value="1"> 
   				    				  <% if (isOutOfStock) { %>
                <button type="submit" class="btn btn-primary aggiungi-al-carrello" disabled><i class="bi bi-cart-plus"></i></button>
            <% }else{ %>
   				<button type="submit" class="btn btn-primary aggiungi-al-carrello"><i class="bi bi-cart-plus"></i></button>
   				<%} %>
				</div>


                <!-- Pulsanti per aggiungere al carrello o comprare subito -->
               
            </form>

            <!-- Descrizione del prodotto -->
            <div class="descrizione-prodotto">
                <h4>Descrizione</h4>
                <p><%= prodotto.getDescrizione() %></p>
            </div>
        </div>
        <%}else {%>
        <%Libro libro = (Libro) prodotto; 
        List<Autore> autori = libro.getAutori();
        boolean isOutOfStock = libro.getQuantità() <= 0;
        %>
        <div class="col-md-5 text-center position-relative">
            <img src="files/images/<%= libro.getSKU() %>.png" 
                 class="product-img img-fluid <% if (isOutOfStock) { %> out-of-stock <% } %>" 
                 alt="${prodotto.nome}" 
                 onerror="this.src='files/images/err.jpeg'">
                 
            <% if (isOutOfStock) { %>
                <div class="out-of-stock-overlay">Out of Stock</div>
            <% } %>
        </div>

        <!-- Sezione informazioni prodotto -->
        <div class="col-md-5">
            <h2 class="titolo-prodotto"><%=libro.getNome()%></h2>
            <p class="prezzo-prodotto">€<fmt:formatNumber value="<%=libro.getPrezzo() %>" minFractionDigits="2" maxFractionDigits="2" /></p>

            <p><b>Disponibilità:</b> <%= libro.getQuantità()>0 ? "Disponibile" : "Non disponibile" %></p>
            <p><b>Categoria:</b> <%= libro.getItemType() %></p>
			<p><b>Lingua:</b><%=libro.getLingua()%>&nbsp&nbsp<b>Pagine:</b><%=libro.getNumeroPagine()%></p>
			
			
			<p><b>Autori:</b>
			<%for (Autore autore : autori){ %>
			 &nbsp<%=autore.getCognome()%>
			<%} %>
			</p>
			<p><b>ISBN:</b><%=libro.getISBN()%></p>
            <form id="addToCart">
                <input type="hidden" id="sku" name="productId" value="<%= libro.getSKU() %>">
                
                
				<p><b>Quantità:</b></p>
               <div class="d-flex align-items-center">
   				 
   				 <input type="number" id="quantity" name="quantity" class="form-control quantity-box me-2" min="1" value="1"> 
   				  <% if (isOutOfStock) { %>
                <button type="submit" class="btn btn-primary aggiungi-al-carrello" disabled><i class="bi bi-cart-plus"></i></button>
            <% }else{ %>
   				<button type="submit" class="btn btn-primary aggiungi-al-carrello"><i class="bi bi-cart-plus"></i></button>
   				<%} %>
				</div>


                <!-- Pulsanti per aggiungere al carrello o comprare subito -->
               
            </form>

            <!-- Descrizione del prodotto -->
            <div class="descrizione-prodotto">
                <h4>Descrizione</h4>
                <p><%= libro.getDescrizione() %></p>
            </div>
        </div>
        <%} %>
    </div>
</div>

    <%@ include file="/html/footer.html" %>

    <!-- Bootstrap JS, Popper.js, and jQuery -->
   <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
   <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
   <script src="${pageContext.request.contextPath}/js/item.js"></script>

</body>
</html>
