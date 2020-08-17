package com.tienda.soap;

import com.tienda.entities.Cliente;
import com.tienda.entities.Orden;
import com.tienda.entities.Producto;
import com.tienda.entities.ProductoCategoria;
import com.tienda.exception.CheckFaultBean;
import com.tienda.exception.CheckVerifyFault;
import com.tienda.service.ClienteService;
import com.tienda.service.OrdenService;
import com.tienda.service.ProductoCategoriaService;
import com.tienda.service.ProductoService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author eliezer
 */
@WebService(serviceName = "TiendaWS")
public class ClienteWS {
    private List<Cliente> listaCliente;
    private Cliente cliente;
    private Orden orden;
    private List<Orden> listaOrden;
    private Producto producto;
    private List<Producto> listaProducto;
    private ProductoCategoria categoria;
    private List<ProductoCategoria> listaCategorias;
    @EJB
    private ClienteService clienteService;
    @EJB
    private OrdenService ordenService;
    @EJB
    private ProductoService productoService;
    @EJB
    private ProductoCategoriaService productoCategoriaService;
    
    
    @PostConstruct
    public void init() {
        cliente = new Cliente();
        listaCliente = new ArrayList<>();
        orden = new Orden();
        listaCliente = new ArrayList<>();
        producto =  new Producto();
        listaProducto = new ArrayList<>();
        categoria = new ProductoCategoria();
        listaCategorias = new ArrayList<>();
    }
    
    @WebMethod(operationName = "listaClientes")
    public List<Cliente> listaClientes() throws CheckVerifyFault{
        return this.listaCliente = this.clienteService.consultarTodosClientes();        
    }
    
    @WebMethod(operationName = "nuevoCliente")
    public Cliente nuevoCliente(@WebParam(name = "primer_nombre") String primer_nombre, 
            @WebParam(name = "segundo_nombre") String segundo_nombre, 
            @WebParam(name = "primer_apellido") String primer_apellido, 
            @WebParam(name = "segundo_apellido") String segundo_apellido, 
            @WebParam(name = "p_dui") String p_dui, 
            @WebParam(name = "p_nit") String p_nit,
            @WebParam(name = "flag_estado") Integer flag_estado) throws CheckVerifyFault, Exception {
        
        cliente.setPrimerNombre(primer_nombre);
        cliente.setSegundoNombre(segundo_nombre);
        cliente.setPrimerApellido(primer_apellido);
        cliente.setSegundoApellido(segundo_apellido);
        cliente.setDuiCliente(p_dui);
        cliente.setNitCliente(p_nit);
        cliente.setFlagEstado(flag_estado);
        return this.clienteService.crear(this.cliente);
    }

    @WebMethod(operationName = "getClientePorId")
    public Cliente getClientePorId(@WebParam(name = "id") Integer id)throws CheckVerifyFault  {
        //TODO write your implementation code here:
        cliente = clienteService.consultarClientePorId(id);
        if(cliente!=null){
            return cliente;
        }else{
            CheckFaultBean thre = new CheckFaultBean() ;
            new CheckVerifyFault("Error", thre);
            return null;
        }
       
    }
    
    @WebMethod(operationName = "eliminarCliente")
    public String eliminarCliente(@WebParam(name = "cliente") Cliente cliente) throws Exception{
        cliente = clienteService.consultarClientePorId(cliente.getIdCliente());
        if(cliente != null){
            clienteService.eliminar(cliente);
            return "Eliminado correctamente";
        }else{
            return "No existe cliente con ese ID";
        }
        
    }
    
    @WebMethod(operationName = "editarCliente")
    public String editarCliente(@WebParam(name = "cliente") Cliente cliente_p) throws Exception{        
        if(cliente_p != null){
            clienteService.editar(cliente_p);
            return "Modificado correctamente";
        }else{
            return "No existe cliente con ese ID: "+cliente_p.getIdCliente();
        }        
    }
    /***************Orden******************/
    @WebMethod(operationName = "listaOrdenes")
    public List<Orden> listaOrdenes() throws Exception{
        return this.listaOrden = this.ordenService.listarTodasOrdenes();        
    }
    
    @WebMethod(operationName = "getOrdenPorId")
    public Orden getOrdenPorId(@WebParam(name = "id") Integer id)throws Exception  {
        //TODO write your implementation code here:
        this.orden = ordenService.consultarOrdenPorId(id);
        if(orden!=null){
            return orden;
        }else{
            CheckFaultBean thre = new CheckFaultBean() ;
            new CheckVerifyFault("Error", thre);
            return null;
        }
       
    }
    
    @WebMethod(operationName = "nuevaOrden")
    public Orden nuevaOrden(@WebParam(name = "cliente_id") Integer cliente_id, 
            @WebParam(name = "fecha_orden") Date fecha_orden, 
            @WebParam(name = "nota") String nota, 
            @WebParam(name = "fecha_crea") Date fecha_crea, 
            @WebParam(name = "ultimo_usuario") String ultimo_usuario
            ) throws CheckVerifyFault, Exception {
        this.cliente = new Cliente();
        cliente = clienteService.consultarClientePorId(cliente_id);
        this.orden = new Orden();
        this.orden.setClienteId(cliente);
        this.orden.setFechaOrden(fecha_orden);
        this.orden.setNota(nota);
        this.orden.setFechaCrea(fecha_crea);
        this.orden.setUltimoUsuario(ultimo_usuario);
        return this.ordenService.crear(this.orden);
    }
    
    //*****************Producto********************//
    @WebMethod(operationName = "listaProductos")
    public List<Producto> listaProductos() throws Exception{
        return this.listaProducto = this.productoService.listarTodosProductos();        
    }
    
    @WebMethod(operationName = "getProductoPorId")
    public Producto getProductoPorId(@WebParam(name = "id") Integer id)throws Exception  {
        //TODO write your implementation code here:
        this.producto = productoService.consultarProductoPorId(id);
        if(producto!=null){
            return producto;
        }else{
            CheckFaultBean thre = new CheckFaultBean() ;
            new CheckVerifyFault("Error", thre);
            return null;
        }
       
    }
    
    @WebMethod(operationName = "crearProducto")
    public Producto crearProducto(@WebParam(name = "producto") Producto prod)throws Exception  {
        //TODO write your implementation code here:
        this.producto = productoService.crear(prod);
        if(producto!=null){
            return producto;
        }else{
            CheckFaultBean thre = new CheckFaultBean() ;
            new CheckVerifyFault("Error", thre);
            return null;
        }
       
    }
    @WebMethod(operationName = "editarProducto")
    public String editarProducto(@WebParam(name = "producto") Producto prod) throws Exception{        
        if(prod != null){
            productoService.editar(prod);
            return "Modificado correctamente";
        }else{
            return "No existe cliente con ese ID: "+prod.getIdProducto();
        }        
    }
    @WebMethod(operationName = "eliminarProducto")
    public String eliminarProducto(@WebParam(name = "producto") Producto prod) throws Exception{
        producto = productoService.consultarProductoPorId(prod.getIdProducto());
        if(producto != null){
            productoService.eliminar(producto);
            return "Eliminado correctamente";
        }else{
            return "No existe cliente con ese ID";
        }
        
    }
    
    //********************Categoria*************************//
    @WebMethod(operationName = "listaCategorias")
    public List<ProductoCategoria> listaCategorias() throws Exception{
        return this.listaCategorias = this.productoCategoriaService.listarTodasCategorias();
    }
}
