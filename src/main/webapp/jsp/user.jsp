<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.yoru.model.Entity.Prodotto"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>YORU - Pagina Utente</title>
    <link rel="stylesheet" href="./css/Prodotti.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <style>
        body {
            font-family: 'Poppins', sans-serif;
        }
        .carrello-section {
            padding: 50px;
        }
        .carrello-header {
            color: #444;
            font-size: 2rem;
            text-align: center;
        }
        .carrello-table {
            width: 100%;
            border-collapse: collapse;
        }
        .carrello-table th, .carrello-table td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        .carrello-table th {
            background-color: #ff7000;
            color: white;
        }
        .carrello-table td {
            background-color: #fff;
            color: #333;
        }
        .btn-checkout {
            background-color: #ff7000;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .btn-checkout:hover {
            background-color: #e06600;
        }
        .empty-carrello {
            text-align: center;
            font-size: 1.5rem;
            color: #808080;
            margin-top: 20px;
        }
    </style>
</head>
<body>

<%@include file="/html/header.html" %>

<!-- Sezione Carrello -->
<div class="container carrello-section">
    <h1 class="carrello-header">Il tuo carrello</h1>
    <%
        HttpSession session = request.getSession();
        ArrayList<Prodotto> carrello = (ArrayList<Prodotto>) session.getAttribute("carrello");
        
        if (carrello == null || carrello.isEmpty()) {
    %>
    <p class="empty-carrello">Il carrello è vuoto!</p>
    <% } else { %>
    <table class="carrello-table">
        <thead>
            <tr>
                <th>Prodotto</th>
                <th>Prezzo</th>
                <th>Quantità</th>
                <th>Totale</th>
                <th>Rimuovi</th>
            </tr>
        </thead>
        <tbody>
        <%
            double totale = 0;
            for (Prodotto p : carrello) {
                double subtotale = p.getPrezzo() * p.getQuantita();
                totale += subtotale;
        %>
            <tr>
                <td><%= p.getNome() %></td>
                <td>€<%= p.getPrezzo() %></td>
                <td><%= p.getQuantita() %></td>
                <td>€<%= subtotale %></td>
                <td><a href="RimuoviDalCarrelloServlet?id=<%= p.getId() %>"><i class="fas fa-trash-alt"></i></a></td>
            </tr>
        <% } %>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="3" style="text-align: right;">Totale:</td>
                <td colspan="2">€<%= totale %></td>
            </tr>
        </tfoot>
    </table>
    <div style="text-align: right; margin-top: 20px;">
        <button class="btn-checkout" onclick="window.location.href='CheckoutServlet'">Procedi all'acquisto</button>
    </div>
    <% } %>
</div>

<%@include file="/html/footer.html" %>

<!-- Bootstrap JS, Popper.js, and jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
