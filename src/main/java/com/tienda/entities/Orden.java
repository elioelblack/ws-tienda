package com.tienda.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author eliezer
 */
@Entity
@Table(name = "orden")
public class Orden implements Serializable{
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Integer idOrden;
    
    @Size(max = 255)
    @Column(name = "nota")
    private String nota;
    
    @Size(max = 25)
    @Column(name = "ultimo_usuario")
    private String ultimoUsuario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")    
    private Cliente clienteId;
    
    @Column(name = "fecha_crea")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCrea;
    
    @Column(name = "fecha_orden")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOrden;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orden_id")
    private Set<OrdenDetalle> ordenDetalle;

    public Set<OrdenDetalle> getOrdenDetalle() {
        return ordenDetalle;
    }

    public void setOrdenDetalle(Set<OrdenDetalle> ordenDetalle) {
        this.ordenDetalle = ordenDetalle;
    }
    
    

    public Integer getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getUltimoUsuario() {
        return ultimoUsuario;
    }

    public void setUltimoUsuario(String ultimoUsuario) {
        this.ultimoUsuario = ultimoUsuario;
    }

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
    }

    public Date getFechaCrea() {
        return fechaCrea;
    }

    public void setFechaCrea(Date fechaCrea) {
        this.fechaCrea = fechaCrea;
    }

    public Date getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(Date fechaOrden) {
        this.fechaOrden = fechaOrden;
    }
    
    
}
