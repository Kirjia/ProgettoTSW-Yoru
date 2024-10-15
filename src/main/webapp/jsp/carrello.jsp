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
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>YORU - Carrello</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/Cart.css" rel="stylesheet">

</head>
<body>

    <%@ include file="/html/header.html" %>

    <%      

        Collection<?> carrello = (Collection<?>) request.getAttribute("carrello");
        if(carrello == null){
            response.sendRedirect("../Carrello");
            return;
        }

    %>
    <div class="container mt-5">
        <div class="card">
            <div class="row">
                <!-- Sezione Carrello -->
                <div class="col-md-8 cart">
                    <div class="title">
                        <h4><b>Il tuo carrello</b></h4>
                    </div>

                    <c:choose>
                        <c:when test="${empty carrello}">
                            <p class="empty-carrello">Il carrello è vuoto!</p>
                        </c:when>
                        <c:otherwise>
                            <div class="cart-items">
                                <c:set var="totale" value="0" scope="page"/>
                                <c:forEach var="p" items="${carrello}">
                                    <!-- Calcola il subtotale -->
                                    <c:set var="subtotale" value="${p.prezzo * p.quantity}" scope="page" />
                                    <c:set var="totale" value="${totale + subtotale}" scope="page" />
                                    
                                    <div class="row border-bottom">
                                        <div class="col-md-4">
                                            <img src="images/${p.SKU}.jpg" class="img-fluid" alt="${p.nome}" onerror="this.src='images/placeholder.jpg'">
                                        </div>
                                        <div class="col-md-5">
                                            <h5>${p.nome}</h5>
                                            <p>Quantità: ${p.quantity}</p>
                                        </div>
                                        <div class="col-md-3 d-flex align-items-end justify-content-end">
                                        
                                      	  <div class="text-right mt-3">
											<button class="btn btn-remove">Rimuovi dal carrello</button>
										  </div>
                                        
    										<p class="text-right subtotale-item">subtotale: €<fmt:formatNumber value="${subtotale}" minFractionDigits="2" maxFractionDigits="2" /></p>
    											
										</div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Sezione Riepilogo Ordine -->
                <div class="col-md-4 summary">
                    <h5><b>Riepilogo ordine</b></h5>
                    <hr>
                    <div class="row">
                        <div class="col">Totale</div>
                        <div class="col text-right">€<fmt:formatNumber value="${totale}" minFractionDigits="2" maxFractionDigits="2" /></div> <!-- Mostra il totale corretto -->
                    </div>
                    <a href="${pageContext.request.contextPath}/jsp/checkout.jsp">
                    <button class="btn btn-dark btn-block mt-4">Procedi all'acquisto</button>
                    </a>
                    <a href="${pageContext.request.contextPath}/jsp/prodotti.jsp" class="btn btn-light btn-block mt-2">Torna allo shopping</a>
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
