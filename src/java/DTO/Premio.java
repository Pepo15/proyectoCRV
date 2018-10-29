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
@Table(name = "premio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Premio.findAll", query = "SELECT p FROM Premio p")
    , @NamedQuery(name = "Premio.findByCodigoPremio", query = "SELECT p FROM Premio p WHERE p.codigoPremio = :codigoPremio")
    , @NamedQuery(name = "Premio.findByNombre", query = "SELECT p FROM Premio p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Premio.findByCoste", query = "SELECT p FROM Premio p WHERE p.coste = :coste")})
public class Premio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoPremio")
    private Integer codigoPremio;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "coste")
    private int coste;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoPremio")
    private List<Canjear> canjearList;
    @OneToMany(mappedBy = "codigoPremio")
    private List<Foto> fotoList;

    public Premio() {
    }

    public Premio(Integer codigoPremio) {
        this.codigoPremio = codigoPremio;
    }

    public Premio(Integer codigoPremio, String nombre, int coste) {
        this.codigoPremio = codigoPremio;
        this.nombre = nombre;
        this.coste = coste;
    }

    public Integer getCodigoPremio() {
        return codigoPremio;
    }

    public void setCodigoPremio(Integer codigoPremio) {
        this.codigoPremio = codigoPremio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCoste() {
        return coste;
    }

    public void setCoste(int coste) {
        this.coste = coste;
    }

    @XmlTransient
    public List<Canjear> getCanjearList() {
        return canjearList;
    }

    public void setCanjearList(List<Canjear> canjearList) {
        this.canjearList = canjearList;
    }

    @XmlTransient
    public List<Foto> getFotoList() {
        return fotoList;
    }

    public void setFotoList(List<Foto> fotoList) {
        this.fotoList = fotoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoPremio != null ? codigoPremio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Premio)) {
            return false;
        }
        Premio other = (Premio) object;
        if ((this.codigoPremio == null && other.codigoPremio != null) || (this.codigoPremio != null && !this.codigoPremio.equals(other.codigoPremio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Premio[ codigoPremio=" + codigoPremio + " ]";
    }
    
}
