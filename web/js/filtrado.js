
$(document).ready(function () {
    var min = 0;
    var max = 0;
    $(" :checkbox").click(function () {
        filtrado();
    });
    $("#slider").on('slidestop', function (event) {

        filtrado();
    });
    $("#myInput").on("keyup", function () {
        filtrado();
    });
    $(".cabecera img").on("click", function () {
        var padre = $(this).parent().parent().find(".contenido");
        if ($(padre).is(":visible")) {
            $(this).attr('src', 'imagenes/Fotos/down.png');
            padre.hide();
        } else {
            $(this).attr('src', 'imagenes/Fotos/up.png');
            padre.show();
        }

    });
    function filtrado() {
        var objeto = {};
        var lista1 = {};
        var lista2 = {};
        var lista3 = {};
        $("#telefonos #contenedorTelefono").hide();
        //soooooooo
        var listaSo = $("#soFiltrado :checkbox:checked").size();
        if (listaSo > 1) {
            $("#soFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
            $("#soFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //soooooooo

        //marca
        var listaMarca = $("#marcaFiltrado :checkbox:checked").size();
        if (listaMarca > 1) {
            $("#marcaFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
            $("#marcaFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //marca

        //raaam
        var listaRam = $("#ramFiltrado :checkbox:checked").size();
        if (listaRam > 1) {
            $("#ramFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
            $("#ramFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //raaam

        //precio
        var listaPrecios = $("#precioFiltrado :checkbox:checked").size();
        if (listaAlmacenamiento > 1) {
            $("#almacenamientoFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
            $("#almacenamientoFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //precio

        //pulgadas
        var listaPulgadas = $("#pulgadasFiltrado :checkbox:checked").size();
        if (listaPulgadas > 1) {
            $("#pulgadasFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
            $("#pulgadasFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //pulgadas

        //almaaa
        var listaAlmacenamiento = $("#almacenamientoFiltrado :checkbox:checked").size();
        if (listaAlmacenamiento > 1) {
            $("#almacenamientoFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
            $("#almacenamientoFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //almaaa

        //camaraTrasera
        var listaCamaraTrasera = $("#camaraTraseraFiltrado :checkbox:checked").size();
        if (listaCamaraTrasera > 1) {
            $("#camaraTraseraFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
            $("#camaraTraseraFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //camaraTrasera

        //camaraDelantera
        var listaCamaraDelantera = $("#camaraDelanteraFiltrado :checkbox:checked").size();
        if (listaCamaraDelantera > 1) {
            $("#camaraDelanteraoFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
            $("#camaraDelanteraFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //camaraDelantera

        //bateria
        var listaBateria = $("#bateriaFiltrado :checkbox:checked").size();
        if (listaBateria > 1) {
            $("#bateriaFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
            $("#bateriaFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //bateria

        //procesador
        var listaProcesador = $("#procesadorFiltrado :checkbox:checked").size();
        if (listaProcesador > 1) {
            $("#procesadorFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
            $("#procesadorFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //procesador

        //resolucion
        var listaResolucion = $("#resolucionFiltrado :checkbox:checked").size();
        if (listaResolucion > 1) {
            $("#resolucionFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
            $("#resolucionFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //resolucion

        //color
        var listaColor = $("#colorFiltrado :checkbox:checked").size();
        if (listaColor > 1) {
            $("#colorFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
            $("#colorFiltrado :checkbox:checked").each(function () {
                var idDivFiltro = $(this).attr("id");
                var idDivFiltroValor = $(this).val();
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //color

        //wifi
        $("#wifiFiltrado :checkbox:checked").each(function () {
            var idDivFiltro = $(this).attr("id");
            var idDivFiltroValor = "1";
            lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
        });
        //wifi

        //detectorHuella
        $("#detectorHuellasFiltrado :checkbox:checked").each(function () {
            var idDivFiltro = $(this).attr("id");
            var idDivFiltroValor = "1";
            lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
        });
        //detectorHuella

        //dualSim
        $("#dualSimFiltrado :checkbox:checked").each(function () {
            var idDivFiltro = $(this).attr("id");
            var idDivFiltroValor = "1";
            lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
        });
        //dualSim

        //sd
        $("#sdFiltrado :checkbox:checked").each(function () {
            var idDivFiltro = $(this).attr("id");
            var idDivFiltroValor = "1";
            lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
        });
        //sd

        //bluetooth
        $("#bluetoothFiltrado :checkbox:checked").each(function () {
            var idDivFiltro = $(this).attr("id");
            var idDivFiltroValor = "1";
            lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
        });
        //bluetooth

        //nfc
        $("#nfcFiltrado :checkbox:checked").each(function () {
            var idDivFiltro = $(this).attr("id");
            var idDivFiltroValor = "1";
            lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
        });
        //nfc

        //3g
        $("#g3Filtrado :checkbox:checked").each(function () {
            var idDivFiltro = $(this).attr("id");
            var idDivFiltroValor = "1";
            lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
        });
        //3g

        //4g
        $("#g4Filtrado :checkbox:checked").each(function () {
            var idDivFiltro = $(this).attr("id");
            var idDivFiltroValor = "1";
            lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
        });
        //4g

        if (Object.keys(lista1).length === 1 && Object.keys(lista2).length === 0) {

            $.each(lista1, function (clave1, valor1) {
                var mostrar = "#" + clave1 + "Telefono" + valor1;
                $(mostrar).parent().parent().parent().show();
            });
        } else if (Object.keys(lista1).length >= 2 && Object.keys(lista2).length === 0) {

            var contador = 0;
            var idMostrar = "";
            $.each(lista1, function (clave1, valor1) {
                if (contador === 0) {
                    idMostrar += "#" + clave1 + "Telefono" + valor1;
                } else {
                    idMostrar += " ~ #" + clave1 + "Telefono" + valor1;
                }
                contador++;
            });
            $(idMostrar).parent().parent().parent().show();
        } else if (Object.keys(lista1).length === 0 && Object.keys(lista2).length >= 2) {


            $.each(lista2, function (clave2, valor2) {
                var mostrar = "#" + clave2 + "Telefono" + valor2;
                $(mostrar).parent().parent().parent().show();
            });
        } else {
            $.each(lista2, function (clave2, valor2) {
                var mostrar = "";
                lista3[clave2] = valor2;
                $.each(lista1, function (clave1, valor1) {
                    lista3[clave1] = valor1;
                });
                var ordered = {};
                Object.keys(lista3).sort().forEach(function (key) {
                    ordered[key] = lista3[key];
                });
                var contador = 0;
                $.each(ordered, function (clave1, valor1) {
                    if (contador == 0) {
                        mostrar = "#" + clave1 + "Telefono" + valor1;
                        contador++;
                    } else {
                        mostrar = mostrar + " ~ #" + clave1 + "Telefono" + valor1;
                    }
                });
                $(mostrar).parent().parent().parent().show();
                lista3 = {};
            });
        }
        var listaPrecios = document.querySelectorAll("#precio");
        min = $("#txt6").val();
        max = $("#txt7").val();
        listaPrecios.forEach(function (valor, index) {
            var mostrar = valor.parentElement.parentElement.parentElement;
            var estado = mostrar.style.display;
            var precio = parseInt(valor.value);
            if (Object.keys(lista1).length > 0 || Object.keys(lista2).length > 0) {
                if (estado == "block") {
                    if (precio >= min) {

                        if (max >= precio) {

                            $(mostrar).show();
                        } else {
                            $(mostrar).hide();
                        }
                    } else {
                        $(mostrar).hide();
                    }
                }
            } else {
                if (precio >= min) {
                    if (max >= precio) {
                        $(mostrar).show();
                    }
                }
            }

        });
        var textoEscrito = $("#myInput").val().toLowerCase();
        var listaTelefonos = document.querySelectorAll("#nombreTelefono");
        listaTelefonos.forEach(function (valor, index) {
            var mostrar = valor.parentElement.parentElement.parentElement;
            var estado = mostrar.style.display;
            var nombreTelefono = valor.value;
            var posicionEncontrado = nombreTelefono.toString().toLowerCase().indexOf(textoEscrito);
            if (estado == "block") {
                if (posicionEncontrado > -1) {
                } else {
                    $(mostrar).hide();
                }
            }

        });
    }

}); //(ready


