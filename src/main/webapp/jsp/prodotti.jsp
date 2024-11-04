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
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

<%@include file="/jsp/header.jsp" %>
<%@page import="java.util.Collection"%>
<% 


Collection<?> items = (Collection<?>) request.getAttribute("items");

if (items == null) {
    response.sendRedirect("../GetAllBook");
    return;
} %>
<!-- Product Section -->
<section class="section-products container mt-5" style=" margin-top: 1rem !important;"><!-- override -->
    <h1 class="text-center mb-4" style="margin-bottom:50px !important;">I nostri libri</h1>
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
                    <img src="files/images/${prodotto.SKU}.jpg" alt="${prodotto.nome}" onerror="this.src='files/images/err.jpeg'">
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

                <!-- Add to Cart Button -->
                <button class="aggiungi-al-carrello" id="${prodotto.SKU}" >Aggiungi al carrello</button>
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
                <a class="page-link" href="GetAllBook?page=${i}">${i}</a>
            </li>
        </c:forEach>
    </ul>
</nav>








<%@include file="/html/footer.html" %>

<!-- Bootstrap JS, Popper.js, and jQuery -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/cart.js"></script>
</body>
</html>
