package com.tienda.service;

import com.tienda.utils.Operaciones;
import com.tienda.entities.Orden;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author eliezer
 */
@Stateless
public class OrdenService extends Operaciones<Orden>{

    public OrdenService() {
        super(Orden.class);
    }
    
    public List<Orden> listarTodasOrdenes(){
        return this.consultarTodos("select t from Orden t");
    }
    
    public Orden consultarOrdenPorId(Integer id){
        return this.consultarPor("select t from Orden t where t.idOrden = :id", "id", id);
    }
}
