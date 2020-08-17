package com.tienda.service;

import com.tienda.utils.Operaciones;
import com.tienda.entities.OrdenDetalle;
import javax.ejb.Stateless;

/**
 *
 * @author eliezer
 */
@Stateless
public class OrdenDetalleService extends Operaciones<OrdenDetalle>{

    public OrdenDetalleService() {
        super(OrdenDetalle.class);
    }
    
}
