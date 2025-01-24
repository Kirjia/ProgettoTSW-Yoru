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
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
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
	<div class="container mt-5">
    <div class="row">
        <div class="col-md-9 mx-auto">
            <!-- Admin Info -->
            <div class="profile-card text-center">
                <h4><b>Pannello Admin</b></h4>
                <p><b>Nome Admin:</b> <%= admin.getNome() %></p>
                <p><b>Email:</b> <%= admin.getEmail() %></p>
            </div>
			
			<!-- Product Management -->
			<div class="product-management text-center">
   				<h4><b>Gestione Prodotti</b></h4>
   					 <div class="d-flex justify-content-center gap-2 flex-wrap">
       					 <button class="btn btn-success" id="add-product-btn">Aggiungi Nuovo Prodotto</button>
        				 <a href="${pageContext.request.contextPath}/admin/GestioneProdotti.jsp" class="btn btn-primary">Visualizza Prodotti</a>
    				</div>
			</div>
			
      

            <!-- Order Management -->
            <div class="order-management text-center">
                <h4><b>Gestione Ordini</b></h4>
                <a href="${pageContext.request.contextPath}/admin/ordini.jsp" class="btn btn-success">Gestione Ordini</a>
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
	 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/admin/admin.js"></script>


	

</body>
</html>
