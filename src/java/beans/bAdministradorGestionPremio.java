package beans;

import DAO.AdministradorJpaController;
import DAO.CaracteristicastelefonoJpaController;
import DAO.DireccionJpaController;
import DAO.FotoJpaController;
import DAO.PedidoJpaController;
import DAO.PoblacionJpaController;
import DAO.PremioJpaController;
import DAO.PremioconfotoJpaController;
import DAO.PremiosinfotoJpaController;
import DAO.TecnicoJpaController;
import DAO.TelefonoJpaController;
import DAO.UsuarioJpaController;
import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import DTO.Administrador;
import DTO.Caracteristicastelefono;
import DTO.Direccion;
import DTO.Foto;
import DTO.Pedido;
import DTO.Poblacion;
import DTO.Premio;
import DTO.Premioconfoto;
import DTO.Premiosinfoto;
import DTO.Tecnico;
import DTO.Telefono;
import DTO.Usuario;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;

public class bAdministradorGestionPremio {

    //Driver para conectar con la base de datos
    private EntityManagerFactory emf;

    //Control para gestionar los telefonos
    private FotoJpaController ctrFotos;
    private PremioJpaController ctrPremios;
    private PremiosinfotoJpaController ctrPremioSinFotos;
    private PremioconfotoJpaController ctrPremioConFotos;
    
    //Variable que guarda la lsta de premios para la tabla
    private List listaPremiosTabla;
    
     //Objeto que guarda el premio seleccionado de una fila de la tabla
    private Premio miPremio;

    //Buffer para la subida de la foto
    private static final int BUFFER_SIZE=6124;
    
    //Variable que guarda la foto del premio
    private UploadedFile filePremio;
    
   //Variable que guarda el nombre del premio
    private String nombrePremio;
    
    //Variable que guarda el coste del premio
    private String coste;
    
    //Variable que guarda el codigo del premio para añadir la foto 
    private String codigoPremio;
    
     private String codigoFotoEliminar;
    
    //Variable que guarda la lista de premios para los select
    private ArrayList listaPremiosSinFotos;
    
    private ArrayList listaPremiosConFotos;
    
    //Variable que guarda el codigo del premio que deseamos eliminar
    private String codigoPremioEliminar;

    //Contructor
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
    

    public ArrayList getListaPremiosConFotos() {
         if (listaPremiosConFotos == null) {
            //Inicializamos la lista de premios
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

                //Creamos un tecnico con los datos
                Premio premio = new Premio(null, nombrePremio, Integer.parseInt(coste));

                //Damos de alta en la base de datos
                ctrPremios.create(premio);
                
                //Inicializamos la lista para que cuando entre ya esten cargados los telefono
            listaPremiosTabla = new ArrayList();

            listaPremiosTabla = ctrPremios.findPremioEntities();
                
                //Actualizamos la tabla, así llamara al get que se encargará de actualizarla cuando recarge la pagina
                listaPremiosSinFotos=null;
                getListaPremiosSinFotos();

                //Escribo el mensaje de alta correcta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha dado de alta al premio correctamente."));

                return "correcto";

            } else {
                
                //Creamos un telefono con los datos
                Premio premio = new Premio(premioRepetido.getCodigoPremio(), nombrePremio, Integer.parseInt(coste));

                //Subo el telefono a la sesion
                subirPremio(premio);

                RequestContext.getCurrentInstance().execute("PF('confirmDlg').show();");

                
                return "correcto";
            }

        
    }
    
    //Metodo para subir un telefono a la sesion
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

        //Añadirle como propiedad el telefono para modificarlo
        manageBeanSesion.setPremioModificar(premio);
    }
    
    //Metodo para modificar un telefono
    public void modificarPremio() {

        //Cojo el telefono de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        Premio premio = (Premio) manageBeanSesion.getPremioModificar();

        try {
            //Edito el tecnico 
            ctrPremios.edit(premio);

            
                //Inicializamos la lista para que cuando entre ya esten cargados los telefono
            listaPremiosTabla = new ArrayList();

            listaPremiosTabla = ctrPremios.findPremioEntities();
                
                //Actualizamos la tabla, así llamara al get que se encargará de actualizarla cuando recarge la pagina
                listaPremiosSinFotos=null;
                getListaPremiosSinFotos();

            //Escribo el mensaje de modificacion correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha modificado al telefono correctamente."));

        } catch (Exception ex) {
            Logger.getLogger(bAdministradorGestionTecnico.class.getName()).log(Level.SEVERE, null, ex);

            //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No ha introducido todos los datos"));

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
                    
                    //Escribo el mensaje de subida correcta
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La foto se ha subido correctamente."));
        
                    //La subo a la base de datos
                    Foto foto = new Foto(null, "premio" + codigoPremio + "." + extension);
                
                    foto.setCodigoPremio(ctrPremios.findPremio(Integer.parseInt(codigoPremio)));
                    
                    ctrFotos.create(foto);
                    
                    //Inicializamos la lista de los modelos tambien para dar de alta las caracteristicas y las foto
            listaPremiosSinFotos = null;
            
            listaPremiosSinFotos = getListaPremiosSinFotos();

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

    
    //Metodo para eliminar un telefono
    public String bajaPremio() {
        try {
            //Cojo el telefono seleccionado de la fila y lo borro
            ctrPremios.destroy(miPremio.getCodigoPremio());

            //Inicializamos la lista para que cuando entre ya esten cargados los telefono
            listaPremiosTabla = new ArrayList();

            listaPremiosTabla = ctrPremios.findPremioEntities();
            
            //Inicializamos la lista de los modelos tambien para dar de alta las caracteristicas y las foto
            listaPremiosConFotos = null;
            
            listaPremiosConFotos = getListaPremiosConFotos();

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
    
    //Metodo para editar en la tabla
    public void onRowEdit(RowEditEvent event) {
        //Cojo el telefono de la fila seleccionada
        Premio premio = (Premio) event.getObject();
        try {
            ctrPremios.edit(premio);
            
            //Inicializamos la lista de los modelos tambien para dar de alta las caracteristicas y las foto
            listaPremiosSinFotos = null;
            
            listaPremiosSinFotos = getListaPremiosSinFotos();
            
            //Escribo el mensaje de modificacion correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha modificado al telefono correctamente."));
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(bAdministradorGestionTelefono.class.getName()).log(Level.SEVERE, null, ex);
             //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No ha introducido todos los datos"));

        } catch (Exception ex) {
            Logger.getLogger(bAdministradorGestionTelefono.class.getName()).log(Level.SEVERE, null, ex);
             //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No ha introducido todos los datos"));

        }
    }

    public void onRowCancel(RowEditEvent event) {   
    }
    
     //Metodo para borrar una foto
    public String borrarFotoPremio(){
        
        //Cojo la foto
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");

        Foto foto =ctrFotos.findFotoByCodigoPremio(ctrPremios.findPremio(Integer.parseInt(codigoFotoEliminar)));
        
        //CREAMOS EL FILE CON LA RUTA ENTERA
            File result = new File(path + "/../../web/imagenes/FotosPremio/" + foto.getNombre());
            
        //Borro el fichero
            result.delete();
            
        try {
            //Lo borro de la base de datos
            ctrFotos.destroy(Integer.parseInt(codigoFotoEliminar));
            
             //Escribo el mensaje de subida correcta
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La foto se ha borrado correctamente."));
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(bAdministradorGestionTelefono.class.getName()).log(Level.SEVERE, null, ex);
            
            //Escribo el mensaje de subida incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al borrar la foto de la base de datos."));
        }
        
        return "correcto";
           
    }
    

}
