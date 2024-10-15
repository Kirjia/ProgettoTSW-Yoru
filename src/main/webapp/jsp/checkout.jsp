<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
    <link rel="stylesheet" href="../css/Checkout.css">
</head>
<body>
	<%@include file="/html/header.html"%>
	
  
<div class="container-checkout">

<div class="heading">
  <h1>Payment details</h1>
  </div>
  <label for="address">Indirizzo</label>
  <input type="text" id="address" name="address" placeholder="via">  </label>
  <label for="name">Name</label>
    <input type="text" id="name" name="name" placeholder="Name"/>
  <br>
  <label for="card">Card number</label>
    <input type="text" minlength="16" maxlength="16" id="card" name="card" placeholder="0000 0000 0000 0000"/>
  <br>
  <div class="exp-cvc">
    <div class="expiration">
  <label for="expiry">Expiration date</label>
    <input class="inputCard" name="expiry" id="expiry" type="text" required placeholder="00/00"/>
    <br>
    </div>
    <div class="security">
  <label for="cvc">CVC</label>
    <input type="text" minlength="3" maxlength="4" id="cvc" name="cvc" placeholder="XXX" />
  <br>
    </div>
  </div>
<div class="btn-checkout">
    <span id="submit">Submit</span>
</div>
</div>
	
	
	<%@include file="/html/footer.html" %>
</body>
</html>