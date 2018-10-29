
package beans;


import DTO.Administrador;
import DTO.Tecnico;
import DTO.Usuario;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


@ManagedBean(name="bSesion")
@SessionScoped
public class manageBeanSesion {


     private Usuario usuarioLog;
     private Tecnico tecnicoLog;
     private Administrador administradorLog;
 
    public manageBeanSesion() {
  
    }

    public Usuario getUsuarioLog() {
        return usuarioLog;
    }

    public void setUsuarioLog(Usuario usuarioLog) {
        this.usuarioLog = usuarioLog;
    }

    public Tecnico getTecnicoLog() {
        return tecnicoLog;
    }

    public void setTecnicoLog(Tecnico tecnicoLog) {
        this.tecnicoLog = tecnicoLog;
    }

    public Administrador getAdministradorLog() {
        return administradorLog;
    }

    public void setAdministradorLog(Administrador administradorLog) {
        this.administradorLog = administradorLog;
    }

    


    public static void redireccionar (String url)
    {
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        try {
                ctx.redirect(url);
            } catch (Exception ex) {}
   
    }
    
    public static void logout(String surl) {
        
        
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        try {
                ((HttpSession) ctx.getSession(false)).invalidate();
                ctx.redirect(surl);
        } catch (Exception ex) {
}
}
    
    
  
}
