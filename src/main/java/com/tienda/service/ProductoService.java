package com.tienda.service;

import com.tienda.entities.Orden;
import com.tienda.utils.Operaciones;
import javax.ejb.Stateless;
import com.tienda.entities.Producto;
import java.util.List;

/**
 *
 * @author eliezer
 */
@Stateless
public class ProductoService extends Operaciones<Producto>{

    public ProductoService() {
        super(Producto.class);
    }
    
    public List<Producto> listarTodosProductos(){
        return this.consultarTodos("select t from Producto t");
    }
    
    public Producto consultarProductoPorId(Integer id){
        return this.consultarPor("select t from Producto t where t.idProducto = :id", "id", id);
    }
}
