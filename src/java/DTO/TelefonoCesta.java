/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author Pedro
 */
public class TelefonoCesta {
    
    private Telefono telefono;
    private int tipo;
    private int codigoReparacion;
    private String rotura;
    private int funcional;
    private int cantidad;

    public TelefonoCesta(Telefono telefono, int tipo, int cantidad) {
        this.telefono = telefono;
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    public TelefonoCesta(Telefono telefono, int tipo, int codigoReparacion, String rotura, int cantidad) {
        this.telefono = telefono;
        this.tipo = tipo;
        this.codigoReparacion = codigoReparacion;
        this.rotura = rotura;
        this.cantidad = cantidad;
    }

    public TelefonoCesta(Telefono telefono, int tipo, int funcional, int cantidad) {
        this.telefono = telefono;
        this.tipo = tipo;
        this.funcional = funcional;
        this.cantidad = cantidad;
    }
    
    

    public int getCodigoReparacion() {
        return codigoReparacion;
    }

    public void setCodigoReparacion(int codigoReparacion) {
        this.codigoReparacion = codigoReparacion;
    }

    public String getRotura() {
        return rotura;
    }

    public void setRotura(String rotura) {
        this.rotura = rotura;
    }
    
    

    public Telefono getTelefono() {
        return telefono;
    }

    public void setTelefono(Telefono telefono) {
        this.telefono = telefono;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getFuncional() {
        return funcional;
    }

    public void setFuncional(int funcional) {
        this.funcional = funcional;
    }
    
    

    
    
    
}
