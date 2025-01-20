$(document).ready(function () {
  $(".addToCart").on("submit", function (event) {
    event.preventDefault();

    var skuValue = $(this).find("#sku").val();
    var quantityValue = $(this).find("#quantity").val();

    $.ajax({
      url: "AddToCart",
      method: "POST",
      data: {
        sku: skuValue,
        quantity: quantityValue,
      },
      success: function (data) {
        var jsonData = data.response[0];
        if (jsonData) {
          showModal("Prodotto aggiunto al carrello con successo!");
        } else {
          showModal("Si è verificato un errore durante l'aggiunta al carrello.", true);
        }
      },
      error: function (xhr, status, error) {
        showModal("Si è verificato un errore durante l'aggiunta al carrello: " + error, true);
      },
    });
  });

  // Funzione per mostrare il modal con un messaggio personalizzato
  function showModal(message, isError = false) {
    const modalBody = $("#modalFeedback .modal-body");
    const modalTitle = $("#modalFeedbackLabel");

    if (isError) {
      modalTitle.text("Errore");
      modalBody.html(`<p class="text-danger">${message}</p>`);
    } else {
      modalTitle.text("Notifica");
      modalBody.html(`<p class="text-success">${message}</p>`);
    }

    const modalElement = $("#modalFeedback");

    // Mostra il modal
    modalElement.modal("show");

    // Imposta un timer per chiudere il modal automaticamente dopo 2 secondi
    setTimeout(function () {
      modalElement.modal("hide");
    }, 2000);
  }
});
