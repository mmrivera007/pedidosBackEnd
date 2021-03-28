package com.altioracorp.pedidos.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altioracorp.pedidos.entity.Cliente;
import com.altioracorp.pedidos.service.ClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Clientes", value = "Microservicios de Clientes")
public class ClienteRestController {

	private static final Log log = LogFactory.getLog(ClienteRestController.class);
	
	@Autowired
	ClienteService clienteService;

	@ApiOperation(value = "Obtiene todos los clientes", 
			notes = "Retorna una lista de clientes.")
	@GetMapping(value = "/obtenerClientes")
	public ResponseEntity<List<Cliente>> obtenerClientes() throws ServletException {
		log.info("EndPoint obtenerClientes...:");

		try {
			List<Cliente> listCliente = clienteService.obtenerClientes();
			return new ResponseEntity<>(listCliente, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Obtiene un cliente por su ID", notes = "Retorna un cliente.")
	@GetMapping(value = "/obtenerCliente/{id}")
	public ResponseEntity<Cliente> obtenerCliente(@PathVariable(required = true) Integer id)
			throws ServletException {
		log.info("EndPoint obtenerCliente...:");

		try {
			Cliente cliente = clienteService.obtenerClienteById(id);
			if (cliente != null) {
				return new ResponseEntity<>(cliente, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Guarda un cliente", notes = "Se deberá remitir el objeto con los datos necesarios.")
	@PostMapping("/guardarCliente")
	public ResponseEntity<Map<String, Object>> guardarCliente(@RequestBody @Valid Cliente cliente)
			throws ServletException {
		log.info("EndPoint guardarCliente...");
		try {
			
			boolean nuevo = cliente.getId()!=null?false:true;
			Integer codigo = clienteService.guardarCliente(cliente);
			if(nuevo) {
				return new ResponseEntity<>(Collections.singletonMap("codigo", codigo), HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>(Collections.singletonMap("codigo", codigo), HttpStatus.OK); 
			}
		} catch (Exception e) {
			log.info(e);
			return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@ApiOperation(value = "Eliminar un cliente", notes = "Se deberá remitir el id del objeto.")
	@DeleteMapping("/eliminarCliente/{id}")
	public ResponseEntity<Map<String, Object>> eliminarCliente(@PathVariable(required = true) Integer id)
			throws ServletException {
		log.info("EndPoint eliminarCliente...");
		try {			
			clienteService.eliminarCliente(id);			
			return new ResponseEntity<>(Collections.singletonMap("codigo", id), HttpStatus.OK);
		} catch (Exception e) {
			log.info(e);
			return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}