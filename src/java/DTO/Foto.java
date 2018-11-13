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
@Table(name = "foto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Foto.findAll", query = "SELECT f FROM Foto f")
    , @NamedQuery(name = "Foto.findByCodigoTelefono", query = "SELECT f FROM Foto f WHERE f.codigoTelefono = :codigoTelefono")
    , @NamedQuery(name = "Foto.findByCodigoPremio", query = "SELECT f FROM Foto f WHERE f.codigoPremio = :codigoPremio")
    , @NamedQuery(name = "Foto.findByCodigoFoto", query = "SELECT f FROM Foto f WHERE f.codigoFoto = :codigoFoto")
    , @NamedQuery(name = "Foto.findByNombre", query = "SELECT f FROM Foto f WHERE f.nombre = :nombre")})
public class Foto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoFoto")
    private Integer codigoFoto;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "codigoTelefono", referencedColumnName = "codigoTelefono")
    @ManyToOne
    private Telefono codigoTelefono;
    @JoinColumn(name = "codigoPremio", referencedColumnName = "codigoPremio")
    @ManyToOne
    private Premio codigoPremio;

    public Foto() {
    }

    public Foto(Integer codigoFoto) {
        this.codigoFoto = codigoFoto;
    }

    public Foto(Integer codigoFoto, String nombre) {
        this.codigoFoto = codigoFoto;
        this.nombre = nombre;
    }

    public Integer getCodigoFoto() {
        return codigoFoto;
    }

    public void setCodigoFoto(Integer codigoFoto) {
        this.codigoFoto = codigoFoto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Telefono getCodigoTelefono() {
        return codigoTelefono;
    }

    public void setCodigoTelefono(Telefono codigoTelefono) {
        this.codigoTelefono = codigoTelefono;
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
        hash += (codigoFoto != null ? codigoFoto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Foto)) {
            return false;
        }
        Foto other = (Foto) object;
        if ((this.codigoFoto == null && other.codigoFoto != null) || (this.codigoFoto != null && !this.codigoFoto.equals(other.codigoFoto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Foto[ codigoFoto=" + codigoFoto + " ]";
    }
    
}
