<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>YORU - Checkout</title>

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
                <% 
                    User utente = (User) session.getAttribute("user");
                   
                %>
                <!-- Sezione Dati Utente e Pagamento -->
                <div class="col-md-8 cart">
                    <div class="title mb-4">
                        <h4 class="text-center"><b>Dati Utente e Pagamento</b></h4>
                    </div>
                    <!-- Dati Utente -->
                    <div class="user-details mb-4">
                        <h5>Informazioni Utente</h5>
                        <p><b>Nome:</b> <%=utente.getNome()%> <%=utente.getCognome()%></p>
                        <p><b>Email:</b> <%=utente.getEmail()%></p>
                        <p><b>Numero:</b> <%=utente.getTelefono() %></p>
                    </div>

                    <!-- Indirizzi -->
                    <div class="user-addresses mb-4">
                        <h5>Indirizzi</h5>
                        <select id="addressDropdown" class="select-custom" name="selectedAddress">
                            <!-- Le opzioni verranno popolate dinamicamente -->
                        </select>
                    </div>
                </div>
                

                <!-- Sezione Riepilogo Ordine -->
                <div class="col-md-4 summary">
                    <h5><b>Riepilogo Ordine</b></h5>
                    <hr>
                    <div id="order-summary" class="order-items">
                        <!-- Elementi dell'ordine caricati dinamicamente -->
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col">Totale:</div>
                        <div id="total-amount" class="col text-right"></div>
                    </div>
                    <button class="btn btn-dark btn-block mt-4" id="confirm-payment">Conferma Pagamento</button>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Bootstrap Modal -->
<div class="modal fade" id="redirectModal" tabindex="-1" aria-labelledby="redirectModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="redirectModalLabel">Informazione</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p id="modalMessage">Grazie per aver completato il modulo!</p>
                <p id="modalCountdown">Verrai reindirizzato tra <span id="countdownTimer"></span> secondi...</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
            </div>
        </div>
    </div>
</div>
    

    <%@ include file="/html/footer.html" %>

    <!-- Bootstrap JS, Popper.js, and jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/js/checkout.js" type="text/javascript"></script>

    
</body>
</html>
