package beans;

import DAO.CaracteristicastelefonoJpaController;
import DAO.FotoJpaController;
import DAO.PedidoJpaController;
import DAO.PoblacionJpaController;
import DAO.TelefonoJpaController;
import DAO.exceptions.NonexistentEntityException;
import DTO.Foto;
import DTO.Pedido;
import DTO.Poblacion;
import DTO.Tecnico;
import DTO.Telefono;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
     private FotoJpaController ctrFoto;

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

    

    public bComprar() {
        emf = Persistence.createEntityManagerFactory("CRVPU");
        ctrTelefono = new TelefonoJpaController(emf);
        ctrFoto = new FotoJpaController(emf);
        ctrCaracteristicas = new CaracteristicastelefonoJpaController(emf);
        
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
    
    
    
    
    
    
    
    
    
    
    public String comprarTelefono(Telefono telefono){
        
        //Cojo el carrito de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        List listaCarrito = (List) manageBeanSesion.getListaCarrito();
        
        //Añado el telefono
        listaCarrito.add(telefono);
        
        //Subo el carrito a la sesion
        subirCarrito(listaCarrito);
        

        return "";
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
    
}


