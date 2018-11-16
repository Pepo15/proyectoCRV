/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pedro
 */
@Entity
@Table(name = "reparacionestelefono")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reparacionestelefono.findAll", query = "SELECT r FROM Reparacionestelefono r")
    , @NamedQuery(name = "Reparacionestelefono.findByCodigoReparacionTelefono", query = "SELECT r FROM Reparacionestelefono r WHERE r.codigoReparacionTelefono = :codigoReparacionTelefono")
    , @NamedQuery(name = "Reparacionestelefono.findByCodigoTelefono", query = "SELECT r FROM Reparacionestelefono r WHERE r.codigoTelefono = :codigoTelefono")
    , @NamedQuery(name = "Reparacionestelefono.findByPrecio", query = "SELECT r FROM Reparacionestelefono r WHERE r.precio = :precio")})
public class Reparacionestelefono implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoReparacionTelefono")
    private Integer codigoReparacionTelefono;
    @Basic(optional = false)
    @Column(name = "precio")
    private int precio;
    @JoinColumn(name = "codigoTelefono", referencedColumnName = "codigoTelefono")
    @ManyToOne(optional = false)
    private Telefono codigoTelefono;
    @JoinColumn(name = "codigoReparacion", referencedColumnName = "codigoReparacion")
    @ManyToOne(optional = false)
    private Reparaciones codigoReparacion;

    public Reparacionestelefono() {
    }

    public Reparacionestelefono(Integer codigoReparacionTelefono) {
        this.codigoReparacionTelefono = codigoReparacionTelefono;
    }

    public Reparacionestelefono(Integer codigoReparacionTelefono, int precio) {
        this.codigoReparacionTelefono = codigoReparacionTelefono;
        this.precio = precio;
    }

    public Integer getCodigoReparacionTelefono() {
        return codigoReparacionTelefono;
    }

    public void setCodigoReparacionTelefono(Integer codigoReparacionTelefono) {
        this.codigoReparacionTelefono = codigoReparacionTelefono;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Telefono getCodigoTelefono() {
        return codigoTelefono;
    }

    public void setCodigoTelefono(Telefono codigoTelefono) {
        this.codigoTelefono = codigoTelefono;
    }

    public Reparaciones getCodigoReparacion() {
        return codigoReparacion;
    }

    public void setCodigoReparacion(Reparaciones codigoReparacion) {
        this.codigoReparacion = codigoReparacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoReparacionTelefono != null ? codigoReparacionTelefono.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reparacionestelefono)) {
            return false;
        }
        Reparacionestelefono other = (Reparacionestelefono) object;
        if ((this.codigoReparacionTelefono == null && other.codigoReparacionTelefono != null) || (this.codigoReparacionTelefono != null && !this.codigoReparacionTelefono.equals(other.codigoReparacionTelefono))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Reparacionestelefono[ codigoReparacionTelefono=" + codigoReparacionTelefono + " ]";
    }
    
}
