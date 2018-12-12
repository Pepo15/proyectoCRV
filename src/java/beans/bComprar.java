package beans;

import DAO.CaracteristicastelefonoJpaController;
import DAO.DireccionJpaController;
import DAO.FotoJpaController;
import DAO.PedidoJpaController;
import DAO.PoblacionJpaController;
import DAO.TarjetaJpaController;
import DAO.TelefonoJpaController;
import DAO.exceptions.NonexistentEntityException;
import DTO.Direccion;
import DTO.Foto;
import DTO.Pedido;
import DTO.Poblacion;
import DTO.Tarjeta;
import DTO.Tecnico;
import DTO.Telefono;
import DTO.TelefonoCesta;
import DTO.Usuario;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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

    //Lista donde guardamos los pedidos
    private List listaTelefonos;
    
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
    
    private int min =0;
    private int max =1500;

    private Telefono telefonoVer;
    
    private int cantidad=1;
    
    private float precioCesta;
    
     //Variable que guarda la lista de las direcciones del usuario
    private List listaDirecciones;

    //Variable que guarda la lista de las tarjetas del usuario
    private List listaTarjetas;
    
     //Variable que comprueba si tiene direcciones
    private boolean noExiste = false;
    
    //Variable que comprueba si tiene tarjetas
    private boolean noExisteT = false;
    
    private int activeIndex=0;
    
     //Variable que comprueba si tiene direcciones
    private boolean direccion = true;
    
    //Variable que comprueba si tiene tarjetas
    private boolean tarjeta = false;
    
    private boolean step = true;
    
    private boolean confirmarCesta =false;
    
     private boolean pedidoCorrecto =false;
    
    private Direccion direccionPedido=null;
    
    private Tarjeta tarjetaPedido=null;
    
    //Mensaje del carrito
    private String mens="No tiene ningun artículo";
    

    public bComprar() {
        emf = Persistence.createEntityManagerFactory("CRVPU");
        ctrTelefono = new TelefonoJpaController(emf);
        ctrFoto = new FotoJpaController(emf);
        ctrCaracteristicas = new CaracteristicastelefonoJpaController(emf);
        ctrDireccion = new DireccionJpaController(emf);
        ctrTarjeta = new TarjetaJpaController(emf);
        ctrPedido = new PedidoJpaController(emf);
        
       //Inicializamos la lista para que cuando entre ya esten cargados
        listaTelefonos = new ArrayList();

        listaTelefonos = ctrTelefono.findTelefonoEntities();
        
        listaSO = new ArrayList();
        
        listaSO = ctrCaracteristicas.findTodosSODistint();
        
        listaMarcas = new ArrayList();
        
        listaMarcas = ctrTelefono.findTodosTelefonosDistint();
        
        listaRam = new ArrayList();
        
        listaRam = ctrCaracteristicas.findTodosRamDistint();
        
        listaPulgadas = new ArrayList();
        
        listaPulgadas = ctrCaracteristicas.findTodosPulgadasDistint();
        
        
        
        listaAlmacenamiento = new ArrayList();
        
        listaAlmacenamiento = ctrCaracteristicas.findTodosAlmacenamientoDistint();
        
        listaCamaraTrasera = new ArrayList();
        
        listaCamaraTrasera = ctrCaracteristicas.findTodosCamaraTraseraDistint();
        
        
        
        listaCamaraDelantera = new ArrayList();
        
        listaCamaraDelantera = ctrCaracteristicas.findTodosCamaraDelanteraDistint();
        
        
        
        listaBateria = new ArrayList();
        
        listaBateria = ctrCaracteristicas.findTodosBateriaDistint();
        
        listaProcesador = new ArrayList();
        
        listaProcesador = ctrCaracteristicas.findTodosProcesadorDistint();
        
        listaResolucion = new ArrayList();
        
       listaResolucion = ctrCaracteristicas.findTodosResolucionDistint();
       
        listaColor = new ArrayList();
        
       listaColor = ctrCaracteristicas.findTodosColorDistint();
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
     
    public String comprarTelefono(Telefono telefono){
        
        //Cojo el carrito de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        List listaCarrito = (List) manageBeanSesion.getListaCarrito();
        
        //Creo el objeto que irá en la lista junto con la cantidad
        TelefonoCesta telefonoCesta = new TelefonoCesta(telefono,1, cantidad);
        
        boolean existeCesta =false;
        
        for (int i = 0; i < listaCarrito.size(); i++) {
                TelefonoCesta telefonoC = (TelefonoCesta) listaCarrito.get(i);
                Telefono te = telefonoC.getTelefono();
                if(te.getCodigoTelefono()==telefono.getCodigoTelefono()){
                    int cantidadFinal = telefonoC.getCantidad()+cantidad;
                    telefonoC.setCantidad(cantidadFinal);
                    listaCarrito.set(i, telefonoC);
                    existeCesta=true;
                }
        }
        
        if(!existeCesta){
           listaCarrito.add(telefonoCesta);
        }
        
        //Subo el carrito a la sesion
        subirCarrito(listaCarrito);
        
        //Resete la cantidad 
        cantidad=1;
        
        //Escribo mensaje carrito
        mens="Tiene " + listaCarrito.size() +" articulos en el carrito.";
        

        return "correcto";
    }
    
    //Metodo para subir usuario a la sesion, es decir como atributo de bTienda ya que será el bean de sesion
    public  void subirCarrito(List listaCarrito)
    {
        //Coger contexto
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        
        manageBeanSesion manageBeanSesion=new manageBeanSesion();
        
            
        //Coger session del contexto
        HttpSession session= (HttpSession)ctx.getSession(false);
            
        if(session.getAttribute("manageBeanSesion")!=null){
                manageBeanSesion=(manageBeanSesion) session.getAttribute("manageBeanSesion");
        }else{
                session.setAttribute("manageBeanSesion",manageBeanSesion);
        }
  
            //Añadirle como propiedad el usuario que se ha logeado
            manageBeanSesion.setListaCarrito(listaCarrito);          
    }
    
    //Metodo que devuelve el nombre de la foto segun el telefono
    public String conocerFoto(Telefono telefono) {
        
        List listaFotos = ctrFoto.findFotoByCodigoTelefono(telefono);

        
        if(listaFotos.size()>0){
            Foto foto= (Foto)listaFotos.get(0);
        return foto.getNombre();
        }
        return "";
    }
    
    public String arreglarID(String id){
        String[] partes = id.split("\\.");
        return partes[0]+"-"+partes[1];
        
    }
    public String arreglarID2(boolean estado){
       
        if(estado){
            return "1";
        }
        else{
            return "0";
        }
        
    }
    public String arreglarResolucion(String resolucion){
        char[] partes = resolucion.toCharArray();
        String cadena="";
        if(resolucion.equals("19201080")){
            return "1920 x 1080 (Full HD)";
        }
        if(resolucion.equals("38402160")){
            return "3840 x 2160 (4K)";
        }
        if(partes.length==6){
            for (int i = 0; i < partes.length; i++) {
                if(i==3){
                    cadena=cadena+" x " ;
                    cadena=cadena+partes[i];
                }
                else{
                    cadena=cadena+partes[i];
                }
                
            }
            return cadena;
        }
         if(partes.length==8){
            for (int i = 0; i < partes.length; i++) {
                if(i==4){
                    cadena=cadena+" x " ;
                    cadena=cadena+partes[i];
                }
                else{
                    cadena=cadena+partes[i];
                }
                
            }
            return cadena;
        }
         return cadena;
    }
    
    //Metodo que devuelve el telefono que queremos ver
    public void conocerTelefono() {
        //Coger contexto
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, String> parameterMap = (Map<String, String>) ctx.getRequestParameterMap();
        
        int numeroParametros = parameterMap.size();
        
        if(numeroParametros==1){
        
        Collection<String> listaValores =parameterMap.values();
        
        String codigoString = listaValores.toString();
        
        String[] separarCodigo1 = codigoString.split("\\[");
        
        String[] separaCodigo2 = separarCodigo1[1].split("\\]");
        telefonoVer = ctrTelefono.findTelefono(Integer.parseInt(separaCodigo2[0]));
        }

    }
    
    //Metodo que devuelve el telefono que queremos ver
    public String arreglarBoolean(boolean tiene) {
        if(tiene){
            return "Si";
        }
        else{
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
        
        if (manageBeanSesion!=null){
        listaCarrito = (List) manageBeanSesion.getListaCarrito();
        float precio=(float) 0.0;
        //Saco el precio total
        for (int i = 0; i < listaCarrito.size(); i++) {
            TelefonoCesta telefonoC = (TelefonoCesta) listaCarrito.get(i);
            precio= precio +(telefonoC.getTelefono().getPrecio()*telefonoC.getCantidad());
        }
        precioCesta=precio;
        }
        else{
            listaCarrito= new ArrayList();
        }
        
        return listaCarrito;
        }

    public String realizarPedido(){
        
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
    
    public String cambiarDireccion(){
        
        direccion=true;
        tarjeta=false;
        confirmarCesta=false;
        pedidoCorrecto =false;
        step=true;
        activeIndex=0;
        return "correcto";
    }
    
    public String cambiarTarjeta(){
        
        direccion=false;
        tarjeta=true;
        confirmarCesta=false;
        pedidoCorrecto =false;
        step=true;
        activeIndex=1;
        return "correcto";
    }
    
    public String seleccionarDireccion(int codigoDireccion){
        
        direccion=false;
        tarjeta=true;
        confirmarCesta=false;
        pedidoCorrecto =false;
        step=true;
        activeIndex=1;
        
        direccionPedido=ctrDireccion.findDireccion(codigoDireccion);
        return "correcto";
    }
    
     public String seleccionarTarjeta(int codigoTarjeta){
        
        direccion=false;
        tarjeta=false;
        confirmarCesta=true;
        pedidoCorrecto =false;
        step=true;
        activeIndex=2;
        
        tarjetaPedido=ctrTarjeta.findTarjeta(codigoTarjeta);
        return "correcto";
    }
     
      public String finalizarPedido(){
        
        direccion=false;
        tarjeta=false;
        confirmarCesta=false;
        pedidoCorrecto =true;
        step=false;
        
        List lista = ctrPedido.findListaPedidoByCodigoPedidoOrder();
        
        int codigoPedido = (int) lista.get(0)+1;
        
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
              Pedido pedido = new Pedido(null, codigoPedido
                      , 4392,
                      1, telefonoC.getTelefono().getCodigoTelefono(),
                      telefonoC.getCantidad(), 1, date);
              pedido.setCodigoTelefono(telefonoC.getTelefono());
              pedido.setCodigoDireccion(direccionPedido);
              pedido.setCodigoTarjeta(tarjetaPedido);
              pedido.setCodigoUsuario(usuario);
              ctrPedido.create(pedido);
              
          }
        
        return "correcto";
    }
    
    public void refrescarCarrito(TelefonoCesta telefonoActualizar) {
        //Cojo el carrito de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        List listaCarrito = (List) manageBeanSesion.getListaCarrito();
        
        float precio=(float) 0.0;
        //Saco el precio total
        for (int i = 0; i < listaCarrito.size(); i++) {
            TelefonoCesta telefonoC = (TelefonoCesta) listaCarrito.get(i);
            if(telefonoC.equals(telefonoActualizar)){
                    telefonoC.setCantidad(cantidad);
                    listaCarrito.set(i, telefonoC);

            }
            precio= precio +(telefonoC.getTelefono().getPrecio()*telefonoC.getCantidad());
        }
        
        precioCesta=precio;
       
}
    public void eliminarArticulo(TelefonoCesta telefonoEliminar) {
        //Cojo el carrito de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        List listaCarrito = (List) manageBeanSesion.getListaCarrito();
        
        float precio=(float) 0.0;
        //Saco el precio total
        for (int i = 0; i < listaCarrito.size(); i++) {
            TelefonoCesta telefonoC = (TelefonoCesta) listaCarrito.get(i);
            if(telefonoC.equals(telefonoEliminar)){
                    listaCarrito.remove(i);

            }
            precio= precio +(telefonoC.getTelefono().getPrecio()*telefonoC.getCantidad());
        }
        
        precioCesta=precio;
        
        //Escribo mensaje carrito
        mens="Tiene " + listaCarrito.size() +" articulos en el carrito.";
        if(listaCarrito.size()==0){
             mens="No tiene ningún articulo";
        }
        
       
}
    public void updateCantidad(ValueChangeEvent event) {
        cantidad = (Integer) event.getNewValue();
}
    
}


