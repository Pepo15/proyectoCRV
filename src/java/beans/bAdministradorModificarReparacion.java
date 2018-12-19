package beans;

import DAO.ReparacionesJpaController;
import DAO.ReparacionestelefonoJpaController;
import DAO.TelefonoJpaController;
import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DTO.Reparaciones;
import DTO.Reparacionestelefono;
import DTO.Telefono;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.primefaces.event.RowEditEvent;

public class bAdministradorModificarReparacion {

    //Driver para conectar con la base de datos
    private EntityManagerFactory emf;

    //Control para gestionar la base de datos
    private ReparacionesJpaController ctrReparaciones;
    private ReparacionestelefonoJpaController ctrReparacionesTelefono;
    private TelefonoJpaController ctrTelefono;

    //Objeto que guarda la reparacion de un telefono de la fila de la tabla de las reparaciones de un telefono
    private Reparacionestelefono miReparacionTelefono;

    //Objeto que guarda la reparacion seleccionada de una fila de la tabla de las reparaciones
    private Reparaciones miReparacion;

    //Lista donde guardamos las reparaciones de un telefono que visualizamos en la tabla
    private List listaReparacionesTelefonoTabla;

    //Lista donde guardamos las reparaciones que visualizamos en la tabla 
    private List listaReparacionesTabla;

    //Variable que guarda el nombre de una reparacion para darla de alta
    private String nombre;

    //Variable que guarda la lista de marcas
    private ArrayList listaMarcas;

    //Variable que guarda la lista de modelos
    private ArrayList listaModelos;

    //Variable que guarda la marca del select
    private String marca;

    //Variable que guarda el codigo del telefono del select
    private String codigoTelefono;

    //Variable que guarda el codigo de la reparacion del select
    private String codigoReparacion;

    //Variable que guarda la lista de reparaciones disponibles
    private List listaReparaciones;

    //Variable que guarda el precio para añadir la reparacion
    private String precio;

    public bAdministradorModificarReparacion() {
        emf = Persistence.createEntityManagerFactory("CRVPU");
        ctrReparaciones = new ReparacionesJpaController(emf);
        ctrReparacionesTelefono = new ReparacionestelefonoJpaController(emf);
        ctrTelefono = new TelefonoJpaController(emf);

        //Inicializamos la lista para que cuando entre ya esten cargados
        listaReparacionesTelefonoTabla = new ArrayList();

        listaReparacionesTelefonoTabla = ctrReparacionesTelefono.findReparacionestelefonoEntities();

        //Inicializamos la lista para que cuando entre ya esten cargados
        listaReparacionesTabla = new ArrayList();

        listaReparacionesTabla = ctrReparaciones.findReparacionesEntities();
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public ReparacionesJpaController getCtrReparaciones() {
        return ctrReparaciones;
    }

    public void setCtrReparaciones(ReparacionesJpaController ctrReparaciones) {
        this.ctrReparaciones = ctrReparaciones;
    }

    public ReparacionestelefonoJpaController getCtrReparacionesTelefono() {
        return ctrReparacionesTelefono;
    }

    public void setCtrReparacionesTelefono(ReparacionestelefonoJpaController ctrReparacionesTelefono) {
        this.ctrReparacionesTelefono = ctrReparacionesTelefono;
    }

    public Reparacionestelefono getMiReparacionTelefono() {
        return miReparacionTelefono;
    }

    public void setMiReparacionTelefono(Reparacionestelefono miReparacionTelefono) {
        this.miReparacionTelefono = miReparacionTelefono;
    }

    public List getListaReparacionesTelefonoTabla() {
        return listaReparacionesTelefonoTabla;
    }

    public void setListaReparacionesTelefonoTabla(List listaReparacionesTelefonoTabla) {
        this.listaReparacionesTelefonoTabla = listaReparacionesTelefonoTabla;
    }

    public Reparaciones getMiReparacion() {
        return miReparacion;
    }

    public void setMiReparacion(Reparaciones miReparacion) {
        this.miReparacion = miReparacion;
    }

    public List getListaReparacionesTabla() {
        return listaReparacionesTabla;
    }

    public void setListaReparacionesTabla(List listaReparacionesTabla) {
        this.listaReparacionesTabla = listaReparacionesTabla;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TelefonoJpaController getCtrTelefono() {
        return ctrTelefono;
    }

    public void setCtrTelefono(TelefonoJpaController ctrTelefono) {
        this.ctrTelefono = ctrTelefono;
    }

    public ArrayList getListaModelos() {
        return listaModelos;
    }

    public void setListaModelos(ArrayList listaModelos) {
        this.listaModelos = listaModelos;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCodigoTelefono() {
        return codigoTelefono;
    }

    public void setCodigoTelefono(String codigoTelefono) {
        this.codigoTelefono = codigoTelefono;
    }

    public String getCodigoReparacion() {
        return codigoReparacion;
    }

    public void setCodigoReparacion(String codigoReparacion) {
        this.codigoReparacion = codigoReparacion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setListaMarcas(ArrayList listaMarcas) {
        this.listaMarcas = listaMarcas;
    }

    public List getListaReparaciones() {
        return listaReparaciones;
    }

    public void setListaReparaciones(List listaReparaciones) {
        this.listaReparaciones = listaReparaciones;
    }

    //Metodo que añade a un select las marcas disponibles para elegir posteriormente un modelo
    public ArrayList getListaMarcas() {
        if (listaMarcas == null) {
            //Inicializamos la lista de marcas
            listaMarcas = new ArrayList();

            List<String> listaMar = ctrTelefono.findTodosTelefonosDistint();
            for (String tel : listaMar) {
                listaMarcas.add(new SelectItem(tel, tel));
            }
        }
        return listaMarcas;
    }

    //Metodo que rellena la lista de modelos según la marca que elijamos
    public void consultarModelos(final AjaxBehaviorEvent event) {

        //Inicializamos la lista de modelos
        listaModelos = new ArrayList();

        //Cojo la marca del telefono
        String marcaTelefono = (String) ((UIOutput) event.getSource()).getValue();

        //Lista donde vuelco el resultado de la consulta
        List listaM = new ArrayList();

        //Ejecutar consulta que devuelve objetos Telefonos segun la marca indicada
        listaM = ctrTelefono.findTelefonoByMarca(marcaTelefono);

        //Recorrer lista con la consulta, para añadirlos a la lista del select con valor y texto
        for (Object p : listaM) {
            Telefono tele = (Telefono) p;
            listaModelos.add(new SelectItem(tele.getCodigoTelefono(), tele.getNombre()));
        }
    }

    public void onRowEdit(RowEditEvent event) {
        //Cojo la reparacion del telefono de la fila seleccionada
        Reparacionestelefono reparacionTelefono = (Reparacionestelefono) event.getObject();
        try {
            //Edito la reparacion del telefono en la base de datos
            ctrReparacionesTelefono.edit(reparacionTelefono);

            //Escribo el mensaje de modificacion correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha modificado la reparacion del telefono correctamente."));

        } catch (NonexistentEntityException ex) {
            Logger.getLogger(bAdministradorGestionTelefono.class.getName()).log(Level.SEVERE, null, ex);

            //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha modificado la reparacion del telefono"));

        } catch (Exception ex) {
            Logger.getLogger(bAdministradorGestionTelefono.class.getName()).log(Level.SEVERE, null, ex);

            //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha modificado la reparacion del telefono"));

        }
    }

    public void onRowCancel(RowEditEvent event) {
    }

    public String bajaReparacionTelefono() {

        try {
            //Elimino la reparacion del telefono de la base de datos
            ctrReparacionesTelefono.destroy(miReparacionTelefono.getCodigoReparacionTelefono());

            //Reinicio la lista de la tabla 
            listaReparacionesTelefonoTabla = null;
            listaReparacionesTelefonoTabla = ctrReparacionesTelefono.findReparacionestelefonoEntities();

            //Escribo el mensaje de baja correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha borrado la reparacion del telefono correctamente."));

        } catch (NonexistentEntityException ex) {
            Logger.getLogger(bAdministradorModificarReparacion.class.getName()).log(Level.SEVERE, null, ex);

            //Escribo el mensaje de baja incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha borrado la reparacion del telefono"));
        }
        return "correcto";
    }

    public String bajaReparacion() {

        try {
            //Borro primero las reparaciones de los telefonos que contengan esa reparacion
            List<Reparacionestelefono> listaReparacionesTelefono = miReparacion.getReparacionestelefonoList();
            for (int j = 0; j < listaReparacionesTelefono.size(); j++) {
                ctrReparacionesTelefono.destroy(listaReparacionesTelefono.get(j).getCodigoReparacionTelefono());
            }

            //Borro la reparacion de la base de datos
            ctrReparaciones.destroy(miReparacion.getCodigoReparacion());

            //Reinicio las listas de la tablas
            listaReparacionesTabla = null;
            listaReparacionesTabla = ctrReparaciones.findReparacionesEntities();

            listaReparacionesTelefonoTabla = null;
            listaReparacionesTelefonoTabla = ctrReparacionesTelefono.findReparacionestelefonoEntities();

            //Escribo el mensaje de baja correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha borrado la reparacion correctamente."));

        } catch (NonexistentEntityException ex) {
            Logger.getLogger(bAdministradorModificarReparacion.class.getName()).log(Level.SEVERE, null, ex);

            //Escribo el mensaje de baja incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha borrado la reparacion"));

        } catch (IllegalOrphanException ex) {
            Logger.getLogger(bAdministradorModificarReparacion.class.getName()).log(Level.SEVERE, null, ex);

            //Escribo el mensaje de baja incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha borrado la reparacion"));

        }
        return "correcto";
    }

    //Metodo para dar de alta una reparacion
    public String altaReparacion() {
        //Buscamos si existe una reparacion con el mismo nombre
        Reparaciones reparacionRepetida = ctrReparaciones.findReparacionByNombre(nombre);

        //Si hemos encontrado una reparacion, no podremos repetirlo, si no existe lo creamos
        if (reparacionRepetida == null) {

            //Creamos una reparacion con los datos
            Reparaciones reparacion = new Reparaciones(null, nombre);

            //Damos de alta en la base de datos la reparacion
            ctrReparaciones.create(reparacion);

            //Reinicio las listas de la tablas
            listaReparacionesTabla = null;
            listaReparacionesTabla = ctrReparaciones.findReparacionesEntities();

            //Escribo el mensaje de alta correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha dado de alta la reparacion correctamente."));

            nombre="";
            return "correcto";

        } else {
            //Escribo el mensaje de alta correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "Ya existe una reparacion con ese nombre."));

            return "correcto";
        }

    }

    //Metodo que devuelve los perfiles en los cuales un telefono todavia no tiene una foto
    public void consultarReparacionesDisponibles(final AjaxBehaviorEvent event) {
        listaReparaciones = new ArrayList();
        if (codigoTelefono != null) {
            Telefono telefono = ctrTelefono.findTelefono(Integer.parseInt(codigoTelefono));
            List lista = new ArrayList();
            List lista2 = new ArrayList();
            lista = ctrReparacionesTelefono.findReparacionByTelefono(telefono);
            for (int j = 0; j < lista.size(); j++) {
                Reparacionestelefono reparaTele = (Reparacionestelefono) lista.get(j);

                lista2.add(reparaTele.getCodigoReparacion().getNombre());

            }
            List lista3 = new ArrayList();
            lista3 = ctrReparaciones.findReparacionesEntities();
            for (int j = 0; j < lista3.size(); j++) {
                Reparaciones repa = (Reparaciones) lista3.get(j);
                if (lista2.size() == 0) {
                    listaReparaciones.add(new SelectItem(repa.getCodigoReparacion(), repa.getNombre()));

                }
                for (int z = 0; z < lista2.size(); z++) {
                    if (repa.getNombre().equals(lista2.get(z))) {
                        lista2.remove(z);
                        z = lista2.size();
                    } else {
                        listaReparaciones.add(new SelectItem(repa.getCodigoReparacion(), repa.getNombre()));

                    }

                }

            }
        }
    }

    //Metodo para dar de alta una reparacion
    public String altaReparacionTelefono() {

        //Comprobamos que hayamos seleccionado alguna reparacion
        if (codigoReparacion != "") {
            Reparacionestelefono reparacionTelefono = new Reparacionestelefono(null, Integer.parseInt(precio));

            reparacionTelefono.setCodigoTelefono(ctrTelefono.findTelefono(Integer.parseInt(codigoTelefono)));
            reparacionTelefono.setCodigoReparacion(ctrReparaciones.findReparaciones(Integer.parseInt(codigoReparacion)));

            ctrReparacionesTelefono.create(reparacionTelefono);

            //Reinicio las listas de la tablas
            listaReparacionesTabla = null;
            listaReparacionesTabla = ctrReparaciones.findReparacionesEntities();

            listaReparacionesTelefonoTabla = null;
            listaReparacionesTelefonoTabla = ctrReparacionesTelefono.findReparacionestelefonoEntities();

            listaMarcas = null;
            listaMarcas = getListaMarcas();

            marca = "";
            codigoTelefono = "";
            codigoReparacion = "";
            precio = "";

            //Escribo el mensaje de alta correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha dado de alta la reparacion del telefono correctamente."));

            return "correcto";

        } else {
            //Escribo el mensaje de alta correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "No ha introducido una reparacion"));

            return "correcto";
        }

    }

}
