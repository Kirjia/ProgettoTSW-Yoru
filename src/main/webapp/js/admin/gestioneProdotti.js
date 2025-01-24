$(document).ready(function () {
    // Gestione pulsante di modifica
    $('.orange-button').click(function () {
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
    $('#edit-book-form').submit(function (event) {
        event.preventDefault();

        // Recupera i valori dai campi del form
        const nuovoPrezzo = $('#book-price').val();
        const nuovaQuantita = $('#book-quantity').val();
        const id = $('#editBookMessage').text().match(/ID "(\w+)"/)[1]; // Estrai lo SKU dal messaggio

        // Esegui la chiamata AJAX per aggiornare il libro
        $.ajax({
            url: 'UpdateItem', // Cambia questo URL con quello corretto
            method: 'POST',
            data: {
                sku: id,
                price: nuovoPrezzo,
                quantity: nuovaQuantita
            },
            success: function (response) {
                // Mostra un messaggio di successo o aggiorna la tabella
                if (response.result) {
                    $('#editBookModal').modal('hide');
                    location.reload(); // Ricarica la pagina per riflettere i cambiamenti
                } else if (response.error) {
                    console.error('Errore durante l\'aggiornamento del libro', response.error);
                    alert('Si è verificato un errore. ' + response.error);
                }
            },
            error: function (error) {
                alert('Si è verificato un errore.' + error);
            }
        });
    });

    // Gestione pulsante di eliminazione
    $('.red-button').click(function () {
        // Recupera lo SKU dal pulsante
        const skuProdotto = $(this).data('sku');

        // Mostra una conferma prima di procedere
        const conferma = confirm(`Sei sicuro di voler eliminare il prodotto con SKU "${skuProdotto}"?`);
        if (conferma) {
            // Chiama la funzione deleteProduct
            deleteProduct(skuProdotto);
        }
    });
});

// Funzione per eliminare un prodotto
function deleteProduct(sku) {
    $.ajax({
        url: 'RemoveItem', // Servlet per eliminare un prodotto
        method: 'POST',
        data: { sku: sku },
        success: function (response) {
            // Ricarica la pagina per riflettere le modifiche
            if (response.result) {
                location.reload();
            } else if (response.error) {
                console.error('Errore durante l\'eliminazione del prodotto', response.error);
                alert('Si è verificato un errore: ' + response.error);
            }
        },
        error: function (error) {
            console.error('Errore durante l\'eliminazione del prodotto', error);
            alert('Si è verificato un errore durante l\'eliminazione.');
        }
    });
}
