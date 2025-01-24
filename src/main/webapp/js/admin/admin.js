$(document).ready(function() {
	
    $('#autori').select2();
	$('#materiali').select2();

	
	
	// inserimento nuovo prodotto
	$('#add-product-form').submit(	function insertItem(){
		
		var formData = new FormData();
		
		
		
		var item = {
			nome: $("#nome").val(),
			prezzo: $("#prezzo").val(),
			quantity: $("#quantity").val(),
			itemType: $("#product-type").val(),
			descrizione: $("#description").val(),
			produttore: $('#editorDropdown').find('option:selected').attr('value')
		}
		
		// Condizionale per i campi specifici
	    if (item.itemType === "libro") {
	        item.autori = $("#autori").val();
	        item.ISBN = $("#ISBN").val();
			
			item.lingua = $("#lingua").val();
			item.pagine = $("#pagine").val();
			
	    } else if (item.itemType === "gadget") {
			item.materiali = $("#materiali").val();
	       	item.marchio = $("#marchio").val();
			item.modello = $("#modello").val();
			
	    }
		console.log(item);
		
		formData.append("item", JSON.stringify(item));
		const fileInput = $('#formFileSm')[0];
		if (fileInput.files.length > 0) {
		    formData.append('file', fileInput.files[0]); // Aggiunge il file al FormData
		}
		 
		
		$.ajax({
			url: "/Yoru/admin/InsertItem",
			method: "POST",
			data: formData,
			processData: false,  
		   	contentType: false,
			success: function(data){
				if(data.result){
					console.log("ok");
				}else{
					
				}
			}
		});
	});
	
	
	// load producers	
	$.ajax({
	    url: '/Yoru/Producers', // URL della servlet
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
		            <select id="autori" name="autori" class="select2" multiple="multiple">
		                <option value="">-- Seleziona un autore --</option>
		                <!-- Le opzioni verranno popolate tramite AJAX -->
		            </select>
		            <div>
	            </fieldset>
		            
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
		                <option value="Italiano">Italiano</option>
		                <option value="Inglese">Inglese</option>
		                <option value="Francese">Francese</option>
		                <option value="Spagnolo">Spagnolo</option>
		                <option value="Tedesco">Tedesco</option>
						<option value="Giapponese">Giapponese</option>
		                <!-- Puoi aggiungere altre lingue qui -->
		            </select>
		            </div>
					
		        `;
		        $('#additional-fields').html(additionalFieldsHtml);
				

				$('#autori').select2({
				// Effettua una richiesta AJAX per ottenere gli autori
				   ajax: {
				       url: '/Yoru/Autori',
				       method: 'POST',
				       dataType: 'json', 
					processResults: function (data) {
				      			return {
				              		results: data.map(item => ({ id: item.id, text: item.cognome }))
				          };
				      }
				   }
				});
	    } else if (selectedType === 'gadget') {
	         additionalFieldsHtml = `
	    	            <div class="form-group-editor">
								<label for="materiali">Seleziona i materiali:</label>
					            <select id="materiali" name="autori" class="select2" multiple="multiple">
		                		<option value="">-- Seleziona materiali --</option>
				                <!-- Le opzioni verranno popolate tramite AJAX -->
				            </select>
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
	    	        

	    	        $('#additional-fields').html(additionalFieldsHtml);
	               
	          
			$('#materiali').select2({
				ajax:{
					url: '/Yoru/Materiali',
					method: "POST",
					dataType: 'json', 
					processResults: function (data) {
	           			return {
		               		results: data.map(item => ({ id: item.materiale, text: item.materiale }))
			           };
			       }
					
				}
			})     

	    }else {
	        $('#additional-fields').empty();
	    }
	});

	           
});

var orderBy = "data"


