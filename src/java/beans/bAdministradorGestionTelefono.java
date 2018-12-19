package beans;

import DAO.CaracteristicastelefonoJpaController;
import DAO.FotoJpaController;
import DAO.PedidoJpaController;
import DAO.ReparacionesJpaController;
import DAO.ReparacionestelefonoJpaController;
import DAO.TelefonoJpaController;
import DAO.exceptions.NonexistentEntityException;
import DTO.Administrador;
import DTO.Caracteristicastelefono;
import DTO.Foto;
import DTO.Pedido;
import DTO.Reparaciones;
import DTO.Reparacionestelefono;
import DTO.Telefono;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;

public class bAdministradorGestionTelefono {

    //Driver para conectar con la base de datos
    private EntityManagerFactory emf;

    //Control para gestionar las alta, bajas y modificaciones
    private TelefonoJpaController ctrTelefono;
    private CaracteristicastelefonoJpaController ctrCaracteristicas;
    private FotoJpaController ctrFotos;
    private ReparacionestelefonoJpaController ctrReparacionesTelefono;
    private PedidoJpaController ctrPedido;

    //Objeto que guarda el telefono seleccionado de una fila de la tabla
    private Telefono miTelefono;

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

    //Lista donde guardamos los telefonos que visualizamos en la tabla
    private List listaTelefonosTabla;

    //Variable que guarda la lista de marcas
    private ArrayList listaMarcas;

    //Variable que guarda la lista de modelos
    private ArrayList listaModelos;

    //Buffer para la subida de la foto
    private static final int BUFFER_SIZE = 6124;

    //Variable que guarda la foto del telefono
    private UploadedFile fileTelefono;

    //Variable que guarda el codigo del telefono para añadir la foto 
    private String codigoTelefonoFoto;

    //Variable que guarda el codigo del telefono para borrar la foto 
    private String codigoTelefonoFotoBorrar;

    //Variable que guarda el perfil de la foto del telefono
    private String perfilFoto;

    //Variable que guarda la lista de perfiles
    private ArrayList listaPerfiles;

    //Variable que guarda la lista de nombresFotos
    private List listaFotos;

    //Contructor
    public bAdministradorGestionTelefono() {
        emf = Persistence.createEntityManagerFactory("CRVPU");
        ctrTelefono = new TelefonoJpaController(emf);
        ctrCaracteristicas = new CaracteristicastelefonoJpaController(emf);
        ctrFotos = new FotoJpaController(emf);
        ctrPedido = new PedidoJpaController(emf);
        ctrReparacionesTelefono = new ReparacionestelefonoJpaController(emf);

        //Inicializamos la lista para que cuando entre ya esten cargados en la tabla los telefonos
        listaTelefonosTabla = new ArrayList();
        listaTelefonosTabla = ctrTelefono.findTelefonoEntities();

        //Inicializamos la lista de modelos pero sin cargarlos ya que deberemos seleccionar antes una marca
        listaModelos = new ArrayList();
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

    public Telefono getMiTelefono() {
        return miTelefono;
    }

    public void setMiTelefono(Telefono miTelefono) {
        this.miTelefono = miTelefono;
    }

    public void setListaMarcas(ArrayList listaMarcas) {
        this.listaMarcas = listaMarcas;
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

    public List getListaTelefonosTabla() {
        return listaTelefonosTabla;
    }

    public void setListaTelefonosTabla(List listaTelefonosTabla) {
        this.listaTelefonosTabla = listaTelefonosTabla;
    }

    public ArrayList getListaModelos() {
        return listaModelos;
    }

    public void setListaModelos(ArrayList listaModelos) {
        this.listaModelos = listaModelos;
    }

    public List getListaFotos() {
        return listaFotos;
    }

    public void setListaFotos(List listaFotos) {
        this.listaFotos = listaFotos;
    }

    public String getCodigoTelefonoFotoBorrar() {
        return codigoTelefonoFotoBorrar;
    }

    public void setCodigoTelefonoFotoBorrar(String codigoTelefonoFotoBorrar) {
        this.codigoTelefonoFotoBorrar = codigoTelefonoFotoBorrar;
    }

    public ArrayList getListaPerfiles() {
        return listaPerfiles;
    }

    public ReparacionestelefonoJpaController getCtrReparacionesTelefono() {
        return ctrReparacionesTelefono;
    }

    public void setCtrReparacionesTelefono(ReparacionestelefonoJpaController ctrReparacionesTelefono) {
        this.ctrReparacionesTelefono = ctrReparacionesTelefono;
    }

    public PedidoJpaController getCtrPedido() {
        return ctrPedido;
    }

    public void setCtrPedido(PedidoJpaController ctrPedido) {
        this.ctrPedido = ctrPedido;
    }
    
    

    //Metodo que devuelve los perfiles en los cuales un telefono todavia no tiene una foto
    public void consultarPerfilesDisponibles(final AjaxBehaviorEvent event) {
        listaPerfiles = new ArrayList();
        if (codigoTelefonoFoto != null) {
            Telefono telefono = ctrTelefono.findTelefono(Integer.parseInt(codigoTelefonoFoto));
            List<Foto> listaPerfilesFoto = new ArrayList();
            listaPerfilesFoto = ctrFotos.findFotoByCodigoTelefono(telefono);
            int cantidadFotos = listaPerfilesFoto.size();
            if (cantidadFotos == 0) {
                listaPerfiles.add(new SelectItem("Delante", "Delante"));
                listaPerfiles.add(new SelectItem("Detras", "Detrás"));
                listaPerfiles.add(new SelectItem("Perfil", "Perfil"));
            } else if (cantidadFotos == 3) {

            } else {
                String perfilAnterior = "";
                String perfil = "";
                for (Foto foto : listaPerfilesFoto) {
                    perfilAnterior = perfil;
                    perfil = foto.getNombre().split("-")[0];
                    if (cantidadFotos == 1) {

                        if (!perfil.equals("Delante")) {
                            listaPerfiles.add(new SelectItem("Delante", "Delante"));
                        }
                        if (!perfil.equals("Detras")) {
                            listaPerfiles.add(new SelectItem("Detras", "Detrás"));
                        }
                        if (!perfil.equals("Perfil")) {
                            listaPerfiles.add(new SelectItem("Perfil", "Perfil"));
                        }

                    }
                    if (cantidadFotos == 2) {
                        if (perfilAnterior.equals("Delante") && perfil.equals("Detras")) {
                            listaPerfiles.add(new SelectItem("Perfil", "Perfil"));
                        }
                        if (perfilAnterior.equals("Delante") && perfil.equals("Perfil")) {
                            listaPerfiles.add(new SelectItem("Detras", "Detras"));
                        }
                        if (perfilAnterior.equals("Detras") && perfil.equals("Perfil")) {
                            listaPerfiles.add(new SelectItem("Delante", "Delante"));
                        }

                    }

                }
            }

        }

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

    //Metodo para dar de alta un telefono
    public String altaTelefono() {
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

            //Damos de alta en la base de datos el telefono
            ctrTelefono.create(telefono);

            //Inicializamos la lista para que cuando entre ya esten cargados los telefonos
            listaTelefonosTabla = new ArrayList();

            listaTelefonosTabla = ctrTelefono.findTelefonoEntities();

            //Inicializamos la lista de los marcas 
            listaMarcas = null;

            listaMarcas = getListaMarcas();

            //Escribo el mensaje de alta correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha dado de alta al telefono correctamente."));

            return "correcto";

        } else {
            //Damos la opcion de modificar el telefono si esta repetido

            //Creamos un telefono con los datos
            Telefono telefono = new Telefono(telefonoRepetido.getCodigoTelefono(), nombre, marca, Float.parseFloat(precio));

            //Cojo el administrador de la sesion
            ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            manageBeanSesion manageBeanSesion = new manageBeanSesion();

            HttpSession session = (HttpSession) ctx.getSession(false);
            manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
            Administrador administrador = (Administrador) manageBeanSesion.getAdministradorLog();

            //Cojo las listas con las que tiene enlace y se las añado para editarlo posteriormente
            telefono.setCodigoAdministrador(administrador);
            telefono.setCaracteristicastelefonoList(telefonoRepetido.getCaracteristicastelefonoList());
            telefono.setFotoList(telefonoRepetido.getFotoList());
            telefono.setReparacionestelefonoList(telefonoRepetido.getReparacionestelefonoList());
            telefono.setPedidoList(telefonoRepetido.getPedidoList());

            //Subo el telefono a la sesion
            subirTelefono(telefono);

            //Muestro la ventana por si quiere modificar el telefono
            RequestContext.getCurrentInstance().execute("PF('confirmDlg').show();");
            return "correcto";
        }

    }

    //Metodo para modificar un telefono
    public void modificarTelefono() {

        //Cojo el telefono de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        Telefono telefono = (Telefono) manageBeanSesion.getTelefonoModificar();

        try {
            //Edito el telefono 
            ctrTelefono.edit(telefono);

            //Inicializamos la lista para que cuando entre ya esten cargados los telefonos
            listaTelefonosTabla = new ArrayList();

            listaTelefonosTabla = ctrTelefono.findTelefonoEntities();

            //Inicializamos la lista de los marcas 
            listaMarcas = null;

            listaMarcas = getListaMarcas();

            //Escribo el mensaje de modificacion correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha modificado el telefono correctamente."));

        } catch (Exception ex) {
            Logger.getLogger(bAdministradorGestionTecnico.class.getName()).log(Level.SEVERE, null, ex);

            //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha modificado el telefono"));

        }

    }

    //Metodo para eliminar un telefono
    public String bajaTelefono() {
        try {
            
              List  <Reparacionestelefono> listaReparacionesTelefono = miTelefono.getReparacionestelefonoList();
              for(int j = 0; j < listaReparacionesTelefono.size(); j++) {
                  ctrReparacionesTelefono.destroy(listaReparacionesTelefono.get(j).getCodigoReparacionTelefono());
              }
            
            List<Pedido> listaPedidos =miTelefono.getPedidoList();
            for(int i = 0; i < listaPedidos.size(); i++) {
               ctrPedido.destroy(listaPedidos.get(i).getCodigo());
            }
            
            List<Foto> listaFotoss =miTelefono.getFotoList();
            for(int i = 0; i < listaFotoss.size(); i++) {
                
            //Borro la foto del servidor
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");

            Foto foto = ctrFotos.findFoto(listaFotoss.get(i).getCodigoFoto());

            //CREAMOS EL FILE CON LA RUTA ENTERA
            File result = new File(path + "/../../web/imagenes/FotosTelefono/" + foto.getNombre());

            //Borro el fichero
            result.delete();
            
            //Borro la foto de la base de datos
            
            ctrFotos.destroy(listaFotoss.get(i).getCodigoFoto());
            }
            
            if(miTelefono.getCaracteristicastelefonoList().size()==1){
                ctrCaracteristicas.destroy(miTelefono.getCaracteristicastelefonoList().get(0).getCodigoCaracteristica());
            }
            
            //Borro el telefono de la base de datos
            ctrTelefono.destroy(miTelefono.getCodigoTelefono());
            
            //Inicializamos la lista para que cuando entre ya esten cargados los telefono
            listaTelefonosTabla = new ArrayList();

            listaTelefonosTabla = ctrTelefono.findTelefonoEntities();

            //Inicializamos la lista de las marcas 
            listaMarcas = null;

            listaMarcas = getListaMarcas();

            //Escribo el mensaje de baja correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La baja del teléfono se ha realizado correctamente."));

            return "correcto";
        } catch (Exception ex) {
            Logger.getLogger(bAdministradorGestionTelefono.class.getName()).log(Level.SEVERE, null, ex);

            //Escribo el mensaje de baja incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha realizado la baja del teléfono"));

            return "correcto";
        }
    }

    //Metodo para dar de alta las caracteristicas de un telefono
    public String altaCaracteristicasTelefono() {
        //Comprobamos que ha introducido con el select un telefono ya que los demas campos
        //se validan desde el cliente
        if (codigoTelefono != "") {

            //Buscamos si existe unas caracteristicas para ese telefono
            Telefono telefono = ctrTelefono.findTelefono(Integer.parseInt(codigoTelefono));
            int caracteristicaRepetida = telefono.getCaracteristicastelefonoList().size();
            
            //Si hemos encontrado unas caracterisitcas para un telefono, no podremos repetirlo, 
            if (caracteristicaRepetida == 0) {

                //Creamos las caracteristicas con los datos
                Caracteristicastelefono caracteristicas
                        = new Caracteristicastelefono(null, so, Integer.parseInt(ram),
                                Float.parseFloat(pulgadas), Integer.parseInt(almacenamiento), 
                                Float.parseFloat(camaraTrasera),Float.parseFloat(camaraDelantera),
                                Integer.parseInt(bateria), procesador, wifi,resolucion, color,
                                detectorDeHuellas, dualSim, sd, bluetooth, nfc, g3, g4);

                caracteristicas.setCodigoTelefono(ctrTelefono.findTelefono(Integer.parseInt(codigoTelefono)));
                try {
                    //Añado las caracteristicas a la base de datos
                    ctrCaracteristicas.create(caracteristicas);

                    //Escribo el mensaje de alta correcta
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se han añadido las características al teléfono."));

                    return "correcto";

                } catch (Exception ex) {
                    //Escribo el mensaje de alta incorrecta
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "Error al añadir las características al teléfono"));

                    return "correcto";
                }

            } else {
                //Subimos las caracteristicas a la sesion por si las quiere modificar
                int codigoCaracteristica = telefono.getCaracteristicastelefonoList().get(0).getCodigoCaracteristica();

                Caracteristicastelefono caracteristicasRepetidas = ctrCaracteristicas.findCaracteristicastelefono(codigoCaracteristica);

                //Creamos un caracteristicas para subirla a la sesion
                Caracteristicastelefono caracteristicas
                        = new Caracteristicastelefono(caracteristicasRepetidas.getCodigoCaracteristica(),
                                so, Integer.parseInt(ram), Float.parseFloat(pulgadas), Integer.parseInt(almacenamiento),
                                Float.parseFloat(camaraTrasera), Float.parseFloat(camaraDelantera),
                                Integer.parseInt(bateria), procesador, wifi, resolucion, color, detectorDeHuellas,
                                dualSim, sd, bluetooth, nfc, g3, g4);

                caracteristicas.setCodigoTelefono(ctrTelefono.findTelefono(Integer.parseInt(codigoTelefono)));

                //Subo las caracteristicas a la sesion
                subirCaracteristica(caracteristicas);

                //Muestro el cuadro si quiere aceptarlas o no
                RequestContext.getCurrentInstance().execute("PF('confirmDlgCaracteristicas').show();");

                return "correcto";
            }

        }
        //Escribo el mensaje de alta incorrecta
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No ha elegido el telefono al que añadirle las caracteristicas"));

        return "correcto";

    }

    //Metodo para modificar las caracteristicas
    public void modificarCaracteristicas() {

        //Cojo las caracteristicas de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        Caracteristicastelefono caracteristicas = (Caracteristicastelefono) manageBeanSesion.getCaracteristicasModificar();

        try {
            //Modifico las caracteristicas en la base de datos
            ctrCaracteristicas.edit(caracteristicas);

            //Escribo el mensaje de modificacion correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha modificado las caracteristicas del telefono correctamente."));

        } catch (Exception ex) {
            Logger.getLogger(bAdministradorGestionTecnico.class.getName()).log(Level.SEVERE, null, ex);

            //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se han podido modificar las caracteristicas del telefono"));

        }

    }

    //Metodo que sube la foto de un telefono
    public String subirFotoTelefono() {
        try {
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");

            //OBTENEMOS EXTENSION DEL FICHERO QUE NOS VIENE
            String extension = "";
            int i = fileTelefono.getFileName().lastIndexOf('.');
            if (i > 0) {
                extension = fileTelefono.getFileName().substring(i + 1);
            }

            //CREAMOS EL FILE CON LA RUTA ENTERA
            File result = new File(path + "/../../web/imagenes/FotosTelefono/" + perfilFoto + "-" + codigoTelefonoFoto + "." + extension);

            if (result.exists()) {
                //Escribo el mensaje de foto duplicada
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ya existe una foto del perfil seleccionado para ese telefono"));

                return "correcto";
            } else {

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

                    //La subo a la base de datos
                    Foto foto = new Foto(null, perfilFoto + "-" + codigoTelefonoFoto + "." + extension);

                    foto.setCodigoTelefono(ctrTelefono.findTelefono(Integer.parseInt(codigoTelefonoFoto)));

                    ctrFotos.create(foto);
                    
                    //Escribo el mensaje de subida correcta
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La foto se ha subido correctamente."));

                } catch (IOException e) {
                    e.printStackTrace();

                    //Escribo el mensaje de subida incorrecta
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al subir la foto al servidor."));

                }

            }
        } catch (Exception ex) {

            //Escribo el mensaje de subida incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al subir la foto a la base de datos."));

            Logger.getLogger(bAdministradorGestionTelefono.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "correcto";

    }

    //Metodo para borrar una foto
    public String borrarFotoTelefono(int codigoFoto, int codigoTelefonoBorrar) {

        //Cojo la foto
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");

        Foto foto = ctrFotos.findFoto(codigoFoto);

        //CREAMOS EL FILE CON LA RUTA ENTERA
        File result = new File(path + "/../../web/imagenes/FotosTelefono/" + foto.getNombre());

        //Borro el fichero
        result.delete();

        try {
            //Lo borro de la base de datos
            ctrFotos.destroy(codigoFoto);

            //Inicializamos la lista de fotos
            listaFotos = new ArrayList();

            //Reiniciamos la lista de fotos del telefono elegido
            Telefono telefono = ctrTelefono.findTelefono(codigoTelefonoBorrar);

            listaFotos = telefono.getFotoList();

            //Escribo el mensaje de borrado correcto
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La foto se ha borrado correctamente."));
        
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(bAdministradorGestionTelefono.class.getName()).log(Level.SEVERE, null, ex);

            //Escribo el mensaje de borrado incorrecto
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al borrar la foto de la base de datos."));
        }

        return "correcto";

    }

    //Metodo para subir un telefono a la sesion
    public void subirTelefono(Telefono telefono) {
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

        //Añadirle como propiedad el telefono para modificarlo
        manageBeanSesion.setTelefonoModificar(telefono);
    }

    //Metodo para subir una caracteristica a la sesion
    public void subirCaracteristica(Caracteristicastelefono caracteristica) {
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

        //Añadirle como propiedad la caracteristica para modificarla
        manageBeanSesion.setCaracteristicasModificar(caracteristica);
    }

    //Metodo para editar en la tabla
    public void onRowEdit(RowEditEvent event) {
        //Cojo el telefono de la fila seleccionada
        Telefono telefono = (Telefono) event.getObject();
        try {
            ctrTelefono.edit(telefono);
            
            //Inicializamos la lista de los modelos tambien para dar de alta las caracteristicas y las foto
            listaMarcas = null;

            listaMarcas = getListaMarcas();

            //Escribo el mensaje de modificacion correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha modificado el telefono correctamente."));
       
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(bAdministradorGestionTelefono.class.getName()).log(Level.SEVERE, null, ex);
            
            //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha modificado el telefono"));

        } catch (Exception ex) {
            Logger.getLogger(bAdministradorGestionTelefono.class.getName()).log(Level.SEVERE, null, ex);
            
            //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha modificado el telefono"));

        }
    }

    public void onRowCancel(RowEditEvent event) {
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

    //Metodo que rellena la lista de fotos según el modelo que elijamos
    public void consultarFotos(final AjaxBehaviorEvent event) {

        //Inicializamos la lista de fotos
        listaFotos = new ArrayList();

        //Cojo el codigo del telefono
        String codigoTelefono = (String) ((UIOutput) event.getSource()).getValue();

        //Ejecutar consulta que devuelve objetos Telefonos segun la marca indicada
        Telefono telefono = ctrTelefono.findTelefono(Integer.parseInt(codigoTelefono));

        listaFotos = telefono.getFotoList();

    }
    
    public List consultarTelefonosMasComprados(){
        List list = ctrPedido.findMaximaCompra();
        List lista =  new ArrayList();
        
         for (int i = 0; i < 3; i++) {
                Object[] lista2 = (Object[]) list.get(i);
                 
                         Telefono telefono = (Telefono) lista2[0];
                         
                         lista.add(telefono);
                     
                 
            }
        return lista;
    }
    public List consultarTelefonosMasComprados2(){
        List list = ctrPedido.findMaximaCompra();
        List lista =  new ArrayList();
        
         for (int i = 0; i < 3; i++) {
                Object[] lista2 = (Object[]) list.get(i);
                 
                         long cantidad = (long) lista2[1];
                         
                         lista.add(String.valueOf(cantidad));
                     
                 
            }
        return lista;
    }
     public List consultarTelefonosMasVendidos(){
        List list = ctrPedido.findMaximaVenta();
        List lista =  new ArrayList();
        
         for (int i = 0; i < 3; i++) {
                Object[] lista2 = (Object[]) list.get(i);
                 
                         Telefono telefono = (Telefono) lista2[0];
                         
                         lista.add(telefono);
                     
                 
            }
        return lista;
    }
     public List consultarTelefonosMasVendidos2(){
        List list = ctrPedido.findMaximaVenta();
        List lista =  new ArrayList();
        
         for (int i = 0; i < 3; i++) {
                Object[] lista2 = (Object[]) list.get(i);
                 
                         long cantidad = (long) lista2[1];
                         
                         lista.add(String.valueOf(cantidad));
                     
                 
            }
        return lista;
    }
     
      public List consultarReparacionesMasRealizadas(){
        List list = ctrPedido.findMaximaReparacion();
        List lista =  new ArrayList();
        
         for (int i = 0; i < 3; i++) {
                Object[] lista2 = (Object[]) list.get(i);
                 
                         Reparaciones telefono = (Reparaciones) lista2[0];
                         
                         lista.add(telefono);
                     
                 
            }
        return lista;
    }
      public List consultarReparacionesMasRealizadas2(){
        List list = ctrPedido.findMaximaReparacion();
        List lista =  new ArrayList();
        
         for (int i = 0; i < 3; i++) {
                Object[] lista2 = (Object[]) list.get(i);
                 
                         long cantidad = (long) lista2[1];
                         
                         lista.add(String.valueOf(cantidad));
                     
                 
            }
        return lista;
    }
      

    //Metodo que rellena los inputs de las caracteristicas cuando seleccionamos un modelo
    public void consultarCaracteristicas(final AjaxBehaviorEvent event) {

        int codigoTelefonoCaracteristica = Integer.parseInt(codigoTelefono);

        Caracteristicastelefono caracteristica = ctrCaracteristicas.findCaracteristicasByTelefono(ctrTelefono.findTelefono(codigoTelefonoCaracteristica));
        
        if (caracteristica != null) {
            so = caracteristica.getSo();
            ram = String.valueOf(caracteristica.getRam());
            pulgadas = String.valueOf(caracteristica.getPulgadas());
            almacenamiento = String.valueOf(caracteristica.getAlmacenamiento());
            camaraTrasera = String.valueOf(caracteristica.getCamaraTrasera());
            camaraDelantera = String.valueOf(caracteristica.getCamaraDelantera());
            bateria = String.valueOf(caracteristica.getBateria());
            procesador = caracteristica.getProcesador();
            wifi = caracteristica.getWifi();
            resolucion = caracteristica.getResolucion();
            color = caracteristica.getColor();
            detectorDeHuellas = caracteristica.getDetectorHuella();
            dualSim = caracteristica.getDualSim();
            sd = caracteristica.getSd();
            bluetooth = caracteristica.getBluetooth();
            nfc = caracteristica.getNfc();
            g3 = caracteristica.getG();
            g4 = caracteristica.getG1();
        } 
        
        else {
            so = "";
            ram = "";
            pulgadas = "";
            almacenamiento = "";
            camaraTrasera = "";
            camaraDelantera = "";
            bateria = "";
            procesador = "";
            wifi = false;
            resolucion = "";
            color = "";
            detectorDeHuellas = false;
            dualSim = false;
            sd = false;
            bluetooth = false;
            nfc = false;
            g3 = false;
            g4 = false;
        }
    }
}
