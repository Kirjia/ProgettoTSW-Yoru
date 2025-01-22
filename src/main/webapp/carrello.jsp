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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
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
                     <a href="${pageContext.request.contextPath}/based/checkout.jsp" rel="noopener noreferrer">
                    <button class="btn btn-dark btn-block mt-4" id="proceed-to-checkout">Procedi all'acquisto</button>
                    </a>
                    <a href="${pageContext.request.contextPath}/prodotti.jsp" rel="noopener noreferrer" class="btn btn-light btn-block mt-2">Torna allo shopping</a>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="/html/footer.html" %>

    <!-- Bootstrap JS, Popper.js, and jQuery -->
   <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/js/cart.js"></script>

</body>
</html>
