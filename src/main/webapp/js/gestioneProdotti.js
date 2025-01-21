$(document).ready(function() {
    $('.orange-button').click(function() {
        // Recupera i dati dal pulsante
        const skuProdotto = $(this).data('sku');
        const nomeProdotto = $(this).data('nome');
        const prezzoProdotto = $(this).data('prezzo');
        const quantitaProdotto = $(this).data('quantita');

        // Aggiorna il messaggio nel modale
        $('#editBookMessage').text(`Stai modificando il libro "${nomeProdotto}" con questo ID "${skuProdotto}"`);

        // Pre-compila i campi del modale con i valori attuali
        $('#book-price').val(prezzoProdotto);
        $('#book-quantity').val(quantitaProdotto);

        // Apri il modale
        $('#editBookModal').modal('show');
    });

    // Gestione del form di modifica
    $('#edit-book-form').submit(function(event) {
        event.preventDefault();

        // Recupera i valori dai campi del form
        const nuovoPrezzo = $('#book-price').val();
        const nuovaQuantita = $('#book-quantity').val();
	    const id=$('#editBookMessage').text().match(/ID "(\w+)"/)[1]; // Estrai lo SKU dal messaggio
	    
	    
        // Esegui la chiamata AJAX per aggiornare il libro
        $.ajax({
            url: 'UpdateItem', // Cambia questo URL con quello corretto
            method: 'POST',
            data: {
                sku:id,
                price: nuovoPrezzo,
                quantity: nuovaQuantita
            },
            success: function(response) {
                // Mostra un messaggio di successo o aggiorna la tabella
                if(response.result){
                $('#editBookModal').modal('hide');
                location.reload(); // Ricarica la pagina per riflettere i cambiamenti
           } else if(response.error){
			  console.error('Errore durante l\'aggiornamento del libro', error);
                alert('Si è verificato un errore. '+response.error);
		   }
           
           },
            error: function(error) {
                alert('Si è verificato un errore.'+error);
            }
        });
    });
});
