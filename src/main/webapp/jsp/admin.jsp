<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.yoru.model.Entity.User"%>
<%@ page import="com.yoru.model.Entity.Prodotto"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pannello Admin</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/Admin.css" rel="stylesheet">
    
    <%
        User admin = (User) session.getAttribute("user");
        if (admin == null) {
            response.sendRedirect("./login.jsp");
            return;
            
        }
        
    %>
</head>
<body>

    <%@ include file="/html/header.html" %>

    <div class="container mt-5">
        <div class="row">
            <!-- Sidebar menu -->
            <div class="col-md-3">
                <div class="list-group">
                    <a href="#" class="list-group-item list-group-item-action">Gestione Utenti</a>
                    <a href="#" class="list-group-item list-group-item-action active">Gestione Prodotti</a>
                    <a href="#" class="list-group-item list-group-item-action">Gestione Ordini</a>
                    <a href="${pageContext.request.contextPath}/Logout" class="list-group-item list-group-item-action">Logout</a>
                </div>
            </div>

            <div class="col-md-9">
                <!-- Admin Info Card -->
                <div class="profile-card mb-4">
                    <h4><b>Pannello Admin</b></h4>
                    <hr>
                    <p><b>Nome Admin:</b> <%= admin.getNome() %></p>
                    <p><b>Email:</b> <%= admin.getEmail() %></p>
                </div>

     <!--Gestione utenti --> 
                    
				<div class="user-management">
                    <h4><b>Gestione Utenti</b></h4>
                    <hr>
                    <button class="btn btn-success mb-3" id="management-user-btn">Gestione utenti</button>
                   </div>
                <!-- Product Management Section -->
                <div class="product-management">
                    <h4><b>Gestione Prodotti</b></h4>
                    <hr>
                    <button class="btn btn-success mb-3" id="add-product-btn">Aggiungi Nuovo Prodotto</button>

                    <!-- Lista Prodotti -->
                    <a href="../jsp/GestioneProdotti.jsp">
                       <button class="btn btn-success mb-3" id="add-product-btn">Visualizza Prodotti</button>
                     </a>

                </div>
                 <!--Gestione ordini --> 
                    
				<div class="order-management">
                    <h4><b>Gestione Ordini</b></h4>
                    <hr>
                    <button class="btn btn-success mb-3" id="management-order-btn">Gestione Ordini</button>
                   </div>
            </div>
        </div>
    </div>

    <!-- Modale per Aggiungere un Nuovo Prodotto -->
    <div class="modal fade" id="addProductModal" tabindex="-1" role="dialog" aria-labelledby="addProductModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addProductModalLabel">Aggiungi Nuovo Prodotto</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="add-product-form">
                        <div class="form-group">
                            <label for="product-name">Nome Prodotto</label>
                            <input type="text" class="form-control" id="product-name" placeholder="Inserisci il nome del libro o gadget" required>
                        </div>
                        <div class="form-group">
                            <label for="product-type">Tipo Prodotto</label>
                            <!-- Selezione iniziale vuota -->
							<select class="form-control" id="product-type" required>
							    <option value="" disabled selected>Seleziona un tipo di prodotto</option>
							    <option value="libro">Libro</option>
							    <option value="gadget">Gadget</option>
							</select>
                        </div>

                        <!-- Campi aggiuntivi per Libri e Gadget -->
                        <div id="additional-fields">
                            <!-- Campi personalizzati -->
                        </div>
                        
                        <button type="submit" class="btn btn-primary">Aggiungi</button>
                    </form>
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
            // Carica i prodotti
            function loadProducts() {
                $.ajax({
                    url: 'GetAllBook', // Servlet che restituisce prodotti in formato JSON
                    method: 'GET',
                    dataType: 'json',
                    success: function(data) {
                        $('#products-list').empty();
                        data.forEach(function(prodotto) {
                            $('#products-list').append(`
                                <div class="col-md-4">
                                    <div class="card mb-4">
                                        <div class="card-body">
                                            <h5 class="card-title">${prodotto.nome}</h5>
                                            <p class="card-text">Prezzo: €${prodotto.prezzo}</p>
                                            <button class="btn btn-danger delete-product" data-id="${prodotto.SKU}">Elimina</button>
                                        </div>
                                    </div>
                                </div>
                            `);
                        });

                        // Listener per il pulsante "Elimina"
                        $('.delete-product').click(function() {
                            const sku = $(this).data('id');
                            deleteProduct(sku);
                        });
                    },
                    error: function(error) {
                        console.error('Errore durante il caricamento dei prodotti', error);
                    }
                });
            }

            // Elimina un prodotto
            function deleteProduct(sku) {
                $.ajax({
                    url: 'DeleteProduct', // Servlet per eliminare un prodotto
                    method: 'POST',
                    data: { sku: sku },
                    success: function(response) {
                        alert('Prodotto eliminato con successo!');
                        loadProducts(); // Ricarica i prodotti
                    },
                    error: function(error) {
                        console.error('Errore durante l\'eliminazione del prodotto', error);
                    }
                });
            }

            
            
            	$('#add-product-btn').click(function() {
                $('#add-product-form')[0].reset();  
                $('#additional-fields').empty();   
                $('#addProductModal').modal('show');
            });

            // Gestione dinamica del form quando si cambia tipo di prodotto
            $('#product-type').change(function() {
                const selectedType = $(this).val();
                let additionalFieldsHtml = '';
                if (selectedType === 'libro') {
                    additionalFieldsHtml = `
                        <div class="form-group">
                            <label for="SKUlibro">SKU:</label>
                            <input type="number" class="form-control" id="SKUlibro">
                        </div>
                        <div class="form-group">
                            <label for="ISBN">ISBN:</label>
                            <input type="number" class="form-control" id="ISBN">
                        </div>
                        <div class="form-group">
                            <label for="pagine">Numero di Pagine:</label>
                            <input type="number" class="form-control" id="pagine">
                        </div>
                        <div class="form-group">
                            <label for="lingua">Lingua:</label>
                            <input type="text" class="form-control" id="lingua">
                        </div>
                    `;
                } else if (selectedType === 'gadget') {
                    additionalFieldsHtml = `
                        <div class="form-group">
                            <label for="SKUgadget">SKU:</label>
                            <input type="number" class="form-control" id="SKUgadget">
                        </div>
                        <div class="form-group">
                            <label for="modello">Modello:</label>
                            <input type="text" class="form-control" id="modello">
                        </div>
                        <div class="form-group">
                            <label for="marchio">Marchio:</label>
                            <input type="text" class="form-control" id="marchio">
                        </div>
                    `;
                }
                $('#additional-fields').html(additionalFieldsHtml);
            });

            // Gestione invio form per aggiungere prodotto
            $('#add-product-form').submit(function(e) {
                e.preventDefault();
                const productName = $('#product-name').val();
                const productType = $('#product-type').val();
                let additionalData = {};
                if (productType === 'libro') {
                    additionalData = {
                        SKU: $('#SKUlibro').val(),
                        ISBN: $('#ISBN').val(),
                        pagine: $('#pagine').val(),
                        lingua: $('#lingua').val()
                    };
                } else if (productType === 'gadget') {
                    additionalData = {
                        SKU: $('#SKUgadget').val(),
                        modello: $('#modello').val(),
                        marchio: $('#marchio').val()
                    };
                }

                $.ajax({
                    url: 'AddProduct',
                    method: 'POST',
                    data: {
                        nome: productName,
                        tipo: productType,
                        ...additionalData
                    },
                    success: function(response) {
                        alert('Prodotto aggiunto con successo!');
                        $('#addProductModal').modal('hide');
                        loadProducts();
                    },
                    error: function(error) {
                        console.error('Errore durante l\'aggiunta del prodotto', error);
                    }
                });
            });

            // Carica i prodotti all'apertura della pagina
            loadProducts();
        });
    </script>

</body>
</html>
