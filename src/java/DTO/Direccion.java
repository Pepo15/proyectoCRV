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
@Table(name = "direccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Direccion.findAll", query = "SELECT d FROM Direccion d")
    , @NamedQuery(name = "Direccion.findByCodigoDireccion", query = "SELECT d FROM Direccion d WHERE d.codigoDireccion = :codigoDireccion")
    , @NamedQuery(name = "Direccion.findByCodigoProvincia", query = "SELECT d FROM Direccion d WHERE d.codigoProvincia = :codigoProvincia")
    , @NamedQuery(name = "Direccion.findByCodigoPoblacion", query = "SELECT d FROM Direccion d WHERE d.codigoPoblacion = :codigoPoblacion")
    , @NamedQuery(name = "Direccion.findByNombre", query = "SELECT d FROM Direccion d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "Direccion.findByTelefono", query = "SELECT d FROM Direccion d WHERE d.telefono = :telefono")})
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoDireccion")
    private Integer codigoDireccion;
    @Basic(optional = false)
    @Column(name = "codigoProvincia")
    private int codigoProvincia;
    @Basic(optional = false)
    @Column(name = "codigoPoblacion")
    private int codigoPoblacion;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "telefono")
    private int telefono;
    @JoinColumn(name = "codigoUsuario", referencedColumnName = "codigoUsuario")
    @ManyToOne(optional = false)
    private Usuario codigoUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoDireccion")
    private List<Canjear> canjearList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoDireccion")
    private List<Pedido> pedidoList;

    public Direccion() {
    }

    public Direccion(Integer codigoDireccion) {
        this.codigoDireccion = codigoDireccion;
    }

    public Direccion(Integer codigoDireccion, int codigoProvincia, int codigoPoblacion, String nombre, int telefono) {
        this.codigoDireccion = codigoDireccion;
        this.codigoProvincia = codigoProvincia;
        this.codigoPoblacion = codigoPoblacion;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public Integer getCodigoDireccion() {
        return codigoDireccion;
    }

    public void setCodigoDireccion(Integer codigoDireccion) {
        this.codigoDireccion = codigoDireccion;
    }

    public int getCodigoProvincia() {
        return codigoProvincia;
    }

    public void setCodigoProvincia(int codigoProvincia) {
        this.codigoProvincia = codigoProvincia;
    }

    public int getCodigoPoblacion() {
        return codigoPoblacion;
    }

    public void setCodigoPoblacion(int codigoPoblacion) {
        this.codigoPoblacion = codigoPoblacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public Usuario getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Usuario codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    @XmlTransient
    public List<Canjear> getCanjearList() {
        return canjearList;
    }

    public void setCanjearList(List<Canjear> canjearList) {
        this.canjearList = canjearList;
    }

    @XmlTransient
    public List<Pedido> getPedidoList() {
        return pedidoList;
    }

    public void setPedidoList(List<Pedido> pedidoList) {
        this.pedidoList = pedidoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoDireccion != null ? codigoDireccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Direccion)) {
            return false;
        }
        Direccion other = (Direccion) object;
        if ((this.codigoDireccion == null && other.codigoDireccion != null) || (this.codigoDireccion != null && !this.codigoDireccion.equals(other.codigoDireccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Direccion[ codigoDireccion=" + codigoDireccion + " ]";
    }
    
}
