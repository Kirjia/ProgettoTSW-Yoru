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
      <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
     <script src="${pageContext.request.contextPath}/js/order.js"></script>
</body>
</html>
