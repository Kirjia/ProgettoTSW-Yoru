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
//User user = (User) session.getAttribute("user");
Collection<?> ordini = (Collection<?>) request.getAttribute("historyOrders");
if (ordini == null) {
	response.sendRedirect("../StoricoOrdini");
	return;
}
%>
</head>
<body>

	<%@ include file="/jsp/header.jsp"%>

	<div class="container mt-5">
		<div class="row">
			<div class="col-md-3">
				<!-- Sidebar menu -->
				<div class="list-group">
					<a href="#" class="list-group-item list-group-item-action">Profilo
						utente</a> <a href="#" class="list-group-item list-group-item-action">Nuovi
						ordini</a> <a href="#" class="list-group-item list-group-item-action">Cronologia
						ordini</a> <a href="#" class="list-group-item list-group-item-action">Impostazioni
						profilo</a> <a href="${pageContext.request.contextPath}/Logout"
						class="list-group-item list-group-item-action">Logout</a>
				</div>
			</div>

			<div class="col-md-9">
				<!-- User Info Card -->
				<div class="profile-card">
					<h4>
						<b>Profilo Utente</b>
					</h4>
					<hr>
					<p>
						<b>Nome:</b>
						<%=user.getNome()%>
						<i class="fas fa-edit" style="cursor: pointer;"></i>
					</p>
					<p>
						<b>Cognome:</b>
						<%=user.getCognome()%>
						<i class="fas fa-edit" style="cursor: pointer;"></i>
					</p>
					<p>
						<b>Email:</b>
						<%=user.getEmail()%>
						<i class="fas fa-edit" style="cursor: pointer;"></i>
					</p>
					<p>
						<b>Telefono:</b>
						<%=user.getTelefono()%>
						<i class="fas fa-edit" style="cursor: pointer;"></i>
					</p>
					<!-- Contenitore per gli indirizzi dell'utente -->
<div id="indirizzi-container"></div>
				</div>

				<!-- Order History Section -->
				<div class="order-history" id=cronologia>
					<h4 class="mt-4">
						<b>La tua Cronologia Ordini</b>
					</h4>
					<hr>
					<c:forEach items="${historyOrders}" var="ordine">
						<div class="order-card">
							<div class="row">
								<div class="col-md-6">
									<p>
										<b>ID Ordine:</b> ${ordine.id}
									</p>
									<p>
										<b>Data:</b> ${ordine.dataPagamento}
									</p>
								</div>
								<div class="col-md-6 text-right">
									<p>
										<b>Totale Pagato:</b> €
										<fmt:formatNumber value="${ordine.costoTotOrdine}"
											minFractionDigits="2" maxFractionDigits="2" />
									</p>


									<button class="btn btn-info">Traccia Ordine</button>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<p>
										<b>Prodotti:</b>
									</p>
									<ul>
										<c:forEach var="item" items="${ordine.orderItemList}">
											<li>${item.nome}- Quantità: ${item.quantity}</li>
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
            url: '${pageContext.request.contextPath}/Indirizzi',  // URL corretto della servlet
            method: 'POST',
            data: { email: emailUtente },  // Invia l'email come parametro
            dataType: 'json',
            success: function(response) {
                // Log del JSON ricevuto per il debug
                console.log(response);

                let indirizziHTML = '<h5>Indirizzi:</h5> ';

                // Verifica che la risposta contenga la chiave "indirizzi"
                if (response.indirizzi && response.indirizzi.length > 0) {
                    // Cicla attraverso l'array "indirizzi" per costruire la lista
                    $.each(response.indirizzi, function(index, indirizzo) {
                        indirizziHTML += '<li>' + indirizzo.via + ', ' + indirizzo.city + ' - ' + indirizzo.CAP + '</li>';
                    });
                } else {
                    indirizziHTML += '<li>Nessun indirizzo disponibile.</li>';
                }

                indirizziHTML += '</ul>';

                // Inserisci la lista degli indirizzi nel div appropriato
                $('#indirizzi-container').html(indirizziHTML);
            },
            error: function() {
                $('#indirizzi-container').html('<p>Errore nel caricamento degli indirizzi.</p>');
            }
        });
    });
</script>


	
	<%@ include file="/html/footer.html"%>

	

</body>
</html>