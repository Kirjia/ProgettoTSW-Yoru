
$(document).ready(function(){
	
	$("#addToCart").on("click", function(event){
			event.preventDefault(); 

			
			
			var skuValue = $("#sku").val();
			var quantityValue = $("#quantity").val();

			console.log(skuValue);
			console.log(quantityValue);
			$.ajax({
				url: "AddToCart",
				method: "POST",
				
				data: {
					sku: skuValue,
					quantity: quantityValue
				},
				
				success: function(data){
					var jsonData = data.response[0];
					if(jsonData){
						alert("Prodotto aggiunto al carrello!");
					}else{
					alert("Si è verificato un errore durante l'aggiunta al carrello.");
					}
                },
				error: function(xhr, status, error){
					alert("Si è verificato un errore durante l'aggiunta al carrello " + error);
				}
						
			});
				
				
		})
	
})

