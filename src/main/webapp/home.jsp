<%@page import="com.yoru.model.Entity.Prodotto"%>
<%@page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carosello Prodotti</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="./css/carousel.css">
</head>
<body>
    <%@include file="/header.jsp"%>

    <%
        Collection<?> items = (Collection<?>) request.getAttribute("bestsellers");
        if (items == null) {
            response.sendRedirect(request.getContextPath() + "/BestSellers");
            return;
        }
    %>

    <!-- Carosello Prodotti -->
    <section class="container mt-5">
        <h2 class="text-center mb-4">Prodotti in Evidenza</h2>
        <div id="carouselProdotti" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
                <c:forEach items="${bestsellers}" var="prodotto" varStatus="status">
                    <div class="carousel-item ${status.first ? 'active' : ''}">
                        <div class="d-flex align-items-center carousel-content">
                            <!-- Copertina -->
                            <div class="col-4 image-container">
                                <img class="img-fluid" src="files/images/${prodotto.SKU}.png" alt="${prodotto.nome}" onerror="this.src='files/images/err.png'">
                            </div>
                            <!-- Dettagli Prodotto -->
                            <div class="col-8 product-details">
                                <h3 class="product-title">${prodotto.nome}</h3>
                                <hr>
                                <div class="price-and-cart">
                                    <p class="price">€<fmt:formatNumber value="${prodotto.prezzo}" minFractionDigits="2" maxFractionDigits="2"/></p>
                                    <button class="btn btn-primary">Aggiungi al Carrello</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <!-- Frecce di navigazione -->
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselProdotti" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselProdotti" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </section>

    <%@include file="/html/footer.html"%>
<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
