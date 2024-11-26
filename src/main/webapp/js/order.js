$(document).ready(function () {
    let sortOrder = 'asc'; // Ordine predefinito
    let lastSortField = 'id'; // Ultimo campo ordinato

    // Funzione per caricare la lista degli ordini tramite AJAX
    function loadOrders(sortField = 'id', sortOrder = 'asc') {
        $.ajax({
            url: 'admin/AllOrders', // Percorso della servlet AllOrders
            method: 'GET',
            data: { sortField: sortField, sortOrder: sortOrder }, // Passa il campo e l'ordine di ordinamento
            dataType: 'json',
            success: function (data) {
                var tbody = $('#ordersTable tbody');
                tbody.empty(); // Svuota la tabella prima di riempirla

                // Itera sui dati JSON e aggiungi una riga per ogni ordine
                data.forEach(function (order) {
                    var row = '<tr class="order-row" data-order-id="' + order.id + '">' +
                        '<td>' + order.id + '</td>' +
                        '<td>' + order.data + '</td>' +
                        '<td>' + order.totale + ' €</td>' +
                        '<td>' + order.email + '</td>' +
                        '<td>' + order.id_pagamento + '</td>' +
                        '</tr>' +
                        '<tr class="details-row" id="details-' + order.id + '">' +
                        '<td colspan="5" class="details-cell">Caricamento dettagli...</td>' +
                        '</tr>';
                    tbody.append(row);
                });
            },
            error: function (xhr, status, error) {
                console.error("Errore durante il recupero degli ordini:", status, error);
                console.log("Risposta:", xhr.responseText); // Log della risposta per ulteriore debug
                alert("Impossibile recuperare gli ordini. Errore: " + error);
            }
        });
    }

    // Gestione del click sull'intestazione della colonna
    $('#ordersTable thead').on('click', 'th', function () {
        var columnIndex = $(this).index(); // Indice della colonna cliccata
        var sortField;

        // Mappa dell'indice della colonna al nome del campo nella servlet
        switch (columnIndex) {
            case 0:
                sortField = 'id';
                break;
            case 1:
                sortField = 'data_pagamento';
                break;
            case 2:
                sortField = 'importo_pagamento';
                break;
            case 3:
                sortField = 'email';
                break;
            case 4:
                sortField = 'id_pagamento';
                break;
            default:
                sortField = 'id'; // Campo predefinito
        }

        // Cambia direzione di ordinamento se si clicca sulla stessa colonna
        if (lastSortField === sortField) {
            sortOrder = sortOrder === 'asc' ? 'desc' : 'asc';
        } else {
            sortOrder = 'asc'; // Reset a ascendente se cambia colonna
        }

        lastSortField = sortField; // Aggiorna il campo ordinato

        // Carica gli ordini ordinati per il campo selezionato e direzione
        loadOrders(sortField, sortOrder);
    });

    // Funzione per mostrare i dettagli di un ordine quando si clicca su una riga
    $('#ordersTable').on('click', '.order-row', function () {
        var orderId = $(this).data('order-id');
        var detailsRow = $('#details-' + orderId);

        if (detailsRow.is(':visible')) {
            // Nascondi la riga dei dettagli se è già visibile
            detailsRow.hide();
        } else {
            // Mostra i dettagli solo se la riga è nascosta
            detailsRow.show();

            // Effettua la richiesta AJAX alla servlet OrderDetails per ottenere i dettagli dell'ordine
            $.ajax({
                url: 'admin/OrderDetails', // Percorso della servlet OrderDetails
                method: 'POST',
                data: { orderId: orderId },
                dataType: 'json',
                success: function (details) {
                    // Costruisci il contenuto della tendina dei dettagli
                    var detailsHtml = '<ul>';
                    details.forEach(function (item) {
                        detailsHtml += '<li>' +
                            'SKU: ' + item.SKU + ', ' +
                            'Nome: ' + item.nome + ', ' +
                            'Quantità: ' + item.quantity + ', ' +
                            'Prezzo: ' + item.prezzo + ' €' +
                            '</li>';
                    });
                    detailsHtml += '</ul>';
                    detailsRow.find('.details-cell').html(detailsHtml);
                },
                error: function (xhr, status, error) {
                    console.error("Errore durante il recupero dei dettagli dell'ordine:", error);
                    detailsRow.find('.details-cell').html("Errore nel caricamento dei dettagli.");
                }
            });
        }
    });

    // Carica la lista degli ordini all'avvio della pagina
    loadOrders();
});
