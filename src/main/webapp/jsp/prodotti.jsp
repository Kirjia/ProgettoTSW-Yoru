<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>YORU
</title>
<link rel="stylesheet" href="../css/Prodotti.css" rel="noopener">
</head>
<body>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.yoru.model.Entity.Prodotto" %>
	<%@include  file="/html/header.html" %>

	<div class="container mx-auto mt-4">
  <div class="row">
    <div class="col-md-4">
      <div class="card" style="width: 18rem;">
  <img src="https://i.imgur.com/ZTkt4I5.jpg" class="card-img-top" alt="...">
  <div class="card-body">
    <h5>Card title</h5>
    <p>Some quick example text to build on the card title and make up the bulk of the card's content.</p>
  </div>
  </div>
    </div>    
       <div class="col-md-4">
<div class="card" style="width: 18rem;">
  <img src="https://i.imgur.com/ZTkt4I5.jpg" class="card-img-top" alt="...">
  <div class="card-body">
    <h5>Card title</h5>
    <p >Some quick example text to build on the card title and make up the bulk of the card's content.</p>
  </div>
  </div>
    </div>    
          <div class="col-md-4">
<div class="card" style="width: 18rem;">
  <img src="https://i.imgur.com/ZTkt4I5.jpg" class="card-img-top" alt="...">
  <div class="card-body">
    <h5>Card title</h5>
    <p >Some quick example text to build on the card title and make up the bulk of the card's content.</p>
  </div>
  </div>
  </div>
    
    <div class="col-md-4">
      <div class="card" style="width: 18rem;">
  <img src="https://i.imgur.com/ZTkt4I5.jpg" class="card-img-top" alt="...">
  <div class="card-body">
    <h5>Card title</h5>
    <p>Some quick example text to build on the card title and make up the bulk of the card's content.</p>
  </div>
  </div>
    </div>    
       <div class="col-md-4">
<div class="card" style="width: 18rem;">
  <img src="https://i.imgur.com/ZTkt4I5.jpg" class="card-img-top" alt="...">
  <div class="card-body">
    <h5 >Card title</h5>
    <p>Some quick example text to build on the card title and make up the bulk of the card's content.</p>
  </div>
  </div>
    </div>    
          <div class="col-md-4">
<div class="card" style="width: 18rem;">
  <img src="https://i.imgur.com/ZTkt4I5.jpg" class="card-img-top" alt="...">
  <div class="card-body">
    <h5>Card title</h5>
    <p >Some quick example text to build on the card title and make up the bulk of the card's content.</p>
  </div>
  </div>
  </div>
    

    

</div>
  </div>
	
	<%@include  file="/html/footer.html" %>
	
</body>
</html>