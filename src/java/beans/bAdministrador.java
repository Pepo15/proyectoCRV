package beans;

import DAO.AdministradorJpaController;
import DAO.CaracteristicastelefonoJpaController;
import DAO.DireccionJpaController;
import DAO.PedidoJpaController;
import DAO.PoblacionJpaController;
import DAO.TecnicoJpaController;
import DAO.TelefonoJpaController;
import DAO.UsuarioJpaController;
import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import DTO.Administrador;
import DTO.Caracteristicastelefono;
import DTO.Direccion;
import DTO.Pedido;
import DTO.Poblacion;
import DTO.Tecnico;
import DTO.Telefono;
import DTO.Usuario;
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

public class bAdministrador {

    //Driver para conectar con la base de datos
    private EntityManagerFactory emf;

    //Control para gestionar los telefonos
    private TelefonoJpaController ctrTelefono;
    private CaracteristicastelefonoJpaController ctrCaracteristicas;

    
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

    //Contructor
    public bAdministrador() {
        emf = Persistence.createEntityManagerFactory("CRVPU");
        ctrTelefono = new TelefonoJpaController(emf);
        ctrCaracteristicas = new CaracteristicastelefonoJpaController(emf);

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
            Telefono telefonoRepetido = ctrTelefono.findTecnicoByNick(nombre);

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
                } catch (PreexistingEntityException ex) {
                    //Escribo el mensaje de alta incorrecta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "Ya existe un telefono con ese nombre"));

                return "telefonoAltaIncorrecta";
                
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

}
