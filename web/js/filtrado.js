
$(document).ready(function () {
    var min = 0;
    var max = 0;
    $(".ui-slider-range").css("width","100%");
            $("#slider div").css("left","0%");
            $("#slider span:nth-child(2)").css("left","0%");
            $("#slider span:nth-child(3)").css("left","100%");
            $("#displayRange").text("Entre 0 y 1500");
            $("#txt7").val(0);
         $("#txt7").val(1500);
         $("#myInput").val("");
    $(" :checkbox").click(function () {
        filtrado();
       paginar();
       $(".container").pagify(9, ".single-item");
      
    });
    $("#resetearFiltro").click(function () {
      $(":checkbox:checked").each(function () {
               $(this).prop('checked', false);
            });
            $(".ui-slider-range").css("width","100%");
            $("#slider div").css("left","0%");
            $("#slider span:nth-child(2)").css("left","0%");
            $("#slider span:nth-child(3)").css("left","100%");
            $("#displayRange").text("Entre 0 y 1500");
            $("#txt7").val(0);
         $("#txt7").val(1500);
         $("#myInput").val("");
            filtrado();
       paginar();
       $(".container").pagify(9, ".single-item");
      
    });
    $("#slider").on('slidestop', function (event) {
       filtrado();
       paginar();
       $(".container").pagify(9, ".single-item");
    });
    $("#myInput").on("keyup", function () {
        filtrado();
        paginar();
        $(".container").pagify(9, ".single-item");
         
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
        $("#contenedorTelefonos #contenedorTelefono").hide();
        $("#contenedorTelefonos #contenedorTelefono").removeClass("single-item");
        $(".pagination").remove();
        //soooooooo
         var listaSo = document.querySelectorAll("#so:checked");
         var longitudSo =listaSo.length;
        if (longitudSo > 1) {
            listaSo.forEach(function (valor, index) {
                var idDivFiltro = "so";
                var idDivFiltroValor = valor.value;
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
          listaSo.forEach(function (valor, index) {
                var idDivFiltro = "so";
                var idDivFiltroValor = valor.value;
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //soooooooo

        //marca
         var listaMarca = document.querySelectorAll("#marca:checked");
         var longitudMarca =listaMarca.length;
        if (longitudMarca > 1) {
            listaMarca.forEach(function (valor, index) {
                var idDivFiltro = "marca";
                var idDivFiltroValor = valor.value;
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
          listaMarca.forEach(function (valor, index) {
                var idDivFiltro = "marca";
                var idDivFiltroValor = valor.value;
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //marca

        //raaam
        var listaRam = document.querySelectorAll("#ram:checked");
         var longitudRam =listaRam.length;
        if (longitudRam > 1) {
            listaRam.forEach(function (valor, index) {
                var idDivFiltro = "ram";
                var idDivFiltroValor = valor.value;
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
          listaRam.forEach(function (valor, index) {
                var idDivFiltro = "ram";
                var idDivFiltroValor = valor.value;
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //raaam

        //precio
        var listaAlmacenamiento = document.querySelectorAll("#almacenamiento:checked");
         var longitudAlmacenamiento =listaAlmacenamiento.length;
        if (longitudAlmacenamiento > 1) {
            listaRam.forEach(function (valor, index) {
                var idDivFiltro = "almacenamiento";
                var idDivFiltroValor = valor.value;
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
          listaAlmacenamiento.forEach(function (valor, index) {
                var idDivFiltro = "almacenamiento";
                var idDivFiltroValor = valor.value;
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //precio

        //pulgadas
       var listaPulgadas = document.querySelectorAll("#pulgadas:checked");
         var longitudPulgadas =listaPulgadas.length;
        if (longitudPulgadas > 1) {
            listaRam.forEach(function (valor, index) {
                var idDivFiltro = "pulgadas";
                var idDivFiltroValor = valor.value;
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
          listaPulgadas.forEach(function (valor, index) {
                var idDivFiltro = "pulgadas";
                var idDivFiltroValor = valor.value;
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //pulgadas

       //camaraTrasera
        var listaCamaraTrasera = document.querySelectorAll("#camaraTrasera:checked");
         var longitudCamaraTrasera =listaCamaraTrasera.length;
        if (longitudCamaraTrasera > 1) {
            listaCamaraTrasera.forEach(function (valor, index) {
                var idDivFiltro = "camaraTrasera";
                var idDivFiltroValor = valor.value;
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
          listaCamaraTrasera.forEach(function (valor, index) {
                var idDivFiltro = "camaraTrasera";
                var idDivFiltroValor = valor.value;
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //camaraTrasera

        //camaraDelantera
        var listaCamaraDelantera = document.querySelectorAll("#camaraDelantera:checked");
         var longitudCamaraDelantera =listaCamaraDelantera.length;
        if (longitudCamaraDelantera > 1) {
            listaCamaraDelantera.forEach(function (valor, index) {
                var idDivFiltro = "camaraDelantera";
                var idDivFiltroValor = valor.value;
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
          listaCamaraDelantera.forEach(function (valor, index) {
                var idDivFiltro = "camaraDelantera";
                var idDivFiltroValor = valor.value;
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //camaraDelantera

        //bateria
        var listaBateria = document.querySelectorAll("#bateria:checked");
         var longitudBateria =listaBateria.length;
        if (longitudBateria > 1) {
            listaBateria.forEach(function (valor, index) {
                var idDivFiltro = "bateria";
                var idDivFiltroValor = valor.value;
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
          listaBateria.forEach(function (valor, index) {
                var idDivFiltro = "bateria";
                var idDivFiltroValor = valor.value;
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //bateria

        //procesador
       var listaProcesador = document.querySelectorAll("#procesador:checked");
         var longitudProcesador =listaProcesador.length;
        if (longitudProcesador > 1) {
            listaBateria.forEach(function (valor, index) {
                var idDivFiltro = "procesador";
                var idDivFiltroValor = valor.value;
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
          listaProcesador.forEach(function (valor, index) {
                var idDivFiltro = "procesador";
                var idDivFiltroValor = valor.value;
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //procesador

        //resolucion
        var listaResolucion = document.querySelectorAll("#resolucion:checked");
         var longitudResolucion =listaResolucion.length;
        if (longitudResolucion > 1) {
            listaResolucion.forEach(function (valor, index) {
                var idDivFiltro = "resolucion";
                var idDivFiltroValor = valor.value;
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
          listaResolucion.forEach(function (valor, index) {
                var idDivFiltro = "resolucion";
                var idDivFiltroValor = valor.value;
                lista1[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        }
        //resolucion

        //color
        var listaColor = document.querySelectorAll("#color:checked");
         var longitudColor =listaColor.length;
        if (longitudColor > 1) {
            listaColor.forEach(function (valor, index) {
                var idDivFiltro = "color";
                var idDivFiltroValor = valor.value;
                lista2[idDivFiltro + "" + idDivFiltroValor] = idDivFiltroValor;
            });
        } else {
          listaColor.forEach(function (valor, index) {
                var idDivFiltro = "color";
                var idDivFiltroValor = valor.value;
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
                var mostrar = "." + clave1 + "Telefono" + valor1;
                $(mostrar).parent().parent().parent().show();
                 $(mostrar).parent().parent().parent().addClass("single-item");
                 $(mostrar).parent().parent().parent().css("display","block");
            });
        } else if (Object.keys(lista1).length >= 2 && Object.keys(lista2).length === 0) {

            var contador = 0;
            var idMostrar = "";
            var ordered = {};
                Object.keys(lista1).sort().forEach(function (key) {
                    ordered[key] = lista1[key];
                });
            $.each(ordered, function (clave1, valor1) {
                if (contador === 0) {
                    idMostrar += "." + clave1 + "Telefono" + valor1;
                } else {
                    idMostrar += " ~ ." + clave1 + "Telefono" + valor1;
                }
                contador++;
            });
            $(idMostrar).parent().parent().parent().show();
            $(idMostrar).parent().parent().parent().addClass("single-item");
            $(idMostrar).parent().parent().parent().css("display","block");
        } else if (Object.keys(lista1).length === 0 && Object.keys(lista2).length >= 2) {


            $.each(lista2, function (clave2, valor2) {
                var mostrar = "." + clave2 + "Telefono" + valor2;
                $(mostrar).parent().parent().parent().show();
                $(mostrar).parent().parent().parent().addClass("single-item");
                $(mostrar).parent().parent().parent().css("display","block");
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
                        mostrar = "." + clave1 + "Telefono" + valor1;
                        contador++;
                    } else {
                        mostrar = mostrar + " ~ ." + clave1 + "Telefono" + valor1;
                    }
                });
                $(mostrar).parent().parent().parent().show();
                $(mostrar).parent().parent().parent().addClass("single-item");
                $(mostrar).parent().parent().parent().css("display","block");
               lista3 = {};
            });
        }
        if(Object.keys(lista1).length === 0 && Object.keys(lista2).length === 0){
             $("#contenedorTelefonos #contenedorTelefono").addClass("single-item");
        }
        var listaPrecios = document.querySelectorAll("#precio");
        min = $("#txt6").val();
        max = $("#txt7").val();
        listaPrecios.forEach(function (valor, index) {
            var mostrar = valor.parentElement.parentElement.parentElement;
            var estado = mostrar.style.display;
            var precio = parseInt(valor.value);
            if (Object.keys(lista1).length > 0 || Object.keys(lista2).length > 0) {
                 if(estado==="block"){
                    if (precio >= min) {

                        if (max >= precio) {

                            $(mostrar).show();
                            $(mostrar).addClass("single-item");
                            $(mostrar).css("display","block");
                        } else {
                            $(mostrar).hide();
                            $(mostrar).removeClass("single-item");
                            $(mostrar).css("display","none");
                        }
                    } else {
                        $(mostrar).hide();
                        $(mostrar).removeClass("single-item");
                        $(mostrar).css("display","none");
                    }
                 }
            } else {
                if (precio >= min) {
                    if (max >= precio) {
                        $(mostrar).show();
                        $(mostrar).addClass("single-item");
                        $(mostrar).css("display","block");
                    }
                     else {
                        $(mostrar).hide();
                        $(mostrar).removeClass("single-item");
                        $(mostrar).css("display","none");
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
            if(estado==="block"){
                if (posicionEncontrado > -1) {
                } else {
                    $(mostrar).hide();
                     $(mostrar).removeClass("single-item");
                     $(mostrar).css("display","none");
                }
            }
        });
    }
    
    function paginar(){

	var pagify = {
		items: {},
		container: null,
		totalPages: 1,
		perPage: 3,
		currentPage: 0,
		createNavigation: function() {
			this.totalPages = Math.ceil(this.items.length / this.perPage);

			$('.pagination', this.container.parent()).remove();
			var pagination = $('<div class="pagination"></div>').append('<a class="nav prev disabled" data-next="false"><</a>');

			for (var i = 0; i < this.totalPages; i++) {
				var pageElClass = "page";
				if (!i)
					pageElClass = "page current";
				var pageEl = '<a class="' + pageElClass + '" data-page="' + (
				i + 1) + '">' + (
				i + 1) + "</a>";
				pagination.append(pageEl);
			}
			pagination.append('<a class="nav next" data-next="true">></a>');

			this.container.after(pagination);

			var that = this;
			$("body").off("click", ".nav");
			this.navigator = $("body").on("click", ".nav", function() {
				var el = $(this);
				that.navigate(el.data("next"));
			});

			$("body").off("click", ".page");
			this.pageNavigator = $("body").on("click", ".page", function() {
				var el = $(this);
				that.goToPage(el.data("page"));
			});
		},
		navigate: function(next) {
			// default perPage to 5
			if (isNaN(next) || next === undefined) {
				next = true;
			}
			$(".pagination .nav").removeClass("disabled");
			if (next) {
				this.currentPage++;
				if (this.currentPage > (this.totalPages - 1))
					this.currentPage = (this.totalPages - 1);
				if (this.currentPage == (this.totalPages - 1))
					$(".pagination .nav.next").addClass("disabled");
				}
			else {
				this.currentPage--;
				if (this.currentPage < 0)
					this.currentPage = 0;
				if (this.currentPage == 0)
					$(".pagination .nav.prev").addClass("disabled");
				}

			this.showItems();
		},
		updateNavigation: function() {

			var pages = $(".pagination .page");
			pages.removeClass("current");
			$('.pagination .page[data-page="' + (
			this.currentPage + 1) + '"]').addClass("current");
		},
		goToPage: function(page) {

			this.currentPage = page - 1;

			$(".pagination .nav").removeClass("disabled");
			if (this.currentPage == (this.totalPages - 1))
				$(".pagination .nav.next").addClass("disabled");

			if (this.currentPage == 0)
				$(".pagination .nav.prev").addClass("disabled");
			this.showItems();
		},
		showItems: function() {
			this.items.hide();
			var base = this.perPage * this.currentPage;
			this.items.slice(base, base + this.perPage).show();

			this.updateNavigation();
		},
		init: function(container, items, perPage) {
			this.container = container;
			this.currentPage = 0;
			this.totalPages = 1;
			this.perPage = perPage;
			this.items = items;
			this.createNavigation();
			this.showItems();
		}
	};

	// stuff it all into a jQuery method!
	$.fn.pagify = function(perPage, itemSelector) {
		var el = $(this);
		var items = $(itemSelector, el);

		// default perPage to 5
		if (isNaN(perPage) || perPage === undefined) {
			perPage = 3;
		}

		// don't fire if fewer items than perPage
		if (items.length <= perPage) {
			return true;
		}

		pagify.init(el, items, perPage);
	};



    }
    }); //(ready


