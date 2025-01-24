

$(document).ready(function() {
       var emailUtente = '<%=user.getEmail()%>';

       $.ajax({
           url: './Indirizzi',
           method: 'POST',
           
           dataType: 'json',
           success: function(response) {
               console.log(response);
               let indirizziHTML = '<h5>Indirizzi:</h5> <ul>';
               if (response.indirizzi && response.indirizzi.length > 0) {
                   $.each(response.indirizzi, function(index, indirizzo) {
                       indirizziHTML += '<li>' + indirizzo.via + ', ' + indirizzo.city + ' - ' + indirizzo.CAP + '</li>';
                   });
               } else {
                   indirizziHTML += '<li>Nessun indirizzo disponibile.</li>';
               }
               indirizziHTML += '</ul>';$(document).ready(function() {
    var emailUtente = '<%=user.getEmail()%>';

    // Funzione per caricare gli indirizzi
    function caricaIndirizzi() {
        // Resetta il contenitore degli indirizzi
        $('#indirizzi-container').html('');

        $.ajax({
            url: './Indirizzi',
            method: 'POST',
            dataType: 'json',
            success: function(response) {
                console.log(response);
                let indirizziHTML = '<h5>Indirizzi:</h5> <ul class="list-group">';
                if (response.indirizzi && response.indirizzi.length > 0) {
                    $.each(response.indirizzi, function(index, indirizzo) {
                        indirizziHTML += '<li class="list-group-item d-flex justify-content-between align-items-center">' +
                            indirizzo.via + ', ' + indirizzo.city + ' - ' + indirizzo.CAP +
                            ' <i class="fas fa-trash text-danger delete-indirizzo" data-id="' + indirizzo.id + '"></i></li>';
                    });
                } else {
                    indirizziHTML += '<li class="list-group-item">Nessun indirizzo disponibile.</li>';
                }
                indirizziHTML += '</ul>';
                $('#indirizzi-container').html(indirizziHTML);
            },
            error: function() {
                $('#indirizzi-container').html('<p>Errore nel caricamento degli indirizzi.</p>');
            }
        });
    }

    // Carica gli indirizzi all'avvio
    caricaIndirizzi();

    // Delegazione evento per eliminare un indirizzo
    $(document).on('click', '.delete-indirizzo', function() {
        let indirizzoId = $(this).data('id');

        if (confirm('Sei sicuro di voler eliminare questo indirizzo?')) {
            $.ajax({
                url: './DeleteAddress',
                method: 'POST',
                data: { id: indirizzoId },
                dataType: 'json',
                success: function(response) {
                    if (response.result) {
                        caricaIndirizzi(); // Ricarica gli indirizzi aggiornati
                    } else {
                        alert('Errore durante l eliminazione dell indirizzo.');
                    }
                },
                error: function() {
                    alert('Errore durante la chiamata al server.');
                }
            });
        }
    });
      // Apri il modale
            $('#open-modal').click(function () {
                $('#addAddressModal').modal('show');
            });
     // Gestisci il salvataggio dell'indirizzo
            $('#submitAddress').click(function () {
                // Raccogli i dati del form
                const formData = {
                    via: $('#via').val(),
                    provincia: $('#provincia').val(),
                    citta: $('#citta').val(),
                    CAP: $('#cap').val()
                };

                // Controlla che i campi siano validi
                if (!formData.via || !formData.provincia || !formData.citta || !formData.CAP) {
                    alert("Compila tutti i campi.");
                    return;
                }

                // Chiamata AJAX alla servlet
                $.ajax({
                    url: './AddAddress', // Percorso alla servlet
                    method: 'POST',
                    data: formData,
                    dataType: 'json',
                    success: function (response) {
                        if (response.result) {
                            alert("Indirizzo aggiunto con successo!");
                            caricaIndirizzi(); // Ricarica gli indirizzi aggiornati
                            $('#addAddressModal').modal('hide');
                            $('#addAddressForm')[0].reset(); // Resetta il form
                             
                        } else {
                            alert("Errore durante l'aggiunta dell'indirizzo. Riprova.");
                        }
                    },
                    error: function () {
                        alert("Errore durante la chiamata al server.");
                    }
                });
            });

    // Gestione modifica numero di telefono
    $('#edit-telefono').click(function() {
        var currentPhone = $('#telefono-value').text();
        var newPhone = prompt("Inserisci il nuovo numero di telefono:", currentPhone);
        if (newPhone !== null && newPhone !== "") {
            $('#telefono-value').text(newPhone);
            // Qui potresti aggiungere una chiamata AJAX per salvare il nuovo numero
        }
    });
});

               $('#indirizzi-container').html(indirizziHTML);
           },
           error: function() {
               $('#indirizzi-container').html('<p>Errore nel caricamento degli indirizzi.</p>');
           }
       });

      $(document).ready(function () {
    $('#editProfileForm').submit(function (e) {
        e.preventDefault();

        const nuovoTelefono = $('#telefono').val();
        const nuovaPassword = $('#password').val();
        const confermaPassword = $('#confirm-password').val();

        // Verifica che le password coincidano
        if (nuovaPassword !== confermaPassword) {
            alert('Le password non coincidono.');
            return;
        }

        // Invio della richiesta AJAX alla servlet
        $.ajax({
            url: 'UpdateProfile', // URL della servlet
            method: 'POST',
            data: {
                upNumber: nuovoTelefono,  // Nome parametro corrispondente alla servlet
                upPassword: nuovaPassword // Nome parametro corrispondente alla servlet
            },
            success: function (response) {
                if (response.result == true) { // Controlla il risultato dalla risposta JSON   
                    $('#editProfileModal').modal('hide');
                    $('#telefono-value').text(nuovoTelefono); // Aggiorna il numero di telefono nella vista
                    location.reload();
                } else {
                    alert('Errore durante l\'aggiornamento del profilo. Riprova più tardi.');
                }
            },
            error: function (xhr, status, error) {
                console.error('Errore durante l\'aggiornamento del profilo:', error);
                alert('Si è verificato un errore. Controlla la connessione o riprova più tardi.');
            }
        });
    });
});

   });