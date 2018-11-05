package beans;

import DAO.AdministradorJpaController;
import DAO.CaracteristicastelefonoJpaController;
import DAO.DireccionJpaController;
import DAO.FotoJpaController;
import DAO.PedidoJpaController;
import DAO.PoblacionJpaController;
import DAO.PremioJpaController;
import DAO.TecnicoJpaController;
import DAO.TelefonoJpaController;
import DAO.UsuarioJpaController;
import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import DTO.Administrador;
import DTO.Caracteristicastelefono;
import DTO.Direccion;
import DTO.Foto;
import DTO.Pedido;
import DTO.Poblacion;
import DTO.Premio;
import DTO.Tecnico;
import DTO.Telefono;
import DTO.Usuario;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;
import org.primefaces.model.UploadedFile;

public class bAdministrador {

    //Driver para conectar con la base de datos
    private EntityManagerFactory emf;

    //Control para gestionar los telefonos
    private TelefonoJpaController ctrTelefono;
    private CaracteristicastelefonoJpaController ctrCaracteristicas;
    private FotoJpaController ctrFotos;
    private PremioJpaController ctrPremios;

    
    //Variables que almacenan los datos de los inputs, para dar de alta un telefono
    private String nombre;
    private String marca;
    private String precio;
    
    
    //Variable que guarda el codigo del telefono para añadir sus caracteristicas
    private String codigoTelefono;

    //Variables que almacenan los datos de los inputs, para dar alta las caracteristicas de un telefono
    private String so;
    private String ram;
    private String pulgadas;
    private String almacenamiento;
    private String camaraTrasera;
    private String camaraDelantera;
    private String bateria;
    private String procesador;
    private boolean wifi;
    private String resolucion;
    private String color;
    private boolean detectorDeHuellas;
    private boolean dualSim;
    private boolean sd;
    private boolean bluetooth;
    private boolean nfc;
    private boolean g3;
    private boolean g4;
    
    //Variable que guarda la lista de telefonos
    private ArrayList listaTelefonos;
    
    //Buffer para la subida de la foto
    private static final int BUFFER_SIZE=6124;
    
    //Variable que guarda la foto del telefono
    private UploadedFile fileTelefono;
    
    //Variable que guarda el codigo del telefono para añadir la foto 
    private String codigoTelefonoFoto;
    
    //Variable que guarda el perfil de la foto del telefono
    private String perfilFoto;
    
    //Variable que guarda la lista de perfiles
    private ArrayList listaPerfiles;
    
    //Variable que guarda el nombre del premio
    private String nombrePremio;
    
    //Variable que guarda el coste del premio
    private String coste;
    
    //Variable que guarda la foto del premio
    private UploadedFile filePremio;
    
    //Variable que guarda el codigo del premio para añadir la foto 
    private String codigoPremio;
    
    //Variable que guarda la lista de premios
    private ArrayList listaPremios;
    
    //Variable que guarda el codigo del telefono que deseamos eliminar
    private String codigoTelefonoEliminar;
            
    //Variable que guarda el codigo del premio que deseamos eliminar
    private String codigoPremioEliminar;

    //Contructor
    public bAdministrador() {
        emf = Persistence.createEntityManagerFactory("CRVPU");
        ctrTelefono = new TelefonoJpaController(emf);
        ctrCaracteristicas = new CaracteristicastelefonoJpaController(emf);
        ctrFotos = new FotoJpaController(emf);
        ctrPremios = new PremioJpaController(emf);
        
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public TelefonoJpaController getCtrTelefono() {
        return ctrTelefono;
    }

    public void setCtrTelefono(TelefonoJpaController ctrTelefono) {
        this.ctrTelefono = ctrTelefono;
    }

    public String getCodigoTelefono() {
        return codigoTelefono;
    }

    public void setCodigoTelefono(String codigoTelefono) {
        this.codigoTelefono = codigoTelefono;
    }

    public CaracteristicastelefonoJpaController getCtrCaracteristicas() {
        return ctrCaracteristicas;
    }

    public void setCtrCaracteristicas(CaracteristicastelefonoJpaController ctrCaracteristicas) {
        this.ctrCaracteristicas = ctrCaracteristicas;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getSo() {
        return so;
    }

    public void setSo(String so) {
        this.so = so;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getPulgadas() {
        return pulgadas;
    }

    public void setPulgadas(String pulgadas) {
        this.pulgadas = pulgadas;
    }

    public String getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(String almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public String getCamaraTrasera() {
        return camaraTrasera;
    }

    public void setCamaraTrasera(String camaraTrasera) {
        this.camaraTrasera = camaraTrasera;
    }

    public String getCamaraDelantera() {
        return camaraDelantera;
    }

    public void setCamaraDelantera(String camaraDelantera) {
        this.camaraDelantera = camaraDelantera;
    }

    public String getBateria() {
        return bateria;
    }

    public void setBateria(String bateria) {
        this.bateria = bateria;
    }

    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isDetectorDeHuellas() {
        return detectorDeHuellas;
    }

    public void setDetectorDeHuellas(boolean detectorDeHuellas) {
        this.detectorDeHuellas = detectorDeHuellas;
    }

    public boolean isDualSim() {
        return dualSim;
    }

    public void setDualSim(boolean dualSim) {
        this.dualSim = dualSim;
    }

    public boolean isSd() {
        return sd;
    }

    public void setSd(boolean sd) {
        this.sd = sd;
    }

    public boolean isBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(boolean bluetooth) {
        this.bluetooth = bluetooth;
    }

    public boolean isNfc() {
        return nfc;
    }

    public void setNfc(boolean nfc) {
        this.nfc = nfc;
    }

    public boolean isG3() {
        return g3;
    }

    public void setG3(boolean g3) {
        this.g3 = g3;
    }

    public boolean isG4() {
        return g4;
    }

    public void setG4(boolean g4) {
        this.g4 = g4;
    }

    public void setListaTelefonos(ArrayList listaTelefonos) {
        this.listaTelefonos = listaTelefonos;
    }

    public UploadedFile getFileTelefono() {
        return fileTelefono;
    }

    public void setFileTelefono(UploadedFile fileTelefono) {
        this.fileTelefono = fileTelefono;
    }

    public String getCodigoTelefonoFoto() {
        return codigoTelefonoFoto;
    }

    public void setCodigoTelefonoFoto(String codigoTelefonoFoto) {
        this.codigoTelefonoFoto = codigoTelefonoFoto;
    }

    public String getPerfilFoto() {
        return perfilFoto;
    }

    public void setPerfilFoto(String perfilFoto) {
        this.perfilFoto = perfilFoto;
    }

    public ArrayList getListaPerfiles() {
        if (listaPerfiles == null) {
            listaPerfiles = new ArrayList();
            listaPerfiles.add(new SelectItem("Delante", "Delante"));
            listaPerfiles.add(new SelectItem("Detras", "Detrás"));
            listaPerfiles.add(new SelectItem("Perfil", "Perfil"));
        }
        return listaPerfiles;
    }

    public void setListaPerfiles(ArrayList listaPerfiles) {
        this.listaPerfiles = listaPerfiles;
    }

    public FotoJpaController getCtrFotos() {
        return ctrFotos;
    }

    public void setCtrFotos(FotoJpaController ctrFotos) {
        this.ctrFotos = ctrFotos;
    }

    public static int getBUFFER_SIZE() {
        return BUFFER_SIZE;
    }

    public PremioJpaController getCtrPremios() {
        return ctrPremios;
    }

    public void setCtrPremios(PremioJpaController ctrPremios) {
        this.ctrPremios = ctrPremios;
    }

    public String getNombrePremio() {
        return nombrePremio;
    }

    public void setNombrePremio(String nombrePremio) {
        this.nombrePremio = nombrePremio;
    }

    public String getCoste() {
        return coste;
    }

    public void setCoste(String coste) {
        this.coste = coste;
    }

    public UploadedFile getFilePremio() {
        return filePremio;
    }

    public void setFilePremio(UploadedFile filePremio) {
        this.filePremio = filePremio;
    }

    public String getCodigoPremio() {
        return codigoPremio;
    }

    public void setCodigoPremio(String codigoPremio) {
        this.codigoPremio = codigoPremio;
    }

    public String getCodigoTelefonoEliminar() {
        return codigoTelefonoEliminar;
    }

    public void setCodigoTelefonoEliminar(String codigoTelefonoEliminar) {
        this.codigoTelefonoEliminar = codigoTelefonoEliminar;
    }

    public String getCodigoPremioEliminar() {
        return codigoPremioEliminar;
    }

    public void setCodigoPremioEliminar(String codigoPremioEliminar) {
        this.codigoPremioEliminar = codigoPremioEliminar;
    }
    
    

    public ArrayList getListaPremios() {
        if (listaPremios == null) {
            //Inicializamos la lista de premios
            listaPremios = new ArrayList();

            List<Premio> listaPre = ctrPremios.findPremioEntities();
            for (Premio pre : listaPre) {
                listaPremios.add(new SelectItem(pre.getCodigoPremio(), pre.getNombre()));
            }
        }
        return listaPremios;
    }

    public void setListaPremios(ArrayList listaPremios) {
        this.listaPremios = listaPremios;
    }
    
    //Devolver valores para el select de cambiar el estado
    public ArrayList getListaTelefonos() {
        if (listaTelefonos == null) {
            //Inicializamos la lista de moviles
            listaTelefonos = new ArrayList();

            List<Telefono> listaTele = ctrTelefono.findTelefonoEntities();
            for (Telefono tel : listaTele) {
                listaTelefonos.add(new SelectItem(tel.getCodigoTelefono(), tel.getNombre()));
            }
        }
        return listaTelefonos;
    }
    
    //Metodo para dar de alta un telefono
    public String altaTelefono() {
        //Comprobamos que ha introducido el nick y la contraseña
        if (nombre != "" && marca != "" && precio != "") {

            //Buscamos si existe un telefono con el mismo nombre
            Telefono telefonoRepetido = ctrTelefono.findTelefonoByNick(nombre);

            //Si hemos encontrado un telefono, no podremos repetirlo, si no existe lo creamos
            if (telefonoRepetido == null) {

                //Creamos un tecnico con los datos
                Telefono telefono = new Telefono(null, nombre, marca, Float.parseFloat(precio));

                //Cojo el administrador de la sesion
                ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
                manageBeanSesion manageBeanSesion = new manageBeanSesion();

                HttpSession session = (HttpSession) ctx.getSession(false);
                manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
                Administrador administrador = (Administrador) manageBeanSesion.getAdministradorLog();

                //Cojo el codigo del administrador para meterselo al tecnico
                telefono.setCodigoAdministrador(administrador);

                //Damos de alta en la base de datos
                ctrTelefono.create(telefono);

                //Escribo el mensaje de alta correcta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha dado de alta al telefono correctamente."));

                return "telefonoAltaCorrecta";

            } else {

                //Escribo el mensaje de alta incorrecta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "Ya existe un telefono con ese nombre"));

                return "telefonoAltaIncorrecta";
            }

        }
        
                //Escribo el mensaje de alta incorrecta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No ha introducido todos los datos"));

                return "telefonoAltaIncorrecta";

    }
    
     //Metodo para dar de alta un telefono
    public String altaCaracteristicasTelefono() {
        //Comprobamos que ha introducido los datos
        if (codigoTelefono!="" && so != "" && ram != "" && pulgadas != "" 
            && almacenamiento != "" && camaraTrasera != ""
            && camaraDelantera != ""  && bateria != ""
            && procesador != "" && resolucion != ""
            && color != "") {

            //Buscamos si existe unas si un telefono ya tiene unas caracteristicas
            Caracteristicastelefono caracteristicasRepetidas = ctrCaracteristicas.findCaracteristicastelefono(Integer.parseInt(codigoTelefono));

            //Si hemos encontrado un telefono, no podremos repetirlo, si no existe añadimos las caracteristicas
            if (caracteristicasRepetidas == null) {

                //Creamos un tecnico con los datos
                Caracteristicastelefono caracteristicas = 
                        new Caracteristicastelefono(Integer.parseInt(codigoTelefono), so, Integer.parseInt(ram),
                                Float.parseFloat(pulgadas),Integer.parseInt(almacenamiento),Float.parseFloat(camaraTrasera),
                                Float.parseFloat(camaraDelantera), Integer.parseInt(bateria), procesador, wifi, 
                                Integer.parseInt(resolucion),
                        color,detectorDeHuellas,dualSim,sd,bluetooth,nfc,g3,g4);

                
                try {
                    ctrCaracteristicas.create(caracteristicas);
                    //Escribo el mensaje de alta correcta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha dado de alta al telefono correctamente."));

                return "telefonoAltaCorrecta";
                } catch (Exception ex) {
                    //Escribo el mensaje de alta incorrecta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "Ya existe un telefono con ese nombre"));

                return "telefonoAltaIncorrecta";
                }

                

            } else {

                //Escribo el mensaje de alta incorrecta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "Ya existe un telefono con ese nombre"));

                return "telefonoAltaIncorrecta";
            }

        }
        
                //Escribo el mensaje de alta incorrecta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No ha introducido todos los datos"));

                return "telefonoAltaIncorrecta";

    }
    
    //Metodo que sube la foto de un telefono
    public void subirFotoTelefono() {
            try {
                String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
                
                //OBTENEMOS EXTENSION DEL FICHERO QUE NOS VIENE
                String extension = "";
                int i = fileTelefono.getFileName().lastIndexOf('.');
                if (i > 0) {
                    extension = fileTelefono.getFileName().substring(i + 1);
                }
                
                //CREAMOS EL FILE CON LA RUTA ENTERA
                File result = new File(path + "/../../web/imagenes/FotosTelefono/" + perfilFoto + "-" + codigoTelefonoFoto + "." + extension );

                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(result);
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int bulk;
                    InputStream inputStream = fileTelefono.getInputstream();
                    while (true) {
                        bulk = inputStream.read(buffer);
                        if (bulk < 0) {
                            break;
                        }
                        fileOutputStream.write(buffer, 0, bulk);
                        fileOutputStream.flush();
                    }
                    fileOutputStream.close();
                    inputStream.close();
                    
                    //Escribo el mensaje de subida correcta
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La foto se ha subido correctamente."));
        
                    //La subo a la base de datos
                    Foto foto = new Foto(null, perfilFoto);
                
                    foto.setCodigoTelefono(ctrTelefono.findTelefono(Integer.parseInt(codigoTelefonoFoto)));

                    ctrFotos.create(foto);

                } catch (IOException e) {
                    e.printStackTrace();

                   //Escribo el mensaje de subida incorrecta
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al subir la foto al servidor."));
        
                }

            } catch (Exception ex) {
                
                 //Escribo el mensaje de subida incorrecta
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al subir la foto a la base de datos."));
        
                Logger.getLogger(bAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }

        
    }
    
    //Metodo para dar de alta un premio
    public String altaPremio() {
        //Comprobamos que ha introducido el nick y la contraseña
        if (nombrePremio != "" && coste != "") {

            //Buscamos si existe un premio con el mismo nombre
            Premio premioRepetido = ctrPremios.findPremioByNombre(nombre);

            //Si hemos encontrado un premio, no podremos repetirlo, si no existe lo creamos
            if (premioRepetido == null) {

                //Creamos un tecnico con los datos
                Premio premio = new Premio(null, nombrePremio, Integer.parseInt(coste));

                //Damos de alta en la base de datos
                ctrPremios.create(premio);
                
                //Actualizamos la tabla, así llamara al get que se encargará de actualizarla cuando recarge la pagina
                listaPremios=null;
                getListaPremios();

                //Escribo el mensaje de alta correcta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha dado de alta al premio correctamente."));

                return "telefonoAltaCorrecta";

            } else {

                //Escribo el mensaje de alta incorrecta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "Ya existe un premio con ese nombre"));

                return "telefonoAltaIncorrecta";
            }

        }
        
                //Escribo el mensaje de alta incorrecta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No ha introducido todos los datos"));

                return "telefonoAltaIncorrecta";

    }
    
    //Metodo que sube la foto de un premio
    public void subirFotoPremio() {
            try {
                String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
                
                //OBTENEMOS EXTENSION DEL FICHERO QUE NOS VIENE
                String extension = "";
                int i = filePremio.getFileName().lastIndexOf('.');
                if (i > 0) {
                    extension = filePremio.getFileName().substring(i + 1);
                }
                
                //CREAMOS EL FILE CON LA RUTA ENTERA
                File result = new File(path + "/../../web/imagenes/FotosPremios/" + "premio" + codigoPremio );

                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(result);
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int bulk;
                    InputStream inputStream = filePremio.getInputstream();
                    while (true) {
                        bulk = inputStream.read(buffer);
                        if (bulk < 0) {
                            break;
                        }
                        fileOutputStream.write(buffer, 0, bulk);
                        fileOutputStream.flush();
                    }
                    fileOutputStream.close();
                    inputStream.close();
                    
                    //Escribo el mensaje de subida correcta
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La foto se ha subido correctamente."));
        
                    //La subo a la base de datos
                    Foto foto = new Foto(null, "premio" + codigoPremio );
                
                    foto.setCodigoPremio(ctrPremios.findPremio(Integer.parseInt(codigoPremio)));
                    
                    ctrFotos.create(foto);

                } catch (IOException e) {
                    e.printStackTrace();

                   //Escribo el mensaje de subida incorrecta
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al subir la foto al servidor."));
        
                }

            } catch (Exception ex) {
                
                 //Escribo el mensaje de subida incorrecta
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al subir la foto a la base de datos."));
        
                Logger.getLogger(bAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }

        
    }
    
    //Metodo para eliminar un telefono
    public String bajaTelefono(){
        try {
            //Eliminamos el telefono
            ctrTelefono.destroy(Integer.parseInt(codigoTelefonoEliminar));
            
            //Escribo el mensaje de baja correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La baja del teléfono se ha realizado correctamente."));

            return "telefonoAltaCorrecta";
        } catch (Exception ex) {
            Logger.getLogger(bAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            //Escribo el mensaje de baja incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha realizado la baja del teléfono"));

            return "telefonoAltaIncorrecta";
        } 
    }
    
    //Metodo para eliminar un premio
    public String bajaPremio(){
        try {
            //Eliminamos el telefono
            ctrPremios.destroy(Integer.parseInt(codigoPremioEliminar));
            
            //Actualizamos la tabla, así llamara al get que se encargará de actualizarla cuando recarge la pagina
            listaPremios = null;
            getListaPremios();
            
            //Escribo el mensaje de baja correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La baja del premio se ha realizado correctamente."));

            return "telefonoAltaCorrecta";
        } catch (Exception ex) {
            Logger.getLogger(bAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            //Escribo el mensaje de baja incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha realizado la baja del premio"));

            return "telefonoAltaIncorrecta";
        } 
    }
    

}
