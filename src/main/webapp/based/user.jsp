<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.yoru.model.Entity.User"%>
<%@ page import="com.yoru.model.Entity.Order"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Profilo Utente</title>

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/User.css" rel="stylesheet">

<!-- Google Fonts -->
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">


<%
Collection<?> ordini = (Collection<?>) request.getAttribute("historyOrders");
if (ordini == null) {
    response.sendRedirect("../based/StoricoOrdini");
    return;
}
%>
</head>
<body>

    <%@ include file="/header.jsp"%>
	<%@ include file="/html/addAdressM.html" %>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-lg-8 col-md-10 col-sm-12">
                <!-- User Info Card -->
               <div class="profile-card p-3 mb-4 border rounded shadow-sm">
    <div class="d-flex justify-content-between align-items-center">
        <h4><b>Profilo Utente</b></h4>
        <!-- Pulsante per aprire il modale -->
        <button class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#editProfileModal">
            Modifica Dati
        </button>
    </div>
    <hr>
    <p><b>Nome:</b> <%=user.getNome()%></p>
    <p><b>Cognome:</b> <%=user.getCognome()%></p>
    <p><b>Email:</b> <%=user.getEmail()%></p>
    <p>
        <b>Telefono:</b> 
        <span id="telefono-value"><%=user.getTelefono()%></span>
    </p> 
    <button class="btn btn-primary" id="open-modal">Aggiungi Indirizzo</button>              
    <div id="indirizzi-container"></div>
</div>

<!-- Modale per modificare i dati -->
<div class="modal fade" id="editProfileModal" tabindex="-1" aria-labelledby="editProfileModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editProfileModalLabel">Modifica Profilo</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="editProfileForm">
                    <div class="mb-3">
                        <label for="telefono" class="form-label">Nuovo Numero di Telefono</label>
                        <input type="text" class="form-control" id="telefono" placeholder="Inserisci il nuovo numero" value="<%=user.getTelefono()%>">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Nuova Password</label>
                        <input type="password" class="form-control" id="password" placeholder="Inserisci la nuova password">
                    </div>
                    <div class="mb-3">
                        <label for="confirm-password" class="form-label">Conferma Password</label>
                        <input type="password" class="form-control" id="confirm-password" placeholder="Conferma la nuova password">
                    </div>
                    <button class="btn btn-primary w-100">Salva Modifiche</button>
                </form>
            </div>
        </div>
    </div>
</div>


                <!-- Order History Section -->
                <div class="order-history p-3 mb-4 border rounded shadow-sm">
                    <h4 class="mt-4" ><b><a name="cronologia">La tua Cronologia Ordini</a></b></h4>
                    <hr>
                    <c:forEach items="${historyOrders}" var="ordine">
                        <div class="order-card p-3 mb-3 border rounded shadow-sm">
                            <div class="row">
                                <div class="col-md-6">
                                    <p><b>ID Ordine:</b> ${ordine.id}</p>
                                    <p><b>Data:</b> ${ordine.dataPagamento}</p>
                                </div>
                                <div class="col-md-6 text-md-right text-left">
                                    <p><b>Totale Pagato:</b> €<fmt:formatNumber value="${ordine.costoTotOrdine}" minFractionDigits="2" maxFractionDigits="2" /></p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <p><b>Prodotti:</b></p>
                                    <ul>
                                        <c:forEach var="item" items="${ordine.orderItemList}">
                                            <li>${item.nome} - Quantità: ${item.quantity}</li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>

 <%@ include file="/html/footer.html"%>


<!-- Bootstrap JS, Popper.js, and jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/user.js" type="text/javascript"></script>

</body>
</html>
