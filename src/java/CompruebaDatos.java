/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import DAO.AdministradorJpaController;
import DAO.PedidoJpaController;
import DAO.PoblacionJpaController;
import DAO.TecnicoJpaController;
import DAO.UsuarioJpaController;
import DTO.Administrador;
import DTO.Poblacion;
import DTO.Tecnico;
import DTO.Telefono;
import DTO.Usuario;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.JSONObject;

/**
 * Este Servlet recibira una peticion ajax de la peticion Preparador Seleccion,
 * que comprobara los datos: email El metodo compruebaEmail puede tener dos
 * salidas: True: que existe el usuario y por lo tanto no se podria dar el ata,
 * y False: lo contrario
 *
 * @author Alvaro
 */
public class CompruebaDatos extends HttpServlet {

    public void proceso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        boolean acceso = false;
        
        Poblacion poblacion = null;
        
        List lista=null;

        String nick = request.getParameter("Nick");
        if(nick!=null){
        try {
            //Leemos el objeto JSON y almacenamos su valor en la variable de email
            acceso = compruebaNick(nick);

            //Preparamos la salida del objeto JSON
            JsonObject object = new JsonObject();
            object.addProperty("Nick", acceso);

            String jsonS = object.toString();
            out.println(jsonS);

            //----------------------------------------------------------**/
            out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
        
        String email = request.getParameter("Email");
        if(email!=null){
        try {
            //Leemos el objeto JSON y almacenamos su valor en la variable de email
            acceso = compruebaEmail(email);

            //Preparamos la salida del objeto JSON
            JsonObject object = new JsonObject();
            object.addProperty("Email", acceso);

            String jsonS = object.toString();
            out.println(jsonS);

            //----------------------------------------------------------**/
            out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
        
        String compra = request.getParameter("Compra");
        if(compra!=null){
        try {
            //Leemos el objeto JSON y almacenamos su valor en la variable de email
            lista = compruebaCompra();

            //Preparamos la salida del objeto JSON
            JsonObject object = new JsonObject();
            for (int i = 0; i < lista.size(); i++) {
                Object[] lista2 = (Object[]) lista.get(i);
                 
                         Telefono telefono = (Telefono) lista2[0];
                         object.addProperty("Nombre"+i, telefono.getNombre());
                    
                         long numeroVeces=(long) lista2[1];
                         object.addProperty("Numero"+i, numeroVeces);
                     
                 
            }
            

            String jsonS = object.toString();
            out.println(jsonS);

            //----------------------------------------------------------**/
            out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
        
        String codigoPoblacion = request.getParameter("codigoPoblacion");
        if(codigoPoblacion!=null){
        try {
            //Leemos el objeto JSON y almacenamos su valor en la variable de email
            poblacion = compruebaPoblacion(Integer.parseInt(codigoPoblacion));

            //Preparamos la salida del objeto JSON
            JsonObject object = new JsonObject();
            object.addProperty("Latitud", poblacion.getLatitud());
            object.addProperty("Longitud", poblacion.getLongitud());
            object.addProperty("Poblacion", poblacion.getNombrePoblacion());
            object.addProperty("Postal", poblacion.getPostal());

            String jsonS = object.toString();
            out.println(jsonS);

            //----------------------------------------------------------**/
            out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        proceso(req, res);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        proceso(req, res);

    }

    public boolean compruebaNick(String nick) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CRVPU");
        UsuarioJpaController ctrUsuario = new UsuarioJpaController(emf);
        AdministradorJpaController ctrAdministrador = new AdministradorJpaController(emf);
        TecnicoJpaController ctrTecnico = new TecnicoJpaController(emf);
        
        Usuario resultado = ctrUsuario.findUsuarioByNick(nick);
        Tecnico resultado1 = ctrTecnico.findTecnicoByNick(nick);
        Administrador resultado2 = ctrAdministrador.findAdministradorByNick(nick);
        
        //Si es distinto a nulo, significa que ya existe alguien
        if (resultado==null &&resultado1==null &&resultado2==null) {
            return false;
        } else {
            return true;
        }
    }
    
     public boolean compruebaEmail(String email) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CRVPU");
        UsuarioJpaController ctrUsuario = new UsuarioJpaController(emf);
        
        Usuario resultado = ctrUsuario.findByEmail(email);
        
        //Si es distinto a nulo, significa que ya existe alguien
        if (resultado==null) {
            return false;
        } else {
            return true;
        }
    }
    
    public Poblacion compruebaPoblacion(int codigoPoblacion) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CRVPU");
        PoblacionJpaController ctrPoblacion = new PoblacionJpaController(emf);
        
        Poblacion pobla = ctrPoblacion.findPoblacion(codigoPoblacion);

        //Si es distinto a nulo, significa que ya existe alguien
        if (pobla==null) {
            return null;
        } else {
            return pobla;
        }
    }
     public List compruebaCompra() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CRVPU");
         PedidoJpaController  ctrPedido = new PedidoJpaController(emf);
        
        List lista = ctrPedido.findMaximaVenta();

        //Si es distinto a nulo, significa que ya existe alguien
        if (lista==null) {
            return null;
        } else {
            return lista;
        }
    }
}
