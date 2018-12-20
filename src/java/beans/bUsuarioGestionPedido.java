package beans;

import DAO.FotoJpaController;
import DAO.PedidoJpaController;
import DAO.PoblacionJpaController;
import DAO.ProvinciaJpaController;
import DAO.TelefonoJpaController;
import DTO.Direccion;
import DTO.Foto;
import DTO.Pedido;
import DTO.Poblacion;
import DTO.Provincia;
import DTO.Telefono;
import DTO.Usuario;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class bUsuarioGestionPedido {

    //Driver para conectar con la base de datos
    private EntityManagerFactory emf;

    //Control para gestionar la base de datos
    private PedidoJpaController ctrPedido;
    private PoblacionJpaController ctrPoblacion;
    private FotoJpaController ctrFoto;
    private TelefonoJpaController ctrTelefono;
    private ProvinciaJpaController ctrProvincia;

    //Variable que almacena el codigo del pedido anterior
    private int codigoPedidoAnterior;

    //Variable que almacena el codigo del pedido
    private int codigoPedido;

    //Guardamos el codigo del pedido, para saber que pedido desamos modificar
    private int codigoPedidoModificar;

    //Variable que almacena la poblacion que queremos agregar
    private String codigoPoblacion;

    //Variable donde guardaremos el nuevo estado
    private String estadoNuevo;

    //Variable para saber si debemos mostrar ventana estado o no
    private boolean estado = false;

    //Variable para saber si debemos mostrar ventana ubicacion o no
    private boolean ubicacion = false;

    //Lista donde guardamos los pedidos
    private List listaPedidos;

    //Lista donde guardamos todas las poblaciones
    private ArrayList listaPoblaciones;

    //Lista que guarda los valores del select de estados
    private ArrayList listaEstados = null;
    
    private boolean booleanCabecera = false;
    private boolean noExisteP = false;

    public bUsuarioGestionPedido() {
        emf = Persistence.createEntityManagerFactory("CRVPU");
        ctrPoblacion = new PoblacionJpaController(emf);
        ctrPedido = new PedidoJpaController(emf);
        ctrFoto = new FotoJpaController(emf);
        ctrTelefono= new TelefonoJpaController(emf);
        ctrProvincia= new ProvinciaJpaController(emf);
                
        
                
    }
    
    public void actualizarListaPed(){
        //Cojo el usuario de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        Usuario usuario = (Usuario) manageBeanSesion.getUsuarioLog();
        
        //Inicializamos la lista para que cuando entre ya esten cargados
        listaPedidos = new ArrayList();

        listaPedidos = usuario.getPedidoList();
        
        Collections.reverse(listaPedidos);
        if(listaPedidos.size()==0){
            noExisteP=true;
        }
    }

    public boolean isNoExisteP() {
        return noExisteP;
    }

    public void setNoExisteP(boolean noExisteP) {
        this.noExisteP = noExisteP;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public PedidoJpaController getCtrPedido() {
        return ctrPedido;
    }

    public void setCtrPedido(PedidoJpaController ctrPedido) {
        this.ctrPedido = ctrPedido;
    }

    public PoblacionJpaController getCtrPoblacion() {
        return ctrPoblacion;
    }

    public void setCtrPoblacion(PoblacionJpaController ctrPoblacion) {
        this.ctrPoblacion = ctrPoblacion;
    }

    public int getCodigoPedidoAnterior() {
        return codigoPedidoAnterior;
    }

    public void setCodigoPedidoAnterior(int codigoPedidoAnterior) {
        this.codigoPedidoAnterior = codigoPedidoAnterior;
    }

    public int getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(int codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public int getCodigoPedidoModificar() {
        return codigoPedidoModificar;
    }

    public void setCodigoPedidoModificar(int codigoPedidoModificar) {
        this.codigoPedidoModificar = codigoPedidoModificar;
    }

    public String getCodigoPoblacion() {
        return codigoPoblacion;
    }

    public void setCodigoPoblacion(String codigoPoblacion) {
        this.codigoPoblacion = codigoPoblacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(boolean ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(String estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public List getListaPedidos() {
        
        return listaPedidos;
    }

    public void setListaPedidos(List listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    public void setListaPoblaciones(ArrayList listaPoblaciones) {
        this.listaPoblaciones = listaPoblaciones;
    }

    public void setListaEstados(ArrayList listaEstados) {
        this.listaEstados = listaEstados;
    }

    //Metodo para pintar un pedido
    public void cabeceraPedido() {
        if (codigoPedido == codigoPedidoAnterior) {

            booleanCabecera= true;
        } else {
            booleanCabecera= false;
        }

    }

    public FotoJpaController getCtrFoto() {
        return ctrFoto;
    }

    public void setCtrFoto(FotoJpaController ctrFoto) {
        this.ctrFoto = ctrFoto;
    }

    public TelefonoJpaController getCtrTelefono() {
        return ctrTelefono;
    }

    public void setCtrTelefono(TelefonoJpaController ctrTelefono) {
        this.ctrTelefono = ctrTelefono;
    }

    public ProvinciaJpaController getCtrProvincia() {
        return ctrProvincia;
    }

    public void setCtrProvincia(ProvinciaJpaController ctrProvincia) {
        this.ctrProvincia = ctrProvincia;
    }

    public boolean isBooleanCabecera() {
        return booleanCabecera;
    }

    public void setBooleanCabecera(boolean booleanCabecera) {
        this.booleanCabecera = booleanCabecera;
    }
    

    //Metodo para saber que tipo de pedido es
    public String saberTipo(int tipo) {
        if (tipo == 1) {
            return "Compra";
        }
        if (tipo == 2) {
            return "Reparación";
        } else {
            return "Venta";
        }
    }

    //Metodo para conocer el estado del pedido
    public String conocerEstado(int estado) {
        if (estado == 1) {
            return "Pedido en espera de envio";
        }
        if (estado == 2) {
            return "Pedido enviado";
        }
        if (estado == 3) {
            return "Pedido en tránsito";
        }
        if (estado == 4) {
            return "Pedido pendiente de entrega";
        } else {
            return "Pedido entregado";
        }
    }

    //Metodo para modificar el estado de un pedido
    public String modificarEstado() {
        //Busco el pedido en la base de datos
        Pedido pedido = ctrPedido.findPedido(codigoPedidoModificar);

        //Modifico el pedido
        pedido.setEstado(Integer.parseInt(estadoNuevo));

        try {
            //Actualizo en la base de datos el pedido
            ctrPedido.edit(pedido);

            //Inicializamos la lista para que actualice los cambios
            listaPedidos = new ArrayList();

            listaPedidos = ctrPedido.findPedidoEntities();

            //Restablezco los parametros para que me pinte la cabecera de los pedidos
            codigoPedido = 0;
            codigoPedidoAnterior = 1;

            //Cierro la ventana modal de cambiar estado
            estado = false;

            //Escribo el mensaje de modificacion correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La modificación del estado se ha realizado correctamente."));
        
        } catch (Exception ex) {
            Logger.getLogger(bUsuarioGestionPedido.class.getName()).log(Level.SEVERE, null, ex);
            
            //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al modifcar el estado del pedido"));
        }

        return "volver";
    }

    //Devolver valores para el select de cambiar el estado
    public ArrayList getListaEstados() {
        if (listaEstados == null) {
            listaEstados = new ArrayList();
            listaEstados.add(new SelectItem("1", "Pedido en espera de envio"));
            listaEstados.add(new SelectItem("2", "Pedido enviado"));
            listaEstados.add(new SelectItem("3", "Pedido en tránsito"));
            listaEstados.add(new SelectItem("4", "Pedido pendiente de entrega"));
            listaEstados.add(new SelectItem("5", "Pedido entregado"));
        }
        return listaEstados;
    }

    //Devolver valores para el datalist
    public ArrayList getListaPoblaciones() {
        if (listaPoblaciones == null) {
            //Inicializamos la lista de moviles
            listaPoblaciones = new ArrayList();

            List<Poblacion> listaPob = ctrPoblacion.findPoblacionEntities();
            for (Poblacion pob : listaPob) {
                listaPoblaciones.add(new SelectItem(pob.getCodigoPoblacion(), pob.getNombrePoblacion() + " - " + pob.getPostal()));
            }
        }
        return listaPoblaciones;
    }

    //Metodo para activar la ventana modal de cambiar estado
    public String cambiarEstado(int codigo) {
        codigoPedidoModificar = codigo;
        estado = true;

        return "volver";
    }

    //Metodo para activar la ventana modal de cambiar ubicacion
    public String cambiarUbicacion(int codigo) {
        codigoPedidoModificar = codigo;
        ubicacion = true;

        return "volver";
    }
    
    //Metodo que devuelve el nombre de la poblacion donde se encuentra el pedido
    public String conocerPoblacion(int codigoPoblacion) {
        
        Poblacion poblacion = ctrPoblacion.findPoblacion(codigoPoblacion);

        return poblacion.getNombrePoblacion();
    }
    
    //Metodo que devuelve el nombre de la foto segun el telefono
    public String conocerFoto(Telefono telefono) {
        
        List listaFotos = ctrFoto.findFotoByCodigoTelefono(telefono);

        
        if(listaFotos.size()>0){
            Foto foto= (Foto)listaFotos.get(0);
        return foto.getNombre();
        }
        return "";
    }

    //Metodo que cambia la poblacion en la que se encuentra un pedido
    public String modificarUbicacion() {
        //Busco el pedido en la base de datos
        Pedido pedido = ctrPedido.findPedido(codigoPedidoModificar);

        //Modifico el pedido
        pedido.setCodigoPoblacion(Integer.parseInt(codigoPoblacion));

        try {
            //Actualizo en la base de datos el pedido
            ctrPedido.edit(pedido);

            //Inicializamos la lista para que actualice los cambios
            listaPedidos = new ArrayList();

            listaPedidos = ctrPedido.findPedidoEntities();

            //Restablezco los parametros para que me pinte la cabecera de los pedidos
            codigoPedido = 0;
            codigoPedidoAnterior = 1;

            //Cierro la ventana modal de cambiar estado
            ubicacion = false;

            //Escribo el mensaje de modificacion correcta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La modificación de la ubicación se ha realizado correctamente."));

        } catch (Exception ex) {
            Logger.getLogger(bUsuarioGestionPedido.class.getName()).log(Level.SEVERE, null, ex);
            
            //Escribo el mensaje de modificacion incorrecta
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fallo al modifcar la ubicación del pedido"));
        }
        return "volver";
    }
    
    public void generarPdf(int numeroPedido){
        
        //Cojo el usuario de la sesion
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        manageBeanSesion manageBeanSesion = new manageBeanSesion();

        HttpSession session = (HttpSession) ctx.getSession(false);
        manageBeanSesion = (manageBeanSesion) session.getAttribute("manageBeanSesion");
        Usuario usuario = (Usuario) manageBeanSesion.getUsuarioLog();
        
        //Conocer Pedido
        
        Pedido pedido =ctrPedido.findPedidoByCodigoPedido(numeroPedido);
        
        List listaPedido= ctrPedido.findListaPedidoByCodigoPedido(numeroPedido);
        
        
        
        //Conocer Direccion
        Direccion direccion=pedido.getCodigoDireccion();
        
        //Conocer Poblacion
        Poblacion poblacion=ctrPoblacion.findPoblacion(direccion.getCodigoPoblacion());
        
        //Conocer Provincia
        Provincia provincia=ctrProvincia.findProvincia(poblacion.getCodigoProvincia());
        

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline=filename=" + ".pdf");
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, baos);
        } catch (DocumentException ex) {
            System.out.println(ex.getMessage());
        }
        document.open();
        document.setMargins(0, 0, 0, 0);
        try {
            //Añadir fuentes
            Font fuenteLogo = new Font(Font.FontFamily.TIMES_ROMAN, 26, Font.BOLD);
            Font fuenteCabecera = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            Font fuenteDatosCabecera =new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
            Font fuenteDatos =new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.NORMAL);
            Font fuentePedidoCabecera =new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
            Font fuentePedido =new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
            
            
            //Añadir imagen al pdf
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
            
            String ruta =path + "/../../web/imagenes/Fotos/logo.png" ;

            Image img = Image.getInstance(ruta);
            img.scaleAbsoluteWidth(60f);
            img.scaleAbsoluteHeight(60f);
            img.setAbsolutePosition(10f, 760f);
            
            
            //Crear letra
            Phrase logo = new Phrase(" \n     CRV MÓVIL", fuenteLogo);
            Phrase cabecera = new Phrase("                      Factura del pedido: " + String.valueOf(numeroPedido), fuenteCabecera);
            
            Phrase espacio = new Phrase(" \n\n", fuenteCabecera);
            Phrase espacio2 = new Phrase(" \n", fuenteCabecera);
            
            //Tabla con los datos de envio y de la empresa
            PdfPTable table = new PdfPTable(2);

            table.getDefaultCell().setBorder(Rectangle.NO_BORDER);//celdas sin bordes
            table.setHorizontalAlignment(Element.ALIGN_LEFT);//alineamos la tabla a la izquierda
            table.setWidthPercentage(100);//porcentaje que ocupa la tabla
            
            Phrase datosClienteCabecera = new Phrase("Enviado a: ", fuenteDatosCabecera);
            Phrase datosCliente1 = new Phrase(usuario.getNombre()+" "+usuario.getApellido1()+" "+usuario.getApellido2(), fuenteDatos);
            Phrase datosCliente2 = new Phrase(direccion.getNombre(), fuenteDatos);
            Phrase datosCliente3 = new Phrase(poblacion.getNombrePoblacion()+" ,"+poblacion.getPostal() , fuenteDatos);
            Phrase datosCliente4 = new Phrase(provincia.getNombreProvincia() , fuenteDatos);
            Phrase datosCliente5 = new Phrase(String.valueOf(direccion.getTelefono()), fuenteDatos);

            
            
            Phrase datosEmpresaCabcera= new Phrase("Datos empresa: " , fuenteDatosCabecera);
            Phrase datosEmpresa1 = new Phrase("CRV MOVIL SL " , fuenteDatos);
            Phrase datosEmpresa2 = new Phrase("CIF:B-733447495 " , fuenteDatos);
            Phrase datosEmpresa3 = new Phrase("Avenida Severo Ochoa nº8 " , fuenteDatos);
            Phrase datosEmpresa4 = new Phrase("Toledo 45001 " , fuenteDatos);
            Phrase datosEmpresa5 = new Phrase("Madrid " , fuenteDatos);

            PdfPCell cell1 = new PdfPCell(datosClienteCabecera);
            PdfPCell cell2 = new PdfPCell(datosEmpresaCabcera);
            PdfPCell cell3 = new PdfPCell(datosCliente1);
            PdfPCell cell4 = new PdfPCell(datosEmpresa1);
            PdfPCell cell5 = new PdfPCell(datosCliente2);
            PdfPCell cell6 = new PdfPCell(datosEmpresa2);
            PdfPCell cell7 = new PdfPCell(datosCliente3);
            PdfPCell cell8 = new PdfPCell(datosEmpresa3);
            PdfPCell cell9 = new PdfPCell(datosCliente4);
            PdfPCell cell10 = new PdfPCell(datosEmpresa4);
            PdfPCell cell11 = new PdfPCell(datosCliente5);
            PdfPCell cell12 = new PdfPCell(datosEmpresa5);
            
            cell1.setPadding(5);
            cell2.setPadding(5);
            cell3.setPadding(5);
            cell4.setPadding(5);
            cell5.setPadding(5);
            cell6.setPadding(5);
            cell7.setPadding(5);
            cell8.setPadding(5);
            cell9.setPadding(5);
            cell10.setPadding(5);
            cell11.setPadding(5);
            cell12.setPadding(5);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
            table.addCell(cell7);
            table.addCell(cell8);
            table.addCell(cell9);
            table.addCell(cell10);
            table.addCell(cell11);
            table.addCell(cell12);
            
            //Tabla con los datos del pedido
            PdfPTable table2 = new PdfPTable(5);
            
            float[] medidaCeldas = {0.55f,0.55f, 2.25f, 0.55f, 0.55f};
            
            table2.setWidths(medidaCeldas);

            table2.getDefaultCell().setBorder(Rectangle.NO_BORDER);//celdas sin bordes
            table2.setHorizontalAlignment(Element.ALIGN_LEFT);//alineamos la tabla a la izquierda
            table2.setWidthPercentage(100);//porcentaje que ocupa la tabla
            
            Phrase cabeceraPedidoCantidad = new Phrase("Cantidad", fuentePedidoCabecera);
            Phrase cabeceraPedidoTipo = new Phrase("Tipo", fuentePedidoCabecera);
            Phrase cabeceraPedidoNombre = new Phrase("Descripción", fuentePedidoCabecera);
            Phrase cabeceraPedidoPrecioUnidad = new Phrase("Precio/   Unidad", fuentePedidoCabecera);
            Phrase cabeceraPedidoPrecioTotal = new Phrase("Importe" , fuentePedidoCabecera);
            
            PdfPCell cell1T2 = new PdfPCell(cabeceraPedidoCantidad);
            PdfPCell cell2T2 = new PdfPCell(cabeceraPedidoTipo);
            PdfPCell cell3T2 = new PdfPCell(cabeceraPedidoNombre);
            PdfPCell cell4T2 = new PdfPCell(cabeceraPedidoPrecioUnidad);
            PdfPCell cell5T2 = new PdfPCell(cabeceraPedidoPrecioTotal);
            
            cell1T2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1T2.setPadding(6);
            cell2T2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2T2.setPadding(6);
            cell3T2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3T2.setPadding(6);
            cell4T2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell4T2.setVerticalAlignment(Element.ALIGN_CENTER);
            cell5T2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell5T2.setPadding(6);
            
            table2.addCell(cell1T2);
            table2.addCell(cell2T2);
            table2.addCell(cell3T2);
            table2.addCell(cell4T2);
            table2.addCell(cell5T2);
            
            float subTotal=(float) 0.0;

            for (int i = 0; i < listaPedido.size(); i++) {
                Pedido ped=(Pedido)listaPedido.get(i);
                
                String cantidad = String.valueOf(ped.getCantidad());
                Phrase  datoPedido = new Phrase(cantidad, fuentePedido);
                PdfPCell cellX1 = new PdfPCell(datoPedido);
                cellX1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table2.addCell(cellX1);
                
                String tipo = saberTipo(ped.getTipo());
                Phrase  datoPedido2 = new Phrase(tipo, fuentePedido);
                PdfPCell cellX2 = new PdfPCell(datoPedido2);
                cellX2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table2.addCell(cellX2);
                
                String nombre = ped.getCodigoTelefono().getNombre();
                if(tipo.equals("Reparación")){
                    String reparación = ped.getCodigoReparacion().getNombre();
                    nombre=nombre.concat(" : "+reparación);
                }
                Phrase  datoPedido3 = new Phrase(nombre, fuentePedido);
                PdfPCell cellX3 = new PdfPCell(datoPedido3);
                cellX3.setHorizontalAlignment(Element.ALIGN_CENTER);
                table2.addCell(cellX3);
                
                String importe =String.valueOf(ped.getPrecio());
                if(tipo.equals("Venta")){
                    importe =String.valueOf(-ped.getPrecio());
                }
                Phrase  datoPedido4 = new Phrase(importe, fuentePedido);
                PdfPCell cellX4 = new PdfPCell(datoPedido4);
                cellX4.setHorizontalAlignment(Element.ALIGN_CENTER);
                 table2.addCell(cellX4);
                 
                 
                
                String importeTotal =String.valueOf(ped.getPrecio()*ped.getCantidad());
                if(tipo.equals("Venta")){
                    importeTotal =String.valueOf(-ped.getPrecio()*ped.getCantidad());
                }
                Phrase  datoPedido5 = new Phrase(importeTotal, fuentePedido);
                PdfPCell cellX5 = new PdfPCell(datoPedido5);
                cellX5.setHorizontalAlignment(Element.ALIGN_CENTER);
                table2.addCell(cellX5);
                
                 if(tipo.equals("Venta")){
                    subTotal=subTotal-(ped.getPrecio()*ped.getCantidad());
                }
                 else{
                subTotal=subTotal+ped.getPrecio()*ped.getCantidad();
                 }
                
            }
            
            Phrase cabeceraPedidoBlanco1= new Phrase("", fuentePedidoCabecera);
            Phrase cabeceraPedidoBlanco2= new Phrase("", fuentePedidoCabecera);
            Phrase cabeceraPedidoBlanco3= new Phrase("", fuentePedidoCabecera);
            Phrase cabeceraPedidoSubTotal= new Phrase("Subtotal", fuentePedidoCabecera);
            Phrase cabeceraPedidoIva= new Phrase("Iva", fuentePedidoCabecera);
            Phrase cabeceraPedidoTotal= new Phrase("Total", fuentePedidoCabecera);
            
            float iva=(float) 0.21;
            float subTotalIva=(subTotal*iva);
            float total=subTotal;
            subTotal=subTotal-subTotalIva;
            
            Phrase cabeceraPedidoSubTotalNumero = new Phrase(String.valueOf(subTotal), fuentePedido);
            Phrase cabeceraPedidoIvaNumero = new Phrase(String.valueOf(subTotalIva), fuentePedido);
            Phrase cabeceraPedidoTotalNumero = new Phrase(String.valueOf(total), fuentePedido
            );
            
            
            PdfPCell cell1T6 = new PdfPCell(cabeceraPedidoBlanco1);
            PdfPCell cell2T7 = new PdfPCell(cabeceraPedidoBlanco2);
            PdfPCell cell3T8 = new PdfPCell(cabeceraPedidoBlanco3);
            
            PdfPCell cell4T9 = new PdfPCell(cabeceraPedidoSubTotal);
            PdfPCell cell5T10 = new PdfPCell(cabeceraPedidoSubTotalNumero);
            
            
            PdfPCell cell4T11 = new PdfPCell(cabeceraPedidoIva);
            PdfPCell cell5T12 = new PdfPCell(cabeceraPedidoIvaNumero);
            
             PdfPCell cell4T13 = new PdfPCell(cabeceraPedidoTotal);
            PdfPCell cell5T14 = new PdfPCell(cabeceraPedidoTotalNumero);
            
            table2.addCell(cell1T6);
            table2.addCell(cell2T7);
            table2.addCell(cell3T8);
            table2.addCell(cell4T9);
            table2.addCell(cell5T10);
            
            table2.addCell(cell1T6);
            table2.addCell(cell2T7);
            table2.addCell(cell3T8);
            table2.addCell(cell4T11);
            table2.addCell(cell5T12);
            
            table2.addCell(cell1T6);
            table2.addCell(cell2T7);
            table2.addCell(cell3T8);
            table2.addCell(cell4T13);
            table2.addCell(cell5T14);
            
            //Añadir a documento
            document.add(img);
            document.add(logo);
            document.add(cabecera);
            document.add(espacio);
            document.add(table);
            document.add(espacio2);
            document.add(table2);
            
        } catch (DocumentException ex) {
            Logger.getLogger(bUsuarioGestionPedido.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(bUsuarioGestionPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
            document.close();
        
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        // setting the content type
        response.setContentType("application/pdf");
        // the contentlength
        response.setContentLength(baos.size());
        // write ByteArrayOutputStream to the ServletOutputStream
        try {
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        context.responseComplete();

    }
    public float conocerPrecioPedido(int codigoPedido){
        
        List listaPedido = ctrPedido.findListaPedidoByCodigoPedido(codigoPedido);
        
        float precio=(float) 0.0;
        for (int i = 0; i < listaPedido.size(); i++) {
            Pedido ped =(Pedido) listaPedido.get(i);
            precio=precio+ped.getPrecio();
        }
        
        return precio;
    }
    

}
