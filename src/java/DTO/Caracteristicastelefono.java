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
@Table(name = "caracteristicastelefono")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Caracteristicastelefono.findAll", query = "SELECT c FROM Caracteristicastelefono c")
    , @NamedQuery(name = "Caracteristicastelefono.findByCodigoTelefono", query = "SELECT c FROM Caracteristicastelefono c WHERE c.codigoTelefono = :codigoTelefono")
    , @NamedQuery(name = "Caracteristicastelefono.findByCodigoCaracteristica", query = "SELECT c FROM Caracteristicastelefono c WHERE c.codigoCaracteristica = :codigoCaracteristica")
    , @NamedQuery(name = "Caracteristicastelefono.findBySo", query = "SELECT c FROM Caracteristicastelefono c WHERE c.so = :so")
    ,@NamedQuery(name = "Caracteristicastelefono.findDistinctSO", query = "SELECT DISTINCT c.so FROM Caracteristicastelefono c")
    , @NamedQuery(name = "Caracteristicastelefono.findByRam", query = "SELECT c FROM Caracteristicastelefono c WHERE c.ram = :ram")
    ,@NamedQuery(name = "Caracteristicastelefono.findDistinctRam", query = "SELECT DISTINCT c.ram FROM Caracteristicastelefono c")
    , @NamedQuery(name = "Caracteristicastelefono.findByPulgadas", query = "SELECT c FROM Caracteristicastelefono c WHERE c.pulgadas = :pulgadas")
    ,@NamedQuery(name = "Caracteristicastelefono.findDistinctPulgadas", query = "SELECT DISTINCT c.pulgadas FROM Caracteristicastelefono c")
    , @NamedQuery(name = "Caracteristicastelefono.findByAlmacenamiento", query = "SELECT c FROM Caracteristicastelefono c WHERE c.almacenamiento = :almacenamiento")
    ,@NamedQuery(name = "Caracteristicastelefono.findDistinctAlmacenamiento", query = "SELECT DISTINCT c.almacenamiento FROM Caracteristicastelefono c")
    , @NamedQuery(name = "Caracteristicastelefono.findByCamaraTrasera", query = "SELECT c FROM Caracteristicastelefono c WHERE c.camaraTrasera = :camaraTrasera")
    ,@NamedQuery(name = "Caracteristicastelefono.findDistinctCamaraTrasera", query = "SELECT DISTINCT c.camaraTrasera FROM Caracteristicastelefono c")
    , @NamedQuery(name = "Caracteristicastelefono.findByCamaraDelantera", query = "SELECT c FROM Caracteristicastelefono c WHERE c.camaraDelantera = :camaraDelantera")
    ,@NamedQuery(name = "Caracteristicastelefono.findDistinctCamaraDelantera", query = "SELECT DISTINCT c.camaraDelantera FROM Caracteristicastelefono c")
    , @NamedQuery(name = "Caracteristicastelefono.findByBateria", query = "SELECT c FROM Caracteristicastelefono c WHERE c.bateria = :bateria")
    ,@NamedQuery(name = "Caracteristicastelefono.findDistinctBateria", query = "SELECT DISTINCT c.bateria FROM Caracteristicastelefono c")
    , @NamedQuery(name = "Caracteristicastelefono.findByProcesador", query = "SELECT c FROM Caracteristicastelefono c WHERE c.procesador = :procesador")
    ,@NamedQuery(name = "Caracteristicastelefono.findDistinctProcesador", query = "SELECT DISTINCT c.procesador FROM Caracteristicastelefono c")
    , @NamedQuery(name = "Caracteristicastelefono.findByWifi", query = "SELECT c FROM Caracteristicastelefono c WHERE c.wifi = :wifi")
    , @NamedQuery(name = "Caracteristicastelefono.findByResolucion", query = "SELECT c FROM Caracteristicastelefono c WHERE c.resolucion = :resolucion")
    ,@NamedQuery(name = "Caracteristicastelefono.findDistinctResolucion", query = "SELECT DISTINCT c.resolucion FROM Caracteristicastelefono c")
    , @NamedQuery(name = "Caracteristicastelefono.findByColor", query = "SELECT c FROM Caracteristicastelefono c WHERE c.color = :color")
    ,@NamedQuery(name = "Caracteristicastelefono.findDistinctColor", query = "SELECT DISTINCT c.color FROM Caracteristicastelefono c")
    , @NamedQuery(name = "Caracteristicastelefono.findByDetectorHuella", query = "SELECT c FROM Caracteristicastelefono c WHERE c.detectorHuella = :detectorHuella")
    , @NamedQuery(name = "Caracteristicastelefono.findByDualSim", query = "SELECT c FROM Caracteristicastelefono c WHERE c.dualSim = :dualSim")
    , @NamedQuery(name = "Caracteristicastelefono.findBySd", query = "SELECT c FROM Caracteristicastelefono c WHERE c.sd = :sd")
    , @NamedQuery(name = "Caracteristicastelefono.findByBluetooth", query = "SELECT c FROM Caracteristicastelefono c WHERE c.bluetooth = :bluetooth")
    , @NamedQuery(name = "Caracteristicastelefono.findByNfc", query = "SELECT c FROM Caracteristicastelefono c WHERE c.nfc = :nfc")
    , @NamedQuery(name = "Caracteristicastelefono.findByG", query = "SELECT c FROM Caracteristicastelefono c WHERE c.g = :g")
    , @NamedQuery(name = "Caracteristicastelefono.findByG1", query = "SELECT c FROM Caracteristicastelefono c WHERE c.g1 = :g1")})
public class Caracteristicastelefono implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoCaracteristica")
    private Integer codigoCaracteristica;
    @Basic(optional = false)
    @Column(name = "so")
    private String so;
    @Basic(optional = false)
    @Column(name = "ram")
    private int ram;
    @Basic(optional = false)
    @Column(name = "pulgadas")
    private float pulgadas;
    @Basic(optional = false)
    @Column(name = "almacenamiento")
    private int almacenamiento;
    @Basic(optional = false)
    @Column(name = "camaraTrasera")
    private float camaraTrasera;
    @Basic(optional = false)
    @Column(name = "camaraDelantera")
    private float camaraDelantera;
    @Basic(optional = false)
    @Column(name = "bateria")
    private int bateria;
    @Basic(optional = false)
    @Column(name = "procesador")
    private String procesador;
    @Basic(optional = false)
    @Column(name = "wifi")
    private boolean wifi;
    @Basic(optional = false)
    @Column(name = "resolucion")
    private String resolucion;
    @Basic(optional = false)
    @Column(name = "color")
    private String color;
    @Basic(optional = false)
    @Column(name = "detectorHuella")
    private boolean detectorHuella;
    @Basic(optional = false)
    @Column(name = "dualSim")
    private boolean dualSim;
    @Basic(optional = false)
    @Column(name = "sd")
    private boolean sd;
    @Basic(optional = false)
    @Column(name = "bluetooth")
    private boolean bluetooth;
    @Basic(optional = false)
    @Column(name = "nfc")
    private boolean nfc;
    @Basic(optional = false)
    @Column(name = "3g")
    private boolean g;
    @Basic(optional = false)
    @Column(name = "4g")
    private boolean g1;
    @JoinColumn(name = "codigoTelefono", referencedColumnName = "codigoTelefono")
    @ManyToOne(optional = false)
    private Telefono codigoTelefono;

    public Caracteristicastelefono() {
    }

    public Caracteristicastelefono(Integer codigoCaracteristica) {
        this.codigoCaracteristica = codigoCaracteristica;
    }

    public Caracteristicastelefono(Integer codigoCaracteristica, String so, int ram, float pulgadas, int almacenamiento, float camaraTrasera, float camaraDelantera, int bateria, String procesador, boolean wifi, String resolucion, String color, boolean detectorHuella, boolean dualSim, boolean sd, boolean bluetooth, boolean nfc, boolean g, boolean g1) {
        this.codigoCaracteristica = codigoCaracteristica;
        this.so = so;
        this.ram = ram;
        this.pulgadas = pulgadas;
        this.almacenamiento = almacenamiento;
        this.camaraTrasera = camaraTrasera;
        this.camaraDelantera = camaraDelantera;
        this.bateria = bateria;
        this.procesador = procesador;
        this.wifi = wifi;
        this.resolucion = resolucion;
        this.color = color;
        this.detectorHuella = detectorHuella;
        this.dualSim = dualSim;
        this.sd = sd;
        this.bluetooth = bluetooth;
        this.nfc = nfc;
        this.g = g;
        this.g1 = g1;
    }

    public Integer getCodigoCaracteristica() {
        return codigoCaracteristica;
    }

    public void setCodigoCaracteristica(Integer codigoCaracteristica) {
        this.codigoCaracteristica = codigoCaracteristica;
    }

    public String getSo() {
        return so;
    }

    public void setSo(String so) {
        this.so = so;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public float getPulgadas() {
        return pulgadas;
    }

    public void setPulgadas(float pulgadas) {
        this.pulgadas = pulgadas;
    }

    public int getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(int almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public float getCamaraTrasera() {
        return camaraTrasera;
    }

    public void setCamaraTrasera(float camaraTrasera) {
        this.camaraTrasera = camaraTrasera;
    }

    public float getCamaraDelantera() {
        return camaraDelantera;
    }

    public void setCamaraDelantera(float camaraDelantera) {
        this.camaraDelantera = camaraDelantera;
    }

    public int getBateria() {
        return bateria;
    }

    public void setBateria(int bateria) {
        this.bateria = bateria;
    }

    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public boolean getWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean getDetectorHuella() {
        return detectorHuella;
    }

    public void setDetectorHuella(boolean detectorHuella) {
        this.detectorHuella = detectorHuella;
    }

    public boolean getDualSim() {
        return dualSim;
    }

    public void setDualSim(boolean dualSim) {
        this.dualSim = dualSim;
    }

    public boolean getSd() {
        return sd;
    }

    public void setSd(boolean sd) {
        this.sd = sd;
    }

    public boolean getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(boolean bluetooth) {
        this.bluetooth = bluetooth;
    }

    public boolean getNfc() {
        return nfc;
    }

    public void setNfc(boolean nfc) {
        this.nfc = nfc;
    }

    public boolean getG() {
        return g;
    }

    public void setG(boolean g) {
        this.g = g;
    }

    public boolean getG1() {
        return g1;
    }

    public void setG1(boolean g1) {
        this.g1 = g1;
    }

    public Telefono getCodigoTelefono() {
        return codigoTelefono;
    }

    public void setCodigoTelefono(Telefono codigoTelefono) {
        this.codigoTelefono = codigoTelefono;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoCaracteristica != null ? codigoCaracteristica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caracteristicastelefono)) {
            return false;
        }
        Caracteristicastelefono other = (Caracteristicastelefono) object;
        if ((this.codigoCaracteristica == null && other.codigoCaracteristica != null) || (this.codigoCaracteristica != null && !this.codigoCaracteristica.equals(other.codigoCaracteristica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Caracteristicastelefono[ codigoCaracteristica=" + codigoCaracteristica + " ]";
    }
    
}
