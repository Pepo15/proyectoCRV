$(document).ready(function () {
    
    $("#myInput").on("keyup", function () {
        filtrado();
    });
 function filtrado() {
     
     
     $("#telefonos #contenedorTelefono").hide();
     
     var textoEscrito = $("#myInput").val().toLowerCase();
        var listaTelefonos = document.querySelectorAll("#nombreTelefono");
        listaTelefonos.forEach(function (valor, index) {
            var mostrar = valor.parentElement.parentElement.parentElement;
            var estado = mostrar.style.display;
            var nombreTelefono = valor.value;
            var posicionEncontrado = nombreTelefono.toString().toLowerCase().indexOf(textoEscrito);
            if (estado == "none") {
                if (posicionEncontrado > -1) {
                     $(mostrar).show();
                } else {
                   
                }
            }

        });
 }
});
