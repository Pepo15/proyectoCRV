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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pedro
 */
@Entity
@Table(name = "premioconfoto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Premioconfoto.findAll", query = "SELECT p FROM Premioconfoto p")
    , @NamedQuery(name = "Premioconfoto.findByCodigoPremio", query = "SELECT p FROM Premioconfoto p WHERE p.codigoPremio = :codigoPremio")
    , @NamedQuery(name = "Premioconfoto.findByNombre", query = "SELECT p FROM Premioconfoto p WHERE p.nombre = :nombre")})
public class Premioconfoto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "codigoPremio")
    @Id
    private int codigoPremio;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    public Premioconfoto() {
    }

    public int getCodigoPremio() {
        return codigoPremio;
    }

    public void setCodigoPremio(int codigoPremio) {
        this.codigoPremio = codigoPremio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
