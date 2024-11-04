function insertItem(){
	
	var formData = new FormData();
	
	var fileInput = $("#fileInput");
	var file = fileInput.files[0];
	
	formData.append("file", file);
	
	var item = {
		nome: $("#nome").value,
		prezzo: $("#prezzo").value,
		quantity: $("#quantity").value,
		itemType: $("#itemType").value,
		descrizione: $("#descrizione").value,
		produttore: $("#produttore").value
	}
	
	// Condizionale per i campi specifici
    if (item.itemType === "libro") {
        item.author = document.getElementById("bookAuthor").value;
        item.ISBN = $("#ISBN").value;
		item.lingua = $("#lingua").value;
		item.pagine = $("#pagine").value;
		
    } else if (item.itemType === "gadgets") {
       	item.marchio = $("#marchio").value;
		item.modello = $("#modello").value;
		
    }
	
	formData.append("item", JSON.stringify(item));
	 
	
	$.ajax({
		url: "/Yoru/admin/InsertItem",
		method: "POST",
		data: formData,
		contentType: "application/json",
		success: function(data){
			if(data.result){
				
			}else{
				
			}
		}
	});
}