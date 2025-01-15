$(document).ready(function(){
	
	var calls = 3;
	
	function loadSlider(){
		calls--;
		if(calls == 0){
			$('.owl-carousel').owlCarousel({
					  autoplay: true,
					  autoplayTimeout: 3000,
					  autoplayHoverPause: true,
					  loop: true,
					  margin: 50,
					  responsiveClass: true,
					  nav: false,
					  responsive: {
					    0: {
					      items: 1
					    },
					    568: {
					      items: 2
					    },
					    600: {
					      items: 2
					    },
					    1000: {
					      items: 3
					    }
					  }
				})
		}
	}
	
	$.ajax({
		url: "Bestseller",
		method:"POST",
		dataType: "json",
		success: function(response){
			let items = response.items;
			for(const x in items){
				
				let item = items[x];
				$("#best-carousel").append(
							`	<div class="item">
							<div class="item-body">
					    <a class="popup-text" href="Item?sku=${item.sku}">
					      <img class="carousel-img" src="files/images/${item.sku}.png">
					    </a>
						</div>
						<div class="item-footer">
							<h6 class="product-title">${item.title}</h6>
						</div>
					  </div>`)
				
			}
			
		},
		complete: function(xhr, status){
			loadSlider();
		}
	})
	
	$.ajax({
		url: "NewBooks",
		method:"POST",
		dataType: "json",
		success: function(response){
			let items = response.items;
			for(const x in items){
				
				let item = items[x];
				$("#new-books-carousel").append(
							`	<div class="item">
							<div class="item-body">
					    <a class="popup-text" href="Item?sku=${item.sku}">
					      <img class="carousel-img" src="files/images/${item.sku}.png">
					    </a>
						</div>
						<div class="item-footer">
							<h6 class="product-title">${item.title}</h6>
						</div>
					  </div>`)
				
			}
			
		},
		complete: function(xhr, status){
			loadSlider();
		}
	})
		
	$.ajax({
		url: "NewGadgets",
		method:"POST",
		dataType: "json",
		success: function(response){
			let items = response.items;
			for(const x in items){
				
				let item = items[x];
				$("#new-gadget-carousel").append(
							`	<div class="item">
							<div class="item-body">
					    <a class="popup-text" href="Item?sku=${item.sku}">
					      <img class="carousel-img" src="files/images/${item.sku}.png">
					    </a>
						</div>
						<div class="item-footer">
							<h6 class="product-title">${item.title}</h6>
						</div>
					  </div>`)
				
			}
			
		},
		complete: function(xhr, status){
			loadSlider();
		}
		
	})
	
	
	
	
});

