
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
						<div class="cart-items">
		 					<div class="row border-bottom">
		                        <div class="col-md-4">
		                            <img src="files/images/${item.SKU}.jpg" class="img-fluid" alt="${item.nome}" onerror="this.src='files/images/err.jpg'"/>
		                        </div>
		                        <div class="col-md-5">
		                            <h5>${item.nome}</h5>
		                            <p>Quantità: ${item.quantity}</p>
		                        </div>
		                        <div class="col-md-3 d-flex align-items-end justify-content-end">
		                        
		                      	  <div class="text-right mt-3">
									<button class="btn btn-remove">Rimuovi dal carrello</button>
								  </div>
		                        
									<p class="text-right subtotale-item">subtotale: €${subtotale.toFixed(2)}</p>
										
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

function removeFromCart(){
				$.post(
	       			"https://localhost/Yoru/RemoveFromCart",{
						sku: item
					}, (function(data) {
					var jsonData = data.response[0];
						if(jsonData){
							alert("Prodotto rimosso dal carrello!");
						}else{
						alert("Si è verificato un errore durante la rimozione dal carrello.");
						}
	                }))
					.fail(function(error) {
			                alert("Si è verificato un errore durante l'aggiunta al carrello." + error);

	            });
		}
