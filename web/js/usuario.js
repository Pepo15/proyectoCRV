 $(function () {//Ready

                    $.validator.addMethod('passwordMatch', function (value, element) {

                        var password = $('#modificarUsuarioPass\\:password').val();
                        var confirmPassword = $('#modificarUsuarioPass\\:passwordR').val();
                        if (password !== confirmPassword) {
                            return false;
                        } else {
                            return true;
                        }
                    }, "Your Password Must Match");




                    $.validator.addMethod('existNick', function (value, element) {

                        var datos = 'Nick=' + $("#modificarUsuario\\:nick").val();
                        var response;
                        //Ponemos el nombre del servlet que nos dará la respuesta y los datos para poder encontrarlas
                        
                        $.ajax({
                                url: "CompruebaDatos",
                                dataType: 'json',
                                async: false,
                                data: datos,
                                success: function (result) {
                                    response = result.Nick;
                                    
                                   
                        }
                                });
                            
                               if (response) {
                                    return false;
                                        } else {
                                         return true;  
                                     }
                        

                    }, "Ya existe ese nick");
                    
                    $.validator.addMethod('existEmail', function (value, element) {

                        var datos = 'Email=' + $("#modificarUsuario\\:correo").val();
                        var response;
                        //Ponemos el nombre del servlet que nos dará la respuesta y los datos para poder encontrarlas
                        
                        $.ajax({
                                url: "CompruebaDatos",
                                dataType: 'json',
                                async: false,
                                data: datos,
                                success: function (result) {
                                    response = result.Email;
                                    
                                   
                        }
                                });
                            
                               if (response) {
                                    return false;
                                        } else {
                                         return true;  
                                     }
                        

                    }, "Ya existe ese email");

                    $('#modificarUsuarioPass').validate({
                        rules: {
                            'modificarUsuarioPass:password': {required: true},
                            'modificarUsuarioPass:passwordR': {required: true, passwordMatch: true}
                        },
                        messages: {
                            'modificarUsuarioPass:password': {required: "Contraseña obligatoria"},
                            'modificarUsuarioPass:passwordR': {required: "Repetir contraseña obligatorio",
                                passwordMatch: "Las contraseñas deben ser iguales"}
                        }

                    }); //..VAlidate


                    $('#modificarUsuario').validate({
                        rules: {
                            'modificarUsuario:nick': {required: true, existNick: true},
                             'modificarUsuario:correo': {required: true,email: true, existEmail: true}
                        },

                        messages: {

                            'modificarUsuario:nick': {required: "El nick debe ser obligatorio.",
                                existNick: "Ya existe ese nick",},
                            'modificarUsuario:correo': {required: "El correo debe ser obligatorio.", email:"Debe tener el formato xxx@xx.xx",
                                existEmail:"Ya tenemos ese email registrado"}
                        }

                    });

                    $(".botonD").on("click", function () {
                        iniciaSesion();
                    });

                    $(".botonT").on("click", function () {
                        iniciaSesion2();
                    });
                    function iniciaSesion() {
                        $("#modalDireccion").css("display", "block");
                    }
                    function iniciaSesion2() {
                        $("#modalTarjeta").css("display", "block");
                    }

                    var modal = document.getElementById('modalDireccion');
                    var modal2 = document.getElementById('modalTarjeta');
                    window.onclick = function (event) {
                        if (event.target == modal) {
                            $("#modalDireccion").css("display", "none");
                        }
                        if (event.target == modal2) {
                            $("#modalTarjeta").css("display", "none");
                        }
                    }

                    var span = document.getElementsByClassName("close")[0];
                    var span2 = document.getElementsByClassName("close")[1];
                    span.onclick = function () {
                        $("#modalDireccion").css("display", "none");
                    }
                    span2.onclick = function () {
                        $("#modalTarjeta").css("display", "none");
                    }



                });