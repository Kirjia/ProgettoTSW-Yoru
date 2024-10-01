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
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>

<%@include file="/html/header.html" %>
<%@page import="java.util.Collection"%>
<% 

Collection<?> items = (Collection<?>) request.getAttribute("items");

if (items == null) {
	response.sendRedirect("../GetAllBook");
	return;
} %>
<!-- Product Section -->
<section class="section-products container mt-5">
    <h1 class="text-center mb-4">I nostri prodotti</h1>
    <div class="row">
        <!-- Itera sui prodotti passati dal servlet -->
        <c:forEach items="${items}" var="prodotto">
        <c:out value="${ordine}" />
   		 <div class="col-md-6 col-lg-4 col-xl-3 mb-4">
      	  <div class="single-product card">
            <!-- Product Image -->
            <div class="part-1">
                <img src="images/${prodotto.SKU}.jpg" alt="${prodotto.nome}" onerror="this.src='images/err.jpeg'">
            </div>
            <!-- Product Details -->
            <div class="part-2">
            	<div class="product-title-wrapper">
                <h3 class="product-title"><c:out value="${prodotto.nome}"/></h3>
                </div>
                

						<span>€<fmt:formatNumber value="${prodotto.prezzo}" minFractionDigits="2" maxFractionDigits="2" /></span>


							<!-- Add to Cart Button -->
                <button class="aggiungi-al-carrello" id="${prodotto.SKU}">Aggiungi al carrello</button>
            </div>
        </div>
    </div>
</c:forEach>

    </div>
</section>

<%@include file="/html/footer.html" %>

<!-- Bootstrap JS, Popper.js, and jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
