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
        <%if(prodotto.getItemType()==Prodotto.ItemType.GADGET){%>
        <%Gadgets gadgets = (Gadgets) prodotto;
        List<String> materiali = gadgets.getMateriali();
        %>
        <div class="col-md-5 text-center"> <!-- Riduciamo la larghezza della colonna e centriamo -->
            <img src="files/images/<%=gadgets.getSKU() %>.jpg" class="product-img img-fluid" alt="${prodotto.nome}" onerror="this.src='files/images/err.jpeg'">
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
            <form action="CartServlet" method="POST">
                <input type="hidden" name="productId" value="<%= prodotto.getSKU() %>">
                
                
				<p><b>Quantità:</b></p>
               <div class="d-flex align-items-center">
   				 
   				 <input type="number" id="quantity" name="quantity" class="form-control quantity-box me-2" min="1" value="1"> 
   				 <button type="submit" class="btn btn-primary"><i class="bi bi-cart-plus"></i></button>
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
        %>
          <div class="col-md-5 text-center"> <!-- Riduciamo la larghezza della colonna e centriamo -->
            <img src="files/images/<%=libro.getSKU() %>.jpg" class="product-img img-fluid" alt="${prodotto.nome}" onerror="this.src='files/images/err.jpeg'">
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
   				 <button type="submit" class="btn btn-primary aggiungi-al-carrello"><i class="bi bi-cart-plus"></i></button>
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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/item.js"></script>

</body>
</html>
