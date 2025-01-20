<%@page import="com.yoru.model.Entity.Prodotto"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>YORU - Products</title>
    <link rel="stylesheet" href="./css/Prodotti.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    
</head>
<body>

<%@include file="/header.jsp" %>
<%@page import="java.util.Collection"%>
<% 

Collection<?> items = (Collection<?>) request.getAttribute("items");

if (items == null) {
	response.sendRedirect("./GetAllGadget");
	return;
} %>
<!-- Product Section -->
<section class="section-products container mt-5" style="margin-top: 0px!important; padding-top: 0px !important;">
    <h1 class="text-center mb-4">I nostri gadget</h1>
    <div class="row">
        <!-- Itera sui prodotti passati dal servlet -->
        <c:forEach items="${items}" var="prodotto">
        <c:out value="${ordine}" />
   		 <div class="col-md-6 col-lg-4 col-xl-3 mb-4">
      	  <div class="single-product card">
           <!-- Product Image -->
            <div class="part-1">
                <!-- Link intorno all'immagine del prodotto -->
                <a href="Item?sku=${prodotto.SKU}">
                    <img  alt="${prodotto.nome}" onerror="this.src='files/images/err.png'" 
                    
                    src="files/images/${prodotto.SKU}.png" class="product-image <c:out value='${prodotto.quantità <= 0 ? "out-of-stock" : ""}'/>">
        
        <!-- Overlay per OUT OF STOCK se quantità <= 0 -->
        <c:if test="${prodotto.quantità <= 0}">
            <div class="out-of-stock-overlay">OUT OF STOCK</div>
        </c:if>
                </a>
            </div>
            <!-- Product Details -->
            <div class="part-2">
                <div class="product-title-wrapper">
                    <!-- Link intorno al nome del prodotto -->
                    <a href="Item?sku=${prodotto.SKU}">
                        <h3 class="product-title"><c:out value="${prodotto.nome}"/></h3>
                    </a>
                </div>

                <span>€<fmt:formatNumber value="${prodotto.prezzo}" minFractionDigits="2" maxFractionDigits="2" /></span>

 <!-- Add to Cart Button con disabilitazione -->
               <button 
    class="aggiungi-al-carrello" 
    id="${prodotto.SKU}"
    data-type="gadget"
    <c:choose>
        <c:when test="${prodotto.quantità <= 0}">
            disabled
        </c:when>
    </c:choose>
>
    Aggiungi al carrello
</button>
  
            </div>
        </div>
    </div>
</c:forEach>

    </div>
</section>


<% 
    int totalProducts = (int) request.getAttribute("counts");
    int itemsPerPage = 20;
    int totalPages = (int) Math.ceil((double) totalProducts / itemsPerPage);
    int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
%>

<c:set var="totalPages" value="<%= totalPages %>" />
<c:set var="currentPage" value="<%= currentPage %>" />

<!-- Pagination -->
<nav aria-label="Page navigation" class="mt-4">
    <ul class="pagination justify-content-center">
        <c:forEach var="i" begin="1" end="${totalPages}">
            <li class="page-item ${i == currentPage ? 'active' : ''}">
                <a class="page-link" href="GetAllGadeget?page=${i}">${i}</a>
            </li>
        </c:forEach>
    </ul>
</nav>



<!-- Modal per messaggio aggiunto al carrello -->
<div class="modal fade" id="cartModal" tabindex="-1" aria-labelledby="cartModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="cartModalLabel">Notifica</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Chiudi"></button>
            </div>
            <div class="modal-body">
                <!-- Il messaggio dinamico verrà inserito qui -->
            </div>
            <div class="modal-footer">
            					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Continua lo shopping</button>
					<a href="${pageContext.request.contextPath}/carrello.jsp"
						class="btn btn-primary">Vai al carrello</a>
						 </div>
        </div>
    </div>
</div>




<%@include file="/html/footer.html" %>

<!-- Bootstrap JS, Popper.js, and jQuery -->

<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/cart.js"></script>
</body>
</html>