<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Prodotti in Magazzino</title>
       <link rel="stylesheet" href="./css/GestioneProdotti.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<body>

<%@include file="/html/header.html" %>
<%@page import="java.util.Collection"%>
<% 

Collection<?> items = (Collection<?>) request.getAttribute("items");

if (items == null) {
    response.sendRedirect("../GetAllItem");
    return;
} %>

<h1>Prodotti in magazzino</h1>

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
            	<td><h3><c:out value="${prodotto.SKU}"/></h3></td>
            	<td><h3><c:out value="${prodotto.itemType }"/></h3></td>
                <td><h3><c:out value="${prodotto.nome}"/></h3></td>
                <td><h3><c:out value="${prodotto.quantità}"/></h3></td>
                <td><h3>€<fmt:formatNumber value="${prodotto.prezzo}" minFractionDigits="2" maxFractionDigits="2" /></h3></td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<%@include file="/html/footer.html" %>

</body>
</html>
