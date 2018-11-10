
package beans;

import DAO.AdministradorJpaController;
import DAO.TecnicoJpaController;
import DAO.UsuarioJpaController;
import DTO.Administrador;
import DTO.Tecnico;
import DTO.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;


public class bAdministradorGestionTecnico {
    
    //Driver para conectar con la base de datos
    private EntityManagerFactory emf;
    
    //Control para gestionar la base de datos
    private TecnicoJpaController ctrTecnico;
    private AdministradorJpaController ctrAdministrador;
    private UsuarioJpaController ctrUsuario;

    //Variables que almacenan los datos de los inputs, para dar de alta un técnico
    private String nick;
    private String password;
    private Boolean existe;
    
    //Lista donde guardamos los tenicos
    private List listaTecnicos;
    
    //Guardamos la fila de la tabla, para saber el tecnico que deseamos borrar
    private HtmlDataTable tabla;
    
    

    public bAdministradorGestionTecnico() {
        emf = Persistence.createEntityManagerFactory("CRVPU");
        ctrTecnico = new TecnicoJpaController(emf);
        ctrAdministrador = new AdministradorJpaController(emf);
        ctrUsuario = new UsuarioJpaController(emf);
        
         //Inicializamos la lista para que cuando entre ya esten cargados
        listaTecnicos = new ArrayList();

        listaTecnicos = ctrTecnico.findTecnicoEntities();

    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public TecnicoJpaController getCtrTecnico() {
        return ctrTecnico;
    }

    public void setCtrTecnico(TecnicoJpaController ctrTecnico) {
        this.ctrTecnico = ctrTecnico;
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

    public List getListaTecnicos() {
        return listaTecnicos;
    }

    public void setListaTecnicos(List listaTecnicos) {
        this.listaTecnicos = listaTecnicos;
    }

    public HtmlDataTable getTabla() {
        return tabla;
    }

    public void setTabla(HtmlDataTable tabla) {
        this.tabla = tabla;
    }

    public Boolean getExiste() {
        return existe;
    }

    public void setExiste(Boolean existe) {
        this.existe = existe;
    }
    
    //Metodo para dar de alta un tecnico
    public String altaTecnico() {
            //Buscamos si existe un tecnico con el mismo nombre
            Tecnico tecnicoRepetido = ctrTecnico.findTecnicoByNick(nick);
            
            //Buscamos si existe un cliente o administrador con ese nombre
            Administrador administradorRepetido = ctrAdministrador.findAdministradorByNick(nick);
            Usuario usuarioRepetido = ctrUsuario.findUsuarioByNick(nick);
            
            //Si existe alguno no podremos darlo de alta para que no cause errores al iniciar sesion
            if(administradorRepetido ==null && usuarioRepetido==null){

            //Si hemos encontrado un tecnico, no podremos repetirlo, si no existe lo creamos
            if (tecnicoRepetido == null) {

                //Creamos un tecnico con los datos
                Tecnico tecnico = new Tecnico(null, nick, password);

                //Cojo el administrador de la sesion
                ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
                manageBeanSesion manageBeanSesion = new manageBeanSesion();

                HttpSession session = (HttpSession) ctx.getSession(false);
                manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
                Administrador administrador = (Administrador) manageBeanSesion.getAdministradorLog();

                //Cojo el codigo del administrador para meterselo al tecnico
                tecnico.setCodigoAdministrador(administrador);

                //Damos de alta en la base de datos
                ctrTecnico.create(tecnico);

                //Actualizamos la tabla
                listaTecnicos = new ArrayList();

                listaTecnicos = ctrTecnico.findTecnicoEntities();

                //Escribo el mensaje de alta correcta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha dado de alta al tecnico correctamente."));

                return "tecnicoAltaCorrecta";

            } else {
                //Creamos un tecnico con los datos
                Tecnico tecnico = new Tecnico(tecnicoRepetido.getCodigoTecnico(), nick, password);

                //Cojo el administrador de la sesion
                ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
                manageBeanSesion manageBeanSesion = new manageBeanSesion();

                HttpSession session = (HttpSession) ctx.getSession(false);
                manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
                Administrador administrador = (Administrador) manageBeanSesion.getAdministradorLog();

                //Cojo el codigo del administrador para meterselo al tecnico
                tecnico.setCodigoAdministrador(administrador);
                tecnico.setPedidoList(tecnicoRepetido.getPedidoList());
                subirTecnico(tecnico);
                
                RequestContext.getCurrentInstance().execute("PF('confirmDlg').show();");
                return "tecnicoAltaIncorrecta";
                
            }       
            }
            else{
                
                //Escribo el mensaje de que no puede elegir ese nick
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "FATAL!", "El nick elegido no esta disponible."));

                return "tecnicoAltaCorrecta";
                
            }
    }
    
    //Metodo para modificar un tecnico
    public void modificarTecnico() {

                //Cojo el tecnico de la sesion
                ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
                manageBeanSesion manageBeanSesion = new manageBeanSesion();

                HttpSession session = (HttpSession) ctx.getSession(false);
                manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
                Tecnico tecnico = (Tecnico) manageBeanSesion.getTecnicoModificar();

               
            try {
                //Edito el tecnico
                ctrTecnico.edit(tecnico);
                
                 //Actualizamos la tabla
                listaTecnicos = new ArrayList();
                listaTecnicos = ctrTecnico.findTecnicoEntities();

                //Escribo el mensaje de alta correcta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se ha modificado al tecnico correctamente."));

            } catch (Exception ex) {
                Logger.getLogger(bAdministradorGestionTecnico.class.getName()).log(Level.SEVERE, null, ex);
                
                //Escribo el mensaje de alta incorrecta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al editar el tecnico"));
            }     
    }
    //Metodo para dar de baja a un tecnico
    public void bajaTecnico() {
        //Recuperamos el objeto(tecnico) seleccionado es decir la fila donde se hizo click
        Tecnico tecnicoSeleccionado = (Tecnico) tabla.getRowData();

        try {
            //Borramos al tecnico
            ctrTecnico.destroy(tecnicoSeleccionado.getCodigoTecnico());

            //Actualizamos la tabla
            listaTecnicos = new ArrayList();
            listaTecnicos = ctrTecnico.findTecnicoEntities();
            
            //Escribo el mensaje de baja correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La baja del tecnico se ha realizado correctamente."));    
        } catch (Exception ex) {
            //Escribo el mensaje de baja incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha realizado la baja del técnico"));
        }
    }
    
    //Metodo para subir el tecnico a la sesion para poder modificarlo
    public  void subirTecnico(Tecnico tecnico)
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
           
        //Añadirle como propiedad el tecnico
        manageBeanSesion.setTecnicoModificar(tecnico);
    }
    
}
