<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Prodotti in Magazzino</title>
<link rel="stylesheet" href="./css/GestioneProdotti.css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">


</head>
<body>

	<%@include file="/header.jsp"%>
	<%@page import="java.util.Collection"%>
	<%
	Collection<?> items = (Collection<?>) request.getAttribute("items");

	if (items == null) {
		response.sendRedirect("./GetAllItem");
		return;
	}
	%>
	<h1>Prodotti in magazzino</h1>
	<div class="center-table">
		<table>
			<thead>
				<tr>
					<th>SKU</th>
					<th>Type</th>
					<th>Nome</th>
					<th>Quantità</th>
					<th>Prezzo</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="prodotto" items="${items}">
					<tr>
						<td><h3>
								<c:out value="${prodotto.SKU}" />
							</h3></td>
						<td><h3>
								<c:out value="${prodotto.itemType }" />
							</h3></td>
						<td><h3>
								<c:out value="${prodotto.nome}" />
							</h3></td>
						<td><h3>
								<c:out value="${prodotto.quantità}" />
							</h3></td>
						<td><h3>
								€
								<fmt:formatNumber value="${prodotto.prezzo}"
									minFractionDigits="2" maxFractionDigits="2" />
							</h3></td>
						<td>
							<button class="orange-button" data-sku="${prodotto.SKU}"
								data-toggle="modal" data-target="#addNewProductModal">
								<i class="bi bi-pencil-square"></i>
							</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<!-- Nuovo Modale per Nome Prodotto, Prezzo e Quantità -->
	<div class="modal fade" id="addNewProductModal" tabindex="-1"
		role="dialog" aria-labelledby="addNewProductModalLabel"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="addNewProductModalLabel">Aggiungi
						Nuovo Prodotto</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="add-new-product-form">
						<div class="form-group">
							<label for="new-product-name">Nome Prodotto</label> <input
								type="text" class="form-control" id="new-product-name"
								placeholder="Inserisci il nome del prodotto" required>
						</div>
						<div class="form-group">
							<label for="new-product-price">Prezzo</label> <input
								type="number" class="form-control" id="new-product-price"
								step="0.01" placeholder="Inserisci il prezzo" required>
						</div>
						<div class="form-group">
							<label for="new-product-quantity">Quantità</label> <input
								type="number" class="form-control" id="new-product-quantity"
								placeholder="Inserisci la quantità" required>
						</div>
						<button type="submit" class="btn btn-primary" id="add">Aggiungi
							modifiche</button>
					</form>
				</div>
			</div>
		</div>
	</div>





	<%@include file="/html/footer.html"%>
	
<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

    <script src="${pageContext.request.contextPath}/js/gestioneProdotti.js" type="text/javascript"></script>


</body>
</html>
