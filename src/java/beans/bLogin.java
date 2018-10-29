package beans;

import DAO.AdministradorJpaController;
import DAO.TecnicoJpaController;
import DAO.UsuarioJpaController;
import DTO.Administrador;
import DTO.Tecnico;
import DTO.Usuario;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;


public class bLogin {
    
    //Driver para conectar con la base de datos
    private EntityManagerFactory emf;
    
    //Control para gestionar el usuario de la base de datos
    private UsuarioJpaController ctrUsuario;
    private TecnicoJpaController ctrTecnico;
    private AdministradorJpaController ctrAdministrador;
    
    //Variables que almacenan los datos de los inputs, para hacer el login
    private String nick;
    private String password;
    
    //Objeto usuario que subiremos a la sesión cuando comprobemos que está en la base de datos
    private Usuario usuarioLogeado;
    private Tecnico tecnicoLogeado;
    private Administrador administradorLogeado;

    //Mensaje que devolveremos para poder comprobar el logeo
    private String mens;

    //Contructor
    public bLogin() {
        emf = Persistence.createEntityManagerFactory("CRVPU");
        ctrUsuario= new UsuarioJpaController(emf);
        ctrTecnico= new TecnicoJpaController(emf);
        ctrAdministrador= new AdministradorJpaController(emf);
    }
    
    //Getter and setter

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public UsuarioJpaController getCtrUsuario() {
        return ctrUsuario;
    }

    public void setCtrUsuario(UsuarioJpaController ctrUsuario) {
        this.ctrUsuario = ctrUsuario;
    }

    public TecnicoJpaController getCtrTecnico() {
        return ctrTecnico;
    }

    public void setCtrTecnico(TecnicoJpaController ctrTecnico) {
        this.ctrTecnico = ctrTecnico;
    }

    public AdministradorJpaController getCtrAdministrador() {
        return ctrAdministrador;
    }

    public void setCtrAdministrador(AdministradorJpaController ctrAdministrador) {
        this.ctrAdministrador = ctrAdministrador;
    }

    public Tecnico getTecnicoLogeado() {
        return tecnicoLogeado;
    }

    public void setTecnicoLogeado(Tecnico tecnicoLogeado) {
        this.tecnicoLogeado = tecnicoLogeado;
    }

    public Administrador getAdministradorLogeado() {
        return administradorLogeado;
    }

    public void setAdministradorLogeado(Administrador administradorLogeado) {
        this.administradorLogeado = administradorLogeado;
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

    public Usuario getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public void setUsuarioLogeado(Usuario usuarioLogeado) {
        this.usuarioLogeado = usuarioLogeado;
    }

    public String getMens() {
        return mens;
    }

    public void setMens(String mens) {
        this.mens = mens;
    }
    
    //Metodo para comprobar el logeo
    public String compruebaLogin(){
        //Resetear valores
        mens="";
        
        //Devuelve el objeto 
        Usuario usuario = ctrUsuario.findUsuarioByNick(nick);
        Tecnico tecnico = ctrTecnico.findTecnicoByNick(nick);
        Administrador administrador = ctrAdministrador.findAdministradorByNick(nick);
        
       
        //Comprobamos que sea usuario y si existe, si la contraseña introducida es correcta
        if(usuario!=null && usuario.getPassword().equals(password)){
            
            //Subir usuario a la sesion, es decir como atributo de bTienda ya que será el bean de sesion
            //puesto que el login es de request y se pierde
            usuarioLogeado=usuario;
            subirUsuario();
            
            return "usuario";
            }
        //Comprobamos que sea tecnico y si existe, si la contraseña introducida es correcta
        else if(tecnico!=null && tecnico.getPassword().equals(password)){ 
            //Subir usuario a la sesion, es decir como atributo de bTienda ya que será el bean de sesion
            //puesto que el login es de request y se pierde
            tecnicoLogeado=tecnico;
            subirTecnico();
             return "tecnico";
        }
        else if(administrador!=null && administrador.getPassword().equals(password)){ 
            //Subir usuario a la sesion, es decir como atributo de bTienda ya que será el bean de sesion
            //puesto que el login es de request y se pierde
            administradorLogeado=administrador;
            subirAdministrador();
             return "administrador";
        }
        else{
            mens= "Datos incorrectos";
             return "error";
             
        }
        
    }
    
    //Metodo para subir usuario a la sesion, es decir como atributo de bTienda ya que será el bean de sesion
    public  void subirUsuario()
    {
        //Coger contexto
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        
        //Crear un objeto bTienda para añadirle despues el cliente como atributo
        manageBeanSesion manageBeanSesion=new manageBeanSesion();
        
            
            //Coger session del contexto
            HttpSession session= (HttpSession)ctx.getSession(false);
            
            //Compruebo si se ha creado el beanTienda en la sesion sino lo creo,
            //se crea automaticamente cuando se pone en el .jsp bTienda.(lo que sea) es decir cuando se menciona
            //tambien podriamos quitar esto y poner en el login.jsp en el titulo un outputText con value
            // bTienda.nada() un metodo que no haga nada y nada mas que por ponerlo ya se crea automaticamente
            if(session.getAttribute("manageBeanSesion")!=null){
                manageBeanSesion=(manageBeanSesion) session.getAttribute("manageBeanSesion");
            }else{
                session.setAttribute("manageBeanSesion",manageBeanSesion);
            }
  
            //Añadirle como propiedad el usuario que se ha logeado
            manageBeanSesion.setUsuarioLog(usuarioLogeado);          
    }
    
    //Metodo para subir usuario a la sesion, es decir como atributo de bTienda ya que será el bean de sesion
    public  void subirTecnico()
    {
        //Coger contexto
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        
        //Crear un objeto bTienda para añadirle despues el cliente como atributo
        manageBeanSesion manageBeanSesion=new manageBeanSesion();
        
            
            //Coger session del contexto
            HttpSession session= (HttpSession)ctx.getSession(false);
            
            //Compruebo si se ha creado el beanTienda en la sesion sino lo creo,
            //se crea automaticamente cuando se pone en el .jsp bTienda.(lo que sea) es decir cuando se menciona
            //tambien podriamos quitar esto y poner en el login.jsp en el titulo un outputText con value
            // bTienda.nada() un metodo que no haga nada y nada mas que por ponerlo ya se crea automaticamente
            if(session.getAttribute("manageBeanSesion")!=null){
                manageBeanSesion=(manageBeanSesion) session.getAttribute("manageBeanSesion");
            }else{
                session.setAttribute("manageBeanSesion",manageBeanSesion);
            }
  
            //Añadirle como propiedad el tecnico que se ha logeado
            manageBeanSesion.setTecnicoLog(tecnicoLogeado);          
    }
    
    //Metodo para subir usuario a la sesion, es decir como atributo de bTienda ya que será el bean de sesion
    public  void subirAdministrador()
    {
        //Coger contexto
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        
        //Crear un objeto bTienda para añadirle despues el cliente como atributo
        manageBeanSesion manageBeanSesion=new manageBeanSesion();
        
            
            //Coger session del contexto
            HttpSession session= (HttpSession)ctx.getSession(false);
            
            //Compruebo si se ha creado el beanTienda en la sesion sino lo creo,
            //se crea automaticamente cuando se pone en el .jsp bTienda.(lo que sea) es decir cuando se menciona
            //tambien podriamos quitar esto y poner en el login.jsp en el titulo un outputText con value
            // bTienda.nada() un metodo que no haga nada y nada mas que por ponerlo ya se crea automaticamente
            if(session.getAttribute("manageBeanSesion")!=null){
                manageBeanSesion=(manageBeanSesion) session.getAttribute("manageBeanSesion");
            }else{
                session.setAttribute("manageBeanSesion",manageBeanSesion);
            }
  
            //Añadirle como propiedad el usuario que se ha logeado
            manageBeanSesion.setAdministradorLog(administradorLogeado);          
    }
    
    

    
    
        
    
   
    
    }

















