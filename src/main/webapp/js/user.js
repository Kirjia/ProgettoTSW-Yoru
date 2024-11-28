

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
               indirizziHTML += '</ul>';
               $('#indirizzi-container').html(indirizziHTML);
           },
           error: function() {
               $('#indirizzi-container').html('<p>Errore nel caricamento degli indirizzi.</p>');
           }
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