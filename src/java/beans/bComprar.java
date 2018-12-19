package beans;

import DAO.CaracteristicastelefonoJpaController;
import DAO.DireccionJpaController;
import DAO.FotoJpaController;
import DAO.PedidoJpaController;
import DAO.PoblacionJpaController;
import DAO.ReparacionesJpaController;
import DAO.ReparacionestelefonoJpaController;
import DAO.TarjetaJpaController;
import DAO.TecnicoJpaController;
import DAO.TelefonoJpaController;
import DAO.UsuarioJpaController;
import DAO.exceptions.NonexistentEntityException;
import DTO.Direccion;
import DTO.Foto;
import DTO.Pedido;
import DTO.Poblacion;
import DTO.Reparaciones;
import DTO.Reparacionestelefono;
import DTO.Tarjeta;
import DTO.Tecnico;
import DTO.Telefono;
import DTO.TelefonoCesta;
import DTO.Usuario;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;

public class bComprar {

    //Driver para conectar con la base de datos
    private EntityManagerFactory emf;

    //Control para gestionar la base de datos
    private TelefonoJpaController ctrTelefono;
    private CaracteristicastelefonoJpaController ctrCaracteristicas;
    private PedidoJpaController ctrPedido;
    private FotoJpaController ctrFoto;
    private DireccionJpaController ctrDireccion;
    private TarjetaJpaController ctrTarjeta;
    private ReparacionestelefonoJpaController ctrReparacionesTelefono;
    private ReparacionesJpaController ctrReparaciones;
    private TecnicoJpaController ctrTecnico;
    private UsuarioJpaController ctrUsuario;

    //Lista donde guardamos los pedidos
    private List listaTelefonos;

    private List listaTelefonosR;

    //Lista donde guardamos los pedidos
    private List listaSO;
    private List listaMarcas;
    private List listaRam;
    private List listaPulgadas;
    private List listaAlmacenamiento;
    private List listaCamaraTrasera;
    private List listaCamaraDelantera;
    private List listaBateria;
    private List listaProcesador;
    private List listaResolucion;
    private List listaColor;

    private List listaColorCheckbox;

    private int min = 0;
    private int max = 1500;

    private Telefono telefonoVer;

    private int cantidad = 1;

    private float precioCesta;

    //Variable que guarda la lista de las direcciones del usuario
    private List listaDirecciones;

    //Variable que guarda la lista de las tarjetas del usuario
    private List listaTarjetas;

    //Variable que comprueba si tiene direcciones
    private boolean noExiste = false;

    //Variable que comprueba si tiene tarjetas
    private boolean noExisteT = false;

    private int activeIndex = 0;

    //Variable que comprueba si tiene direcciones
    private boolean direccion = true;

    //Variable que comprueba si tiene tarjetas
    private boolean tarjeta = false;

    private boolean step = true;

    private boolean confirmarCesta = false;

    private boolean pedidoCorrecto = false;

    private Direccion direccionPedido = null;

    private Tarjeta tarjetaPedido = null;

    //Mensaje del carrito
    private String mens ;

    //Realizar Reparacion
    private int reparacionSeleccionada;

    private float precioReparacion;

    private boolean verPrecio = false;
    private String rotura;

    private int estado;

    private String precioVenta;

    public bComprar() {
        emf = Persistence.createEntityManagerFactory("CRVPU");
        ctrTelefono = new TelefonoJpaController(emf);
        ctrFoto = new FotoJpaController(emf);
        ctrCaracteristicas = new CaracteristicastelefonoJpaController(emf);
        ctrDireccion = new DireccionJpaController(emf);
        ctrTarjeta = new TarjetaJpaController(emf);
        ctrPedido = new PedidoJpaController(emf);
        ctrReparacionesTelefono = new ReparacionestelefonoJpaController(emf);
        ctrReparaciones = new ReparacionesJpaController(emf);

        ctrTecnico = new TecnicoJpaController(emf);
        ctrUsuario = new UsuarioJpaController(emf);

        //Inicializamos la lista para que cuando entre ya esten cargados
        listaTelefonos = new ArrayList();

        listaTelefonos = ctrTelefono.findTelefonoEntities();

        listaTelefonosR = new ArrayList();

        listaTelefonosR = ctrTelefono.findTodosTelefonosDistintLista();

        //Inicializamos la lista para que cuando entre ya esten cargados
        listaTelefonos = new ArrayList();

        listaTelefonos = ctrTelefono.findTelefonoEntities();

        listaSO = new ArrayList();

        listaSO = ctrCaracteristicas.findTodosSODistint();
        Collections.sort(listaSO);

        listaMarcas = new ArrayList();

        listaMarcas = ctrTelefono.findTodosTelefonosDistint();
        Collections.sort(listaMarcas);

        listaRam = new ArrayList();

        listaRam = ctrCaracteristicas.findTodosRamDistint();
        Collections.sort(listaRam);

        listaPulgadas = new ArrayList();

        listaPulgadas = ctrCaracteristicas.findTodosPulgadasDistint();
        Collections.sort(listaPulgadas);

        listaAlmacenamiento = new ArrayList();

        listaAlmacenamiento = ctrCaracteristicas.findTodosAlmacenamientoDistint();
        Collections.sort(listaAlmacenamiento);

        listaCamaraTrasera = new ArrayList();

        listaCamaraTrasera = ctrCaracteristicas.findTodosCamaraTraseraDistint();
        Collections.sort(listaCamaraTrasera);

        listaCamaraDelantera = new ArrayList();

        listaCamaraDelantera = ctrCaracteristicas.findTodosCamaraDelanteraDistint();
        Collections.sort(listaCamaraDelantera);

        listaBateria = new ArrayList();

        listaBateria = ctrCaracteristicas.findTodosBateriaDistint();
        Collections.sort(listaBateria);

        listaProcesador = new ArrayList();

        listaProcesador = ctrCaracteristicas.findTodosProcesadorDistint();
        Collections.sort(listaProcesador);

        listaResolucion = new ArrayList();

        listaResolucion = ctrCaracteristicas.findTodosResolucionDistint();
        Collections.sort(listaResolucion);

        listaColor = new ArrayList();

        listaColor = ctrCaracteristicas.findTodosColorDistint();
         Collections.sort(listaColor);
         
         if("es"==Locale.getDefault().getLanguage()){
             mens="No tiene ningun artículo";
         }
         else{
              mens = "You do not have any article";
         }
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

    public List getListaTelefonos() {
        return listaTelefonos;
    }

    public void setListaTelefonos(List listaTelefonos) {
        this.listaTelefonos = listaTelefonos;
    }

    public FotoJpaController getCtrFoto() {
        return ctrFoto;
    }

    public void setCtrFoto(FotoJpaController ctrFoto) {
        this.ctrFoto = ctrFoto;
    }

    public CaracteristicastelefonoJpaController getCtrCaracteristicas() {
        return ctrCaracteristicas;
    }

    public void setCtrCaracteristicas(CaracteristicastelefonoJpaController ctrCaracteristicas) {
        this.ctrCaracteristicas = ctrCaracteristicas;
    }

    public List getListaSO() {
        return listaSO;
    }

    public void setListaSO(List listaSO) {
        this.listaSO = listaSO;
    }

    public List getListaMarcas() {
        return listaMarcas;
    }

    public void setListaMarcas(List listaMarcas) {
        this.listaMarcas = listaMarcas;
    }

    public List getListaRam() {
        return listaRam;
    }

    public void setListaRam(List listaRam) {
        this.listaRam = listaRam;
    }

    public List getListaPulgadas() {
        return listaPulgadas;
    }

    public void setListaPulgadas(List listaPulgadas) {
        this.listaPulgadas = listaPulgadas;
    }

    public List getListaAlmacenamiento() {
        return listaAlmacenamiento;
    }

    public void setListaAlmacenamiento(List listaAlmacenamiento) {
        this.listaAlmacenamiento = listaAlmacenamiento;
    }

    public List getListaCamaraTrasera() {
        return listaCamaraTrasera;
    }

    public void setListaCamaraTrasera(List listaCamaraTrasera) {
        this.listaCamaraTrasera = listaCamaraTrasera;
    }

    public List getListaCamaraDelantera() {
        return listaCamaraDelantera;
    }

    public void setListaCamaraDelantera(List listaCamaraDelantera) {
        this.listaCamaraDelantera = listaCamaraDelantera;
    }

    public List getListaBateria() {
        return listaBateria;
    }

    public void setListaBateria(List listaBateria) {
        this.listaBateria = listaBateria;
    }

    public List getListaProcesador() {
        return listaProcesador;
    }

    public void setListaProcesador(List listaProcesador) {
        this.listaProcesador = listaProcesador;
    }

    public List getListaResolucion() {
        return listaResolucion;
    }

    public void setListaResolucion(List listaResolucion) {
        this.listaResolucion = listaResolucion;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public List getListaColor() {
        return listaColor;
    }

    public void setListaColor(List listaColor) {
        this.listaColor = listaColor;
    }

    public List getListaColorCheckbox() {
        return listaColorCheckbox;
    }

    public void setListaColorCheckbox(List listaColorCheckbox) {
        this.listaColorCheckbox = listaColorCheckbox;
    }

    public Telefono getTelefonoVer() {
        return telefonoVer;
    }

    public void setTelefonoVer(Telefono telefonoVer) {
        this.telefonoVer = telefonoVer;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecioCesta() {
        return precioCesta;
    }

    public void setPrecioCesta(float precioCesta) {
        this.precioCesta = precioCesta;
    }

    public List getListaDirecciones() {
        return listaDirecciones;
    }

    public void setListaDirecciones(List listaDirecciones) {
        this.listaDirecciones = listaDirecciones;
    }

    public List getListaTarjetas() {
        return listaTarjetas;
    }

    public void setListaTarjetas(List listaTarjetas) {
        this.listaTarjetas = listaTarjetas;
    }

    public boolean isNoExiste() {
        return noExiste;
    }

    public void setNoExiste(boolean noExiste) {
        this.noExiste = noExiste;
    }

    public boolean isNoExisteT() {
        return noExisteT;
    }

    public void setNoExisteT(boolean noExisteT) {
        this.noExisteT = noExisteT;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public boolean isDireccion() {
        return direccion;
    }

    public void setDireccion(boolean direccion) {
        this.direccion = direccion;
    }

    public boolean isTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(boolean tarjeta) {
        this.tarjeta = tarjeta;
    }

    public boolean isConfirmarCesta() {
        return confirmarCesta;
    }

    public void setConfirmarCesta(boolean confirmarCesta) {
        this.confirmarCesta = confirmarCesta;
    }

    public Direccion getDireccionPedido() {
        return direccionPedido;
    }

    public void setDireccionPedido(Direccion direccionPedido) {
        this.direccionPedido = direccionPedido;
    }

    public DireccionJpaController getCtrDireccion() {
        return ctrDireccion;
    }

    public void setCtrDireccion(DireccionJpaController ctrDireccion) {
        this.ctrDireccion = ctrDireccion;
    }

    public TarjetaJpaController getCtrTarjeta() {
        return ctrTarjeta;
    }

    public void setCtrTarjeta(TarjetaJpaController ctrTarjeta) {
        this.ctrTarjeta = ctrTarjeta;
    }

    public Tarjeta getTarjetaPedido() {
        return tarjetaPedido;
    }

    public void setTarjetaPedido(Tarjeta tarjetaPedido) {
        this.tarjetaPedido = tarjetaPedido;
    }

    public boolean isStep() {
        return step;
    }

    public void setStep(boolean step) {
        this.step = step;
    }

    public PedidoJpaController getCtrPedido() {
        return ctrPedido;
    }

    public void setCtrPedido(PedidoJpaController ctrPedido) {
        this.ctrPedido = ctrPedido;
    }

    public boolean isPedidoCorrecto() {
        return pedidoCorrecto;
    }

    public void setPedidoCorrecto(boolean pedidoCorrecto) {
        this.pedidoCorrecto = pedidoCorrecto;
    }

    public String getMens() {
        return mens;
    }

    public void setMens(String mens) {
        this.mens = mens;
    }

    public List getListaTelefonosR() {
        return listaTelefonosR;
    }

    public void setListaTelefonosR(List listaTelefonosR) {
        this.listaTelefonosR = listaTelefonosR;
    }

    public int getReparacionSeleccionada() {
        return reparacionSeleccionada;
    }

    public void setReparacionSeleccionada(int reparacionSeleccionada) {
        this.reparacionSeleccionada = reparacionSeleccionada;
    }

    public ReparacionestelefonoJpaController getCtrReparacionesTelefono() {
        return ctrReparacionesTelefono;
    }

    public void setCtrReparacionesTelefono(ReparacionestelefonoJpaController ctrReparacionesTelefono) {
        this.ctrReparacionesTelefono = ctrReparacionesTelefono;
    }

    public ReparacionesJpaController getCtrReparaciones() {
        return ctrReparaciones;
    }

    public void setCtrReparaciones(ReparacionesJpaController ctrReparaciones) {
        this.ctrReparaciones = ctrReparaciones;
    }

    public float getPrecioReparacion() {
        return precioReparacion;
    }

    public void setPrecioReparacion(float precioReparacion) {
        this.precioReparacion = precioReparacion;
    }

    public boolean isVerPrecio() {
        return verPrecio;
    }

    public void setVerPrecio(boolean verPrecio) {
        this.verPrecio = verPrecio;
    }

    public String getRotura() {
        return rotura;
    }

    public TecnicoJpaController getCtrTecnico() {
        return ctrTecnico;
    }

    public void setCtrTecnico(TecnicoJpaController ctrTecnico) {
        this.ctrTecnico = ctrTecnico;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setRotura(String rotura) {
        this.rotura = rotura;
    }

    public String getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(String precioVenta) {
        this.precioVenta = precioVenta;
    }

    public UsuarioJpaController getCtrUsuario() {
        return ctrUsuario;
    }

    public void setCtrUsuario(UsuarioJpaController ctrUsuario) {
        this.ctrUsuario = ctrUsuario;
    }
    
    

    public List conocerReparaciones(Telefono telefono) {

        //Inicializamos la lista de provincias
        List listaReparaciones = new ArrayList();

        List<Reparacionestelefono> listaRe = telefono.getReparacionestelefonoList();
        for (Reparacionestelefono rp : listaRe) {
            Reparaciones repara = rp.getCodigoReparacion();

            listaReparaciones.add(new SelectItem(rp.getCodigoReparacionTelefono(), repara.getNombre()));
        }

        return listaReparaciones;
    }

    //Metodo que rellena la lista de poblaciones según la provincia que elijamos
    public void consultarPrecioReparacion(final AjaxBehaviorEvent event) {

        //Borro el codigo que tenia por defecto de la direccion para que no se guarde
        precioReparacion = 0;

        //Cojo el codigo de provincia para conseguir sus poblaciones
        int codigoReparacion = (int) ((UIOutput) event.getSource()).getValue();

        Reparacionestelefono reparacionTelefono = ctrReparacionesTelefono.findReparacionestelefono(codigoReparacion);

        precioReparacion = reparacionTelefono.getPrecio();

        verPrecio = true;
    }

    //Metodo que rellena la lista de poblaciones según la provincia que elijamos
    public void consultarPrecioVenta(final AjaxBehaviorEvent event) {

        //Borro el codigo que tenia por defecto de la direccion para que no se guarde
        precioReparacion = 0;

        //Cojo el codigo de provincia para conseguir sus poblaciones
        int estadoFuncional = (int) ((UIOutput) event.getSource()).getValue();

        
        float precio = telefonoVer.getPrecio();
        if (estadoFuncional == 0) {
            float precioV = precio / 2;

            float porcentaje = (precioV * 40) / 100;

            float precioFinal = precioV - porcentaje;

           precioVenta =String.valueOf(Math.round(precio*100.0)/100.0);

        }
        if (estadoFuncional == 1) {
            float precioV = precio / 2;

            float porcentaje = (precioV * 15) / 100;

            float precioFinal = precioV - porcentaje;

            precioVenta =String.valueOf(Math.round(precio*100.0)/100.0);
        }
    }

    public String comprarTelefono(Telefono telefono) {

        //Cojo el carrito de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        List listaCarrito = (List) manageBeanSesion.getListaCarrito();

        //Creo el objeto que irá en la lista junto con la cantidad
        TelefonoCesta telefonoCesta = new TelefonoCesta(telefono, 1, cantidad);

        boolean existeCesta = false;

        for (int i = 0; i < listaCarrito.size(); i++) {
            TelefonoCesta telefonoC = (TelefonoCesta) listaCarrito.get(i);
            Telefono te = telefonoC.getTelefono();
            if (te.getCodigoTelefono() == telefono.getCodigoTelefono() && telefonoC.getTipo() == 1) {
                int cantidadFinal = telefonoC.getCantidad() + cantidad;
                telefonoC.setCantidad(cantidadFinal);
                listaCarrito.set(i, telefonoC);
                existeCesta = true;
            }
        }

        if (!existeCesta) {
            listaCarrito.add(telefonoCesta);
        }

        //Subo el carrito a la sesion
        subirCarrito(listaCarrito);

        //Resete la cantidad 
        cantidad = 1;

        //Escribo mensaje carrito
        mens = "Tiene " + listaCarrito.size() + " articulos en el carrito.";

        return "correcto";
    }

    public String repararTelefono(Telefono telefono) {

        //Cojo el carrito de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        List listaCarrito = (List) manageBeanSesion.getListaCarrito();

        //Creo el objeto que irá en la lista junto con la cantidad
        TelefonoCesta telefonoCesta = new TelefonoCesta(telefono, 2, reparacionSeleccionada, rotura, cantidad);

        boolean existeCesta = false;

        for (int i = 0; i < listaCarrito.size(); i++) {
            TelefonoCesta telefonoC = (TelefonoCesta) listaCarrito.get(i);
            Telefono te = telefonoC.getTelefono();
            if (te.getCodigoTelefono() == telefono.getCodigoTelefono() && telefonoC.getTipo() == 2 && telefonoC.getCodigoReparacion()==reparacionSeleccionada) {
                int cantidadFinal = telefonoC.getCantidad() + cantidad;
                telefonoC.setCantidad(cantidadFinal);
                listaCarrito.set(i, telefonoC);
                existeCesta = true;
            }
        }

        if (!existeCesta) {
            listaCarrito.add(telefonoCesta);
        }

        //Subo el carrito a la sesion
        subirCarrito(listaCarrito);

        //Resete la cantidad 
        cantidad = 1;

        //Escribo mensaje carrito
        mens = "Tiene " + listaCarrito.size() + " articulos en el carrito.";

        return "correcto";
    }

    public String venderTelefono(Telefono telefono) {

        //Cojo el carrito de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        List listaCarrito = (List) manageBeanSesion.getListaCarrito();

        //Creo el objeto que irá en la lista junto con la cantidad
        TelefonoCesta telefonoCesta = new TelefonoCesta(telefono, 3, estado, cantidad);

        boolean existeCesta = false;

        for (int i = 0; i < listaCarrito.size(); i++) {
            TelefonoCesta telefonoC = (TelefonoCesta) listaCarrito.get(i);
            Telefono te = telefonoC.getTelefono();
            if (telefonoC.getTipo() == 3) {
                int cantidadFinal = telefonoC.getCantidad() + cantidad;
                telefonoC.setCantidad(cantidadFinal);
                listaCarrito.set(i, telefonoC);
                existeCesta = true;
            }
        }

        if (!existeCesta) {
            listaCarrito.add(telefonoCesta);
        }

        //Subo el carrito a la sesion
        subirCarrito(listaCarrito);

        //Resete la cantidad 
        cantidad = 1;

        //Escribo mensaje carrito
        mens = "Tiene " + listaCarrito.size() + " articulos en el carrito.";

        return "correcto";
    }

    //Metodo para subir usuario a la sesion, es decir como atributo de bTienda ya que será el bean de sesion
    public void subirCarrito(List listaCarrito) {
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
        manageBeanSesion.setListaCarrito(listaCarrito);
    }

    //Metodo que devuelve el nombre de la foto segun el telefono
    public String conocerFoto(Telefono telefono) {

        List listaFotos = ctrFoto.findFotoByCodigoTelefono(telefono);

        if (listaFotos.size() > 0) {
            Foto foto = (Foto) listaFotos.get(0);
            return foto.getNombre();
        }
        return "";
    }

    //Metodo que devuelve el nombre de la foto segun el telefono
    public String conocerNombre(TelefonoCesta telefono) {

        //Es compra
        if (telefono.getTipo() == 1) {
            return telefono.getTelefono().getNombre();
        }
        //Es reparacion
        if (telefono.getTipo() == 2) {
            Reparacionestelefono reparacion = ctrReparacionesTelefono.findReparacionestelefono(telefono.getCodigoReparacion());

            String nombre = "Reparacion: " + telefono.getTelefono().getNombre() + "- Rotura: " + reparacion.getCodigoReparacion().getNombre();
            return nombre;
        } 
        if(telefono.getTipo()==3){
             String nombre = "Venta: " + telefono.getTelefono().getNombre();
            return nombre;
        }
        return "";
    }

    //Metodo que devuelve el nombre de la foto segun el telefono
    public String conocerPrecio(TelefonoCesta telefono) {

        //Es compra
        if (telefono.getTipo() == 1) {
            return String.valueOf(telefono.getTelefono().getPrecio());
        }
        //Es reparacion
        if (telefono.getTipo() == 2) {
            Reparacionestelefono reparacion = ctrReparacionesTelefono.findReparacionestelefono(telefono.getCodigoReparacion());

            return String.valueOf(reparacion.getPrecio());
        }
        
        //Es venta
                if (telefono.getTipo() == 3) {
                    
                        float precio2 = telefono.getTelefono().getPrecio();
                        if (telefono.getFuncional() == 0) {
                            float precioV = precio2 / 2;

                            float porcentaje = (precioV * 40) / 100;

                            float precioFinal = precioV - porcentaje;

                            return  precioVenta =String.valueOf(Math.round(precioFinal*100.0)/100.0);

                        }
                        if (telefono.getFuncional() == 1) {
                            float precioV = precio2 / 2;

                            float porcentaje = (precioV * 15) / 100;

                            float precioFinal = precioV - porcentaje;

                             return  precioVenta =String.valueOf(Math.round(precioFinal*100.0)/100.0);
                        }
                    }
                
        
            return "";
        
    }

    public String arreglarID(String id) {
        String[] partes = id.split("\\.");
        return partes[0] + "-" + partes[1];

    }

    public String arreglarID2(boolean estado) {

        if (estado) {
            return "1";
        } else {
            return "0";
        }

    }

    public String arreglarResolucion(String resolucion) {
        char[] partes = resolucion.toCharArray();
        String cadena = "";
        if (resolucion.equals("1920x1080")) {
            return "1920 x 1080 (Full HD)";
        }
        if (resolucion.equals("3840x2160")) {
            return "3840 x 2160 (4K)";
        }
        char conocer ='x';
        if (partes.length == 7) {
            for (int i = 0; i < partes.length; i++) {
                if (partes[i] == conocer) {
                    cadena = cadena + " x ";
                } else {
                    cadena = cadena + partes[i];
                }

            }
            return cadena;
        }
        if (partes.length == 8) {
            for (int i = 0; i < partes.length; i++) {
                if (partes[i] == conocer) {
                    cadena = cadena + " x ";
                } else {
                    cadena = cadena + partes[i];
                }

            }
            return cadena;
        }
        if (partes.length == 9) {
            for (int i = 0; i < partes.length; i++) {
                if (partes[i] == conocer) {
                    cadena = cadena + " x ";
                } else {
                    cadena = cadena + partes[i];
                }

            }
            return cadena;
        }
        return cadena;
    }

    //Metodo que devuelve el telefono que queremos ver
    public void conocerTelefono() {
        verPrecio = false;
        //Coger contexto
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, String> parameterMap = (Map<String, String>) ctx.getRequestParameterMap();

        int numeroParametros = parameterMap.size();

        if (numeroParametros == 1) {

            Collection<String> listaValores = parameterMap.values();

            String codigoString = listaValores.toString();

            String[] separarCodigo1 = codigoString.split("\\[");

            String[] separaCodigo2 = separarCodigo1[1].split("\\]");
            telefonoVer = ctrTelefono.findTelefono(Integer.parseInt(separaCodigo2[0]));
        }
        
      
        float precio = telefonoVer.getPrecio();
       
            float precioV = precio / 2;

            float porcentaje = (precioV * 40) / 100;

            float precioFinal = precioV - porcentaje;

           precioVenta =String.valueOf(Math.round(precioFinal*100.0)/100.0);

    }

    //Metodo que devuelve el telefono que queremos ver
    public String arreglarBoolean(boolean tiene) {
        if (tiene) {
            return "Si";
        } else {
            return "No";
        }

    }

    //Metodo que devuelve el telefono que queremos ver
    public List conocerCesta() {
        
        //Cojo el carrito de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        List listaCarrito;

        if (manageBeanSesion != null) {
            listaCarrito = (List) manageBeanSesion.getListaCarrito();
            float precio = (float) 0.0;
            //Saco el precio total
            for (int i = 0; i < listaCarrito.size(); i++) {
                TelefonoCesta telefonoC = (TelefonoCesta) listaCarrito.get(i);
                //Es compra
                if (telefonoC.getTipo()== 1 ) {
                    precio = precio + (telefonoC.getTelefono().getPrecio() * telefonoC.getCantidad());
                }
                //Es reparacion
                if (telefonoC.getTipo()== 2) {
                    Reparacionestelefono reparacion = ctrReparacionesTelefono.findReparacionestelefono(telefonoC.getCodigoReparacion());
                    precio = precio + (reparacion.getPrecio() * telefonoC.getCantidad());
                }

                //Es venta
                if (telefonoC.getTipo() == 3) {
                   float precio2 = telefonoC.getTelefono().getPrecio()* telefonoC.getCantidad();
                        if (telefonoC.getFuncional() == 0) {
                            float precioV = precio2 / 2;

                            float porcentaje = (precioV * 40) / 100;

                            float precioFinal = precioV - porcentaje;
                            float precioF=(float) (Math.round(precioFinal*100.0)/100.0);
                            precio = precio - precioF;

                        }
                        if (telefonoC.getFuncional() == 1) {
                            float precioV = precio2 / 2;

                            float porcentaje = (precioV * 15) / 100;

                            float precioFinal = precioV - porcentaje;

                           float precioF=(float) (Math.round(precioFinal*100.0)/100.0);
                            precio = precio - precioF;
                        }
                    
                }

            }
            cantidad=0;
            precioCesta =(float) (Math.round(precio*100.0)/100.0);
        } else {
            listaCarrito = new ArrayList();
        }

        return listaCarrito;
    }

    public String realizarPedido() {
        direccion = true;
        tarjeta = false;
        confirmarCesta = false;
        pedidoCorrecto = false;
        step = true;
        activeIndex = 0;

        //Cargo direcciones y pedidos
        //Cojo el usuario de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        Usuario usuario = (Usuario) manageBeanSesion.getUsuarioLog();

        //Inicializamos la lista para que cuando entre ya esten cargados
        listaDirecciones = new ArrayList();

        listaDirecciones = usuario.getDireccionList();

        //Inicializamos la lista para que cuando entre ya esten cargados
        listaTarjetas = new ArrayList();

        listaTarjetas = usuario.getTarjetaList();

        return "finalizar";
    }

    public String cambiarDireccion() {

        direccion = true;
        tarjeta = false;
        confirmarCesta = false;
        pedidoCorrecto = false;
        step = true;
        activeIndex = 0;
        return "correcto";
    }

    public String cambiarTarjeta() {

        direccion = false;
        tarjeta = true;
        confirmarCesta = false;
        pedidoCorrecto = false;
        step = true;
        activeIndex = 1;
        return "correcto";
    }

    public String seleccionarDireccion(int codigoDireccion) {

        direccion = false;
        tarjeta = true;
        confirmarCesta = false;
        pedidoCorrecto = false;
        step = true;
        activeIndex = 1;

        direccionPedido = ctrDireccion.findDireccion(codigoDireccion);
        return "correcto";
    }

    public String seleccionarTarjeta(int codigoTarjeta) {

        direccion = false;
        tarjeta = false;
        confirmarCesta = true;
        pedidoCorrecto = false;
        step = true;
        activeIndex = 2;

        tarjetaPedido = ctrTarjeta.findTarjeta(codigoTarjeta);
        return "correcto";
    }

    public String finalizarPedido() {

        direccion = false;
        tarjeta = false;
        confirmarCesta = false;
        pedidoCorrecto = true;
        step = false;

        List lista = ctrPedido.findListaPedidoByCodigoPedidoOrder();

        int codigoPedido = (int) lista.get(0) + 1;

        //Cojo el carrito de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        List listaCarrito = (List) manageBeanSesion.getListaCarrito();
        Usuario usuario = (Usuario) manageBeanSesion.getUsuarioLog();

        java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());

        for (int i = 0; i < listaCarrito.size(); i++) {
            TelefonoCesta telefonoC = (TelefonoCesta) listaCarrito.get(i);
            Pedido pedido = null;
            if (telefonoC.getTipo() == 1) {
                pedido = new Pedido(null, codigoPedido,
                        4392,
                        1, telefonoC.getTelefono().getPrecio(),
                        telefonoC.getCantidad(), 1, date);
            }
            //Es reparacion
            if (telefonoC.getTipo() == 2) {
               Reparacionestelefono repara = ctrReparacionesTelefono.findReparacionestelefono(telefonoC.getCodigoReparacion());

                pedido = new Pedido(null, codigoPedido,
                        direccionPedido.getCodigoDireccion(),
                        2, repara.getPrecio(),
                        telefonoC.getCantidad(), 2, date);
                
                pedido.setCodigoReparacion(repara.getCodigoReparacion());
                pedido.setRotura(telefonoC.getRotura());

                List listaP = ctrPedido.findListaPedidoByOrder();
                int codigoT = 0;
                for (int j = 0; j < listaP.size(); j++) {
                    Pedido ped = (Pedido) listaP.get(j);
                    if (ped.getCodigoTecnico() != null) {
                        codigoT = ped.getCodigoTecnico().getCodigoTecnico();
                        j = listaP.size();
                    }
                }

                List listaT = ctrTecnico.findTecnicoEntities();
                for (int j = 0; j < listaT.size(); j++) {
                    Tecnico tec = (Tecnico) listaT.get(j);
                    if (tec.getCodigoTecnico() == codigoT) {
                        if (j++ == listaT.size()) {
                            Tecnico tec2 = (Tecnico) listaT.get(0);
                            codigoT = tec2.getCodigoTecnico();
                            j = listaT.size();
                        }
                        Tecnico tec3 = (Tecnico) listaT.get(j-1);
                        codigoT = tec3.getCodigoTecnico();
                    }
                }

                pedido.setCodigoTecnico(ctrTecnico.findTecnico(codigoT));
            }
            
            //Si es venta
            if(telefonoC.getTipo()==3){
                
                 float precio2 = telefonoC.getTelefono().getPrecio()* telefonoC.getCantidad();
                 float precioF=(float) 0.0;
                        if (telefonoC.getFuncional() == 0) {
                            float precioV = precio2 / 2;

                            float porcentaje = (precioV * 40) / 100;

                            float precioFinal = precioV - porcentaje;
                             precioF=(float) (Math.round(precioFinal*100.0)/100.0);
                            

                        }
                        if (telefonoC.getFuncional() == 1) {
                            float precioV = precio2 / 2;

                            float porcentaje = (precioV * 15) / 100;

                            float precioFinal = precioV - porcentaje;

                           precioF=(float) (Math.round(precioFinal*100.0)/100.0);
                        }
                pedido = new Pedido(null, codigoPedido,
                        direccionPedido.getCodigoDireccion(),
                        3,precioF , telefonoC.getCantidad(), 1, date);
            }

            pedido.setCodigoTelefono(telefonoC.getTelefono());
            pedido.setCodigoDireccion(direccionPedido);
            pedido.setCodigoTarjeta(tarjetaPedido);
            pedido.setCodigoUsuario(usuario);
            ctrPedido.create(pedido);
            
            

        }
        
        Usuario usuarioActualizado = ctrUsuario.findUsuario(usuario.getCodigoUsuario());
        subirUsuario(usuarioActualizado);
            listaCarrito=new ArrayList();
            subirCarrito(listaCarrito);
            
              mens = "No tiene ningún articulo";
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
        
        manageBeanSesion.setLogeado(false);
    }

    public void refrescarCarrito(TelefonoCesta telefonoActualizar) {
        //Cojo el carrito de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        List listaCarrito = (List) manageBeanSesion.getListaCarrito();

        float precio = (float) 0.0;
        //Saco el precio total
        //Saco el precio total
        for (int i = 0; i < listaCarrito.size(); i++) {
            TelefonoCesta telefonoC = (TelefonoCesta) listaCarrito.get(i);
            //Es compra
            if (telefonoC.getTipo() == 1) {
                precio = precio + (telefonoC.getTelefono().getPrecio() * telefonoC.getCantidad());
            }
            //Es reparacion
            if (telefonoC.getTipo() == 2) {
                Reparacionestelefono reparacion = ctrReparacionesTelefono.findReparacionestelefono(telefonoC.getCodigoReparacion());
                precio = precio + (reparacion.getPrecio() * telefonoC.getCantidad());
            }
            //Es venta
                if (telefonoC.getTipo() == 3) {
                   
                        float precio2 = telefonoC.getTelefono().getPrecio()* telefonoC.getCantidad();
                        if (telefonoC.getFuncional() == 0) {
                            float precioV = precio2 / 2;

                            float porcentaje = (precioV * 40) / 100;

                            float precioFinal = precioV - porcentaje;

                             float precioF=(float) (Math.round(precioFinal*100.0)/100.0);
                            precio = precio - precioF;

                        }
                        if (telefonoC.getFuncional() == 1) {
                            float precioV = precio2 / 2;

                            float porcentaje = (precioV * 15) / 100;

                            float precioFinal = precioV - porcentaje;

                             float precioF=(float) (Math.round(precioFinal*100.0)/100.0);
                            precio = precio - precioF;
                        }
                    
                }

        }

        precioCesta = precio;

    }

    public void eliminarArticulo(TelefonoCesta telefonoEliminar) {
        //Cojo el carrito de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        List listaCarrito = (List) manageBeanSesion.getListaCarrito();

        float precio = (float) 0.0;
        //Saco el precio total
        for (int i = 0; i < listaCarrito.size(); i++) {
            TelefonoCesta telefonoC = (TelefonoCesta) listaCarrito.get(i);
            if (telefonoC.equals(telefonoEliminar)) {
                listaCarrito.remove(i);

            }
            precio = precio + (telefonoC.getTelefono().getPrecio() * telefonoC.getCantidad());
        }

        precioCesta = precio;

        //Escribo mensaje carrito
        mens = "Tiene " + listaCarrito.size() + " articulos en el carrito.";
        if (listaCarrito.size() == 0) {
            mens = "No tiene ningún articulo";
        }

    }
    
    public void comprobarMensaje(){
        String hola=Locale.getDefault().getLanguage();
    }

    public void updateCantidad(ValueChangeEvent event) {
        cantidad = (Integer) event.getNewValue();
    }
    
    public String quitarEspacios(String valor){
        String devolver= valor.replace(" ", "");
        String devolver2= devolver.replace(".","");
         return devolver2;
    }

}
