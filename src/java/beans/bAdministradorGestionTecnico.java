
package beans;

import DAO.TecnicoJpaController;
import DTO.Administrador;
import DTO.Tecnico;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;


public class bAdministradorGestionTecnico {
    
    //Driver para conectar con la base de datos
    private EntityManagerFactory emf;
    
    //Control para gestionar el tecnico de la base de datos
    private TecnicoJpaController ctrTecnico;

    //Variables que almacenan los datos de los inputs, para dar de alta un técnico
    private String nick;
    private String password;
    
    //Lista donde guardamos los tenicos
    private List listaTecnicos;
    
    //Guardamos la fila de la tabla, para saber el tecnico que deseamos borrar
    private HtmlDataTable tabla;
    
    

    public bAdministradorGestionTecnico() {
        emf = Persistence.createEntityManagerFactory("CRVPU");
        ctrTecnico = new TecnicoJpaController(emf);
        
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
    
    
    
    //Metodo para dar de alta un tecnico
    public String altaTecnico() {

        //Comprobamos que ha introducido el nick y la contraseña
        if (nick != "" && password != "") {

            //Buscamos si existe un tecnico con el mismo nombre
            Tecnico tecnicoRepetido = ctrTecnico.findTecnicoByNick(nick);

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
                //Escribo el mensaje de alta incorrecta
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "Ya existe un tecnico con ese nick"));

                return "tecnicoAltaIncorrecta";
            }       
        }

        //Escribo el mensaje de alta incorrecta
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No ha introducido todos los datos"));

        return "tecnicoAltaIncorrecta";

    }

    //Metodo para dar de baja a un tecnico
    public String bajaTecnico() {
        //Recuperamos el objeto(tecnco) seleccionado es decir la fila donde se hizo click
        Tecnico tecnicoSeleccionado = (Tecnico) tabla.getRowData();

        try {
            //Borramos al tecnico
            ctrTecnico.destroy(tecnicoSeleccionado.getCodigoTecnico());

            //Actualizamos la tabla
            listaTecnicos = new ArrayList();

            listaTecnicos = ctrTecnico.findTecnicoEntities();
            
            //Escribo el mensaje de baja correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La baja del tecnico se ha realizado correctamente."));

            return "bajaCorrecta";
        } catch (Exception ex) {

            //Escribo el mensaje de baja incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha realizado la baja del técnico"));

            return "bajaIncorrecta";

        }
    }
    
}
