package com.altioracorp.pedidos.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.altioracorp.pedidos.entity.Cliente;
import com.altioracorp.pedidos.entity.Detalle;
import com.altioracorp.pedidos.entity.Orden;
import com.altioracorp.pedidos.repository.OrdenDAO;
import com.altioracorp.pedidos.repository.spec.OrdenSpec;
import com.altioracorp.pedidos.service.OrdenService;

@Service
public class OrdenServiceImpl implements OrdenService{
	private static final Log log = LogFactory.getLog(OrdenServiceImpl.class);
	
	@Autowired
	private OrdenDAO ordenDAO;
	@Autowired
	private DetalleServiceImpl detalleService;
		
	@Transactional
	public Integer guardarOrden(Orden orden) throws Exception {
		log.info("guardarOrden servicio...");		
		if(orden == null || orden.getCliente() == null || orden.getFecha() == null) {
			throw new Exception ("Se requiere el objeto con los valores obligatorios para guardar");
		}
		
		return ordenDAO.save(orden).getId();				
	}

	@Transactional
	public void eliminarOrden(Integer id) throws Exception {
		log.info("eliminarOrden servicio...");
		if(id == null) {
			throw new Exception ("Se requiere el objeto para eliminar");
		}
		//Valida si tiene detalles
		List<Detalle> detalles = detalleService.obtenerDetallesByOrden(id);
		if(!ObjectUtils.isEmpty(detalles)) {
			detalles.forEach(detalle ->{
				try {
					detalleService.eliminarDetalle(detalle.getId());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
		}		
		ordenDAO.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Orden obtenerOrdenById(Integer id) throws Exception {
		log.info("obtenerOrdenById servicio...");
		if(id == null) {
			throw new Exception ("Se requiere el objeto para consultar");
		}
		return ordenDAO.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public List<Orden> obtenerOrdenesByCliente(Integer idCliente) throws Exception {
		log.info("obtenerOrdens servicio...");
		if(idCliente == null) {
			throw new Exception ("Se requiere el objeto para consultar");
		}
		Orden orden = new Orden();
		if(idCliente > 0) {
			orden.setCliente(new Cliente());
			orden.getCliente().setId(idCliente);
		}
		
		return ordenDAO.findAll(OrdenSpec.filtrosByCriteriaOrden(orden));
	}
}
