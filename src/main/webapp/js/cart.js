
$(document).ready(function(){
	
	
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
		                alert("Si è verificato un errore durante l'aggiunta al carrello." + error);

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
