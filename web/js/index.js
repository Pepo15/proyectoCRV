 $(document).ready(function () {
                    $("#inicioSesion").on("click", function () {
                        iniciaSesion();
                    });
                    function iniciaSesion() {
                        $("#modalInicioSesion").css("display", "block");
                    }

                    var modal = document.getElementById('modalInicioSesion');
                    window.onclick = function (event) {
                        if (event.target == modal) {
                            $("#modalInicioSesion").css("display", "none");
                              $("#logeo").css("display", "block");
                           $("#olvidarPassword").css("display", "none");
                        }
                    }

                    var span = document.getElementsByClassName("close")[0];
                    span.onclick = function () {
                        $("#modalInicioSesion").css("display", "none");
                         $("#logeo").css("display", "block");
                           $("#olvidarPassword").css("display", "none");
                        
                    }
                    $("#olvC").on("click", function () {
                         $("#logeo").css("display", "none");
                           $("#olvidarPassword").css("display", "block");
                    });
                });