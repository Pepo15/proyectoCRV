package beans;

import DAO.AdministradorJpaController;
import DAO.DireccionJpaController;
import DAO.PedidoJpaController;
import DAO.PoblacionJpaController;
import DAO.TecnicoJpaController;
import DAO.TelefonoJpaController;
import DAO.UsuarioJpaController;
import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DTO.Administrador;
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
    
    //Control para gestionar el usuario de la base de datos
    private UsuarioJpaController ctrUsuario;
    private TecnicoJpaController ctrTecnico;
    private AdministradorJpaController ctrAdministrador;
    
    //Control para gestionar los pedidos
    private PedidoJpaController ctrPedido;
    
    //Control para gestionar los telefonos
    private TelefonoJpaController ctrTelefono;
    
     //Control para gestionar las poblaciones
    private PoblacionJpaController ctrPoblacion;
    
    //Variables que almacenan los datos de los inputs, para dar de alta un técnico
    private String nick;
    private String password;
    
    //Variables que almacenan los datos de los inputs, para dar de alta un telefono
    private String nombre;
    private String marca;
    private String precio;
    
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
    
    
    //Variable que almacena el codigo del pedido anterior
    private int codigoPedidoAnterior;
    private int codigoPedido;
    
    //Variable que almacena la poblacion que queremos agregar
    private String codigoPoblacion;
    
     //Variable para saber si debemos mostrar ventana estado o no
    private boolean estado=false;
    
     //Variable para saber si debemos mostrar ventana estado o no
    private boolean ubicacion=false;
    
    //Variable donde guardaremos el nuevo estado
    private String estadoNuevo;
      
    //Mensaje que devolveremos para poder comprobar el logeo
    private String mens;
    
    //Lista donde guardamos los tenicos
    private List listaTecnicos;
    
    //Lista donde guardamos los pedidos
    private List listaPedidos;
    
    //Lista donde guardamos todas las poblaciones
    private ArrayList listaPoblaciones;
    
    //Lista que guarda los valores del select de estados
    private ArrayList listaEstados = null;
    
    //Guardamos la fila de la tabla, para saber el tecnico que deseamos borrar
    private HtmlDataTable tabla;
    
    //Guardamos el codigo del pedido, para saber que pedido desamos modificar
    private int codigoPedidoModificar;

    //Contructor
    public bAdministrador() {
        emf = Persistence.createEntityManagerFactory("CRVPU");
        ctrUsuario= new UsuarioJpaController(emf);
        ctrTecnico= new TecnicoJpaController(emf);
        ctrAdministrador= new AdministradorJpaController(emf);
        ctrPedido= new PedidoJpaController(emf);
        ctrTelefono= new TelefonoJpaController(emf);
        
        ctrPoblacion= new PoblacionJpaController(emf);
        
        //Inicializamos la lista para que cuando entre ya esten cargados
        listaTecnicos= new ArrayList();
         
        listaTecnicos=ctrTecnico.findTecnicoEntities();
        
        //Inicializamos la lista para que cuando entre ya esten cargados
        listaPedidos= new ArrayList();
         
        listaPedidos=ctrPedido.findPedidoEntities();
        
    }
    
    //Getter and setter

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

    public TelefonoJpaController getCtrTelefono() {
        return ctrTelefono;
    }

    public void setCtrTelefono(TelefonoJpaController ctrTelefono) {
        this.ctrTelefono = ctrTelefono;
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

    public String getMens() {
        return mens;
    }

    public void setMens(String mens) {
        this.mens = mens;
    }

    public List getListaTecnicos() {
        return listaTecnicos;
    }

    public void setListaTecnicos(List listaTecnicos) {
        this.listaTecnicos = listaTecnicos;
    }

    public PedidoJpaController getCtrPedido() {
        return ctrPedido;
    }

    public void setCtrPedido(PedidoJpaController ctrPedido) {
        this.ctrPedido = ctrPedido;
    }

    public List getListaPedidos() {
        return listaPedidos;
    }

    public void setListaPedidos(List listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    public int getCodigoPedidoAnterior() {
        return codigoPedidoAnterior;
    }

    public void setCodigoPedidoAnterior(int codigoPedidoAnterior) {
        this.codigoPedidoAnterior = codigoPedidoAnterior;
    }

    public int getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(int codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    

    public String getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(String estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public HtmlDataTable getTabla() {
        return tabla;
    }

    public void setTabla(HtmlDataTable tabla) {
        this.tabla = tabla;
    }

    public int getCodigoPedidoModificar() {
        return codigoPedidoModificar;
    }

    public void setCodigoPedidoModificar(int codigoPedidoModificar) {
        this.codigoPedidoModificar = codigoPedidoModificar;
    }

    public PoblacionJpaController getCtrPoblacion() {
        return ctrPoblacion;
    }

    public void setCtrPoblacion(PoblacionJpaController ctrPoblacion) {
        this.ctrPoblacion = ctrPoblacion;
    }

    public boolean isUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(boolean ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setListaPoblaciones(ArrayList listaPoblaciones) {
        this.listaPoblaciones = listaPoblaciones;
    }

    public void setListaEstados(ArrayList listaEstados) {
        this.listaEstados = listaEstados;
    }


  
    public String getCodigoPoblacion() {
        return codigoPoblacion;
    }

    public void setCodigoPoblacion(String codigoPoblacion) {
        this.codigoPoblacion = codigoPoblacion;
    }

 
    
    

    //Metodo para dar de alta un tecnico
    public String altaTecnico(){
        //Resetear valores
        mens="";
        
        //Comprobamos que ha introducido el nick y la contraseña
        if(nick!="" && password!=""){
            
            //Buscamos si existe un tecnico con el mismo nombre
            Tecnico tecnicoRepetido = ctrTecnico.findTecnicoByNick(nick);
            
            //Si hemos encontrado un tecnico, no podremos repetirlo, si no existe lo creamos
            if(tecnicoRepetido==null){
                
                //Creamos un tecnico con los datos
                Tecnico tecnico = new Tecnico(null, nick, password);
               
                //Cojo el administrador de la sesion
                ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
                manageBeanSesion manageBeanSesion=new manageBeanSesion();

                HttpSession session= (HttpSession)ctx.getSession(false);
                manageBeanSesion=(manageBeanSesion) session.getAttribute("manageBeanSesion");
                Administrador  administrador=(Administrador) manageBeanSesion.getAdministradorLog();
                
                //Cojo el codigo del administrador para meterselo al tecnico
                tecnico.setCodigoAdministrador(administrador);
            
                //Damos de alta en la base de datos
                ctrTecnico.create(tecnico);
                
                //Actualizamos la tabla
                listaTecnicos= new ArrayList();
         
                listaTecnicos=ctrTecnico.findTecnicoEntities();
                
                mens="Alta correcta";
                
                return "tecnicoAltaCorrecta";
                
            }
            
            else{
                mens="Ya existe un tecnico con ese nick";
                
                return "tecnicoAltaIncorrecta";
                
            }
            
         }
        
        mens="No ha introducido todos los datos";
                
        return "tecnicoAltaIncorrecta";
        
    }
    
    //Metodo para dar de baja a un tecnico
    public String bajaTecnico(){
        //Recuperamos el objeto(tecnco) seleccionado es decir la fila donde se hizo click
        Tecnico tecnicoSeleccionado = (Tecnico) tabla.getRowData();
        
        try {
            //Borramos al tecnico
            ctrTecnico.destroy(tecnicoSeleccionado.getCodigoTecnico());
            
            //Actualizamos la tabla
            listaTecnicos= new ArrayList();
         
            listaTecnicos=ctrTecnico.findTecnicoEntities();
            
            mens="Baja correcta";
            
            return "bajaCorrecta";
        } 
        catch (Exception ex) {
            
            mens="No se ha producido la baja";
            
            return "bajaIncorrecta";
            
        }
    }
    
    //Metodo para dar de alta un telefono
    public String altaTelefono(){
        //Resetear valores
        mens="";
        
        //Comprobamos que ha introducido el nick y la contraseña
        if(nombre!="" && marca!="" && precio!=""){
            
            //Buscamos si existe un telefono con el mismo nombre
            Telefono telefonoRpetido = ctrTelefono.findTecnicoByNick(nombre);
            
            //Si hemos encontrado un telefono, no podremos repetirlo, si no existe lo creamos
            if(telefonoRpetido==null){
                
                //Creamos un tecnico con los datos
                Telefono telefono = new Telefono(null, nombre, marca,Float.parseFloat(precio));
               
                //Cojo el administrador de la sesion
                ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
                manageBeanSesion manageBeanSesion=new manageBeanSesion();

                HttpSession session= (HttpSession)ctx.getSession(false);
                manageBeanSesion=(manageBeanSesion) session.getAttribute("manageBeanSesion");
                Administrador  administrador=(Administrador) manageBeanSesion.getAdministradorLog();
                
                //Cojo el codigo del administrador para meterselo al tecnico
                telefono.setCodigoAdministrador(administrador);
            
                //Damos de alta en la base de datos
                ctrTelefono.create(telefono);
                
                mens="Alta correcta";
                
                return "telefonoAltaCorrecta";
                
            }
            
            else{
                mens="Ya existe un telefono con ese nick";
                
                return "telefonoAltaIncorrecta";
                
            }
            
         }
        
        mens="No ha introducido todos los datos";
                
        return "telefonoAltaIncorrecta";
        
    }
    
    //Metodo para pintar un pedido
    public boolean cabeceraPedido(){
        if(codigoPedido==codigoPedidoAnterior){
            
            return true;
        }
        else 
            
            return false;
                   
    }
    
    //Metodo para saber que tipo de pedido es
    public String saberTipo(int tipo){
        if(tipo==1){
            return "Compra";
        }
        if(tipo==2){
            return "Reparar";
        }
        else{
            return "Vender";
        }
    }
    
    //Metodo para conocer el estado del pedido
    public String conocerEstado(int estado){
        if(estado==1){
            return "Pedido en espera de envio";
        }
        if(estado==2){
            return "Pedido enviado";
        }
        if(estado==3){
            return "Pedido en tránsito";
        }
        if(estado==4){
            return "Pedido pendiente de entrega";
        }
        else{
            return "Pedido entregado"; 
        }
    }
    
    //Metodo para modificar el estado de un pedido
    public String modificarEstado(){
       //Busco el pedido en la base de datos
        Pedido pedido = ctrPedido.findPedido(codigoPedidoModificar);
        
        //Modifico el pedido
        pedido.setEstado(Integer.parseInt(estadoNuevo));
        
        try {
            //Actualizo en la base de datos el pedido
            ctrPedido.edit(pedido);
        } catch (Exception ex) {
            Logger.getLogger(bAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "volver";
    }
    
    //Devolver valores para el select de cambiar el estado
    public ArrayList getListaEstados() {
        if (listaEstados == null) {
            listaEstados = new ArrayList();
            listaEstados.add(new SelectItem("1", "Pedido en espera de envio"));
            listaEstados.add(new SelectItem("2", "Pedido enviado"));
            listaEstados.add(new SelectItem("3", "Pedido en tránsito"));
            listaEstados.add(new SelectItem("4", "Pedido pendiente de entrega"));
            listaEstados.add(new SelectItem("5", "Pedido entregado"));
        }
        return listaEstados;
}
    
    //Devolver valores para el datalist
    public ArrayList getListaPoblaciones() {
        if (listaPoblaciones == null) {
            //Inicializamos la lista de moviles
            listaPoblaciones= new ArrayList();
            
            List<Poblacion> listaPob = ctrPoblacion.findPoblacionEntities();
            for (Poblacion pob : listaPob) {
                listaPoblaciones.add(new SelectItem(pob.getCodigoPoblacion(), pob.getNombrePoblacion() + " - " + pob.getPostal()));
            }
        }
        return listaPoblaciones;
}
    
    //Metodo para activar la ventana modal de cambiar estado
    public String cambiarEstado(int codigo){
        codigoPedidoModificar= codigo;
        estado=true;
        
        return "volver";
    }
    
    //Metodo para activar la ventana modal de cambiar ubicacion
    public String cambiarUbicacion(int codigo){
        codigoPedidoModificar= codigo;
        ubicacion=true;
        
        return "volver";
    }
    
    //Metodo que cambia la poblacion en la que se encuentra un pedido
    public String modificarUbicacion(){
       //Busco el pedido en la base de datos
        Pedido pedido = ctrPedido.findPedido(codigoPedidoModificar);
        
        //Modifico el pedido
        pedido.setCodigoPoblacion(Integer.parseInt(codigoPoblacion));
        
        try {
            //Actualizo en la base de datos el pedido
            ctrPedido.edit(pedido);
        } catch (Exception ex) {
            Logger.getLogger(bAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "PrimeFaces Rocks."));
        return "volver";
    }
    
     
    }

















