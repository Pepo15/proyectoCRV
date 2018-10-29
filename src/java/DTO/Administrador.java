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
@Table(name = "administrador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Administrador.findAll", query = "SELECT a FROM Administrador a")
    , @NamedQuery(name = "Administrador.findByCodigoAdministrador", query = "SELECT a FROM Administrador a WHERE a.codigoAdministrador = :codigoAdministrador")
    , @NamedQuery(name = "Administrador.findByNick", query = "SELECT a FROM Administrador a WHERE a.nick = :nick")
    , @NamedQuery(name = "Administrador.findByPassword", query = "SELECT a FROM Administrador a WHERE a.password = :password")})
public class Administrador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoAdministrador")
    private Integer codigoAdministrador;
    @Basic(optional = false)
    @Column(name = "nick")
    private String nick;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoAdministrador")
    private List<Tecnico> tecnicoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoAdministrador")
    private List<Telefono> telefonoList;

    public Administrador() {
    }

    public Administrador(Integer codigoAdministrador) {
        this.codigoAdministrador = codigoAdministrador;
    }

    public Administrador(Integer codigoAdministrador, String nick, String password) {
        this.codigoAdministrador = codigoAdministrador;
        this.nick = nick;
        this.password = password;
    }

    public Integer getCodigoAdministrador() {
        return codigoAdministrador;
    }

    public void setCodigoAdministrador(Integer codigoAdministrador) {
        this.codigoAdministrador = codigoAdministrador;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public List<Tecnico> getTecnicoList() {
        return tecnicoList;
    }

    public void setTecnicoList(List<Tecnico> tecnicoList) {
        this.tecnicoList = tecnicoList;
    }

    @XmlTransient
    public List<Telefono> getTelefonoList() {
        return telefonoList;
    }

    public void setTelefonoList(List<Telefono> telefonoList) {
        this.telefonoList = telefonoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoAdministrador != null ? codigoAdministrador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Administrador)) {
            return false;
        }
        Administrador other = (Administrador) object;
        if ((this.codigoAdministrador == null && other.codigoAdministrador != null) || (this.codigoAdministrador != null && !this.codigoAdministrador.equals(other.codigoAdministrador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Administrador[ codigoAdministrador=" + codigoAdministrador + " ]";
    }
    
}
