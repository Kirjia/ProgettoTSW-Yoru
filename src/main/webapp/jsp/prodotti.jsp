<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>YORU</title>
<link rel="stylesheet" href="../css/Prodotti.css" rel="noopener">
</head>
<body>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@page import="com.yoru.model.Entity.Prodotto"%>
	<%@include file="/html/headersearch.html"%>

	<section class="section-products">

		<div class="row">
			<!-- Single Product -->
			<div class="col-md-6 col-lg-4 col-xl-3">
				<div id="product-1" class="single-product">
					<div class="part-1">
						<ul>
							<li><a href="#"><i class="fas fa-shopping-cart"></i></a></li>
							<li><a href="#"><i class="fas fa-heart"></i></a></li>
						</ul>
					</div>
					<div class="part-2">
						<h3 class="product-title">Here Product Title</h3>
						<h4 class="product-price">$49.99</h4>
					</div>
				</div>
			</div>
			<!-- Single Product -->
			<div class="col-md-6 col-lg-4 col-xl-3">
				<div id="product-2" class="single-product">
					<div class="part-1">
						<ul>
							<li><a href="#"><i class="fas fa-shopping-cart"></i></a></li>
							<li><a href="#"><i class="fas fa-heart"></i></a></li>
						</ul>
					</div>
					<div class="part-2">
						<h3 class="product-title">Here Product Title</h3>
						<h4 class="product-price">$49.99</h4>
					</div>
				</div>
			</div>
			<!-- Single Product -->
			<div class="col-md-6 col-lg-4 col-xl-3">
				<div id="product-3" class="single-product">
					<div class="part-1">
						<ul>
							<li><a href="#"><i class="fas fa-shopping-cart"></i></a></li>
							<li><a href="#"><i class="fas fa-heart"></i></a></li>
						</ul>
					</div>
					<div class="part-2">
						<h3 class="product-title">Here Product Title</h3>
						<h4 class="product-price">$49.99</h4>
					</div>
				</div>
			</div>
			<!-- Single Product -->
			<div class="col-md-6 col-lg-4 col-xl-3">
				<div id="product-4" class="single-product">
					<div class="part-1">
						<ul>
							<li><a href="#"><i class="fas fa-shopping-cart"></i></a></li>
							<li><a href="#"><i class="fas fa-heart"></i></a></li>
						</ul>
					</div>
					<div class="part-2">
						<h3 class="product-title">Here Product Title</h3>
						<h4 class="product-price">$49.99</h4>
					</div>
				</div>
			</div>
			<!-- Single Product -->
			<div class="col-md-6 col-lg-4 col-xl-3">
				<div id="product-1" class="single-product">
					<div class="part-1">
						<ul>
							<li><a href="#"><i class="fas fa-shopping-cart"></i></a></li>
							<li><a href="#"><i class="fas fa-heart"></i></a></li>
						</ul>
					</div>
					<div class="part-2">
						<h3 class="product-title">Here Product Title</h3>
						<h4 class="product-price">$49.99</h4>
					</div>
				</div>
			</div>
			<!-- Single Product -->
			<div class="col-md-6 col-lg-4 col-xl-3">
				<div id="product-2" class="single-product">
					<div class="part-1">
						<ul>
							<li><a href="#"><i class="fas fa-shopping-cart"></i></a></li>
							<li><a href="#"><i class="fas fa-heart"></i></a></li>
						</ul>
					</div>
					<div class="part-2">
						<h3 class="product-title">Here Product Title</h3>
						<h4 class="product-price">$49.99</h4>
					</div>
				</div>
			</div>
			<!-- Single Product -->
			<div class="col-md-6 col-lg-4 col-xl-3">
				<div id="product-3" class="single-product">
					<div class="part-1">
						<ul>
							<li><a href="#"><i class="fas fa-shopping-cart"></i></a></li>
							<li><a href="#"><i class="fas fa-heart"></i></a></li>

						</ul>
					</div>
					<div class="part-2">
						<h3 class="product-title">Here Product Title</h3>
						<h4 class="product-price">$49.99</h4>
					</div>
				</div>
			</div>
			<!-- Single Product -->
			<div class="col-md-6 col-lg-4 col-xl-3">
				<div id="product-4" class="single-product">
					<div class="part-1">
						<ul>
							<li><a href="#"><i class="fas fa-shopping-cart"></i></a></li>
							<li><a href="#"><i class="fas fa-heart"></i></a></li>
						</ul>
					</div>
					<div class="part-2">
						<h3 class="product-title">Here Product Title</h3>
						<h4 class="product-price">$49.99</h4>
					</div>
				</div>
			</div>
		</div>
	</section>

	<%@include file="/html/footer.html"%>

</body>
</html>