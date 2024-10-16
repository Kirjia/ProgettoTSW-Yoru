function insertItem(){
	
	 
	
	$.ajax({
		url: "/Yoru/admin/InsertItem",
		method: "POST",
		data: JSON.stringify(),
		contentType: "application/json",
		success: function(data){
			if(data.result){
				
			}else{
				
			}
		}
	});
}