package beans;

import DAO.FotoJpaController;
import DAO.PremioJpaController;
import DAO.PremioconfotoJpaController;
import DAO.PremiosinfotoJpaController;
import DAO.exceptions.NonexistentEntityException;
import DTO.Foto;
import DTO.Premio;
import DTO.Premioconfoto;
import DTO.Premiosinfoto;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;

public class bAdministradorGestionPremio {

    //Driver para conectar con la base de datos
    private EntityManagerFactory emf;

    //Control para gestionar la base de datos
    private FotoJpaController ctrFotos;
    private PremioJpaController ctrPremios;
    private PremiosinfotoJpaController ctrPremioSinFotos;
    private PremioconfotoJpaController ctrPremioConFotos;

    //Variable que guarda la lista de premios para la tabla
    private List listaPremiosTabla;

    //Objeto que guarda el premio seleccionado de una fila de la tabla
    private Premio miPremio;

    //Buffer para la subida de la foto
    private static final int BUFFER_SIZE = 6124;

    //Variable que guarda la foto del premio
    private UploadedFile filePremio;

    //Variable que guarda el nombre del premio, para darlo de alta
    private String nombrePremio;

    //Variable que guarda el coste del premio, para darlo de alta
    private String coste;

    //Variable que guarda el codigo del premio para añadir la foto 
    private String codigoPremio;

    //Variable que guarda el codigo de la foto para borrarla
    private String codigoFotoEliminar;

    //Variable que guarda el codigo del premio que deseamos eliminar
    private String codigoPremioEliminar;

    //Variable que guarda la lista de premios sin fotos para el select
    private ArrayList listaPremiosSinFotos;

    //Variable que guarda la lista de premios sin fotos para el select
    private ArrayList listaPremiosConFotos;

    //Constructor
    public bAdministradorGestionPremio() {
        emf = Persistence.createEntityManagerFactory("CRVPU");
        ctrFotos = new FotoJpaController(emf);
        ctrPremios = new PremioJpaController(emf);
        ctrPremioSinFotos = new PremiosinfotoJpaController(emf);
        ctrPremioConFotos = new PremioconfotoJpaController(emf);

        //Inicializamos la lista para que cuando entre ya esten cargados en la tabla los premios
        listaPremiosTabla = new ArrayList();
        listaPremiosTabla = ctrPremios.findPremioEntities();

    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public FotoJpaController getCtrFotos() {
        return ctrFotos;
    }

    public void setCtrFotos(FotoJpaController ctrFotos) {
        this.ctrFotos = ctrFotos;
    }

    public PremioJpaController getCtrPremios() {
        return ctrPremios;
    }

    public void setCtrPremios(PremioJpaController ctrPremios) {
        this.ctrPremios = ctrPremios;
    }

    public UploadedFile getFilePremio() {
        return filePremio;
    }

    public void setFilePremio(UploadedFile filePremio) {
        this.filePremio = filePremio;
    }

    public String getNombrePremio() {
        return nombrePremio;
    }

    public void setNombrePremio(String nombrePremio) {
        this.nombrePremio = nombrePremio;
    }

    public String getCoste() {
        return coste;
    }

    public void setCoste(String coste) {
        this.coste = coste;
    }

    public String getCodigoPremio() {
        return codigoPremio;
    }

    public void setCodigoPremio(String codigoPremio) {
        this.codigoPremio = codigoPremio;
    }

    public String getCodigoPremioEliminar() {
        return codigoPremioEliminar;
    }

    public void setCodigoPremioEliminar(String codigoPremioEliminar) {
        this.codigoPremioEliminar = codigoPremioEliminar;
    }

    public List getListaPremiosTabla() {
        return listaPremiosTabla;
    }

    public void setListaPremiosTabla(List listaPremiosTabla) {
        this.listaPremiosTabla = listaPremiosTabla;
    }

    public Premio getMiPremio() {
        return miPremio;
    }

    public void setMiPremio(Premio miPremio) {
        this.miPremio = miPremio;
    }

    public PremiosinfotoJpaController getCtrPremioSinFotos() {
        return ctrPremioSinFotos;
    }

    public void setCtrPremioSinFotos(PremiosinfotoJpaController ctrPremioSinFotos) {
        this.ctrPremioSinFotos = ctrPremioSinFotos;
    }

    public PremioconfotoJpaController getCtrPremioConFotos() {
        return ctrPremioConFotos;
    }

    public void setCtrPremioConFotos(PremioconfotoJpaController ctrPremioConFotos) {
        this.ctrPremioConFotos = ctrPremioConFotos;
    }

    public String getCodigoFotoEliminar() {
        return codigoFotoEliminar;
    }

    public void setCodigoFotoEliminar(String codigoFotoEliminar) {
        this.codigoFotoEliminar = codigoFotoEliminar;
    }

    //Metodo que devuelve la lista para el select de borrar fotos, con los premios con fotos
    public ArrayList getListaPremiosConFotos() {
        if (listaPremiosConFotos == null) {

            //Inicializamos la lista de premios con fotos
            listaPremiosConFotos = new ArrayList();

            List<Premioconfoto> listaPre = ctrPremioConFotos.findPremioconfotoEntities();
            for (Premioconfoto pre : listaPre) {
                listaPremiosConFotos.add(new SelectItem(pre.getCodigoPremio(), pre.getNombre()));
            }
        }
        return listaPremiosConFotos;
    }

    public void setListaPremiosConFotos(ArrayList listaPremiosConFotos) {
        this.listaPremiosConFotos = listaPremiosConFotos;
    }

    //Metodo que devuelve la lista para el select de subir fotos, con los premios sin fotos
    public ArrayList getListaPremiosSinFotos() {
        if (listaPremiosSinFotos == null) {
            //Inicializamos la lista de premios
            listaPremiosSinFotos = new ArrayList();
            

            List<Premiosinfoto> listaPre = ctrPremioSinFotos.findPremiosinfotoEntities();
            for (Premiosinfoto pre : listaPre) {
                listaPremiosSinFotos.add(new SelectItem(pre.getCodigoPremio(), pre.getNombre()));
            }
        }
        return listaPremiosSinFotos;
    }

    public void setListaPremiosSinFotos(ArrayList listaPremiosSinFotos) {
        this.listaPremiosSinFotos = listaPremiosSinFotos;
    }

    //Metodo para dar de alta un premio
    public String altaPremio() {
        //Buscamos si existe un premio con el mismo nombre
        Premio premioRepetido = ctrPremios.findPremioByNombre(nombrePremio);

        //Si hemos encontrado un premio, no podremos repetirlo, si no existe lo creamos
        if (premioRepetido == null) {

            //Creamos un premio con los datos
            Premio premio = new Premio(null, nombrePremio, Integer.parseInt(coste));

            //Damos de alta en la base de datos
            ctrPremios.create(premio);

            //Inicializamos la lista para que cuando entre ya esten cargados los premios en la tabla
            listaPremiosTabla = new ArrayList();

            listaPremiosTabla = ctrPremios.findPremioEntities();

            //Actualizamos la lista de premios sin fotos, ya que hemos creado uno, sin añadirle foto
            listaPremiosSinFotos = null;
            getListaPremiosSinFotos();

            //Escribo el mensaje de alta correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha dado de alta al premio correctamente."));

            return "correcto";

        } //Si existe uno lo subimos a la sesion, y preguntamos si lo quiere modificar
        else {

            //Creamos un premio con los datos
            Premio premio = new Premio(premioRepetido.getCodigoPremio(), nombrePremio, Integer.parseInt(coste));

            premio.setFotoList(premioRepetido.getFotoList());
            //Subo el telefono a la sesion
            subirPremio(premio);

            //Visualizo ventana modal
            RequestContext.getCurrentInstance().execute("PF('confirmDlg').show();");

            return "correcto";
        }

    }

    //Metodo para subir un premio a la sesion
    public void subirPremio(Premio premio) {
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

        //Añadirle como propiedad el premio para modificarlo
        manageBeanSesion.setPremioModificar(premio);
    }

    //Metodo para modificar un premio
    public void modificarPremio() {

        //Cojo el premio de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        Premio premio = (Premio) manageBeanSesion.getPremioModificar();

        try {
            //Edito el premio 
            ctrPremios.edit(premio);

            //Inicializamos la lista para que cuando entre ya esten cargados los premios en la tabla
            listaPremiosTabla = new ArrayList();

            listaPremiosTabla = ctrPremios.findPremioEntities();

            //Actualizamos la tabla, así llamara al get que se encargará de actualizarla cuando recarge la pagina
            listaPremiosSinFotos = null;
            listaPremiosSinFotos = getListaPremiosSinFotos();

            listaPremiosConFotos = null;
            listaPremiosConFotos = getListaPremiosConFotos();

            //Escribo el mensaje de modificacion correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha modificado el premio correctamente."));

        } catch (Exception ex) {
            Logger.getLogger(bAdministradorGestionTecnico.class.getName()).log(Level.SEVERE, null, ex);

            //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha modificado el premio"));

        }

    }

    //Metodo que sube la foto de un premio
    public void subirFotoPremio() {
        try {
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");

            //OBTENEMOS EXTENSION DEL FICHERO QUE NOS VIENE
            String extension = "";
            int i = filePremio.getFileName().lastIndexOf('.');
            if (i > 0) {
                extension = filePremio.getFileName().substring(i + 1);
            }

            //CREAMOS EL FILE CON LA RUTA ENTERA
            File result = new File(path + "/../../web/imagenes/FotosPremios/" + "premio" + codigoPremio + "." + extension);

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(result);
                byte[] buffer = new byte[BUFFER_SIZE];
                int bulk;
                InputStream inputStream = filePremio.getInputstream();
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

                //Creo la foto con todos los datos
                Foto foto = new Foto(null, "premio" + codigoPremio + "." + extension);

                foto.setCodigoPremio(ctrPremios.findPremio(Integer.parseInt(codigoPremio)));

                //Subo la foto a la base de datos
                ctrFotos.create(foto);

                //Inicializamos la lista para que cuando entre ya esten cargados los premios
                listaPremiosTabla = new ArrayList();

                listaPremiosTabla = ctrPremios.findPremioEntities();

                //Actualizamos la lista de premios sin fotos, ya que hemos añadido una foto a un premio
                //por lo tanto habrá un premio menos 
                listaPremiosSinFotos = null;

                listaPremiosSinFotos = getListaPremiosSinFotos();

                //Tambien actualizamos la lista de premios con fotos ya que hemos añadido una foto a un premio
                //por lo tanto habra un premio mas
                listaPremiosConFotos = null;

                listaPremiosConFotos = getListaPremiosConFotos();

                //Escribo el mensaje de subida correcta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La foto se ha subido correctamente al servidor y la base de datos."));

            } catch (IOException e) {
                e.printStackTrace();

                //Escribo el mensaje de subida incorrecta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al subir la foto al servidor."));

            }

        } catch (Exception ex) {

            //Escribo el mensaje de subida incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al subir la foto a la base de datos."));

            Logger.getLogger(bAdministradorGestionPremio.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Metodo para eliminar un premio
    public String bajaPremio() {
        try {
            //Cojo el premio seleccionado de la fila y lo borro
            ctrPremios.destroy(miPremio.getCodigoPremio());
            
             //Cojo la foto
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");

        //CREAMOS EL FILE CON LA RUTA ENTERA
        File result = new File(path + "/../../web/imagenes/FotosPremios/" + miPremio.getFotoList().get(0).getNombre());

        //Borro el fichero
        result.delete();

            //Inicializamos la lista para que cuando entre ya esten cargados los premios
            listaPremiosTabla = new ArrayList();

            listaPremiosTabla = ctrPremios.findPremioEntities();

            //Actualizamos la lista de premios sin fotos, ya que hemos borrado un premio
            //por lo tanto habrá un premio menos 
            listaPremiosSinFotos = null;

            listaPremiosSinFotos = getListaPremiosSinFotos();

            //Tambien actualizamos la lista de premios con fotos ya que hemos borrado un premio
            //por lo tanto habra un premio menos
            listaPremiosConFotos = null;

            listaPremiosConFotos = getListaPremiosConFotos();

            //Escribo el mensaje de baja correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La baja del premio se ha realizado correctamente."));

            return "correcto";
        } catch (Exception ex) {
            Logger.getLogger(bAdministradorGestionTelefono.class.getName()).log(Level.SEVERE, null, ex);

            //Escribo el mensaje de baja incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha realizado la baja del premio"));

            return "correcto";
        }
    }

    //Metodo para editar en la tabla
    public void onRowEdit(RowEditEvent event) {
        //Cojo el premio de la fila seleccionada
        Premio premio = (Premio) event.getObject();
        try {
            //Lo edito en la base de datos
            ctrPremios.edit(premio);

            //Inicializamos la lista para que cuando entre ya esten cargados los premios
            listaPremiosTabla = new ArrayList();

            listaPremiosTabla = ctrPremios.findPremioEntities();

            //Actualizamos la lista de premios sin fotos, ya que hemos editado un premio
            listaPremiosSinFotos = null;

            listaPremiosSinFotos = getListaPremiosSinFotos();

            //Tambien actualizamos la lista de premios con fotos ya que hemos editado un premio
            listaPremiosConFotos = null;

            listaPremiosConFotos = getListaPremiosConFotos();

            //Escribo el mensaje de modificacion correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha modificado el premio correctamente."));

        } catch (NonexistentEntityException ex) {
            Logger.getLogger(bAdministradorGestionTelefono.class.getName()).log(Level.SEVERE, null, ex);

            //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha producido la modificacion"));

        } catch (Exception ex) {
            Logger.getLogger(bAdministradorGestionTelefono.class.getName()).log(Level.SEVERE, null, ex);

            //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha producido la modificacion"));

        }
    }

    public void onRowCancel(RowEditEvent event) {
    }

    //Metodo para borrar una foto
    public String borrarFotoPremio() {

        //Cojo la foto
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");

        Foto foto = ctrFotos.findFotoByCodigoPremio(ctrPremios.findPremio(Integer.parseInt(codigoFotoEliminar)));

        //CREAMOS EL FILE CON LA RUTA ENTERA
        File result = new File(path + "/../../web/imagenes/FotosPremios/" + foto.getNombre());

        //Borro el fichero
        result.delete();

        try {
            //Lo borro de la base de datos
            ctrFotos.destroy(foto.getCodigoFoto());

            //Inicializamos la lista para que cuando entre ya esten cargados los premios
            listaPremiosTabla = new ArrayList();

            listaPremiosTabla = ctrPremios.findPremioEntities();

            //Actualizamos la lista de premios sin fotos, ya que hemos eliminado un premio
            listaPremiosSinFotos = null;

            listaPremiosSinFotos = getListaPremiosSinFotos();

            //Tambien actualizamos la lista de premios con fotos ya que hemos eliminado un premio
            listaPremiosConFotos = null;

            listaPremiosConFotos = getListaPremiosConFotos();

            //Escribo el mensaje de subida correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La foto se ha borrado correctamente."));

        } catch (NonexistentEntityException ex) {
            Logger.getLogger(bAdministradorGestionTelefono.class.getName()).log(Level.SEVERE, null, ex);

            //Escribo el mensaje de subida incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al borrar la foto de la base de datos y del servidor."));
        }

        return "correcto";

    }

}
