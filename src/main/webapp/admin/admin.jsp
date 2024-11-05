<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.yoru.model.Entity.User"%>
<%@ page import="com.yoru.model.Entity.Prodotto"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Pannello Admin</title>

<!-- Bootstrap CSS -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/Admin.css"
	rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />

<%
User admin = (User) session.getAttribute("user");
if (admin == null) {
	response.sendRedirect("./login.jsp");
	return;
}
%>
</head>
<body>

	<%@ include file="/header.jsp"%>
	<div class="container mt-5" style="margin-top: 1rem !important;">

		<div class="row justify-content-center">
			<!-- Sidebar menu
            <div class="col-md-3 mb-4">
                <div class="list-group text-center">
                    <a href="#" class="list-group-item list-group-item-action">Gestione Utenti</a>
                    <a href="#" class="list-group-item list-group-item-action active">Gestione Prodotti</a>
                    <a href="#" class="list-group-item list-group-item-action">Gestione Ordini</a>
                    <a href="${pageContext.request.contextPath}/Logout" class="list-group-item list-group-item-action">Logout</a>
                </div>
            </div> -->

			<div class="col-md-9 text-center">
				<!-- Admin Info Card -->
				<div class="profile-card mb-4 mx-auto">

					<h4>
						<b>Pannello Admin</b>
					</h4>

					<p>
						<b>Nome Admin:</b>
						<%=admin.getNome()%></p>
					<p>
						<b>Email:</b>
						<%=admin.getEmail()%></p>
				</div>

				<!-- Gestione utenti 
                <div class="user-management mb-4">
                <hr>
                    <h4><b>Gestione Utenti</b></h4>
                    
                    <button class="btn btn-success mb-3" id="management-user-btn">Gestione utenti</button>
                </div>-->

				<!-- Product Management Section -->
				<div class="product-management mb-4">
					<hr>
					<h4>
						<b>Gestione Prodotti</b>
					</h4>

					<button class="btn btn-success mb-3" id="add-product-btn">Aggiungi
						Nuovo Prodotto</button>
					<a href="../jsp/GestioneProdotti.jsp" class="btn btn-success mb-3">Visualizza
						Prodotti</a>
				</div>

				<!-- Gestione ordini -->
				<div class="order-management mb-4">
					<hr>
					<h4>
						<b>Gestione Ordini</b>
					</h4>

					<button class="btn btn-success mb-3" id="management-order-btn">Gestione
						Ordini</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Modale per Aggiungere un Nuovo Prodotto -->
	<div class="modal fade" id="addProductModal" tabindex="-1"
		role="dialog" aria-labelledby="addProductModalLabel"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="addProductModalLabel">Aggiungi
						Nuovo Prodotto</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="add-product-form">
						<div class="form-group">
							<label for="nome">Nome</label> 
							<input type="text" class="form-control" id="nome"
								placeholder="Inserisci il nome del libro o gadget" required>
						</div>
						<div class="form-group">
							<label for="prezzo">Prezzo</label> 
							<input type="number" class="form-control" id="prezzo"
								placeholder="Inserisci il prezzo del prodotto" required>
						</div>
						<div class="form-group">
							<label for="quantity">quantity</label> 
							<input type="number" class="form-control" id="quantity"
								placeholder="Inserisci la quantità di prodotti presenti" required>
						</div>
						<div class="form-group-editor">
							 <!-- Menu a tendina per la selezione della casa editrice -->
		   					<label for="editorDropdown">Seleziona la casa editrice:</label>
		   					<select id="editorDropdown" name="selectededitor">
		   					<option value="">-- Seleziona una casa editrice --</option>
		   					<!-- Le opzioni verranno popolate dinamicamente -->
		   
						   </select>
					   </div>
					   
						<div class="form-group">
							<label for="product-type">Tipo Prodotto</label> <select
								class="form-control" id="product-type" required>
								<option value="" disabled selected>Seleziona un tipo di
									prodotto</option>
								<option value="libro">Libro</option>
								<option value="gadget">Gadget</option>
							</select>
						</div>
						<div id="additional-fields"></div>
						<div class="form-group">
					        <label for="description">Descrizione</label>
					        <textarea class="form-control" id="description" name="description" rows="4" placeholder="Inserisci la descrizione del prodotto"></textarea>
				    	</div>
						<div class="mb-3">
							<label for="formFileSm" class="form-label">Inserisci una cover</label>
							<input class="form-control form-control-sm" id="formFileSm" type="file">
						</div>
						<button type="submit" class="btn btn-primary">Aggiungi</button>
					</form>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="/html/footer.html"%>

	<!-- Bootstrap JS, Popper.js, and jQuery -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/admin.js"></script>


	

</body>
</html>
