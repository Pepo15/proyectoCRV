 $(document).ready(function () {
                $(".botonCambiarEstado").on("click", function () {
                iniciaSesion();
                });
                        function iniciaSesion() {
                        $("#modalCambiarEstado").css("display", "block");
                        }

                var modal = document.getElementById('modalCambiarEstado');
                        window.onclick = function (event) {
                        if (event.target == modal) {
                        $("#modalCambiarEstado").css("display", "none");
                        }
                        }

                var span = document.getElementsByClassName("close")[1];
                        span.onclick = function () {
                        $("#modalCambiarEstado").css("display", "none");
                        }
                    });