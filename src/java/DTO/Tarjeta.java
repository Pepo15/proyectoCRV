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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tarjeta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tarjeta.findAll", query = "SELECT t FROM Tarjeta t")
    , @NamedQuery(name = "Tarjeta.findByCodigoTarjeta", query = "SELECT t FROM Tarjeta t WHERE t.codigoTarjeta = :codigoTarjeta")
    , @NamedQuery(name = "Tarjeta.findByNumeroTarjeta", query = "SELECT t FROM Tarjeta t WHERE t.numeroTarjeta = :numeroTarjeta")
    , @NamedQuery(name = "Tarjeta.findByFechaCaducidad", query = "SELECT t FROM Tarjeta t WHERE t.fechaCaducidad = :fechaCaducidad")
    , @NamedQuery(name = "Tarjeta.findByCvv", query = "SELECT t FROM Tarjeta t WHERE t.cvv = :cvv")})
public class Tarjeta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoTarjeta")
    private Integer codigoTarjeta;
    @Basic(optional = false)
    @Column(name = "numeroTarjeta")
    private String numeroTarjeta;
    @Basic(optional = false)
    @Column(name = "fechaCaducidad")
    private int fechaCaducidad;
    @Basic(optional = false)
    @Column(name = "cvv")
    private int cvv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoTarjeta")
    private List<Pedido> pedidoList;
    @JoinColumn(name = "codigoUsuario", referencedColumnName = "codigoUsuario")
    @ManyToOne(optional = false)
    private Usuario codigoUsuario;

    public Tarjeta() {
    }

    public Tarjeta(Integer codigoTarjeta) {
        this.codigoTarjeta = codigoTarjeta;
    }

    public Tarjeta(Integer codigoTarjeta, String numeroTarjeta, int fechaCaducidad, int cvv) {
        this.codigoTarjeta = codigoTarjeta;
        this.numeroTarjeta = numeroTarjeta;
        this.fechaCaducidad = fechaCaducidad;
        this.cvv = cvv;
    }

    public Integer getCodigoTarjeta() {
        return codigoTarjeta;
    }

    public void setCodigoTarjeta(Integer codigoTarjeta) {
        this.codigoTarjeta = codigoTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public int getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(int fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    @XmlTransient
    public List<Pedido> getPedidoList() {
        return pedidoList;
    }

    public void setPedidoList(List<Pedido> pedidoList) {
        this.pedidoList = pedidoList;
    }

    public Usuario getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Usuario codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoTarjeta != null ? codigoTarjeta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tarjeta)) {
            return false;
        }
        Tarjeta other = (Tarjeta) object;
        if ((this.codigoTarjeta == null && other.codigoTarjeta != null) || (this.codigoTarjeta != null && !this.codigoTarjeta.equals(other.codigoTarjeta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Tarjeta[ codigoTarjeta=" + codigoTarjeta + " ]";
    }
    
}
