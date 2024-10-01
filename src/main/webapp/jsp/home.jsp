<%@page import="com.yoru.model.Entity.Prodotto"%>
<%@page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>YORU - Carosello Prodotti</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <style>
        .carousel-inner {
            padding: 1em;
        }
        .card {
        align:center;
            box-shadow: 2px 6px 8px rgba(22, 22, 26, 0.18);
            border: none;
            margin: 30px ;
    		max-width: 200px;
   			width: 100%;
        }
        .card img {
            max-height: 200px;
            min-height: 200px; /* Imposta l'altezza massima per le immagini */
            object-fit: cover; /* Mantiene le proporzioni dell'immagine */
        }
        @media (max-width: 767px) {
    .carousel-inner .carousel-item > div {
        display: none;
    }
    .carousel-inner .carousel-item > div:first-child {
        display: block;
    }
}

.carousel-inner .carousel-item.active,
.carousel-inner .carousel-item-next,
.carousel-inner .carousel-item-prev {
    display: flex;
}

/* medium and up screens */
@media (min-width: 768px) {
    
    .carousel-inner .carousel-item-end.active,
    .carousel-inner .carousel-item-next {
      transform: translateX(25%);
    }
    
    .carousel-inner .carousel-item-start.active, 
    .carousel-inner .carousel-item-prev {
      transform: translateX(-25%);
    }
}

.carousel-inner .carousel-item-end,
.carousel-inner .carousel-item-start { 
  transform: translateX(0);
}
    </style>
</head>
<body>

<%@include file="/html/header.html" %>

<% 
Collection<?> items = (Collection<?>) request.getAttribute("bestsellers");
if(items == null){
	response.sendRedirect("../BestSellers");
	return;
}
%>

<!-- Carosello Prodotti -->
<section class="container mt-5">
    <h2 class="text-center">Prodotti in Evidenza</h2>
    <div id="carouselProdotti" class="carousel" data-bs-ride="carousel" data-interval="3000" data-pause="hover">
        <div class="carousel-inner" role="listbox">
            <c:forEach items="${bestsellers}" var="prodotto" varStatus="status">
                    <div class="carousel-item <c:if test='${status.first}'>active</c:if>">

                <div class=""col-md-3">
                    <div class="card">
                        <img class="card-img-top" class="img-fluid" src="${pageContext.request.contextPath}/images/${prodotto.SKU}.jpg" alt="${prodotto.nome}" onerror="this.src='images/err.jpeg'">
                        <div class="card-body text-center">
                            <h5 class="card-title">${prodotto.nome}</h5>
                            <p class="card-text">€${prodotto.prezzo}</p>
                            <button class="btn btn-primary">Aggiungi al Carrello</button>
                        </div>
                    </div>
				</div>
				</div>
                
            </c:forEach>
        </div>
        <a class="carousel-control-prev bg-transparent w-aut" href="#recipeCarousel" role="button" data-bs-slide="prev">
					<span class="carousel-control-prev-icon" aria-hidden="true"></span>
				</a>
				<a class="carousel-control-next bg-transparent w-aut" href="#recipeCarousel" role="button" data-bs-slide="next">
					<span class="carousel-control-next-icon" aria-hidden="true"></span>
				</a>
    </div>
</section>

<%@include file="/html/footer.html" %>
<script> 

let items = document.querySelectorAll('.carousel .carousel-item')

items.forEach((el) => {
	const minPerSlide = 3
	let next = el.nextElementSibling
	for (var i=1; i<minPerSlide; i++) {
		if (!next) {
    // wrap carousel by using first child
    next = items[0]
}
let cloneChild = next.cloneNode(true)
el.appendChild(cloneChild.children[0])
next = next.nextElementSibling
}
})
</script>

<!-- Bootstrap JS, Popper.js, and jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>

</body>
</html>
