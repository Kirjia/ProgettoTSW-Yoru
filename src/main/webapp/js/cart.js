
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
		$.post(
       			"https://localhost/Yoru/AddToCart",{
					sku: item
				}, (function(data) {
				var jsonData = data.response[0];
					if(jsonData){
						alert("Prodotto aggiunto al carrello!");
					}else{
					alert("Si è verificato un errore durante l'aggiunta al carrello.");
					}
                }))
				.fail(function(error) {
		                alert("Si è verificato un errore durante l'aggiunta al carrello " + error);

            });
	})
})

$(document).on('click', '.remove-item', function() {
	var sku = $(this).attr("id");
	var totStr = $("#cart_tot").text();
	var tot = parseFloat(totStr.replace("€", ""));
	var div = $("#item-"+sku);
	$.post(
			"https://localhost/Yoru/RemoveFromCart",{
				sku: sku
			}, (function(data) {
			var jsonData = data.response[0];
				if(jsonData){
					div.fadeOut(400, function() {
						var str = $('#sub-'+sku).text().replace('Subtotale: €', '');
						
						var subtotaleRimosso = parseFloat(str);
					    tot -= subtotaleRimosso;
				        div.remove(); // Rimuovi l'elemento solo dopo che è scomparso
						
					    
					    $('#cart_tot').html(`€${tot.toFixed(2)}`); // Aggiorna il totale
						if(tot <= 0){
							$('#cart-slot').append(
								`
								<p class="empty-carrello">Il carrello è vuoto!</p>
								`
							);
						}
				    }); // Rimuove l'elemento dall'HTML
				    
					
					
				}else{
				alert("Si è verificato un errore durante la rimozione dal carrello.");
				}
	        }))
			.fail(function(error) {
	                alert("Si è verificato un errore durante l'aggiunta al carrello." + error);

	    });
    
});



				
		
