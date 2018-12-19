package beans;

import DAO.AdministradorJpaController;
import DAO.TecnicoJpaController;
import DAO.UsuarioJpaController;
import DAO.exceptions.NonexistentEntityException;
import DTO.Administrador;
import DTO.Tecnico;
import DTO.Telefono;
import DTO.TelefonoCesta;
import DTO.Usuario;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;


import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;


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
    private String email;
    
    //Objeto usuario que subiremos a la sesión cuando comprobemos que está en la base de datos
    private Usuario usuarioLogeado;
    private Tecnico tecnicoLogeado;
    private Administrador administradorLogeado;

    //Mensaje que devolveremos para poder comprobar el logeo
    private String mens;
    private String mens2;
    
    //Lista con los articulos de la compra(Carrito)
    private List<TelefonoCesta> listaCarrito;

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

    public List<TelefonoCesta> getListaCarrito() {
        return listaCarrito;
    }

    public void setListaCarrito(List<TelefonoCesta> listaCarrito) {
        this.listaCarrito = listaCarrito;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMens2() {
        return mens2;
    }

    public void setMens2(String mens2) {
        this.mens2 = mens2;
    }
    
    
    
    
    
    public boolean comprobarRender(Boolean boo){
        if(boo==null){
            return true;
        }
    if(boo==true){
        return true;
    }
    else{
        return false;
    }
    }
     public boolean comprobarRenderAdmin(Boolean boo){
        if(boo==null){
            return true;
        }
    if(boo==true){
        return true;
    }
    else{
        return false;
    }
    }
      public boolean comprobarRenderTecnico(Boolean boo){
        if(boo==null){
            return true;
        }
    if(boo==true){
        return true;
    }
    else{
        return false;
    }
    }
     public boolean comprobarRender2(Boolean boo){
        if(boo==null){
            return true;
        }
    if(boo==true){
       return true;
    }
    else{
          //Cojo el carrito de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        List listaCarrito = (List) manageBeanSesion.getListaCarrito();
        if(listaCarrito.size()==0){
            return true;
        }
        else{
        return false;
        }
    }
    }
    
     public boolean comprobarRender2(Boolean boo,Boolean boo2){
        if(boo==null && boo2 ==null){
            return true;
        }
    if(boo==true && boo2==true){
        return true;
    }
    else{
        return false;
    }
    
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
            
            //Subo el carrito a la sesion para saber que articulos compra
            listaCarrito=new ArrayList();
            subirCarrito();
            
            //Muestro la ventana por si quiere modificar el telefono
          PrimeFaces.current().executeScript("$('#modalInicioSesion').css('display','none')");
            
            return "";
            }
        //Comprobamos que sea tecnico y si existe, si la contraseña introducida es correcta
        else if(tecnico!=null && tecnico.getPassword().equals(password)){ 
            //Subir usuario a la sesion, es decir como atributo de bTienda ya que será el bean de sesion
            //puesto que el login es de request y se pierde
            tecnicoLogeado=tecnico;
            subirTecnico();
             //Coger contexto
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            try {
                ctx.redirect("/CRV/faces/tecnico.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(bLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
             return "tecnico";
        }
        else if(administrador!=null && administrador.getPassword().equals(password)){ 
            //Subir usuario a la sesion, es decir como atributo de bTienda ya que será el bean de sesion
            //puesto que el login es de request y se pierde
            administradorLogeado=administrador;
            subirAdministrador();
            //Coger contexto
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            try {
                ctx.redirect("/CRV/faces/administrador.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(bLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            
            manageBeanSesion.setLogeado(false);
             manageBeanSesion.setLogeadoOtro(true);
              manageBeanSesion.setLogeadoTecnico(true);
               manageBeanSesion.setLogeadoAdmin(true);
    }
    
    //Metodo para subir usuario a la sesion, es decir como atributo de bTienda ya que será el bean de sesion
    public  void subirCarrito()
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
            manageBeanSesion.setListaCarrito(listaCarrito);          
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
            
             manageBeanSesion.setLogeado(true);
             manageBeanSesion.setLogeado(true);
           manageBeanSesion.setLogeadoTecnico(false);
            manageBeanSesion.setLogeadoAdmin(true);
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
            
            manageBeanSesion.setLogeado(true);
            manageBeanSesion.setLogeado(true);
            manageBeanSesion.setLogeadoAdmin(false);
             manageBeanSesion.setLogeadoTecnico(true);
    }
    
    public String olvidarPassword() {

        Usuario usu = ctrUsuario.findByEmail(email);

        if (usu != null) {
            String contraseñaAleatoria = "";
            //generacion contraseña Aleatoria
            char[] elementos = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',//NUMBERS
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',//MINUS
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',//MAYUS
                '!', '?', '*', '-','.'};//SYMBOLS
            int longitud = elementos.length;
            char[] conjunto = new char[8];

            for (int i = 0; i < 8; i++) {
                int el = (int) (Math.random() * longitud);
                conjunto[i] = (char) elementos[el];
            }
            contraseñaAleatoria = new String(conjunto);
            try {
                // Usuario y el password
                String usuario = "crvmovil@gmail.com";
                String pass = "crv1912@";

                String asunto = "Se ha modificado su contraseña";
                String mensaje = "Hola " + usu.getNombre() + " le enviamos una nueva contraseña, podrá cambiarla o mantenerla! "
                        + "\n \n " + contraseñaAleatoria
                        + "\n \n Equipo de CRV MÓVIL";

                Properties props = new Properties();
                props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");

                // Creamos una sesión que nos permita identificarnos en el servidor con el usuario y pass informados previamente
                Authenticator autenticador = new Authenticator() {
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(usuario, pass);
                    }
                };

                Session session = Session.getInstance(props, autenticador);

                Message correo = new MimeMessage(session);

                // Entidad que envia el email
                correo.setFrom(new InternetAddress(usuario));

                // Destinatarios
                InternetAddress[] listaDestinatarios = {new InternetAddress(email)};
                correo.setRecipients(Message.RecipientType.TO, listaDestinatarios);

                // Asunto
                correo.setSubject(asunto);

                // Paso 4. Informamos la fecha de hoy
                correo.setSentDate(new Date());

                // Mensaje que queremos enviar
                correo.setText(mensaje);

                // Una vez creado el objeto Message con el email, se realiza en envío. En caso de fallo elevará una excepción
                Transport.send(correo);

                usu.setPassword(contraseñaAleatoria);
                try {
                    ctrUsuario.edit(usu);
                    mens2 = "Su nueva contraseña está en su correo electrónico";
                    // Si hemos llegado a este punto significa que no se ha lanzado ninguna excepción y podemos decir que el email se ha enviado correctamente
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(bLogin.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(bLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (MessagingException ex) {
                Logger.getLogger(bLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            mens2 = "Introduzca un e-mail válido";
        }
        email = "";
        return "";
    }

public List listarFicherosPorCarpeta() {
    //Cojo la foto
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");

        //CREAMOS EL FILE CON LA RUTA ENTERA
        File result = new File(path + "/../../web/imagenes/FotosIndex/");
        List listaFotos= new ArrayList();
    for (File ficheroEntrada : result.listFiles()) {
        
            listaFotos.add(ficheroEntrada.getName());
        }
    return listaFotos;
    }

 public void idiomaEs(ActionEvent evento) {
        FacesContext contexto = FacesContext.getCurrentInstance();
            contexto.getViewRoot().setLocale(Locale.getDefault());
        }
     
    public void idiomaEn(ActionEvent evento) {
        FacesContext contexto = FacesContext.getCurrentInstance();
            contexto.getViewRoot().setLocale(Locale.ENGLISH);
        }

    
    }

















