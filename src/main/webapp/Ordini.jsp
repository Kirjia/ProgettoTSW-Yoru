<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Lista Ordini</title>
	    <link href="${pageContext.request.contextPath}/css/Order.css" rel="stylesheet">
</head>
<body>
		<%@include file="/header.jsp"%>
    <h1>Lista degli Ordini</h1>
    <div class="center-table">
    <table id="ordersTable">
       <thead>
    <tr>
        <th class="sortable">ID Ordine</th>
        <th class="sortable">Data</th>
        <th class="sortable">Totale</th>
        <th class="sortable">Email Cliente</th>
        <th class="sortable">ID Pagamento</th>
    </tr>
</thead>
       
        <tbody>
           <!-- I dati verranno inseriti qui tramite AJAX -->
        </tbody>
    </table>
    </div>
	<%@include file="/html/footer.html"%>

    <!-- Bootstrap JS, Popper.js, and jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
     <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
     <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
     <script src="${pageContext.request.contextPath}/js/order.js"></script>
</body>
</html>
