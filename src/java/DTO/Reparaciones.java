/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Pedro
 */
@Entity
@Table(name = "reparaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reparaciones.findAll", query = "SELECT r FROM Reparaciones r")
    , @NamedQuery(name = "Reparaciones.findByCodigoReparacion", query = "SELECT r FROM Reparaciones r WHERE r.codigoReparacion = :codigoReparacion")
    , @NamedQuery(name = "Reparaciones.findByNombre", query = "SELECT r FROM Reparaciones r WHERE r.nombre = :nombre")})
public class Reparaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoReparacion")
    private Integer codigoReparacion;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "codigoReparacion")
    private List<Pedido> pedidoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoReparacion")
    private List<Reparacionestelefono> reparacionestelefonoList;

    public Reparaciones() {
    }

    public Reparaciones(Integer codigoReparacion) {
        this.codigoReparacion = codigoReparacion;
    }

    public Reparaciones(Integer codigoReparacion, String nombre) {
        this.codigoReparacion = codigoReparacion;
        this.nombre = nombre;
    }

    public Integer getCodigoReparacion() {
        return codigoReparacion;
    }

    public void setCodigoReparacion(Integer codigoReparacion) {
        this.codigoReparacion = codigoReparacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Pedido> getPedidoList() {
        return pedidoList;
    }

    public void setPedidoList(List<Pedido> pedidoList) {
        this.pedidoList = pedidoList;
    }

    @XmlTransient
    public List<Reparacionestelefono> getReparacionestelefonoList() {
        return reparacionestelefonoList;
    }

    public void setReparacionestelefonoList(List<Reparacionestelefono> reparacionestelefonoList) {
        this.reparacionestelefonoList = reparacionestelefonoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoReparacion != null ? codigoReparacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reparaciones)) {
            return false;
        }
        Reparaciones other = (Reparaciones) object;
        if ((this.codigoReparacion == null && other.codigoReparacion != null) || (this.codigoReparacion != null && !this.codigoReparacion.equals(other.codigoReparacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Reparaciones[ codigoReparacion=" + codigoReparacion + " ]";
    }
    
}
