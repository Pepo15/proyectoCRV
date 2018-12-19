package beans;

import DAO.PedidoJpaController;
import DAO.PoblacionJpaController;
import DAO.exceptions.NonexistentEntityException;
import DTO.Pedido;
import DTO.Poblacion;
import DTO.Tecnico;
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
import org.primefaces.PrimeFaces;

public class bTecnico {

    //Driver para conectar con la base de datos
    private EntityManagerFactory emf;

    //Control para gestionar la base de datos
    private PedidoJpaController ctrPedido;
    private PoblacionJpaController ctrPoblacion;

    //Variable que almacena el codigo del pedido anterior
    private int codigoPedidoAnterior;

    //Variable que almacena el codigo del pedido
    private int codigoPedido;

    //Guardamos el codigo del pedido, para saber que pedido desamos modificar
    private int codigoPedidoModificar;

    //Variable que almacena la poblacion que queremos agregar
    private String codigoPoblacion;

    //Variable donde guardaremos el nuevo estado
    private String estadoNuevo;

    //Variable para saber si debemos mostrar ventana estado o no
    private boolean estado = false;

    //Variable para saber si debemos mostrar ventana ubicacion o no
    private boolean ubicacion = false;
    
    //Variable para saber si debemos mostrar un mensaje si tiene pedidos para reparar o no
    private boolean noExiste = false;

    //Lista donde guardamos los pedidos
    private List listaPedidos;

    //Lista que guarda los valores del select de estados
    private ArrayList listaEstados = null;
    
     private boolean booleanCabecera = false;
     
     private String rotura;

    public bTecnico() {
        emf = Persistence.createEntityManagerFactory("CRVPU");
        ctrPoblacion = new PoblacionJpaController(emf);
        ctrPedido = new PedidoJpaController(emf);
        
        //Cojo el tecnico de la sesion
            ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            manageBeanSesion manageBeanSesion = new manageBeanSesion();

            HttpSession session = (HttpSession) ctx.getSession(false);
            manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
            Tecnico tecnico = (Tecnico) manageBeanSesion.getTecnicoLog();

        //Inicializamos la lista para que cuando entre ya esten cargados
        listaPedidos = new ArrayList();

        listaPedidos = ctrPedido.findPedidoByCodigoTecnico(tecnico);
        
        if(listaPedidos.size()==0){
            noExiste=true;
        }
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public PedidoJpaController getCtrPedido() {
        return ctrPedido;
    }

    public void setCtrPedido(PedidoJpaController ctrPedido) {
        this.ctrPedido = ctrPedido;
    }

    public PoblacionJpaController getCtrPoblacion() {
        return ctrPoblacion;
    }

    public void setCtrPoblacion(PoblacionJpaController ctrPoblacion) {
        this.ctrPoblacion = ctrPoblacion;
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

    public int getCodigoPedidoModificar() {
        return codigoPedidoModificar;
    }

    public void setCodigoPedidoModificar(int codigoPedidoModificar) {
        this.codigoPedidoModificar = codigoPedidoModificar;
    }

    public String getCodigoPoblacion() {
        return codigoPoblacion;
    }

    public void setCodigoPoblacion(String codigoPoblacion) {
        this.codigoPoblacion = codigoPoblacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(boolean ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(String estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public List getListaPedidos() {
        //Para pintar la cabecera 
        codigoPedido=0;
        codigoPedidoAnterior=1;
        if(listaPedidos.size()==0){
            noExiste=true;
        }
        else{
            noExiste=false;
        }
        return listaPedidos;
    }

    public void setListaPedidos(List listaPedidos) {
        this.listaPedidos = listaPedidos;
    }
    
    public void setListaEstados(ArrayList listaEstados) {
        this.listaEstados = listaEstados;
    }

    public boolean isNoExiste() {
        return noExiste;
    }

    public void setNoExiste(boolean noExiste) {
        this.noExiste = noExiste;
    }

    public boolean isBooleanCabecera() {
        return booleanCabecera;
    }

    public void setBooleanCabecera(boolean booleanCabecera) {
        this.booleanCabecera = booleanCabecera;
    }

    public String getRotura() {
        return rotura;
    }

    public void setRotura(String rotura) {
        this.rotura = rotura;
    }
    
    
    
    

    
    //Metodo para pintar un pedido
    public boolean permitirEliminar(int codigo) {
          for (int i = 0; i < listaPedidos.size(); i++) {
                Pedido pedido = (Pedido)listaPedidos.get(i);
                if(codigo==pedido.getCodigo()){
                    if(pedido.getEstado()==11){
                        return true;
                    }
                    
                }
        }
        
        
            return false;
        

    }

    //Metodo para saber que tipo de pedido es
    public String saberTipo(int tipo) {
        if (tipo == 1) {
            return "Compra";
        }
        if (tipo == 2) {
            return "Reparar";
        } else {
            return "Vender";
        }
    }

    //Metodo para conocer el estado del pedido
    public String conocerEstado(int estado) {
        if (estado == 6) {
            return "Pedido en espera de reparación";
        }
        if (estado == 7) {
            return "Pedido en reparación";
        }
        if (estado == 8) {
            return "Pedido reparado";
        }
        if (estado == 9) {
            return "Pedido en tránsito";
        }
        if (estado == 10) {
            return "Pedido pendiente de entrega";
        } else {
            return "Pedido entregado";
        }
    }

    //Metodo para modificar el estado de un pedido
    public String modificarEstado() {
        //Busco el pedido en la base de datos
        Pedido pedido = ctrPedido.findPedido(codigoPedidoModificar);

        //Modifico el pedido
        pedido.setEstado(Integer.parseInt(estadoNuevo));

        try {
            //Actualizo en la base de datos el pedido
            ctrPedido.edit(pedido);

             //Cojo el tecnico de la sesion
            ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            manageBeanSesion manageBeanSesion = new manageBeanSesion();

            HttpSession session = (HttpSession) ctx.getSession(false);
            manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
            Tecnico tecnico = (Tecnico) manageBeanSesion.getTecnicoLog();

        //Inicializamos la lista para que cuando entre ya esten cargados
        listaPedidos = new ArrayList();

        listaPedidos = ctrPedido.findPedidoByCodigoTecnico(tecnico);
        
        if(listaPedidos.size()==0){
            noExiste=true;
        }

            //Restablezco los parametros para que me pinte la cabecera de los pedidos
            codigoPedido = 0;
            codigoPedidoAnterior = 1;

            //Cierro la ventana modal de cambiar estado
            estado = false;
            
             //Muestro la ventana por si quiere modificar el telefono
          PrimeFaces.current().executeScript("$('#modalCambiarEstado').css('display','none')");

            //Escribo el mensaje de modificacion correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La modificación del estado se ha realizado correctamente."));
        
        } catch (Exception ex) {
            Logger.getLogger(bTecnico.class.getName()).log(Level.SEVERE, null, ex);
            
            //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al modifcar el estado del pedido"));
        }

        return "volver";
    }

    //Devolver valores para el select de cambiar el estado
    public ArrayList getListaEstados() {
        if (listaEstados == null) {
            listaEstados = new ArrayList();
            listaEstados.add(new SelectItem("6", "Pedido en espera de reparación"));
            listaEstados.add(new SelectItem("7", "Pedido en reparación"));
            listaEstados.add(new SelectItem("8", "Pedido reparado"));
            listaEstados.add(new SelectItem("9", "Pedido en tránsito"));
            listaEstados.add(new SelectItem("10", "Pedido pendiente de entrega"));
            listaEstados.add(new SelectItem("11", "Pedido entregado"));
        }
        return listaEstados;
    }
    
    //Metodo para activar la ventana modal de cambiar estado
    public String borrarPedido(int codigo) {
        try {
            ctrPedido.destroy(codigo);
            
            //Cojo el tecnico de la sesion
            ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            manageBeanSesion manageBeanSesion = new manageBeanSesion();

            HttpSession session = (HttpSession) ctx.getSession(false);
            manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
            Tecnico tecnico = (Tecnico) manageBeanSesion.getTecnicoLog();

        //Inicializamos la lista para que cuando entre ya esten cargados
        listaPedidos = new ArrayList();

        listaPedidos = ctrPedido.findPedidoByCodigoTecnico(tecnico);
        
        //Para pintar la cabecera 
        codigoPedido=0;
        codigoPedidoAnterior=1;
        
            //Escribo el mensaje de modificacion correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha borrado correctamente el pedido."));
        
        
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(bTecnico.class.getName()).log(Level.SEVERE, null, ex);
            
             //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al modifcar el estado del pedido"));
        
        }
        return "volver";
    }

    //Metodo para activar la ventana modal de cambiar estado
    public String cambiarEstado(int codigo) {
        codigoPedidoModificar = codigo;
        estado = true;

        return "volver";
    }
    
     //Metodo para activar la ventana modal de cambiar estado
    public String masDatos(String verRotura) {
        rotura = verRotura;
        
        if("".equals(rotura)){
            rotura="No hay más datos.";
        }

        return "volver";
    }

    //Metodo para activar la ventana modal de cambiar ubicacion
    public String cambiarUbicacion(int codigo) {
        codigoPedidoModificar = codigo;
        ubicacion = true;

        return "volver";
    }
    
    //Metodo que devuelve el nombre de la poblacion donde se encuentra el pedido
    public String conocerPoblacion(int codigoPoblacion) {
        
        Poblacion poblacion = ctrPoblacion.findPoblacion(codigoPoblacion);

        return poblacion.getNombrePoblacion();
    }
    
    //Metodo para pintar un pedido
    public void cabeceraPedido() {
        if (codigoPedido == codigoPedidoAnterior) {

            booleanCabecera= true;
        } else {
            booleanCabecera= false;
        }

    }

}
