 $(document).ready(function () {
                $(".botonCambiarEstado").on("click", function () {
                iniciaSesion();
                });
                $(".botonMasDatos").on("click", function () {
                iniciaSesion2();
                });
                        function iniciaSesion() {
                        $("#modalCambiarEstado").css("display", "block");
                        }
                        function iniciaSesion2() {
                        $("#modalMasDatos").css("display", "block");
                        }

                var modal = document.getElementById('modalCambiarEstado');
                var modal2 = document.getElementById('modalMasDatos');
                        window.onclick = function (event) {
                        if (event.target == modal) {
                        $("#modalCambiarEstado").css("display", "none");
                        }
                        if (event.target == modal2) {
                        $("#modalMasDatos").css("display", "none");
                        }
                        }

                var span = document.getElementsByClassName("close")[1];
                var span2 = document.getElementsByClassName("close")[2];
                        span.onclick = function () {
                        $("#modalCambiarEstado").css("display", "none");
                        }
                        span2.onclick = function () {
                        $("#modalMasDatos").css("display", "none");
                        }
                    });