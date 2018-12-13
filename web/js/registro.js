  $(function () {//Ready

                    $.validator.addMethod('passwordMatch', function (value, element) {

                        var password = $('#altaUsuario\\:password').val();
                        var confirmPassword = $('#altaUsuario\\:passwordR').val();
                        if (password !== confirmPassword) {
                            return false;
                        } else {
                            return true;
                        }
                    }, "Your Password Must Match");




                    $.validator.addMethod('existNick', function (value, element) {

                        var datos = 'Nick=' + $("#altaUsuario\\:nick").val();
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

                        var datos = 'Email=' + $("#altaUsuario\\:correo").val();
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

                    $('#altaUsuario').validate({
                        rules: {
                            'altaUsuario:nick': {required: true, existNick: true},
                            'altaUsuario:correo': {required: true,email: true, existEmail: true},
                            'altaUsuario:password': {required: true},
                            'altaUsuario:passwordR': {required: true, passwordMatch: true}
                        },

                        messages: {

                            'altaUsuario:nick': {required: "El nick debe ser obligatorio.",existNick: "Ya existe ese nick"},
                            'altaUsuario:correo': {required: "El correo debe ser obligatorio.", email:"Debe tener el formato xxx@xx.xx",existEmail:"Ya tenemos ese email registrado"},
                            'altaUsuario:password': {required: "Contraseña obligatoria"},
                            'altaUsuario:passwordR': {required: "Repetir contraseña obligatorio",passwordMatch: "Las contraseñas deben ser iguales"}
                        }

                    });
                    
    });