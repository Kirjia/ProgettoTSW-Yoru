<%@page import="com.yoru.model.Entity.Prodotto"%>
<%@page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>YORU - Carosello Prodotti</title>
    
    <!-- Bootstrap CSS -->
    <!-- Bootstrap JS, Popper.js, and jQuery -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
	    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="./css/home.css">
</head>
<body>

<%@include file="/html/header.html" %>

<%
Collection<?> items = (Collection<?>) request.getAttribute("bestsellers");
if (items == null) {
    response.sendRedirect("../BestSellers");
    return;
}
%>

<!-- Carosello Prodotti -->
<section class="container mt-5">
    <h2 class="text-center">Prodotti in Evidenza</h2>
    <div id="carouselProdotti" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-inner" role="listbox">
            <c:forEach items="${bestsellers}" var="prodotto" varStatus="status">
                <div class="carousel-item <c:if test='${status.first}'>active</c:if>">
                    <div class="col-md-3">
                        <div class="card">
                        <a href="Item?sku=${prodotto.SKU}">
                            <img class="card-img-top img-fluid" src="${pageContext.request.contextPath}/images/${prodotto.SKU}.jpg" alt="${prodotto.nome}" onerror="this.src='images/err.jpeg'">
                        </a>
                            <div class="card-body text-center">
                            	<a href="Item?sku=${prodotto.SKU}">
                                	<h5 class="card-title">${prodotto.nome}</h5>
                                </a>
                                <p class="card-text">€<fmt:formatNumber value="${prodotto.prezzo}" minFractionDigits="2" maxFractionDigits="2" /></p>
                                <button class="btn btn-primary">Aggiungi al Carrello</button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- Frecce per scorrere il carosello -->
        <a class="carousel-control-prev" href="#carouselProdotti" role="button" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselProdotti" role="button" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </a>
    </div>
</section>



<%@include file="/html/footer.html" %>

<!-- #Bootstrap JS, Popper.js, and jQuery--> 
<script> 

let items = document.querySelectorAll('.carousel .carousel-item')

items.forEach((el) => {
    const minPerSlide = 3
    let next = el.nextElementSibling
    for (var i = 1; i < minPerSlide; i++) {
        if (!next) {
            next = items[0] // wrap carousel
        }
        let cloneChild = next.cloneNode(true)
        el.appendChild(cloneChild.children[0])
        next = next.nextElementSibling
    }
})
</script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>

</body>
</html>
