<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>YORU - Checkout</title>

    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/Cart.css" rel="stylesheet">
</head>
<body>

    <%@ include file="/header.jsp" %>

    <!-- Contenitore principale -->
    <div class="container mt-5">
        <div class="card">
            <div class="row">
                <% 
                    User utente = (User) session.getAttribute("user");
                    if (utente != null) { 
                %>
                <!-- Sezione Dati Utente e Pagamento -->
                <div class="col-md-8 cart">
                    <div class="title mb-4">
                        <h4 class="text-center"><b>Dati Utente e Pagamento</b></h4>
                    </div>
                    <!-- Dati Utente -->
                    <div class="user-details mb-4">
                        <h5>Informazioni Utente</h5>
                        <p><b>Nome:</b> <%=utente.getNome()%> <%=utente.getCognome()%></p>
                        <p><b>Email:</b> <%=utente.getEmail()%></p>
                        <p><b>Numero:</b> <%=utente.getTelefono() %></p>
                    </div>

                    <!-- Indirizzi -->
                    <div class="user-addresses mb-4">
                        <h5>Indirizzi</h5>
                        <select id="addressDropdown" class="select-custom" name="selectedAddress">
                            <!-- Le opzioni verranno popolate dinamicamente -->
                        </select>
                    </div>
                </div>
                <% } else { %>
                    <p>Utente non autenticato.</p>
                <% } %>

                <!-- Sezione Riepilogo Ordine -->
                <div class="col-md-4 summary">
                    <h5><b>Riepilogo Ordine</b></h5>
                    <hr>
                    <div id="order-summary" class="order-items">
                        <!-- Elementi dell'ordine caricati dinamicamente -->
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col">Totale:</div>
                        <div id="total-amount" class="col text-right"></div>
                    </div>
                    <button class="btn btn-dark btn-block mt-4" id="confirm-payment">Conferma Pagamento</button>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="/html/footer.html" %>

    <!-- Bootstrap JS, Popper.js, and jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <script>
    $(document).ready(function() {
        // Funzione per caricare i dati del carrello tramite AJAX
        function loadCart() {
            $.ajax({
                url: '${pageContext.request.contextPath}/Carrello', // URL della servlet che restituisce il carrello
                type: 'POST', // Metodo POST
                dataType: 'json', // Tipo di dato che ci aspettiamo dalla servlet (JSON)
                success: function(response) {
                    if (response.cart && response.cart.length > 0) {
                        let cartHtml = '';  // Variabile per l'HTML del carrello
                        let totalAmount = 0; // Variabile per il calcolo del totale

                        // Itera sugli articoli del carrello
                        $.each(response.cart, function(index, item) {
                            // Costruisci l'HTML per ciascun articolo
                            cartHtml += `
                                <div class="order-item mb-3">
                                    <div class="row">
                                        <div class="col"><strong>Nome:</strong> ${item.nome}</div>
                                        <div class="col"><strong>Quantità:</strong> ${item.quantity}</div>
                                        <div class="col"><strong>Prezzo:</strong> ${item.prezzo}€</div>
                                    </div>
                                </div>
                            `;
                            totalAmount += item.prezzo * item.quantity; // Calcolo del totale
                        });

                        // Inserisci gli articoli nel riepilogo ordine
                        $('#order-summary').html(cartHtml);

                        // Mostra il totale
                        $('#total-amount').text(totalAmount.toFixed(2) + '€');
                    } else {
                        // Se il carrello è vuoto
                        $('#order-summary').html('<p>Il carrello è vuoto.</p>');
                        $('#total-amount').text('0.00€');
                    }
                },
                error: function(error) {
                    console.log('Errore nel recupero del carrello:', error.responseText);
                }
            });
        }

        // Carica il carrello al caricamento della pagina
        loadCart();

        // Funzione per caricare gli indirizzi
        function loadAddresses(email) {
            $.ajax({
                url: '${pageContext.request.contextPath}/Indirizzi', // URL della servlet per gli indirizzi
                type: 'POST', // Metodo POST
                data: { email: email }, // Passa l'email dell'utente
                dataType: 'json', // Tipo di dato che ci aspettiamo
                success: function(response) {
                    var addresses = response.indirizzi;
                    var select = $('#addressDropdown');
                    select.empty();  // Pulisci il dropdown

                    // Aggiungi le opzioni nel dropdown degli indirizzi
                    addresses.forEach(function(address) {
                        var option = $('<option>', {
                            value: address.id,
                            text: address.via + ', ' + address.city + ', ' + address.provincia + ' ' + address.CAP
                        });
                        select.append(option);
                    });
                },
                error: function(error) {
                    console.log('Errore nel recupero degli indirizzi:', error.responseText);
                }
            });
        }

        var userEmail = '<%= utente != null ? utente.getEmail() : "" %>'; // Ottieni l'email dell'utente dal backend
        if (userEmail) {
            loadAddresses(userEmail); // Carica gli indirizzi se l'utente è autenticato
        } else {
            console.log('Email utente non disponibile');
        }
    });
    </script>
</body>
</html>
