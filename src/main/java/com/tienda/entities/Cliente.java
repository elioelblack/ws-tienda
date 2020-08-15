package com.tienda.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author eliezer
 */
@Entity
public class Cliente implements Serializable{
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;
    
    @Size(max = 25)
    @Column(name = "primer_nombre", nullable = false)
    private String primerNombre;
    
    @Size(max = 25)
    @Column(name = "segundo_nombre")
    private String segundoNombre;
    
    @Size(max = 25)
    @Column(name = "primer_apellido", nullable = false)
    private String primerApellido;
    
    @Size(max = 25)
    @Column(name = "segundo_apellido")
    private String segundoApellido;
    
    @Size(max = 25)
    @Column(name = "dui_cliente")
    private String duiCliente;
    
    @Size(max = 25)
    @Column(name = "nit_cliente",unique = true)
    private String nitCliente;
    
    @Column(name = "flag_estado",unique = true)
    private Integer flagEstado;
    
    @Column(name = "fecha_crea")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCrea;

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getDuiCliente() {
        return duiCliente;
    }

    public void setDuiCliente(String duiCliente) {
        this.duiCliente = duiCliente;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public Integer getFlagEstado() {
        return flagEstado;
    }

    public void setFlagEstado(Integer flagEstado) {
        this.flagEstado = flagEstado;
    }

    public Date getFechaCrea() {
        return fechaCrea;
    }

    public void setFechaCrea(Date fechaCrea) {
        this.fechaCrea = fechaCrea;
    }
    
    
}
