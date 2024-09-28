
$(document).ready(function(){
	
	
	$("#aggiungi-al-carrello").on("submit", function(event){
		event.preventDefault(); 

		        
        var sku = $(document.activeElement).attr("id");
		$.ajax({
                url: "",
                method: "POST",
                data: JSON.stringify(sku),
                contentType: "application/json",
                success: function(data) {
					if(data.contains("done"))
						alert("Prodotto aggiunto al carrello!");
					
					alert("Si è verificato un errore durante l'aggiunta al carrello.");
                },
				error: function(xhr, status, error) {
		                alert("Si è verificato un errore durante l'aggiunta al carrello.");
		            }
            });
	})
})