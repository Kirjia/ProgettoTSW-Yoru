
$('.orange-button')
	.click(
		function() {
			const skuProdotto = $(this).data('sku');
	
			// Esegui una chiamata AJAX per ottenere i dettagli del prodotto e riempi i campi del modale
			$.ajax({
				url : 'GetProductDetails', // Cambia questo URL con quello corretto per recuperare i dettagli del prodotto
				method : 'GET',
				data : {
					sku : skuProdotto
				},
				success : function(data) {
					// Pre-compila i campi del modale con i dati del prodotto
					$('#new-product-name').val(
							data.nome);
					$('#new-product-price').val(
							data.prezzo);
					$('#new-product-quantity').val(
							data.quantita);
			
					// Apri il modale
					$('#addNewProductModal').modal(
							'show');
				},
				error : function(error) {
					console
							.error(
									'Errore durante il recupero dei dettagli del prodotto',
									error);
					}
	});
});