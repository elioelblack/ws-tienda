package com.tienda.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author eliezer
 */
@Entity
@Table(name = "orden_detalle")
public class OrdenDetalle implements Serializable{
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden_detalle")
    private Integer idOrdenDetalle;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")    
    private Producto prodcutoId;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "orden_id")
    private Orden ordenId;    
   
    @Column(name = "cantidad")
    private Integer cantidad;
    
    @Column(name = "subtotal")
    private Double subtotal;
    
    @Column(name = "iva")
    private Double iva;
    
    @Column(name = "descuento")
    private Double descuento;
    
    @Column(name = "total")
    private Double total;
    
    @Column(name = "fecha_crea")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCrea;
    
    @Size(max = 25)
    @Column(name = "ultimo_usuario")
    private String ultimoUsuario;

    public Integer getIdOrdenDetalle() {
        return idOrdenDetalle;
    }

    public void setIdOrdenDetalle(Integer idOrdenDetalle) {
        this.idOrdenDetalle = idOrdenDetalle;
    }

    public Producto getProdcutoId() {
        return prodcutoId;
    }

    public void setProdcutoId(Producto prodcutoId) {
        this.prodcutoId = prodcutoId;
    }

    public Orden getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(Orden ordenId) {
        this.ordenId = ordenId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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
    
    
    
    
}
