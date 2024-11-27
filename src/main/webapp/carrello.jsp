<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

    <%@ include file="/header.jsp" %>

    <!-- Contenitore principale -->
    <div class="container mt-5">
        <div class="card">
            <div class="row">
                <!-- Sezione Carrello -->
                <div class="col-md-8 cart">
                    <div class="title mb-4">
                        <h4 class="text-center"><b>Il tuo carrello</b></h4>
                    </div>
                    <div id="cart-slot" class="cart-items">
                        <!-- Elementi del carrello caricati dinamicamente -->
                    </div>
                </div>

                <!-- Sezione Riepilogo Ordine -->
                <div class="col-md-4 summary">
                    <h5><b>Riepilogo ordine</b></h5>
                    <hr>
                    <div class="row">
                        <div class="col">Totale:</div>
                        <div id="cart_tot" class="col text-right">€0.00</div>
                    </div>
                     <a href="${pageContext.request.contextPath}/based/checkout.jsp">
                    <button class="btn btn-dark btn-block mt-4" id="proceed-to-checkout">Procedi all'acquisto</button>
                    </a>
                    <a href="${pageContext.request.contextPath}/prodotti.jsp" class="btn btn-light btn-block mt-2">Torna allo shopping</a>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="/html/footer.html" %>

    <!-- Bootstrap JS, Popper.js, and jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cart.js"></script>

</body>
</html>
