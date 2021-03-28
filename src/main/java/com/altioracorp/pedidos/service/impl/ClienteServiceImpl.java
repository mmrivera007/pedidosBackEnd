package com.altioracorp.pedidos.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altioracorp.pedidos.entity.Cliente;
import com.altioracorp.pedidos.repository.ClienteDAO;
import com.altioracorp.pedidos.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService{
	private static final Log log = LogFactory.getLog(ClienteServiceImpl.class);
	
	@Autowired
	private ClienteDAO clienteDAO;
	@Autowired
	private OrdenServiceImpl ordenService;
		
	@Transactional
	public Integer guardarCliente(Cliente cliente) throws Exception {
		log.info("guardarCliente servicio...");		
		if(cliente == null || cliente.getNombre() == null || cliente.getApellido() == null) {
			throw new Exception ("Se requiere el objeto con los valores obligatorios para guardar");
		}
		
		return clienteDAO.save(cliente).getId();				
	}

	@Transactional
	public void eliminarCliente(Integer id) throws Exception {
		log.info("eliminarCliente servicio...");
		if(id == null) {
			throw new Exception ("Se requiere el objeto para eliminar");
		}
		//Si el cliente existe, valida que no tenga ninguna orden asociada
		if(clienteDAO.findById(id).isPresent()) {
			if(!ordenService.obtenerOrdenesByCliente(id).isEmpty()) {
				throw new Exception ("El cliente no puede ser eliminado, tiene ordenes asociadas.");
			}
		}
		clienteDAO.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Cliente obtenerClienteById(Integer id) throws Exception {
		log.info("obtenerClienteById servicio...");
		if(id == null) {
			throw new Exception ("Se requiere el objeto para consultar");
		}
		return clienteDAO.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public List<Cliente> obtenerClientes() throws Exception {
		log.info("obtenerClientes servicio...");
		
		return clienteDAO.findAll();
	}
}
