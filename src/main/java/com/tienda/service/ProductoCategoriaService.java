package com.tienda.service;

import com.tienda.entities.Producto;
import com.tienda.utils.Operaciones;
import com.tienda.entities.ProductoCategoria;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author eliezer
 */
@Stateless
public class ProductoCategoriaService extends Operaciones<ProductoCategoria>{

    public ProductoCategoriaService() {
        super(ProductoCategoria.class);
    }
    public List<ProductoCategoria> listarTodasCategorias(){
        return this.consultarTodos("select t from ProductoCategoria t");
    }
}
