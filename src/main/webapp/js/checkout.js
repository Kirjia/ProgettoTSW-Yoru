


$(document).ready(function() {
        // Funzione per caricare i dati del carrello tramite AJAX
        function loadCart() {
            $.ajax({
                url: '../Carrello', // URL della servlet che restituisce il carrello
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
        function loadAddresses() {
            $.ajax({
                url: '../based/Indirizzi', // URL della servlet per gli indirizzi
                type: 'POST', 
                dataType: 'json', 
                success: function(response) {
                    var addresses = response.indirizzi;
                    var select = $('#addressDropdown');
                    select.empty();  // Pulisci il dropdown

                    
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

        loadAddresses(); // Carica gli indirizzi se l'utente è autenticato
		
		$('#confirm-payment').on('click', function(event){
			event.preventDefault(); 
			
			var address_id = $('#addressDropdown').val();
			
			$.ajax({
                url: '../based/CheckOut', 
                type: 'POST', 
				data: {addressId: address_id},
                dataType: 'json', 
                success: function(response) {
                    if(response.result){
						
						mostraBootstrapModalConRedirect(
						    "Grazie per aver completato il modulo!",
						    5000, // 5 secondi
						    "../home.jsp" // URL della home
						);
					}else{
						
					}
                },
                error: function(error) {
                    console.log('Errore nel recupero degli indirizzi:', error.responseText);
                }
            });
			
			
		});
		
		function mostraBootstrapModalConRedirect(messaggio, durata, url) {
		   
		    
		    const modalMessage = $('#modalMessage');
		    const countdownTimer = document.getElementById('countdownTimer');

		    // Imposta il messaggio e mostra il modal
		    modalMessage.textContent = messaggio;

		   
		    let tempoRimasto = durata / 1000; // Converti durata in secondi
		    countdownTimer.textContent = tempoRimasto;

		    const modalInstance = new bootstrap.Modal(document.getElementById("redirectModal"), {}); 
		    modalInstance.show(); // Mostra il modal

		    // Countdown e reindirizzamento
		    const intervallo = setInterval(() => {
		        tempoRimasto--;
		        countdownTimer.textContent = tempoRimasto;

		        if (tempoRimasto <= 0) {
		            clearInterval(intervallo);
		            modalInstance.hide(); 
		            window.location.href = url; // Reindirizza
		        }
		    }, 1000);
		}

		

		

       
    });