  $(document).ready(function () {

                //Con el .on, podemos añadirle cualquier evento "keyup" , "keypress" , "keydown" en vez de poner .keyup , .keypress
                $("#myInput").on("keyup", function () {
                    //Aquí el $(this) significa esto: $("#myInput"), el valor de lo que escribimos en el input
                    var textoEscrito = $(this).val().toLowerCase();
                    $("#tablaR tbody tr").filter(function () {
                        //Hacemos esto para filtar por columnas

                        //Variable que guarda el contenido la celdas que contempla las columnas seleccionadas para filtrar
                        var textoCeldas = "";

                        //Variable que guarda #myTable tbody tr es decir la fila ya que al meternos en el each perderemos cambia el this
                        var fila = this;

                        //Cogemos los checkbox que estan checkeados, esto devuelve una lista, la recorremos
                        $(":checkbox:checked").each(function () {
                            //Guardamos el id de la columna que hemos seleccionado, con el attr accedemos al atributo indicado en este caso el id
                            //Que empieza en 0 así coincide el id con la posición de la columna	
                            var posicionColumna = $(this).val();
                            //Añadimos a la variable textoCeldas lo que contiene cada celda de la columna seleccionada
                            textoCeldas += fila.cells[posicionColumna].textContent;
                        })


                        //Cogemos el valor de la posicion de las letras, si no las encuentras es -1
                        var posicionEncontrado = textoCeldas.toLowerCase().indexOf(textoEscrito);

                        //Comprobamos que radio esta seleccionado si da verdadero sera el primero y lo ocultaremos si es falso lo pondrá en rojo

                        $(this).css("color", "black");
                        //Este metodo si se le pasa un parametro verdadero muestra el contenido(la deja como esta), si da falso la oculta
                        $(this).toggle(posicionEncontrado > -1)



                    });
                });

                //Si hacemos el click en un radio llamamos otra vez a la funcion del keyup que hemos programado
                $(":checkbox").click(function () {
                    $("#myInput").trigger("keyup");
                });

                //Si hacemos el click en un checkbox llamamos otra vez a la funcion del keyup que hemos programado
                $(":checkbox").click(function () {
                    $("#myInput").trigger("keyup");
                });

                //Mostrar imagen en grande
                $().fancybox({
                    selector: '[data-fancybox="showImg"]'
                });

            });//(ready

