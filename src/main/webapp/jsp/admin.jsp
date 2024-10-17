<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.yoru.model.Entity.User"%>
<%@ page import="com.yoru.model.Entity.Prodotto"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Pannello Admin</title>

<!-- Bootstrap CSS -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/Admin.css"
	rel="stylesheet">

<%
User admin = (User) session.getAttribute("user");
if (admin == null) {
	response.sendRedirect("./login.jsp");
	return;
}
%>
</head>
<body>

	<%@ include file="/jsp/header.jsp"%>
	<div class="container mt-5" style="margin-top: 1rem !important;">

		<div class="row justify-content-center">
			<!-- Sidebar menu
            <div class="col-md-3 mb-4">
                <div class="list-group text-center">
                    <a href="#" class="list-group-item list-group-item-action">Gestione Utenti</a>
                    <a href="#" class="list-group-item list-group-item-action active">Gestione Prodotti</a>
                    <a href="#" class="list-group-item list-group-item-action">Gestione Ordini</a>
                    <a href="${pageContext.request.contextPath}/Logout" class="list-group-item list-group-item-action">Logout</a>
                </div>
            </div> -->

			<div class="col-md-9 text-center">
				<!-- Admin Info Card -->
				<div class="profile-card mb-4 mx-auto">

					<h4>
						<b>Pannello Admin</b>
					</h4>

					<p>
						<b>Nome Admin:</b>
						<%=admin.getNome()%></p>
					<p>
						<b>Email:</b>
						<%=admin.getEmail()%></p>
				</div>

				<!-- Gestione utenti 
                <div class="user-management mb-4">
                <hr>
                    <h4><b>Gestione Utenti</b></h4>
                    
                    <button class="btn btn-success mb-3" id="management-user-btn">Gestione utenti</button>
                </div>-->

				<!-- Product Management Section -->
				<div class="product-management mb-4">
					<hr>
					<h4>
						<b>Gestione Prodotti</b>
					</h4>

					<button class="btn btn-success mb-3" id="add-product-btn">Aggiungi
						Nuovo Prodotto</button>
					<a href="../jsp/GestioneProdotti.jsp" class="btn btn-success mb-3">Visualizza
						Prodotti</a>
				</div>

				<!-- Gestione ordini -->
				<div class="order-management mb-4">
					<hr>
					<h4>
						<b>Gestione Ordini</b>
					</h4>

					<button class="btn btn-success mb-3" id="management-order-btn">Gestione
						Ordini</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Modale per Aggiungere un Nuovo Prodotto -->
	<div class="modal fade" id="addProductModal" tabindex="-1"
		role="dialog" aria-labelledby="addProductModalLabel"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="addProductModalLabel">Aggiungi
						Nuovo Prodotto</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="add-product-form">
						<div class="form-group">
							<label for="product-name">Nome Prodotto</label> <input
								type="text" class="form-control" id="product-name"
								placeholder="Inserisci il nome del libro o gadget" required>
						</div>
						<div class="form-group">
							<label for="product-type">Tipo Prodotto</label> <select
								class="form-control" id="product-type" required>
								<option value="" disabled selected>Seleziona un tipo di
									prodotto</option>
								<option value="libro">Libro</option>
								<option value="gadget">Gadget</option>
							</select>
						</div>
						<div id="additional-fields"></div>
						<button type="submit" class="btn btn-primary">Aggiungi</button>
					</form>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="/html/footer.html"%>

	<!-- Bootstrap JS, Popper.js, and jQuery -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>



	<script>
        $(document).ready(function() {
            // Carica i prodotti
            function loadProducts() {
                $.ajax({
                    url: '${pageContext.request.contextPath}/Prodotti', // Servlet che restituisce prodotti in formato JSON
                    method: 'POST',
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
	        	   <div class="form-group-editor">
	        	<label for="autori">Seleziona un autore:</label>
	            <select id="autori" name="autori" class="form-select">
	                <option value="">-- Seleziona un autore --</option>
	                <!-- Le opzioni verranno popolate tramite AJAX -->
	            </select>
	            <div>
            </fieldset>
	            <div class="form-group-editor">
				 <!-- Menu a tendina per la selezione della casa editrice -->
	   <label for="editorDropdown">Seleziona la casa editrice:</label>
	   <select id="editorDropdown" name="selectededitor">
	   <option value="">-- Seleziona una casa editrice --</option>
	   <!-- Le opzioni verranno popolate dinamicamente -->
	   
	   </select>
	   <div>
	            <div class="form-group">
	                <label for="ISBN">ISBN:</label>
	                <input type="number" class="form-control" id="ISBN">
	            </div>
	            <div class="form-group">
	                <label for="pagine">Numero di Pagine:</label>
	                <input type="number" class="form-control" id="pagine">
	            </div>
	            <div class="form-group">
	            <label for="lingua">Seleziona la lingua:</label>
	            <select id="lingua" name="lingua" class="form-select">
	                <option value="">-- Seleziona una lingua --</option>
	                <option value="it">Italiano</option>
	                <option value="en">Inglese</option>
	                <option value="fr">Francese</option>
	                <option value="es">Spagnolo</option>
	                <option value="de">Tedesco</option>
	                <!-- Puoi aggiungere altre lingue qui -->
	            </select>
	            </div>
	        `;
	        // Function to fetch publisher data and populate the dropdown
function fetchPublishers() {
	$.ajax({
	    url: '${pageContext.request.contextPath}/Producers', // URL della tua servlet
	    method: 'POST',
	    dataType: 'json',
	    success: function(data) {
	        const editorDropdown = $('#editorDropdown');
	        editorDropdown.empty(); // Pulisci il select prima di aggiungere nuove opzioni

	        // Aggiungi l'opzione iniziale con il testo "Scegli l'editore"
	        const defaultOption = $('<option></option>')
	            .attr('value', '') // Valore vuoto per l'opzione predefinita
	            .text('-- Scegli l\'editore --'); // Testo descrittivo
	        editorDropdown.append(defaultOption);

	        // Cicla attraverso ogni editore nella risposta JSON e aggiungi un'opzione al dropdown
	        data.forEach(publisher => {
	            const option = $('<option></option>')
	                .attr('value', publisher.id)
	                .text(publisher.nome); // Usa il nome dell'editore come testo
	            editorDropdown.append(option);
	        });
	    },
	    error: function(xhr, status, error) {
	        console.error('Errore nel caricamento dei dati degli editori:', error);
	    }
	});

    
    console.log('Checkboxes aggiunte al DOM:', additionalFieldsHtml);
 // Effettua una richiesta AJAX per ottenere gli autori
    $.ajax({
        url: '${pageContext.request.contextPath}/Autori',
        method: 'POST',
        dataType: 'json', // Specifica che ti aspetti una risposta JSON
        success: function(autori) {
            let select = document.getElementById('autori'); // Campo select esistente
            
            // Pulisci il select prima di aggiungere nuovi elementi
            select.innerHTML = '';

            // Aggiungi un'opzione iniziale con il testo "Scegli l'autore"
            let defaultOption = document.createElement('option');
            defaultOption.value = ''; // Valore vuoto per l'opzione predefinita
            defaultOption.textContent = '-- Scegli l\'autore --'; // Testo descrittivo
            select.appendChild(defaultOption); // Aggiungi l'opzione al select
            
            
            if (Array.isArray(autori)) {
                autori.forEach(function(item) {
                    console.log('[' + (item.aka ? item.aka : item.cognome) + ']'); // Log per verificare la risposta

                    // Crea l'opzione per il campo select
                    let option = document.createElement('option');
                    option.value = item.aka ? item.aka : item.cognome; // Usa il valore 'aka' o 'nome'
                    option.textContent = item.aka ? item.aka : item.cognome; // Mostra 'aka' o 'nome' se 'aka' non è presente

                    // Aggiungi l'opzione al campo select
                    select.appendChild(option);
                });
            } else {
                console.error('La risposta non è un array:', autori);
            }
        },
        error: function(error) {
            console.error('Errore nel caricamento degli autori', error);
            alert('Impossibile caricare gli autori. Riprova più tardi.');
        }
    });

}

// Call the function when the document is ready or when the selected type is 'libro'
$(document).ready(function() {
    if (selectedType === 'libro') {
        fetchPublishers();
    }
});

	        
    
        $('#additional-fields').html(additionalFieldsHtml);
        console.log('Checkboxes aggiunte al DOM:', additionalFieldsHtml);
    } else if (selectedType === 'gadget') {
         additionalFieldsHtml = `
    	            <fieldset class="form-group">
    	                <legend> Materiali</legend>
                        <div id="materiali">
                        </div>
    	            </fieldset>
    	            <div class="form-group">
    	                <label for="modello">Modello:</label>
    	                <input type="text" class="form-control" id="modello">
    	            </div>
    	            <div class="form-group">
    	                <label for="marchio">Marchio:</label>
    	                <input type="text" class="form-control" id="marchio">
    	            </div>
    	        `;
    	        

    	        $('#additional-fields').html(additionalFieldsHtml);
               
               
    	        console.log('Checkboxes aggiunte al DOM:', additionalFieldsHtml);
        // Effettua una richiesta AJAX per ottenere i materiali
    	$.ajax({
    	    url: '${pageContext.request.contextPath}/Materiali',
    	    method: 'POST',
    	    dataType: 'json', // Specifica che ti aspetti una risposta JSON
    	    success: function(materials) {
    	       
                let container = document.getElementById('materiali');
               
    	        if (Array.isArray(materials)) {
    	            materials.forEach(function(item) {
                         console.log('[' + item.materiale + ']'); // Log per verificare la risposta
                         
                            let div = document.createElement('div');
                            div.className = "form-check";

                            let input = document.createElement('input');
                            input.className = "form-check-input";
                            input.type = "checkbox";
                            input.id = `materiale-${item.materiale}`; // Usa il valore dinamico
                            input.name = "materiale";
                            input.value = item.materiale;

                            let label = document.createElement('label');
                            label.className = "form-check-label";
                            label.setAttribute('for', `materiale-${item.materiale}`);

                            // Assegna il valore dinamico alla label
                            label.textContent = item.materiale; // O innerHTML se hai HTML complesso

                            // Appendi gli elementi nel DOM
                            div.appendChild(input);
                            div.appendChild(label);
                            container.appendChild(div);
                        

    	            });
    	        } else {
    	            console.error('La risposta non è un array:', materials);
    	        }

    	       
    	    },
    	    error: function(error) {
    	        console.error('Errore nel caricamento dei materiali', error);
    	        alert('Impossibile caricare i materiali. Riprova più tardi.');
    	    }
    	});

    }

 else {
        $('#additional-fields').empty();
    }
});



            // Gestione invio form per aggiungere prodotto
            $('#add-product-form').submit(function(e) {
                e.preventDefault();
                const productName = $('#product-name').val();
                const productType = $('#product-type').val();
                let additionalData = {};
                if (productType === 'libro') {
                    additionalData = {
                        Autore: $('#autore').val(),
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
