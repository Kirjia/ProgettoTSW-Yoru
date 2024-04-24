<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.yoru.model.Entity.Prodotto" %>


<% Collection<?> items = (Collection<?>) request.getAttribute("items");%>

	<div id="list items">
		<ul>
		<c:forEach  items="${items}" var="item">
			<li class="item">
				<div class="item info">
					<div class="item-top">
						<img src="images/${item.SKU }.jpeg" alt="images/err.jpeg">
					</div>
					<div class="item details">
						<strong><c:out value="${item.nome}"/></strong>
						<strong>prezzo <c:out value="${item.prezzo}"/> $</strong>
					</div>
				
				</div>
			
			</li>
			</c:forEach>
		</ul>
	
	</div>
	
	<%@include  file="/html/footer.html" %>
	
</body>
</html>