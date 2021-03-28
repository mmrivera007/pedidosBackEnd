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

import com.altioracorp.pedidos.entity.Orden;
import com.altioracorp.pedidos.service.OrdenService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Ordens", value = "Microservicios de Ordens")
public class OrdenRestController {

	private static final Log log = LogFactory.getLog(OrdenRestController.class);
	
	@Autowired
	OrdenService ordenService;

	@ApiOperation(value = "Obtiene todos las ordenes de un cliente", 
			notes = "Retorna una lista de ordens.")
	@GetMapping(value = "/obtenerOrdenes/{id}")
	public ResponseEntity<List<Orden>> obtenerOrdenes(@PathVariable(required = true) Integer id) throws ServletException {
		log.info("EndPoint obtenerOrdens...:");

		try {
			List<Orden> listOrden = ordenService.obtenerOrdenesByCliente(id);
			return new ResponseEntity<>(listOrden, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Obtiene una orden por su ID", notes = "Retorna un orden.")
	@GetMapping(value = "/obtenerOrden/{id}")
	public ResponseEntity<Orden> obtenerOrden(@PathVariable(required = true) Integer id)
			throws ServletException {
		log.info("EndPoint obtenerOrden...:");

		try {
			Orden orden = ordenService.obtenerOrdenById(id);
			if (orden != null) {
				return new ResponseEntity<>(orden, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Guarda una orden", notes = "Se deberá remitir el objeto con los datos necesarios.")
	@PostMapping("/guardarOrden")
	public ResponseEntity<Map<String, Object>> guardarOrden(@RequestBody @Valid Orden orden)
			throws ServletException {
		log.info("EndPoint guardarOrden...");
		try {
			
			boolean nuevo = orden.getId()!=null?false:true;
			Integer codigo = ordenService.guardarOrden(orden);
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
	
	@ApiOperation(value = "Eliminar una Orden", notes = "Se deberá remitir el id del objeto.")
	@DeleteMapping("/eliminarOrden/{id}")
	public ResponseEntity<Map<String, Object>> eliminarOrden(@PathVariable(required = true) Integer id)
			throws ServletException {
		log.info("EndPoint eliminarOrden...");
		try {			
			ordenService.eliminarOrden(id);		
			return new ResponseEntity<>(Collections.singletonMap("codigo", id), HttpStatus.OK);
		} catch (Exception e) {
			log.info(e);
			return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}