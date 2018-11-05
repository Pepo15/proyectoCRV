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
@Table(name = "pedido")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pedido.findAll", query = "SELECT p FROM Pedido p")
    , @NamedQuery(name = "Pedido.findByCodigo", query = "SELECT p FROM Pedido p WHERE p.codigo = :codigo")
    , @NamedQuery(name = "Pedido.findByCodigoPedido", query = "SELECT p FROM Pedido p WHERE p.codigoPedido = :codigoPedido")
    , @NamedQuery(name = "Pedido.findByCodigoPoblacion", query = "SELECT p FROM Pedido p WHERE p.codigoPoblacion = :codigoPoblacion")
    , @NamedQuery(name = "Pedido.findByTipo", query = "SELECT p FROM Pedido p WHERE p.tipo = :tipo")
    , @NamedQuery(name = "Pedido.findByPrecio", query = "SELECT p FROM Pedido p WHERE p.precio = :precio")
    , @NamedQuery(name = "Pedido.findByCantidad", query = "SELECT p FROM Pedido p WHERE p.cantidad = :cantidad")
    , @NamedQuery(name = "Pedido.findByRotura", query = "SELECT p FROM Pedido p WHERE p.rotura = :rotura")
    , @NamedQuery(name = "Pedido.findByFuncional", query = "SELECT p FROM Pedido p WHERE p.funcional = :funcional")
    , @NamedQuery(name = "Pedido.findByEstado", query = "SELECT p FROM Pedido p WHERE p.estado = :estado")
    , @NamedQuery(name = "Pedido.findByFecha", query = "SELECT p FROM Pedido p WHERE p.fecha = :fecha")})
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "codigoPedido")
    private int codigoPedido;
    @Basic(optional = false)
    @Column(name = "codigoPoblacion")
    private int codigoPoblacion;
    @Basic(optional = false)
    @Column(name = "tipo")
    private int tipo;
    @Basic(optional = false)
    @Column(name = "precio")
    private float precio;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    @Column(name = "rotura")
    private String rotura;
    @Column(name = "funcional")
    private Boolean funcional;
    @Basic(optional = false)
    @Column(name = "estado")
    private int estado;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "codigoTecnico", referencedColumnName = "codigoTecnico")
    @ManyToOne
    private Tecnico codigoTecnico;
    @JoinColumn(name = "codigoReparacion", referencedColumnName = "codigoReparacion")
    @ManyToOne
    private Reparaciones codigoReparacion;
    @JoinColumn(name = "codigoTelefono", referencedColumnName = "codigoTelefono")
    @ManyToOne(optional = false)
    private Telefono codigoTelefono;
    @JoinColumn(name = "codigoDireccion", referencedColumnName = "codigoDireccion")
    @ManyToOne(optional = false)
    private Direccion codigoDireccion;
    @JoinColumn(name = "codigoTarjeta", referencedColumnName = "codigoTarjeta")
    @ManyToOne(optional = false)
    private Tarjeta codigoTarjeta;
    @JoinColumn(name = "codigoUsuario", referencedColumnName = "codigoUsuario")
    @ManyToOne(optional = false)
    private Usuario codigoUsuario;

    public Pedido() {
    }

    public Pedido(Integer codigo) {
        this.codigo = codigo;
    }

    public Pedido(Integer codigo, int codigoPedido, int codigoPoblacion, int tipo, float precio, int cantidad, int estado, Date fecha) {
        this.codigo = codigo;
        this.codigoPedido = codigoPedido;
        this.codigoPoblacion = codigoPoblacion;
        this.tipo = tipo;
        this.precio = precio;
        this.cantidad = cantidad;
        this.estado = estado;
        this.fecha = fecha;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public int getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(int codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public int getCodigoPoblacion() {
        return codigoPoblacion;
    }

    public void setCodigoPoblacion(int codigoPoblacion) {
        this.codigoPoblacion = codigoPoblacion;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getRotura() {
        return rotura;
    }

    public void setRotura(String rotura) {
        this.rotura = rotura;
    }

    public Boolean getFuncional() {
        return funcional;
    }

    public void setFuncional(Boolean funcional) {
        this.funcional = funcional;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Tecnico getCodigoTecnico() {
        return codigoTecnico;
    }

    public void setCodigoTecnico(Tecnico codigoTecnico) {
        this.codigoTecnico = codigoTecnico;
    }

    public Reparaciones getCodigoReparacion() {
        return codigoReparacion;
    }

    public void setCodigoReparacion(Reparaciones codigoReparacion) {
        this.codigoReparacion = codigoReparacion;
    }

    public Telefono getCodigoTelefono() {
        return codigoTelefono;
    }

    public void setCodigoTelefono(Telefono codigoTelefono) {
        this.codigoTelefono = codigoTelefono;
    }

    public Direccion getCodigoDireccion() {
        return codigoDireccion;
    }

    public void setCodigoDireccion(Direccion codigoDireccion) {
        this.codigoDireccion = codigoDireccion;
    }

    public Tarjeta getCodigoTarjeta() {
        return codigoTarjeta;
    }

    public void setCodigoTarjeta(Tarjeta codigoTarjeta) {
        this.codigoTarjeta = codigoTarjeta;
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
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedido)) {
            return false;
        }
        Pedido other = (Pedido) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Pedido[ codigo=" + codigo + " ]";
    }
    
}
