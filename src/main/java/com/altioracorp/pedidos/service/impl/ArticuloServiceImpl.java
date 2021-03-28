package com.altioracorp.pedidos.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altioracorp.pedidos.entity.Articulo;
import com.altioracorp.pedidos.repository.ArticuloDAO;
import com.altioracorp.pedidos.service.ArticuloService;

@Service
public class ArticuloServiceImpl implements ArticuloService{
	private static final Log log = LogFactory.getLog(ArticuloServiceImpl.class);
	
	@Autowired
	private ArticuloDAO articuloDAO;
	@Autowired
	private DetalleServiceImpl detalleService;
		
	@Transactional
	public Integer guardarArticulo(Articulo articulo) throws Exception {
		log.info("guardarArticulo servicio...");		
		if(articulo == null || articulo.getNombre() == null || articulo.getCodigo() == null || articulo.getPrecio() == null) {
			throw new Exception ("Se requiere el objeto con los valores obligatorios para guardar");
		}
		//Nuevo registro: Valida que el código no exista
		if(articulo.getId() == null) {
			if(articuloDAO.findByCodigo(articulo.getCodigo()).isPresent() ) {
				throw new Exception ("El código enviado ya existe, favor verifique.");
			}
		}
		
		return articuloDAO.save(articulo).getId();				
	}

	@Transactional
	public void eliminarArticulo(Integer id) throws Exception {
		log.info("eliminarArticulo servicio...");
		if(id == null) {
			throw new Exception ("Se requiere el objeto para eliminar");
		}
		//Valida que el articulo no se encuentre en el detalle de una orden
		if(articuloDAO.findById(id).isPresent()) {
			if(!detalleService.obtenerDetallesByArticulo(id).isEmpty()) {
				throw new Exception ("El articulo no puede ser eliminado, tiene detalles asociados.");
			}
		}

		
		articuloDAO.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Articulo obtenerArticuloById(Integer id) throws Exception {
		log.info("obtenerArticuloById servicio...");
		if(id == null) {
			throw new Exception ("Se requiere el objeto para consultar");
		}
		return articuloDAO.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public List<Articulo> obtenerArticulos() throws Exception {
		log.info("obtenerArticulos servicio...");
		
		return articuloDAO.findAll();
	}
}
