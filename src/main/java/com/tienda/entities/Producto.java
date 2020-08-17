package com.tienda.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author eliezer
 */
@Entity
public class Producto implements Serializable{
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;
    
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
    
    @Column(name = "costo")
    private Double costo;
    
    @Column(name = "precio")
    private Double precio;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")    
    private ProductoCategoria categoriaId;

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
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

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public ProductoCategoria getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(ProductoCategoria categoriaId) {
        this.categoriaId = categoriaId;
    }
    
    
}
