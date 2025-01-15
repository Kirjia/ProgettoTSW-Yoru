<%@page import="com.yoru.model.Entity.Prodotto"%>
<%@page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carosello Prodotti</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.theme.default.min.css" rel="stylesheet">
    
	<link href="css/home.css" rel="stylesheet">
    
</head>
<body>
    <%@include file="/header.jsp"%>


    <!-- Carosello Prodotti -->
    <section class="container mt-5" style="display: block">
        <h1 class="text-center mb-4">Bestseller</h2>
    	<div id="best-carousel" class="owl-carousel">
    	  
		</div>
		
		<div id="new-books-carousel" class="owl-carousel">
    	  
		</div>
		
		<div id="new-gadget-carousel" class="owl-carousel">
    	  
		</div>

    </section>

    <%@include file="/html/footer.html"%>
    
    
    
<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"></script>
<script type="text/javascript" src="js/home.js"></script>

</body>
</html>
