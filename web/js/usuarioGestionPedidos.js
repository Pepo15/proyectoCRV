               $(function () {//Ready
                    
                    $(".coordenadasMapa").on("click", function () {
                        iniciaSesion();
                    });
                    
                    function iniciaSesion() {
                        $("#modalMapa").css("display", "block");
                    }
                    var modal = document.getElementById('modalMapa');
                    window.onclick = function (event) {
                        if (event.target == modal) {
                            $("#modalMapa").css("display", "none");
                        }
                    }

                    var span = document.getElementsByClassName("close")[1];
                    span.onclick = function () {
                        $("#modalMapa").css("display", "none");
                    }


                    var latitud;
                    var longitud;
                    var poblacion;
                    var postal;
                    

                    $("#recargaPedidos").on("click", ".coordenadasMapa", function () {
                        var codigoPedido = $(this).attr("id");
                            var datos = 'codigoPoblacion=' + codigoPedido;
                            $.ajax({
                                url: "CompruebaDatos",
                                dataType: 'json',
                                async: false,
                                data: datos,
                                success: function (result) {
                                    //Recorro el resultado. i->es la clave, field->es el valor 
                                    $.each(result, function (i, field) {

                                        //Cogemos el select de las provincias y le añadimos los options
                                        if (i == "Latitud") {

                                            latitud = field;
                                        }
                                        if (i == "Longitud") {

                                            longitud = field;
                                        }
                                        if (i == "Poblacion") {

                                            poblacion = field;
                                        }
                                        if (i == "Postal") {

                                            postal = field;
                                        }
                                    });

                                    //Pinto mapa
                                    var map = $("#mapa").geomap({
                                        center: [longitud, latitud],
                                        zoom: 7
                                    });
                                    $("#mapa").geomap("append",
                                            {
                                                type: "Point",
                                                coordinates: [longitud, latitud] // Coordinates are lng/lat
                                            }
                                    );
                                }
                            });


                            $("#poblaMapa").text("Poblacion: " + poblacion);

                            $("#cpMapa").text("Cp: " + postal);


                        });//Click boton




                    });//Ready