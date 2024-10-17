<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.yoru.model.Entity.User"%>
<%@page import="com.yoru.model.Entity.Role"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" rel="noopener">
</head>
<body>

<% User user = (User) session.getAttribute("user");
%>


	<header>
		 <a href="${pageContext.request.contextPath}/jsp/home.jsp" class="logo"><img alt="YORU" src="${pageContext.request.contextPath}/images/LOGO.png" width="100px" height="50px"></a>
		 <ul class="slider-menu">
			<li class="icon"><a href="${pageContext.request.contextPath}/jsp/home.jsp"><i class="bi bi-house-fill"></i></a></li>
			
			
			<%if(user==null){ %>
						<li class="drop"><a href="${pageContext.request.contextPath}/jsp/login.jsp"><i class="bi bi-person-fill"></i></a>
					
			<%}else if(user.getRole()==Role.USER){ %>
			<li class="drop"><a href="${pageContext.request.contextPath}/jsp/user.jsp"><i class="bi bi-person-fill"></i></a>
				
				
				<%}else if(user.getRole()==Role.ADMIN){ %>
					<li class="drop"><a href="${pageContext.request.contextPath}/jsp/admin.jsp"><i class="bi bi-person-fill"></i></a>
					
					
					
					
					
					
					<%}if(user!=null && user.getRole()==Role.USER){ %>
				<ul>
					<li class="dropdown"><a href="${pageContext.request.contextPath}/jsp/user.jsp">Profilo</a></li>
					<li class="dropdown"><a href="${pageContext.request.contextPath}/jsp/user.jsp#cronologia">Cronologia ordini</a></li>
					<li class="dropdown"><a href="${pageContext.request.contextPath}/Logout">Logout</a></li>
				</ul></li>
				
				
				<%}else if(user!=null && user.getRole()==Role.ADMIN){ %>
				
				<ul>
					<li class="dropdown"><a href="${pageContext.request.contextPath}/jsp/admin.jsp">Profilo</a></li>
					<li class="dropdown"><a href="${pageContext.request.contextPath}/Logout">Logout</a></li>
				</ul></li>
				
				
				
					<%}else{%>
						<ul>
						<li class="dropdown"><a href="${pageContext.request.contextPath}/jsp/login.jsp">Login</a></li>
					</ul></li>
					<%} %>
							
				
				
				
   			<li class="icon"><a href="${pageContext.request.contextPath}/jsp/prodotti.jsp"><i class="bi bi-book"></i></a></li>
            <li class="icon"><a href="${pageContext.request.contextPath}/jsp/gadget.jsp"><i class="bi bi-controller"></i></a></li>
			<li class="icon"><a href="${pageContext.request.contextPath}/jsp/carrello.jsp"><i class="bi bi-cart-fill"></i></a></li
			>	
		</ul>
	</header>
	
</body>
</html>