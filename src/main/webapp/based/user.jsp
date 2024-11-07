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
<link
    href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
    rel="stylesheet">
<link
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
    rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/User.css"
    rel="stylesheet">

<!-- Bootstrap JS, Popper.js, and jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script
    src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script
    src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    

<%
Collection<?> ordini = (Collection<?>) request.getAttribute("historyOrders");
if (ordini == null) {
    response.sendRedirect("../StoricoOrdini");
    return;
}
%>
</head>
<body>

    <%@ include file="/header.jsp"%>

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-lg-8 col-md-10 col-sm-12">
                <!-- User Info Card -->
                <div class="profile-card p-3 mb-4 border rounded">
                    <h4><b>Profilo Utente</b></h4>
                    <hr>
                    <p>
                        <b>Nome:</b> <%=user.getNome()%>
                    </p>
                    <p>
                        <b>Cognome:</b> <%=user.getCognome()%>
                    </p>
                    <p>
                        <b>Email:</b> <%=user.getEmail()%>
                    </p>
                    <p>
                        <b>Telefono:</b> 
                        <span id="telefono-value"><%=user.getTelefono()%></span>
                        <i class="fas fa-edit" style="cursor: pointer;" id="edit-telefono"></i>
                    </p>
                    <div id="indirizzi-container"></div>
                </div>

                <!-- Order History Section -->
                <div class="order-history p-3 mb-4 border rounded">
                    <h4 class="mt-4"><b>La tua Cronologia Ordini</b></h4>
                    <hr>
                    <c:forEach items="${historyOrders}" var="ordine">
                        <div class="order-card p-3 mb-3">
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

<script>
    $(document).ready(function() {
        var emailUtente = '<%=user.getEmail()%>';

        $.ajax({
            url: '${pageContext.request.contextPath}/Indirizzi',
            method: 'POST',
            data: { email: emailUtente },
            dataType: 'json',
            success: function(response) {
                console.log(response);
                let indirizziHTML = '<h5>Indirizzi:</h5> <ul>';
                if (response.indirizzi && response.indirizzi.length > 0) {
                    $.each(response.indirizzi, function(index, indirizzo) {
                        indirizziHTML += '<li>' + indirizzo.via + ', ' + indirizzo.city + ' - ' + indirizzo.CAP + '</li>';
                    });
                } else {
                    indirizziHTML += '<li>Nessun indirizzo disponibile.</li>';
                }
                indirizziHTML += '</ul>';
                $('#indirizzi-container').html(indirizziHTML);
            },
            error: function() {
                $('#indirizzi-container').html('<p>Errore nel caricamento degli indirizzi.</p>');
            }
        });

        // Gestione modifica numero di telefono
        $('#edit-telefono').click(function() {
            var currentPhone = $('#telefono-value').text();
            var newPhone = prompt("Inserisci il nuovo numero di telefono:", currentPhone);
            if (newPhone !== null && newPhone !== "") {
                $('#telefono-value').text(newPhone);
                // Qui potresti aggiungere una chiamata AJAX per salvare il nuovo numero
            }
        });
    });
</script>

    <%@ include file="/html/footer.html"%>

</body>
<style>
	.mt-4, .my-4{
		margin-top:0px !important;
	}
</style>

</html>
