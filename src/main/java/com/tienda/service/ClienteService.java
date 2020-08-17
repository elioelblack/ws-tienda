package com.tienda.service;

import com.tienda.utils.Operaciones;
import javax.ejb.Stateless;
import com.tienda.entities.Cliente;
import java.util.List;
/**
 *
 * @author eliezer
 */
@Stateless
public class ClienteService extends Operaciones<Cliente>{

    public ClienteService() {
        super(Cliente.class);
    }   
    
    public List<Cliente> consultarTodosClientes() {
        return this.consultarTodos("select t from Cliente t");
    }
    
    public Cliente consultarClientePorId(Integer id){
        return this.consultarPor("select t from Cliente t where t.idCliente = :id", "id", id);
    }
    
}
