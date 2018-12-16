package beans;

import DAO.PedidoJpaController;
import DAO.PoblacionJpaController;
import DTO.Pedido;
import DTO.Poblacion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class bAdministradorModificarPedido {

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

    //Lista donde guardamos los pedidos
    private List listaPedidos;

    //Lista donde guardamos todas las poblaciones
    private ArrayList listaPoblaciones;

    //Lista que guarda los valores del select de estados
    private ArrayList listaEstados = null;
    
     private boolean booleanCabecera = false;

    public bAdministradorModificarPedido() {
        emf = Persistence.createEntityManagerFactory("CRVPU");
        ctrPoblacion = new PoblacionJpaController(emf);
        ctrPedido = new PedidoJpaController(emf);

        //Inicializamos la lista para que cuando entre ya esten cargados
        listaPedidos = new ArrayList();

        listaPedidos = ctrPedido.findPedidoEntities();
        
        for (int i = 0; i < listaPedidos.size(); i++) {
            Pedido ped =(Pedido) listaPedidos.get(i);
            if(ped.getEstado()==5){
                listaPedidos.remove(i);
            }
        }
        Collections.reverse(listaPedidos);
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
        return listaPedidos;
    }

    public void setListaPedidos(List listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    public void setListaPoblaciones(ArrayList listaPoblaciones) {
        this.listaPoblaciones = listaPoblaciones;
    }

    public void setListaEstados(ArrayList listaEstados) {
        this.listaEstados = listaEstados;
    }

    public boolean isBooleanCabecera() {
        return booleanCabecera;
    }

    public void setBooleanCabecera(boolean booleanCabecera) {
        this.booleanCabecera = booleanCabecera;
    }
    
    

    //Metodo para pintar un pedido
    public void cabeceraPedido() {
        if (codigoPedido == codigoPedidoAnterior) {

            booleanCabecera= true;
        } else {
           booleanCabecera= false;
        }

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
        if (estado == 1) {
            return "Pedido en espera de envio";
        }
        if (estado == 2) {
            return "Pedido enviado";
        }
        if (estado == 3) {
            return "Pedido en tránsito";
        }
        if (estado == 4) {
            return "Pedido pendiente de entrega";
        } else {
            return "Pedido entregado";
        }
    }

    //Metodo para modificar el estado de un pedido
    public String modificarEstado() {
        //Busco el pedido en la base de datos
        Pedido pedido = ctrPedido.findPedidoByCodigoPedido(codigoPedidoModificar);

        if(pedido!=null){
        //Modifico el pedido
        pedido.setEstado(Integer.parseInt(estadoNuevo));

        try {
            //Actualizo en la base de datos el pedido
            ctrPedido.edit(pedido);

            //Inicializamos la lista para que actualice los cambios
            listaPedidos = new ArrayList();

            listaPedidos = ctrPedido.findPedidoEntities();
            
             for (int i = 0; i < listaPedidos.size(); i++) {
            Pedido ped =(Pedido) listaPedidos.get(i);
            if(ped.getEstado()==5){
                listaPedidos.remove(i);
            }
        }

              Collections.reverse(listaPedidos);
            //Restablezco los parametros para que me pinte la cabecera de los pedidos
            codigoPedido = 0;
            codigoPedidoAnterior = 1;

            //Cierro la ventana modal de cambiar estado
            estado = false;
            
             codigoPedidoModificar=0;

            //Escribo el mensaje de modificacion correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La modificación del estado se ha realizado correctamente."));
        
        } catch (Exception ex) {
            Logger.getLogger(bAdministradorModificarPedido.class.getName()).log(Level.SEVERE, null, ex);
            
            //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al modifcar el estado del pedido"));
        }
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
            listaPoblaciones = new ArrayList();

            List<Poblacion> listaPob = ctrPoblacion.findPoblacionEntities();
            for (Poblacion pob : listaPob) {
                listaPoblaciones.add(new SelectItem(pob.getCodigoPoblacion(), pob.getNombrePoblacion() + " - " + pob.getPostal()));
            }
        }
        return listaPoblaciones;
    }

    //Metodo para activar la ventana modal de cambiar estado
    public String cambiarEstado(int codigo) {
        codigoPedidoModificar = codigo;
        estado = true;

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

    //Metodo que cambia la poblacion en la que se encuentra un pedido
    public String modificarUbicacion() {
        //Busco el pedido en la base de datos
        Pedido pedido = ctrPedido.findPedidoByCodigoPedido(codigoPedidoModificar);

        if(pedido!=null){
        //Modifico el pedido
        pedido.setCodigoPoblacion(Integer.parseInt(codigoPoblacion));

        try {
            //Actualizo en la base de datos el pedido
            ctrPedido.edit(pedido);

            //Inicializamos la lista para que actualice los cambios
            listaPedidos = new ArrayList();

            listaPedidos = ctrPedido.findPedidoEntities();

            //Restablezco los parametros para que me pinte la cabecera de los pedidos
            codigoPedido = 0;
            codigoPedidoAnterior = 1;

            //Cierro la ventana modal de cambiar estado
            ubicacion = false;
            
            codigoPoblacion="";

            //Escribo el mensaje de modificacion correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La modificación de la ubicación se ha realizado correctamente."));

        } catch (Exception ex) {
            Logger.getLogger(bAdministradorModificarPedido.class.getName()).log(Level.SEVERE, null, ex);
            
            //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al modifcar la ubicación del pedido"));
        }
    }
        return "volver";
    }

}
