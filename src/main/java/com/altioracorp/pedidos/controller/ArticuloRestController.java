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

import com.altioracorp.pedidos.entity.Articulo;
import com.altioracorp.pedidos.service.ArticuloService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Articulos", value = "Microservicios de Articulos")
public class ArticuloRestController {

	private static final Log log = LogFactory.getLog(ArticuloRestController.class);
	
	@Autowired
	ArticuloService articuloService;

	@ApiOperation(value = "Obtiene todos los articulos", 
			notes = "Retorna una lista de articulos.")
	@GetMapping(value = "/obtenerArticulos")
	public ResponseEntity<List<Articulo>> obtenerArticulos() throws ServletException {
		log.info("EndPoint obtenerArticulos...:");

		try {
			List<Articulo> listArticulo = articuloService.obtenerArticulos();
			return new ResponseEntity<>(listArticulo, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Obtiene un articulo por su ID", notes = "Retorna un articulo.")
	@GetMapping(value = "/obtenerArticulo/{id}")
	public ResponseEntity<Articulo> obtenerArticulo(@PathVariable(required = true) Integer id)
			throws ServletException {
		log.info("EndPoint obtenerArticulo...:");

		try {
			Articulo articulo = articuloService.obtenerArticuloById(id);
			if (articulo != null) {
				return new ResponseEntity<>(articulo, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Guarda un articulo", notes = "Se deberá remitir el objeto con los datos necesarios.")
	@PostMapping("/guardarArticulo")
	public ResponseEntity<Map<String, Object>> guardarArticulo(@RequestBody @Valid Articulo articulo)
			throws ServletException {
		log.info("EndPoint guardarArticulo...");
		try {
			
			boolean nuevo = articulo.getId()!=null?false:true;
			Integer codigo = articuloService.guardarArticulo(articulo);
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
	@ApiOperation(value = "Eliminar un articulo", notes = "Se deberá remitir el id del objeto.")
	@DeleteMapping("/eliminarArticulo/{id}")
	public ResponseEntity<Map<String, Object>> eliminarArticulo(@PathVariable(required = true) Integer id)
			throws ServletException {
		log.info("EndPoint eliminarArticulo...");
		try {			
			articuloService.eliminarArticulo(id);			
			return new ResponseEntity<>(Collections.singletonMap("codigo", id), HttpStatus.OK);
		} catch (Exception e) {
			log.info(e);
			return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}