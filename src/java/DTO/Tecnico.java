/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Pedro
 */
@Entity
@Table(name = "tecnico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tecnico.findAll", query = "SELECT t FROM Tecnico t")
    , @NamedQuery(name = "Tecnico.findByCodigoTecnico", query = "SELECT t FROM Tecnico t WHERE t.codigoTecnico = :codigoTecnico")
    , @NamedQuery(name = "Tecnico.findByNick", query = "SELECT t FROM Tecnico t WHERE t.nick = :nick")
    , @NamedQuery(name = "Tecnico.findByPassword", query = "SELECT t FROM Tecnico t WHERE t.password = :password")})
public class Tecnico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoTecnico")
    private Integer codigoTecnico;
    @Basic(optional = false)
    @Column(name = "nick")
    private String nick;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "codigoTecnico")
    private List<Pedido> pedidoList;
    @JoinColumn(name = "codigoAdministrador", referencedColumnName = "codigoAdministrador")
    @ManyToOne(optional = false)
    private Administrador codigoAdministrador;

    public Tecnico() {
    }

    public Tecnico(Integer codigoTecnico) {
        this.codigoTecnico = codigoTecnico;
    }

    public Tecnico(Integer codigoTecnico, String nick, String password) {
        this.codigoTecnico = codigoTecnico;
        this.nick = nick;
        this.password = password;
    }

    public Integer getCodigoTecnico() {
        return codigoTecnico;
    }

    public void setCodigoTecnico(Integer codigoTecnico) {
        this.codigoTecnico = codigoTecnico;
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
        hash += (codigoTecnico != null ? codigoTecnico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tecnico)) {
            return false;
        }
        Tecnico other = (Tecnico) object;
        if ((this.codigoTecnico == null && other.codigoTecnico != null) || (this.codigoTecnico != null && !this.codigoTecnico.equals(other.codigoTecnico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Tecnico[ codigoTecnico=" + codigoTecnico + " ]";
    }
    
}
