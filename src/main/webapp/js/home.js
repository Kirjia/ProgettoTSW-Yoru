$(document).ready(function(){
var multipleCardCarousel = document.querySelector(
  "#carouselNuoviArrivi"
);

let items = document.querySelectorAll('.carousel .carousel-item')

items.forEach((el) => {
    const minPerSlide = 4
    let next = el.nextElementSibling
    for (var i=1; i<minPerSlide; i++) {
        if (!next) {
            // wrap carousel by using first child
        	next = items[0]
      	}
        let cloneChild = next.cloneNode(true)
        el.appendChild(cloneChild.children[0])
        next = next.nextElementSibling
    }
})
if (window.matchMedia("(min-width: 768px)").matches) {
	var carousel = new bootstrap.Carousel(multipleCardCarousel, {
	    interval: false,
	  });
  var carouselWidth = $(".carousel-inner")[0].scrollWidth;
  var cardWidth = $(".carousel-item").width();
  var scrollPosition = 0;
  $("#carouselNuoviArrivi .carousel-control-next").on("click", function () {
    if (scrollPosition < carouselWidth - cardWidth * 4) {
      scrollPosition += cardWidth;
      $("#carouselExampleControls .carousel-inner").animate(
        { scrollLeft: scrollPosition },
        600
      );
    }
  });
  $("#carouselNuoviArrivi .carousel-control-prev").on("click", function () {
    if (scrollPosition > 0) {
      scrollPosition -= cardWidth;
      $("#carouselNuoviArrivi .carousel-inner").animate(
        { scrollLeft: scrollPosition },
        600
      );
    }
  });
} else {
  $(multipleCardCarousel).addClass("slide");
}
});

