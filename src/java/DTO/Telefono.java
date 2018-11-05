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
@Table(name = "telefono")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Telefono.findAll", query = "SELECT t FROM Telefono t")
    , @NamedQuery(name = "Telefono.findByCodigoTelefono", query = "SELECT t FROM Telefono t WHERE t.codigoTelefono = :codigoTelefono")
    , @NamedQuery(name = "Telefono.findByNombre", query = "SELECT t FROM Telefono t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "Telefono.findByMarca", query = "SELECT t FROM Telefono t WHERE t.marca = :marca")
    , @NamedQuery(name = "Telefono.findByPrecio", query = "SELECT t FROM Telefono t WHERE t.precio = :precio")})
public class Telefono implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoTelefono")
    private Integer codigoTelefono;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "marca")
    private String marca;
    @Basic(optional = false)
    @Column(name = "precio")
    private float precio;
    @OneToMany(mappedBy = "codigoTelefono")
    private List<Foto> fotoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoTelefono")
    private List<Reparaciones> reparacionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoTelefono")
    private List<Caracteristicastelefono> caracteristicastelefonoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoTelefono")
    private List<Pedido> pedidoList;
    @JoinColumn(name = "codigoAdministrador", referencedColumnName = "codigoAdministrador")
    @ManyToOne(optional = false)
    private Administrador codigoAdministrador;

    public Telefono() {
    }

    public Telefono(Integer codigoTelefono) {
        this.codigoTelefono = codigoTelefono;
    }

    public Telefono(Integer codigoTelefono, String nombre, String marca, float precio) {
        this.codigoTelefono = codigoTelefono;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
    }

    public Integer getCodigoTelefono() {
        return codigoTelefono;
    }

    public void setCodigoTelefono(Integer codigoTelefono) {
        this.codigoTelefono = codigoTelefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @XmlTransient
    public List<Foto> getFotoList() {
        return fotoList;
    }

    public void setFotoList(List<Foto> fotoList) {
        this.fotoList = fotoList;
    }

    @XmlTransient
    public List<Reparaciones> getReparacionesList() {
        return reparacionesList;
    }

    public void setReparacionesList(List<Reparaciones> reparacionesList) {
        this.reparacionesList = reparacionesList;
    }

    @XmlTransient
    public List<Caracteristicastelefono> getCaracteristicastelefonoList() {
        return caracteristicastelefonoList;
    }

    public void setCaracteristicastelefonoList(List<Caracteristicastelefono> caracteristicastelefonoList) {
        this.caracteristicastelefonoList = caracteristicastelefonoList;
    }

    @XmlTransient
    public List<Pedido> getPedidoList() {
        return pedidoList;
    }

    public void setPedidoList(List<Pedido> pedidoList) {
        this.pedidoList = pedidoList;
    }

    public Administrador getCodigoAdministrador() {
        return codigoAdministrador;
    }

    public void setCodigoAdministrador(Administrador codigoAdministrador) {
        this.codigoAdministrador = codigoAdministrador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoTelefono != null ? codigoTelefono.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Telefono)) {
            return false;
        }
        Telefono other = (Telefono) object;
        if ((this.codigoTelefono == null && other.codigoTelefono != null) || (this.codigoTelefono != null && !this.codigoTelefono.equals(other.codigoTelefono))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Telefono[ codigoTelefono=" + codigoTelefono + " ]";
    }
    
}
