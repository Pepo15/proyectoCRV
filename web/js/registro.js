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
                            'altaUsuario:nombre':{required:true, minlength : 3},
                            'altaUsuario:apellido1':{required:true, minlength : 3},
                            'altaUsuario:apellido2':{required:true, minlength : 3},
                            'altaUsuario:nick': {required: true,minlength : 3, existNick: true},
                            'altaUsuario:correo': {required: true,email: true, existEmail: true},
                            'altaUsuario:password': {required: true, minlength : 5},
                            'altaUsuario:passwordR': {required: true, minlength : 5, passwordMatch: true}
                        },

                        messages: {
                            'altaUsuario:nombre': {required: "El nombre es obligatorio.",minlength: "Debe tener un mínimo de 3 caracteres."},
                            'altaUsuario:apellido1': {required: "El primer apellido es obligatorio.",minlength: "Debe tener un  mínimo de 3 caracteres."},
                            'altaUsuario:apellido2': {required: "El segundo apellido es obligatorio.",minlength: "Debe tener un mínimo de 3 caracteres."},
                            
                            'altaUsuario:nick': {required: "El nick es obligatorio.",minlength: "Debe tener un mínimo de 3 caracteres.",existNick: "Lo sentimos, ya existe ese nick."},
                            'altaUsuario:correo': {required: "El correo es obligatorio.", email:"Debe tener el formato xxx@xx.xx",existEmail:"Lo sentimos, ya existe ese email."},
                            'altaUsuario:password': {required: "La contraseña es obligatoria.",minlength: "Debe tener un mínimo de 5 caracteres."},
                            'altaUsuario:passwordR': {required: "Repetir la contraseña es obligatorio.",minlength: "Debe tener un mínimo de 5 caracteres.",passwordMatch: "Las contraseñas no coinciden."}
                        }

                    });
                    
    });