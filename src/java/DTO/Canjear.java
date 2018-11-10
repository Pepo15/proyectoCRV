/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pedro
 */
@Entity
@Table(name = "canjear")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Canjear.findAll", query = "SELECT c FROM Canjear c")
    , @NamedQuery(name = "Canjear.findByCodigoCanjear", query = "SELECT c FROM Canjear c WHERE c.codigoCanjear = :codigoCanjear")
    , @NamedQuery(name = "Canjear.findByCostePuntos", query = "SELECT c FROM Canjear c WHERE c.costePuntos = :costePuntos")
    , @NamedQuery(name = "Canjear.findByFecha", query = "SELECT c FROM Canjear c WHERE c.fecha = :fecha")
    , @NamedQuery(name = "Canjear.findByEstado", query = "SELECT c FROM Canjear c WHERE c.estado = :estado")})
public class Canjear implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoCanjear")
    private Integer codigoCanjear;
    @Basic(optional = false)
    @Column(name = "costePuntos")
    private int costePuntos;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "estado")
    private int estado;
    @JoinColumn(name = "codigoUsuario", referencedColumnName = "codigoUsuario")
    @ManyToOne(optional = false)
    private Usuario codigoUsuario;
    @JoinColumn(name = "codigoDireccion", referencedColumnName = "codigoDireccion")
    @ManyToOne(optional = false)
    private Direccion codigoDireccion;
    @JoinColumn(name = "codigoPremio", referencedColumnName = "codigoPremio")
    @ManyToOne
    private Premio codigoPremio;

    public Canjear() {
    }

    public Canjear(Integer codigoCanjear) {
        this.codigoCanjear = codigoCanjear;
    }

    public Canjear(Integer codigoCanjear, int costePuntos, Date fecha, int estado) {
        this.codigoCanjear = codigoCanjear;
        this.costePuntos = costePuntos;
        this.fecha = fecha;
        this.estado = estado;
    }

    public Integer getCodigoCanjear() {
        return codigoCanjear;
    }

    public void setCodigoCanjear(Integer codigoCanjear) {
        this.codigoCanjear = codigoCanjear;
    }

    public int getCostePuntos() {
        return costePuntos;
    }

    public void setCostePuntos(int costePuntos) {
        this.costePuntos = costePuntos;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Usuario getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Usuario codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public Direccion getCodigoDireccion() {
        return codigoDireccion;
    }

    public void setCodigoDireccion(Direccion codigoDireccion) {
        this.codigoDireccion = codigoDireccion;
    }

    public Premio getCodigoPremio() {
        return codigoPremio;
    }

    public void setCodigoPremio(Premio codigoPremio) {
        this.codigoPremio = codigoPremio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoCanjear != null ? codigoCanjear.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Canjear)) {
            return false;
        }
        Canjear other = (Canjear) object;
        if ((this.codigoCanjear == null && other.codigoCanjear != null) || (this.codigoCanjear != null && !this.codigoCanjear.equals(other.codigoCanjear))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Canjear[ codigoCanjear=" + codigoCanjear + " ]";
    }
    
}
