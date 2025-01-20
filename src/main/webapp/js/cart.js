
$(document).ready(function(){
	
	$.ajax({
		url: "Carrello",
		method: "POST",
		dataType: "json",
		success: function(data){
			var cart = data.cart;
			if(cart != null){
				var tot = 0;
				cart.forEach(item => {
					var subtotale = item.prezzo * item.quantity;
					tot += subtotale;
					$('#cart-slot').append(`				
						<div id="item-${item.SKU}" class="cart-item">
		 					<div class="row border-bottom">
		                        <div class="col-md-4">
		                            <img src="files/images/${item.SKU}.png" class="img-fluid" alt="${item.nome}" onerror="this.src='files/images/err.png'"/>
		                        </div>
		                        <div class="col-md-5">
		                            <h5>${item.nome}</h5>
		                            <p>Quantità: ${item.quantity}</p>
		                        </div>
		                        <div class="col-md-3 d-flex align-items-end justify-content-end">
		                        
		                      	  <div class="text-right mt-3">
									<button id="${item.SKU}" class="btn btn-remove remove-item">Rimuovi dal carrello</button>
								  </div>
		                        
									<p id="sub-${item.SKU}" class="text-right subtotale-item">Subtotale: €${subtotale.toFixed(2)}</p>
										
								</div>
		                    </div>
		                	</div>
						`
						);
						
					
				});
				
				$("#cart_tot").html(`€${tot.toFixed(2)}`)
				}else{
					$('#cart-slot').append(
						`
						<p class="empty-carrello">Il carrello è vuoto!</p>
						`
					);
				}
		},
		error: function(xhr, status, error){
			console.error('Errore nel caricamento del carrello:', error);
		}
	})
	
	

    $(".aggiungi-al-carrello").on("click", function(event){
        event.preventDefault(); 
        
        var item = $(document.activeElement).attr("id");
        var productType = $(this).data("type"); // Ottieni il tipo di prodotto (libro o gadget)
        
        $.post(
            "https://localhost/Yoru/AddToCart", {
                sku: item
            }, (function(data) {
                var jsonData = data.response[0];
                if (jsonData) {
                    // A seconda del tipo di prodotto, mostra un messaggio diverso
                    var message = (productType === "libro") 
                        ? "Il prodotto è stato aggiunto al carrello con successo!" 
                        : "Il gadget è stato aggiunto al carrello con successo!";
                    showModal(message);
                } else {
                    showModal("Si è verificato un errore durante l'aggiunta al carrello.");
                }
            }))
            .fail(function(error) {
                showModal("Si è verificato un errore durante l'aggiunta al carrello.");
            });
    });

    // Funzione per mostrare il modal con il messaggio dinamico
    function showModal(message) {
    $('#cartModal .modal-body').text(message);
    $('#cartModal').modal('show'); // Usa 'show' per mostrare il modal
}


})

$(document).on("click", ".remove-item", function () {
  var sku = $(this).attr("id");
  var totStr = $("#cart_tot").text();
  var tot = parseFloat(totStr.replace("€", ""));
  var div = $("#item-" + sku);

  $.post(
    "https://localhost/Yoru/RemoveFromCart",
    {
      sku: sku,
    },
    function (data) {
      var jsonData = data.result[0];
      var modalBody = $("#cartModal .modal-body");

      if (jsonData) {
        div.fadeOut(400, function () {
          var str = $("#sub-" + sku)
            .text()
            .replace("Subtotale: €", "");
          var subtotaleRimosso = parseFloat(str);
          tot -= subtotaleRimosso;
          div.remove();

          $("#cart_tot").html(`€${tot.toFixed(2)}`); // Aggiorna il totale
          if (tot <= 0) {
            $("#cart-slot").append(`<p class="empty-carrello">Il carrello è vuoto!</p>`);
          }
        });

        modalBody.html("Prodotto rimosso dal carrello!");
      } else {
        modalBody.html("Si è verificato un errore durante la rimozione dal carrello.");
      }

      // Mostra il modal
      $("#cartModal").modal("show");
    }
  ).fail(function (error) {
    var modalBody = $("#cartModal .modal-body");
    modalBody.html("Si è verificato un errore durante la rimozione dal carrello: " + error);

    // Mostra il modal
    $("#cartModal").modal("show");
  });
});



				
		
