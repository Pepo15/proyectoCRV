/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import DAO.PedidoJpaController;
import DTO.Pedido;
import java.util.List;
import org.primefaces.model.chart.PieChartModel;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ManagedBean
public class JobMarketBean {
    private PieChartModel model;
     //Driver para conectar con la base de datos
    private EntityManagerFactory emf;

    private PedidoJpaController ctrPedido;


    @PostConstruct
    public void init() {
        model = new PieChartModel();
        emf = Persistence.createEntityManagerFactory("CRVPU");
        ctrPedido = new PedidoJpaController(emf);
         List lista= ctrPedido.findPedidoEntities();
         float compras = 0;
         float ventas = 0;
         float reparaciones = 0;
         for (int i = 0; i < lista.size(); i++) {
            Pedido ped= (Pedido) lista.get(i);
            if(ped.getTipo()==1){
                compras=compras+ped.getPrecio();
            }
            if(ped.getTipo()==2){
               reparaciones=reparaciones+ped.getPrecio();
            }
            if(ped.getTipo()==3){
                ventas=ventas+ped.getPrecio();
            }
        }
        model.set("Compras", compras);//jobs in thousands
        model.set("Reparaciones", reparaciones);
        model.set("Ventas", ventas);

        //followings are some optional customizations:
        //set title
        model.setTitle("Dinero recaudado por sección:");
        //set legend position to 'e' (east), other values are 'w', 's' and 'n'
        model.setLegendPosition("e");
        //enable tooltips
        model.setShowDatatip(true);
        //show labels inside pie chart
        model.setShowDataLabels(true);
        //show label text  as 'value' (numeric) , others are 'label', 'percent' (default). Only one can be used.
        model.setDataFormat("value");
        //format: %d for 'value', %s for 'label', %d%% for 'percent'
        model.setDataLabelFormatString("%s€");
        //pie sector colors
        model.setSeriesColors("aaf,afa,faa");
    }

    public PieChartModel getModel() {
        return model;
    }
}