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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altioracorp.pedidos.entity.Detalle;
import com.altioracorp.pedidos.service.DetalleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Detalles", value = "Microservicios de Detalles")
public class DetalleRestController {

	private static final Log log = LogFactory.getLog(DetalleRestController.class);
	
	@Autowired
	DetalleService detalleService;

	@ApiOperation(value = "Obtiene todos los detalles de una orden", 
			notes = "Retorna una lista de detalles.")
	@GetMapping(value = "/obtenerDetalles/{id}")
	public ResponseEntity<List<Detalle>> obtenerDetalles(@PathVariable(required = true) Integer id) throws ServletException {
		log.info("EndPoint obtenerDetalles...:");

		try {
			List<Detalle> listDetalle = detalleService.obtenerDetallesByOrden(id);
			return new ResponseEntity<>(listDetalle, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Obtiene un detalle por su ID", notes = "Retorna un detalle.")
	@GetMapping(value = "/obtenerDetalle/{id}")
	public ResponseEntity<Detalle> obtenerDetalle(@PathVariable(required = true) Integer id)
			throws ServletException {
		log.info("EndPoint obtenerDetalle...:");

		try {
			Detalle detalle = detalleService.obtenerDetalleById(id);
			if (detalle != null) {
				return new ResponseEntity<>(detalle, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Guarda un detalle", notes = "Se deberá remitir el objeto con los datos necesarios.")
	@PostMapping("/guardarDetalle")
	public ResponseEntity<Map<String, Object>> guardarDetalle(@RequestBody @Valid Detalle detalle)
			throws ServletException {
		log.info("EndPoint guardarDetalle...");
		try {
			
			boolean nuevo = detalle.getId()!=null?false:true;
			Integer codigo = detalleService.guardarDetalle(detalle);
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
	
	@ApiOperation(value = "Guarda varios detalles", notes = "Se deberá remitir los objetos con los datos necesarios.")
	@PostMapping("/guardarDetalles")
	public ResponseEntity<Map<String, Object>> guardarDetalles(@RequestHeader(name = "idOrden",required = true) String id,
			@RequestBody @Valid List<Detalle> detalles)	throws ServletException {
		log.info("EndPoint guardarDetalles..." + id);
		try {
			detalleService.guardarDetalles(Integer.valueOf(id), detalles);
			return new ResponseEntity<>(HttpStatus.OK); 
		} catch (Exception e) {
			log.info(e);
			return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}