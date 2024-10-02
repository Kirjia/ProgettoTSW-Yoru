<%@page import="com.yoru.model.Entity.Prodotto"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestione Prodotti</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

<%@ include file="/html/header.html" %>

<div class="container mt-5">
    <h2>Gestione Prodotti</h2>

    <!-- Pulsante per aggiungere nuovo prodotto -->
    <button class="btn btn-success mb-3" id="add-product-btn">Aggiungi Nuovo Prodotto</button>

    <!-- Lista Prodotti -->
    <div id="products-list" class="row">
        <!-- Qui verranno caricati i prodotti tramite AJAX -->
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
                <!-- Form per inserire un nuovo prodotto -->
                <form id="add-product-form">
                    <div class="form-group">
                        <label for="product-name">Nome Prodotto</label>
                        <input type="text" class="form-control" id="product-name" required>
                    </div>
                    <div class="form-group">
                        <label for="product-type">Tipo Prodotto</label>
                        <select class="form-control" id="product-type" required>
                            <option value="libro">Libro</option>
                            <option value="gadget">Gadget</option>
                        </select>
                    </div>

                    <!-- Campi aggiuntivi per Libri e Gadget -->
                    <div id="additional-fields">
                        <!-- Inserisci qui i campi specifici per Libri o Gadget -->
                        <!-- Per libri: autore, numero di pagine -->
                        <!-- Per gadget: garanzia, peso -->
                    </div>
                    
                    <button type="submit" class="btn btn-primary">Aggiungi</button>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="/html/footer.html" %>

<script>
$(document).ready(function() {
    // Funzione per caricare i prodotti tramite AJAX
    function loadProducts() {
        $.ajax({
            url: 'GetAllProducts', // Servlet che restituisce prodotti in formato JSON
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

                // Aggiungi il listener per il pulsante "Elimina"
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

    // Funzione per eliminare un prodotto
    function deleteProduct(sku) {
        $.ajax({
            url: 'DeleteProduct', // Servlet per eliminare il prodotto
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

    // Apri modale per aggiungere un nuovo prodotto
    $('#add-product-btn').click(function() {
        $('#addProductModal').modal('show');
    });

    // Gestisci la scelta del tipo di prodotto
    $('#product-type').change(function() {
        const selectedType = $(this).val();
        let additionalFieldsHtml = '';
        if (selectedType === 'libro') {
            additionalFieldsHtml = `
                <div class="form-group">
                    <label for="author">Autore</label>
                    <input type="text" class="form-control" id="author">
                </div>
                <div class="form-group">
                    <label for="pages">Numero di Pagine</label>
                    <input type="number" class="form-control" id="pages">
                </div>
            `;
        } else if (selectedType === 'gadget') {
            additionalFieldsHtml = `
                <div class="form-group">
                    <label for="warranty">Garanzia</label>
                    <input type="text" class="form-control" id="warranty">
                </div>
                <div class="form-group">
                    <label for="weight">Peso (kg)</label>
                    <input type="number" class="form-control" id="weight">
                </div>
            `;
        }
        $('#additional-fields').html(additionalFieldsHtml);
    });

    // Gestisci l'invio del form per aggiungere un prodotto
    $('#add-product-form').submit(function(e) {
        e.preventDefault();
        const productName = $('#product-name').val();
        const productType = $('#product-type').val();
        // Raccogli le caratteristiche specifiche
        let additionalData = {};
        if (productType === 'libro') {
            additionalData = {
                author: $('#author').val(),
                pages: $('#pages').val()
            };
        } else if (productType === 'gadget') {
            additionalData = {
                warranty: $('#warranty').val(),
                weight: $('#weight').val()
            };
        }

        $.ajax({
            url: 'AddProduct', // Servlet per aggiungere un prodotto
            method: 'POST',
            data: {
                nome: productName,
                tipo: productType,
                ...additionalData
            },
            success: function(response) {
                alert('Prodotto aggiunto con successo!');
                $('#addProductModal').modal('hide');
                loadProducts(); // Ricarica la lista dei prodotti
            },
            error: function(error) {
                console.error('Errore durante l\'aggiunta del prodotto', error);
            }
        });
    });

    // Carica i prodotti quando la pagina è pronta
    loadProducts();
});
</script>

</body>
</html>
