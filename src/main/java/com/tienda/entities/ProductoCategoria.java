package com.tienda.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author eliezer
 */
@Entity
@Table(name = "producto_categoria")
public class ProductoCategoria implements Serializable{
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;
    
    @Size(max = 25)
    @Column(name = "nombre")
    private String nombre;
    
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "fecha_crea")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCrea;
    
    @Size(max = 25)
    @Column(name = "ultimo_usuario")
    private String ultimoUsuario;
    
    @Column(name = "flag_estado",unique = true)
    private Integer flagEstado;

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCrea() {
        return fechaCrea;
    }

    public void setFechaCrea(Date fechaCrea) {
        this.fechaCrea = fechaCrea;
    }

    public String getUltimoUsuario() {
        return ultimoUsuario;
    }

    public void setUltimoUsuario(String ultimoUsuario) {
        this.ultimoUsuario = ultimoUsuario;
    }

    public Integer getFlagEstado() {
        return flagEstado;
    }

    public void setFlagEstado(Integer flagEstado) {
        this.flagEstado = flagEstado;
    }
    
    
}
