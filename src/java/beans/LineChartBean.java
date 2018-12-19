/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import DAO.PedidoJpaController;
import DTO.Pedido;
import DTO.Telefono;
import java.util.List;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ManagedBean
@ViewScoped
public class LineChartBean {
    private LineChartModel lineModel;
     //Driver para conectar con la base de datos
    private EntityManagerFactory emf;

    private PedidoJpaController ctrPedido;

    

    @PostConstruct
    public void init() {
        lineModel = new LineChartModel();
        LineChartSeries s = new LineChartSeries();
        emf = Persistence.createEntityManagerFactory("CRVPU");
        ctrPedido = new PedidoJpaController(emf);
         List lista= ctrPedido.findPedidoEntities();
         int compras = 0;
         int ventas = 0;
         int reparaciones = 0;
         s.setLabel("Cantidad");
         for (int i = 0; i < lista.size(); i++) {
            Pedido ped= (Pedido) lista.get(i);
            if(ped.getTipo()==1){
                compras++;
            }
            if(ped.getTipo()==2){
                reparaciones++;
            }
            if(ped.getTipo()==3){
                ventas++;
            }
        }
          s.set("1", compras);
          s.set("2", reparaciones);
          s.set("3", ventas);
        lineModel.addSeries(s);
        lineModel.setLegendPosition("e");
        Axis y = lineModel.getAxis(AxisType.Y);
        y.setMin(0);
        y.setMax(100);
        y.setTickInterval("10");
        y.setLabel("Cantidad");

        Axis x = lineModel.getAxis(AxisType.X);
        x.setMin(1);
        x.setMax(3);
        x.setTickInterval("1");
        x.setLabel("Compra:1, Reparacion:2, Venta:3");

    }

    public LineChartModel getLineModel() {
        return lineModel;
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
    
    
}