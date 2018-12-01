/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import DAO.PoblacionJpaController;
import DAO.UsuarioJpaController;
import DTO.Poblacion;
import DTO.Usuario;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
        String codigoPoblacion = request.getParameter("codigoPoblacion");
        if(codigoPoblacion!=null){
        try {
            //Leemos el objeto JSON y almacenamos su valor en la variable de email
            poblacion = compruebaPoblacion(Integer.parseInt(codigoPoblacion));

            //Preparamos la salida del objeto JSON
            JsonObject object = new JsonObject();
            object.addProperty("Latitud", poblacion.getLatitud());
            object.addProperty("Longitud", poblacion.getLongitud());

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
        
        Usuario resultado = ctrUsuario.findUsuarioByNick(nick);

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
}
