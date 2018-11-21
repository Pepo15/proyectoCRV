package beans;

import DAO.AdministradorJpaController;
import DAO.CanjearJpaController;
import DAO.DireccionJpaController;
import DAO.PedidoJpaController;
import DAO.PoblacionJpaController;
import DAO.ProvinciaJpaController;
import DAO.TarjetaJpaController;
import DAO.TecnicoJpaController;
import DAO.UsuarioJpaController;
import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DTO.Administrador;
import DTO.Direccion;
import DTO.Poblacion;
import DTO.Provincia;
import DTO.Tarjeta;
import DTO.Tecnico;
import DTO.Usuario;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;

public class bUsuarioGestionPersonal {

    //Driver para conectar con la base de datos
    private EntityManagerFactory emf;

    //Control para gestionar la base de datos
    private UsuarioJpaController ctrUsuario;
    private TecnicoJpaController ctrTecnico;
    private AdministradorJpaController ctrAdministrador;
    private DireccionJpaController ctrDireccion;
    private PoblacionJpaController ctrPoblacion;
    private ProvinciaJpaController ctrProvincia;
    private TarjetaJpaController ctrTarjeta;
    private PedidoJpaController ctrPedido;
    private CanjearJpaController ctrCanjear;

    //Variable que guarda el nick del usuario, donde tambien se modifica
    private String nick;
    
    //Variable que guarda el email del usuario, donde tambien se modifica
    private String email;

    //Variable que guarda la contraseña y su repeticion para modificarla
    private String password;
    private String passwordR;

    //Variable que guarda la lista de las direcciones del usuario
    private List listaDirecciones;

    //Variable que guarda la lista de las tarjetas del usuario
    private List listaTarjetas;

    //Variable que comprueba si tiene direcciones
    private boolean noExiste = false;
    
    //Variable que comprueba si tiene tarjetas
    private boolean noExisteT = false;

    //Variable para modificar la direccion
    private String cp;
    
    private int codigoDireccionModificar;

    private int codigoProvinciaModificar;

    private int codigoPoblacionModificar;
    
    private ArrayList listaProvincias;

    private ArrayList listaPoblaciones;

    private String telefono;

    private String direccionModificar;

    //Variable para mostrar si se debe mostrar la ventana modal para editar la direccion
    private boolean direccion = false;
    
    //Variable para modificar la tarjeta
    private int codigoTarjetaModificar;

    private List listaMeses;

    private List listaAnios;

    private int mes;

    private int anio;

    private String numeroTarjeta;

    private int cvv;

    //Variable para mostrar si se debe mostrar la ventana modal para editar la tarjeta
    private boolean tarjeta = false;
    
    //Variables para dar de alta una direccion
    private String direccionAlta;
    
    private int codigoProvinciaAlta;
    
    private int codigoPoblacionAlta;
    
    private String telefonoAlta;
    
    //Variables para dar de alta una tarjeta
    private String numeroTarjetaAlta;
    
    private String mesAlta;
    
    private int anioAlta;
    
    private String cvvAlta;

   

    //Constructor
    public bUsuarioGestionPersonal() {
        emf = Persistence.createEntityManagerFactory("CRVPU");
        ctrUsuario = new UsuarioJpaController(emf);
        ctrTecnico = new TecnicoJpaController(emf);
        ctrAdministrador = new AdministradorJpaController(emf);

        ctrPoblacion = new PoblacionJpaController(emf);
        ctrProvincia = new ProvinciaJpaController(emf);
        ctrDireccion = new DireccionJpaController(emf);
        ctrTarjeta = new TarjetaJpaController(emf);
        ctrPedido = new PedidoJpaController(emf);
        ctrCanjear = new CanjearJpaController(emf);
        
        //Cojo el usuario de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        Usuario usuario = (Usuario) manageBeanSesion.getUsuarioLog();

        //Relleno los inputs con sus datos, por si quiere modificarlos
        nick = usuario.getNick();
        email = usuario.getEmail();

        //Inicializamos la lista para que cuando entre ya esten cargados
        listaDirecciones = new ArrayList();

        listaDirecciones = usuario.getDireccionList();

        //Inicializamos la lista para que cuando entre ya esten cargados
        listaTarjetas = new ArrayList();

        listaTarjetas = usuario.getTarjetaList();

        
        //Comprobamos si tiene o no para mostrar una ventana con un mensaje
        if (listaDirecciones.size() == 0) {
            noExiste = true;
        }
        if (listaTarjetas.size() == 0) {
            noExisteT = true;
        }

    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public UsuarioJpaController getCtrUsuario() {
        return ctrUsuario;
    }

    public void setCtrUsuario(UsuarioJpaController ctrUsuario) {
        this.ctrUsuario = ctrUsuario;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordR() {
        return passwordR;
    }

    public void setPasswordR(String passwordR) {
        this.passwordR = passwordR;
    }

    public TecnicoJpaController getCtrTecnico() {
        return ctrTecnico;
    }

    public void setCtrTecnico(TecnicoJpaController ctrTecnico) {
        this.ctrTecnico = ctrTecnico;
    }

    public AdministradorJpaController getCtrAdministrador() {
        return ctrAdministrador;
    }

    public void setCtrAdministrador(AdministradorJpaController ctrAdministrador) {
        this.ctrAdministrador = ctrAdministrador;
    }

    public PoblacionJpaController getCtrPoblacion() {
        return ctrPoblacion;
    }

    public void setCtrPoblacion(PoblacionJpaController ctrPoblacion) {
        this.ctrPoblacion = ctrPoblacion;
    }

    public List getListaDirecciones() {
        return listaDirecciones;
    }

    public void setListaDirecciones(List listaDirecciones) {
        this.listaDirecciones = listaDirecciones;
    }

    public boolean isNoExiste() {
        return noExiste;
    }

    public void setNoExiste(boolean noExiste) {
        this.noExiste = noExiste;
    }

    public ProvinciaJpaController getCtrProvincia() {
        return ctrProvincia;
    }

    public void setCtrProvincia(ProvinciaJpaController ctrProvincia) {
        this.ctrProvincia = ctrProvincia;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public boolean isDireccion() {
        return direccion;
    }

    public void setDireccion(boolean direccion) {
        this.direccion = direccion;
    }

    public int getCodigoDireccionModificar() {
        return codigoDireccionModificar;
    }

    public void setCodigoDireccionModificar(int codigoDireccionModificar) {
        this.codigoDireccionModificar = codigoDireccionModificar;
    }

    public int getCodigoProvinciaModificar() {
        return codigoProvinciaModificar;
    }

    public void setCodigoProvinciaModificar(int codigoProvinciaModificar) {
        this.codigoProvinciaModificar = codigoProvinciaModificar;
    }

    //Devolver valores para el datalist
    public ArrayList getListaProvincias() {
        if (listaProvincias == null) {
            //Inicializamos la lista de provincias
            listaProvincias = new ArrayList();

            List<Provincia> listaPro = ctrProvincia.findProvinciaEntities();
            for (Provincia pro : listaPro) {
                listaProvincias.add(new SelectItem(pro.getCodigoProvincia(), pro.getNombreProvincia()));
            }
        }
        return listaProvincias;
    }

    public void setListaProvincias(ArrayList listaProvincias) {
        this.listaProvincias = listaProvincias;
    }

    public ArrayList getListaPoblaciones() {
        return listaPoblaciones;
    }

    public void setListaPoblaciones(ArrayList listaPoblaciones) {
        this.listaPoblaciones = listaPoblaciones;
    }

    public int getCodigoPoblacionModificar() {
        return codigoPoblacionModificar;
    }

    public void setCodigoPoblacionModificar(int codigoPoblacionModificar) {
        this.codigoPoblacionModificar = codigoPoblacionModificar;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccionModificar() {
        return direccionModificar;
    }

    public void setDireccionModificar(String direccionModificar) {
        this.direccionModificar = direccionModificar;
    }

    public DireccionJpaController getCtrDireccion() {
        return ctrDireccion;
    }

    public void setCtrDireccion(DireccionJpaController ctrDireccion) {
        this.ctrDireccion = ctrDireccion;
    }

    public List getListaTarjetas() {
        return listaTarjetas;
    }

    public void setListaTarjetas(List listaTarjetas) {
        this.listaTarjetas = listaTarjetas;
    }

    public boolean isNoExisteT() {
        return noExisteT;
    }

    public void setNoExisteT(boolean noExisteT) {
        this.noExisteT = noExisteT;
    }

    public TarjetaJpaController getCtrTarjeta() {
        return ctrTarjeta;
    }

    public void setCtrTarjeta(TarjetaJpaController ctrTarjeta) {
        this.ctrTarjeta = ctrTarjeta;
    }

    //Metodo que añade los meses del año a un select
    public List getListaMeses() {
        if (listaMeses == null) {
            listaMeses = new ArrayList();
            listaMeses.add(new SelectItem("01", "1"));
            listaMeses.add(new SelectItem("02", "2"));
            listaMeses.add(new SelectItem("03", "3"));
            listaMeses.add(new SelectItem("04", "4"));
            listaMeses.add(new SelectItem("05", "5"));
            listaMeses.add(new SelectItem("06", "6"));
            listaMeses.add(new SelectItem("07", "7"));
            listaMeses.add(new SelectItem("08", "8"));
            listaMeses.add(new SelectItem("09", "9"));
            listaMeses.add(new SelectItem("10", "10"));
            listaMeses.add(new SelectItem("11", "11"));
            listaMeses.add(new SelectItem("12", "12"));
        }
        return listaMeses;
    }

    public void setListaMeses(List listaMeses) {
        this.listaMeses = listaMeses;
    }

    //Metodo que devuelve los siguientes 20 años a la fecha actual
    public List getListaAnios() {
        if (listaAnios == null) {
            listaAnios = new ArrayList();

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            for (int i = 0; i < 20; i++) {
                listaAnios.add(new SelectItem(year, String.valueOf(year)));
                year++;
            }
        }
        return listaAnios;
    }

    public void setListaAnios(List listaAnios) {
        this.listaAnios = listaAnios;
    }

    public boolean isTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(boolean tarjeta) {
        this.tarjeta = tarjeta;
    }

    public int getCodigoTarjetaModificar() {
        return codigoTarjetaModificar;
    }

    public void setCodigoTarjetaModificar(int codigoTarjetaModificar) {
        this.codigoTarjetaModificar = codigoTarjetaModificar;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public PedidoJpaController getCtrPedido() {
        return ctrPedido;
    }

    public void setCtrPedido(PedidoJpaController ctrPedido) {
        this.ctrPedido = ctrPedido;
    }

    public CanjearJpaController getCtrCanjear() {
        return ctrCanjear;
    }

    public void setCtrCanjear(CanjearJpaController ctrCanjear) {
        this.ctrCanjear = ctrCanjear;
    }

    public String getDireccionAlta() {
        return direccionAlta;
    }

    public void setDireccionAlta(String direccionAlta) {
        this.direccionAlta = direccionAlta;
    }

    public int getCodigoProvinciaAlta() {
        return codigoProvinciaAlta;
    }

    public void setCodigoProvinciaAlta(int codigoProvinciaAlta) {
        this.codigoProvinciaAlta = codigoProvinciaAlta;
    }

    public int getCodigoPoblacionAlta() {
        return codigoPoblacionAlta;
    }

    public void setCodigoPoblacionAlta(int codigoPoblacionAlta) {
        this.codigoPoblacionAlta = codigoPoblacionAlta;
    }

    public String getTelefonoAlta() {
        return telefonoAlta;
    }

    public void setTelefonoAlta(String telefonoAlta) {
        this.telefonoAlta = telefonoAlta;
    }

    public String getNumeroTarjetaAlta() {
        return numeroTarjetaAlta;
    }

    public void setNumeroTarjetaAlta(String numeroTarjetaAlta) {
        this.numeroTarjetaAlta = numeroTarjetaAlta;
    }

    public String getMesAlta() {
        return mesAlta;
    }

    public void setMesAlta(String mesAlta) {
        this.mesAlta = mesAlta;
    }

    public int getAnioAlta() {
        return anioAlta;
    }

    public void setAnioAlta(int anioAlta) {
        this.anioAlta = anioAlta;
    }

    public String getCvvAlta() {
        return cvvAlta;
    }

    public void setCvvAlta(String cvvAlta) {
        this.cvvAlta = cvvAlta;
    }
    
    
    
    

    //Metodo para modificar un usuario
    public String modificarUsuario() {
        
        //Cojo el usuario de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        Usuario usuarioSesion = (Usuario) manageBeanSesion.getUsuarioLog();

        //Buscamos si existe un tecnico,administrador y usuario con el mismo nombre
        Tecnico tecnicoRepetido = ctrTecnico.findTecnicoByNick(nick);
        Administrador administradorRepetido = ctrAdministrador.findAdministradorByNick(nick);
        Usuario usuarioRepetido = ctrUsuario.findUsuarioByNick(nick);

        //Si existe alguno no podremos darlo de alta para que no cause errores al iniciar sesion
        if (administradorRepetido == null && tecnicoRepetido == null && usuarioSesion.getNick() == usuarioRepetido.getNick()) {
            try {
                //Creo un usuario con los datos proporcioandos el resto lo cojo de los que ya tenia
                Usuario usuario = new Usuario(usuarioSesion.getCodigoUsuario(),
                        usuarioSesion.getNombre(),
                        usuarioSesion.getApellido1(),
                        usuarioSesion.getApellido2(),
                        email, nick, usuarioSesion.getPassword());

                usuario.setPuntosAcumulados(usuarioSesion.getPuntosAcumulados());

                usuario.setCanjearList(usuarioSesion.getCanjearList());
                usuario.setDireccionList(usuarioSesion.getDireccionList());
                usuario.setTarjetaList(usuarioSesion.getTarjetaList());
                usuario.setPedidoList(usuarioSesion.getPedidoList());

                //Edito el usuario
                ctrUsuario.edit(usuario);
                
                //Actualizo el usuario de la sesion
                Usuario usuarioNuevo = ctrUsuario.findUsuario(usuarioSesion.getCodigoUsuario());
                subirUsuario(usuarioNuevo);

                //Escribo el mensaje de modificacion correcta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se han modificado los datos personales correctamente."));
                
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(bUsuarioGestionPersonal.class.getName()).log(Level.SEVERE, null, ex);

                //Escribo el mensaje de modificacion incorrecta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se han podido modifcar los datos personales correctamente"));

                return "correcto";
            } catch (Exception ex) {
                Logger.getLogger(bUsuarioGestionPersonal.class.getName()).log(Level.SEVERE, null, ex);

                //Escribo el mensaje de modificacion incorrecta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se han podido modifcar los datos personales correctamente"));

                return "correcto";
            }

        } else {

            //Escribo el mensaje de que no puede elegir ese nick
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "FATAL!", "El nick elegido no esta disponible."));
            
            //Reseteo el input y vuelvo a poner el que tenia ya que no se ha producido la modificacion
            nick = usuarioSesion.getNick();
            return "correcto";
        }
        return "correcto";
    }

    //Metodo para modificar la constraseña del usuario
    public String modificarPassword() {

        //Compruebo que las contraseñas coincidan
        if (password.equals(passwordR)) {
            //Cojo el usuario de la sesion
            ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            manageBeanSesion manageBeanSesion = new manageBeanSesion();

            HttpSession session = (HttpSession) ctx.getSession(false);
            manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
            Usuario usuarioSesion = (Usuario) manageBeanSesion.getUsuarioLog();

            //Creo un usuario con los datos proporcioandos el resto lo cojo de los que ya tenia
            Usuario usuario = new Usuario(usuarioSesion.getCodigoUsuario(),
                    usuarioSesion.getNombre(),
                    usuarioSesion.getApellido1(),
                    usuarioSesion.getApellido2(),
                    usuarioSesion.getEmail(), usuarioSesion.getNick(), password);

            usuario.setPuntosAcumulados(usuarioSesion.getPuntosAcumulados());

            usuario.setCanjearList(usuarioSesion.getCanjearList());
            usuario.setDireccionList(usuarioSesion.getDireccionList());
            usuario.setTarjetaList(usuarioSesion.getTarjetaList());
            usuario.setPedidoList(usuarioSesion.getPedidoList());

            try {
                //Edito el usuario
                ctrUsuario.edit(usuario);
                
                //Actualizo el usuario de la sesion
                Usuario usuarioNuevo = ctrUsuario.findUsuario(usuarioSesion.getCodigoUsuario());
                subirUsuario(usuarioNuevo);

                //Escribo el mensaje de modificacion correcta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se han modificado la contraseña correctamente."));
                
                return "correcto";
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(bUsuarioGestionPersonal.class.getName()).log(Level.SEVERE, null, ex);
                
                //Escribo el mensaje de modificacion incorrecta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha podido modificar la contraseña"));

            } catch (Exception ex) {
                Logger.getLogger(bUsuarioGestionPersonal.class.getName()).log(Level.SEVERE, null, ex);
                
                //Escribo el mensaje de alta incorrecta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha podido modificar la contraseña"));

            }

        }

        return "correcto";
    }

    //Metodo que devuelve el nombre de la poblacion donde se encuentra el pedido
    public String conocerPoblacion(int codigoPoblacion) {

        Poblacion poblacion = ctrPoblacion.findPoblacion(codigoPoblacion);

        //Relleno tambien el input con el cp que tenia
        cp = poblacion.getPostal().toString();

        return poblacion.getNombrePoblacion();
    }

    //Metodo que devuelve el nombre de la provincia donde se encuentra el pedido
    public String conocerProvincia(int codigoProvincia) {

        Provincia provincia = ctrProvincia.findProvincia(codigoProvincia);

        return provincia.getNombreProvincia();
    }

    //Metodo que devuelve la fecha de la tarjeta
    public String conocerFecha(String fecha) {

        int longitud=fecha.length();
        if(longitud==4){
            
        return fecha.substring(0, 2) + "/" + fecha.substring(2, 4);
        }
        else{
           return "0"+fecha.substring(0, 1) + "/" + fecha.substring(1, 3);
         
        }
    }

    //Metodo para activar la ventana modal de cambiar direccion
    public String editarDireccion(int codigoDireccion) {
        //Activo la ventana modal
        direccion = true;
        
        //Relleno los inputs con los datos de la direccion elegida
        Direccion direccionElegida = (Direccion) ctrDireccion.findDireccion(codigoDireccion);

        codigoDireccionModificar = codigoDireccion;
        direccionModificar = direccionElegida.getNombre();
        codigoProvinciaModificar = direccionElegida.getCodigoProvincia();
        codigoPoblacionModificar = direccionElegida.getCodigoPoblacion();
        int te = direccionElegida.getTelefono();
        telefono = String.valueOf(te);
        
        //Inicializamos la lista de poblaciones para que coja la poblacion elegida
        listaPoblaciones = new ArrayList();

        List listaP = new ArrayList();

        listaP = ctrPoblacion.findPoblacionByProvincia(codigoProvinciaModificar);

        for (Object p : listaP) {
            Poblacion pob = (Poblacion) p;
            listaPoblaciones.add(new SelectItem(pob.getCodigoPoblacion(), pob.getNombrePoblacion() + " - " + pob.getPostal()));
        }

        return "correcto";
    }

    //Metodo para activar la ventana modal de cambiar tarjeta
    public String editarTarjeta(int codigoTarjeta) {
        //Activo la ventana modal
        tarjeta = true;
        

        //Relleno los inputs con los datos de la tarjeta elegida
        Tarjeta tarjetaModificar = (Tarjeta) ctrTarjeta.findTarjeta(codigoTarjetaModificar);

        String fecha = String.valueOf(tarjetaModificar.getFechaCaducidad());
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String anioFinal = year.substring(0, 2) + "" + fecha.substring(2, 4);
        
        codigoTarjetaModificar = codigoTarjeta;
        numeroTarjeta = tarjetaModificar.getNumeroTarjeta();
        mes = Integer.parseInt(fecha.substring(0, 2));
        anio = Integer.parseInt(anioFinal);
        cvv = tarjetaModificar.getCvv();
        
        return "correcto";
    }

    //Metodo que rellena la lista de poblaciones según la provincia que elijamos
    public void consultarPoblacion(final AjaxBehaviorEvent event) {

        //Inicializamos la lista de poblaciones
        listaPoblaciones = new ArrayList();

        //Borro el codigo que tenia por defecto de la direccion para que no se guarde
        codigoPoblacionModificar = 0;

        //Cojo el codigo de provincia para conseguir sus poblaciones
        int codigoProvincia = (int) ((UIOutput) event.getSource()).getValue();

        //Lista donde vuelco el resultado de la consulta
        List listaP = new ArrayList();

        //Ejecutar consulta que devuelve las poblaciones segun la provincia
        listaP = ctrPoblacion.findPoblacionByProvincia(codigoProvincia);

        //Recorrer lista con la consulta, para añadirlos a la lista del select con valor y texto
        for (Object p : listaP) {
            Poblacion pob = (Poblacion) p;
            listaPoblaciones.add(new SelectItem(pob.getCodigoPoblacion(), pob.getNombrePoblacion() + " - " + pob.getPostal()));
        }
    }

    //Metodo para modificar la direccion
    public String modificarDireccion() {
        //Cojo el usuario de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        Usuario usuarioSesion = (Usuario) manageBeanSesion.getUsuarioLog();
        
            
            
        //Creo la direccion con todos sus datos
        Direccion direccionModificada = new Direccion(codigoDireccionModificar, codigoProvinciaModificar,
                codigoPoblacionModificar, direccionModificar, Integer.parseInt(telefono));

        direccionModificada.setCanjearList(usuarioSesion.getCanjearList());
        direccionModificada.setPedidoList(usuarioSesion.getPedidoList());
        direccionModificada.setCodigoUsuario(usuarioSesion);
        try {
            //Actualizo en la base de datos la direccion
            ctrDireccion.edit(direccionModificada);
            
            //Actualizo el usuario de la sesion
            Usuario usuarioNuevo = ctrUsuario.findUsuario(usuarioSesion.getCodigoUsuario());
            subirUsuario(usuarioNuevo);

            //Inicializamos la lista para que actualice los cambios
            listaDirecciones = new ArrayList();

            listaDirecciones = usuarioNuevo.getDireccionList();

            //Cierro la ventana modal de cambiar direccion
            direccion = false;

            //Escribo el mensaje de modificacion correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La modificación de la direccion se ha realizado correctamente."));

        } catch (Exception ex) {
            Logger.getLogger(bAdministradorModificarPedido.class.getName()).log(Level.SEVERE, null, ex);

            //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al modificar la direccion"));
        }
        return "correcto";
    }
    
    //Metodo para modificar la direccion
    public String altaDireccion() {
        //Cojo el usuario de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        Usuario usuarioSesion = (Usuario) manageBeanSesion.getUsuarioLog();
        
        //Creo la direccion con todos sus datos
        Direccion direccionNueva = new Direccion(null, codigoProvinciaAlta,
                codigoPoblacionAlta, direccionAlta, Integer.parseInt(telefonoAlta));
        
        direccionNueva.setCodigoUsuario(usuarioSesion);
        try {
            //Actualizo en la base de datos la direccion
            ctrDireccion.create(direccionNueva);
            
            //Actualizo el usuario de la sesion
            Usuario usuarioNuevo = ctrUsuario.findUsuario(usuarioSesion.getCodigoUsuario());
            subirUsuario(usuarioNuevo);                 

            //Inicializamos la lista para que actualice los cambios
            listaDirecciones = new ArrayList();

            listaDirecciones = usuarioNuevo.getDireccionList();
            
            //Reseteo valores
            codigoProvinciaAlta=0;
            listaProvincias=null;
            listaProvincias=getListaProvincias();
            codigoPoblacionAlta=0;
            listaPoblaciones=null;
            direccionAlta="";
            telefonoAlta="";

            //Escribo el mensaje de modificacion correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha añadido la direccion correctamente."));

        } catch (Exception ex) {
            Logger.getLogger(bAdministradorModificarPedido.class.getName()).log(Level.SEVERE, null, ex);

            //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al añadir la direccion"));
        }
        return "correcto";
    }
    
    
    
    //Metodo para modifcar una tarjeta
    public String modificarTarjeta() {
        //Cojo el usuario de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        Usuario usuarioSesion = (Usuario) manageBeanSesion.getUsuarioLog();

        //Creo la tarjeta con todos los datos
        String anios = String.valueOf(anio);
        String fecha = mes + "" + anios.substring(2, 4);
        Tarjeta tarjetaModificada = new Tarjeta(codigoTarjetaModificar, numeroTarjeta, Integer.parseInt(fecha), cvv);

        
        tarjetaModificada.setPedidoList(usuarioSesion.getPedidoList());
        tarjetaModificada.setCodigoUsuario(usuarioSesion);
        
        try {
            //Actualizo en la base de datos la tarjeta
            ctrTarjeta.edit(tarjetaModificada);
            
            //Actualizo el usuario de la sesion
            Usuario usuarioNuevo = ctrUsuario.findUsuario(usuarioSesion.getCodigoUsuario());
            subirUsuario(usuarioNuevo); 

            //Inicializamos la lista para que actualice los cambios
            listaTarjetas = new ArrayList();

            listaTarjetas = usuarioNuevo.getTarjetaList();

            //Cierro la ventana modal de cambiar tarjeta
            tarjeta = false;

            //Escribo el mensaje de modificacion correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La modificación de la tarjeta se ha realizado correctamente."));

        } catch (Exception ex) {
            Logger.getLogger(bAdministradorModificarPedido.class.getName()).log(Level.SEVERE, null, ex);

            //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al modificar la tarjeta"));
        }
        return "correcto";
    }
    
    //Metodo para dar de alta una tarjeta
    public String altaTarjeta() {
        //Cojo el usuario de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        Usuario usuarioSesion = (Usuario) manageBeanSesion.getUsuarioLog();

        //Creo la tarjeta con todos los datos
        String anios = String.valueOf(anioAlta);
        String fecha = mesAlta + "" + anios.substring(2, 4);
        Tarjeta tarjetaAlta = new Tarjeta(null, numeroTarjetaAlta, Integer.parseInt(fecha), Integer.parseInt(cvvAlta));

        
        tarjetaAlta.setCodigoUsuario(usuarioSesion);
        
        try {
            //Actualizo en la base de datos la tarjeta
            ctrTarjeta.create(tarjetaAlta);
            
            //Actualizo el usuario de la sesion
            Usuario usuarioNuevo = ctrUsuario.findUsuario(usuarioSesion.getCodigoUsuario());
            subirUsuario(usuarioNuevo); 

            //Inicializamos la lista para que actualice los cambios
            listaTarjetas = new ArrayList();

            listaTarjetas = usuarioNuevo.getTarjetaList();

            //Reseteo valores
            numeroTarjetaAlta="";
            mesAlta="";
            anioAlta=0;
            cvvAlta="";

            //Escribo el mensaje de alta correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha añadido la tarjeta correctamente."));

        } catch (Exception ex) {
            Logger.getLogger(bAdministradorModificarPedido.class.getName()).log(Level.SEVERE, null, ex);

            //Escribo el mensaje de alta incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al añadir la tarjeta"));
        }
        return "correcto";
    }

    //Metodo para subir usuario a la sesion
    public void subirUsuario(Usuario usuario) {
        //Coger contexto
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();

        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        //Coger session del contexto
        HttpSession session = (HttpSession) ctx.getSession(false);

        if (session.getAttribute("manageBeanSesion") != null) {
            manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        } else {
            session.setAttribute("manageBeanSesion", manageBeanSesion);
        }

        //Añadirle como propiedad el usuario que se ha logeado
        manageBeanSesion.setUsuarioLog(usuario);
    }

    //Metodo para borrar una tarjeta
    public String borrarTarjeta(int codigoT) {
        try {
            //Borro los pedidos a los que pertenece la tarjeta
            Tarjeta tarjetas = ctrTarjeta.findTarjeta(codigoT);
            
            for (int i = 0; i < tarjetas.getPedidoList().size(); i++) {
                ctrPedido.destroy(tarjetas.getPedidoList().get(i).getCodigo());
            }
            
            //Borro la tarjeta de la base de datos
            ctrTarjeta.destroy(codigoT);
            
            //Cojo el usuario de la sesion
            ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            manageBeanSesion manageBeanSesion = new manageBeanSesion();

            HttpSession session = (HttpSession) ctx.getSession(false);
            manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
            Usuario usuarioSesion = (Usuario) manageBeanSesion.getUsuarioLog();
            
            //Actualizo el usuario de la sesion
            Usuario usuarioNuevo = ctrUsuario.findUsuario(usuarioSesion.getCodigoUsuario());
            subirUsuario(usuarioNuevo);

            //Inicializamos la lista para que cuando entre ya esten cargados
            listaTarjetas = new ArrayList();

            listaTarjetas = usuarioNuevo.getTarjetaList();

            //Comprobamos si quedan o no tarjetas para informar al usuario
            if (listaTarjetas.size() == 0) {
                noExisteT = true;
            }

            //Escribo el mensaje de baja correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha borrado la tarjeta correctamente."));

        } catch (NonexistentEntityException ex) {
            Logger.getLogger(bTecnico.class.getName()).log(Level.SEVERE, null, ex);

            //Escribo el mensaje de baja incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al borrar la tarjeta"));

        } catch (IllegalOrphanException ex) {
            Logger.getLogger(bUsuarioGestionPersonal.class.getName()).log(Level.SEVERE, null, ex);
            
            //Escribo el mensaje de baja incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al borrar la tarjeta"));

        }
        return "correcto";
    }

    //Metodo para borrar una direccion
    public String borrarDireccion(int codigoD) {
        try {
            //Borro los pedidos y canjeos a los que pertenece la direccion
            Direccion direcciones = ctrDireccion.findDireccion(codigoD);
            
            for (int i = 0; i < direcciones.getPedidoList().size(); i++) {
                ctrPedido.destroy(direcciones.getPedidoList().get(i).getCodigo());
            }
            for (int i = 0; i < direcciones.getCanjearList().size(); i++) {
                ctrCanjear.destroy(direcciones.getCanjearList().get(i).getCodigoCanjear());
            }
            
            //Borro la direccion de la base de datos
            ctrDireccion.destroy(codigoD);
            
            //Cojo el usuario de la sesion
            ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            manageBeanSesion manageBeanSesion = new manageBeanSesion();

            HttpSession session = (HttpSession) ctx.getSession(false);
            manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
            Usuario usuarioSesion = (Usuario) manageBeanSesion.getUsuarioLog();
            
            //Actualizo el usuario de la sesion
            Usuario usuarioNuevo = ctrUsuario.findUsuario(usuarioSesion.getCodigoUsuario());
            subirUsuario(usuarioNuevo);

            //Inicializamos la lista para que cuando entre ya esten cargados
            listaDirecciones = new ArrayList();

            listaDirecciones = usuarioNuevo.getDireccionList();

            //Comprobamos si quedan o no direcciones para informar al usuario
            if (listaDirecciones.size() == 0) {
                noExiste = true;
            }
            
            //Escribo el mensaje de baja correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha borrado la direccion correctamente."));

        } catch (NonexistentEntityException ex) {
            Logger.getLogger(bTecnico.class.getName()).log(Level.SEVERE, null, ex);

            //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al borrar la direccion"));

        } catch (IllegalOrphanException ex) {
            Logger.getLogger(bUsuarioGestionPersonal.class.getName()).log(Level.SEVERE, null, ex);
            
            //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al borrar la direccion"));

        }
        return "correcto";
    }

}
