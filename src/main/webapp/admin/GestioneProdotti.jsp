<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Prodotti in Magazzino</title>
<link rel="stylesheet" href="../css/GestioneProdotti.css">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
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
	<div class="container">
		<h1 class="text-center my-4">Prodotti in Magazzino</h1>
		<div class="center-table">
			<table class="table table-hover table-bordered">
				<thead class="table-dark">
					<tr>
						<th>SKU</th>
						<th>Type</th>
						<th>Nome</th>
						<th>Quantità</th>
						<th>Prezzo</th>
						<th>Azioni</th>
					</tr>
				</thead>
				<tbody>
			<c:forEach var="prodotto" items="${items}">
    <tr class="${prodotto.deleted != 0 ? 'row-dark' : ''}">
        <td><c:out value="${prodotto.SKU}" /></td>
        <td><c:out value="${prodotto.itemType}" /></td>
        <td><c:out value="${prodotto.nome}" /></td>
        <td><c:out value="${prodotto.quantità}" /></td>
        <td>€ <fmt:formatNumber value="${prodotto.prezzo}" minFractionDigits="2" maxFractionDigits="2" /></td>
        <td>
            <div class="action-buttons">
                  <button class="btn btn-warning orange-button" 
                    data-sku="${prodotto.SKU}" 
                    data-nome="${prodotto.nome}" 
                    data-prezzo="${prodotto.prezzo}" 
                    data-quantita="${prodotto.quantità}"
                    ${prodotto.deleted != 0 ? 'disabled' : ''}>
                    <i class="bi bi-pencil-square"></i>
                </button>
                <button class="btn btn-danger red-button" 
                    data-sku="${prodotto.SKU}" 
                    data-nome="${prodotto.nome}"
                    ${prodotto.deleted != 0 ? 'disabled' : ''}>
                    <i class="bi bi-trash"></i>
                </button>
            </div>
        </td>
    </tr>
</c:forEach>

				</tbody>
			</table>
		</div>
	</div>
	<!-- Modal per modificare il libro -->
	<div class="modal fade" id="editBookModal" tabindex="-1" role="dialog" aria-labelledby="editBookModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="editBookModalLabel">Modifica Libro</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<p id="editBookMessage" class="mb-4 text-center fw-bold"></p>
					<form id="edit-book-form">
						<div class="form-group mb-3">
							<label for="book-price" class="form-label">Prezzo</label>
							<input type="number" class="form-control" id="book-price" step="0.01" placeholder="Inserisci il nuovo prezzo" min="0" required>
						</div>
						<div class="form-group mb-3">
							<label for="book-quantity" class="form-label">Quantità</label>
							<input type="number" class="form-control" id="book-quantity" placeholder="Inserisci la nuova quantità" min="0" required>
						</div>
						<button type="submit" class="btn btn-primary w-100">Salva Modifiche</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/html/footer.html"%>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/js/admin/gestioneProdotti.js" type="text/javascript"></script>
</body>
</html>
