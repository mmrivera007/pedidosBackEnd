package com.altioracorp.pedidos.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.altioracorp.pedidos.entity.Articulo;
import com.altioracorp.pedidos.entity.Detalle;
import com.altioracorp.pedidos.entity.Orden;
import com.altioracorp.pedidos.repository.DetalleDAO;
import com.altioracorp.pedidos.repository.spec.DetalleSpec;
import com.altioracorp.pedidos.service.DetalleService;

@Service
public class DetalleServiceImpl implements DetalleService{
	private static final Log log = LogFactory.getLog(DetalleServiceImpl.class);
	
	@Autowired
	private DetalleDAO detalleDAO;
	@Autowired
	private ArticuloServiceImpl articuloService;
		
	@Transactional
	public Integer guardarDetalle(Detalle detalle) throws Exception {
		log.info("guardarDetalle servicio...");		
		if(detalle == null || detalle.getOrden() == null || detalle.getArticulo() == null) {
			throw new Exception ("Se requiere el objeto con los valores obligatorios para guardar");
		}
		if(detalle.getId() != null && detalle.getId().equals(-1)) {
			detalle.setId(null);
		}
		
		Articulo art = articuloService.obtenerArticuloById(detalle.getArticulo().getId());
		if(art.getStock() < detalle.getCantidad()) {
			throw new RuntimeException("No hay stock para el articulo '" + art.getNombre() + "', stock:" + art.getStock());
		}else {
			art.setStock(art.getStock() - detalle.getCantidad());
			articuloService.guardarArticulo(art);
		}		
		
		return detalleDAO.save(detalle).getId();
	}

	@Transactional
	public void eliminarDetalle(Integer id) throws Exception {
		log.info("eliminarDetalle servicio...");
		if(id == null) {
			throw new Exception ("Se requiere el objeto para eliminar");
		}
		
		detalleDAO.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Detalle obtenerDetalleById(Integer id) throws Exception {
		log.info("obtenerDetalleById servicio...");
		if(id == null) {
			throw new Exception ("Se requiere el objeto para consultar");
		}
		return detalleDAO.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public List<Detalle> obtenerDetallesByOrden(Integer idOrden) throws Exception {
		log.info("obtenerDetallesByOrden servicio...");
		if(idOrden == null) {
			throw new Exception ("Se requiere el objeto para consultar");
		}
		Detalle detalle = new Detalle();
		detalle.setOrden(new Orden());
		detalle.getOrden().setId(idOrden);
		
		return detalleDAO.findAll(DetalleSpec.filtrosByCriteriaDetalle(detalle));
	}

	public  List<Detalle> obtenerDetallesByArticulo(Integer idArticulo) throws Exception {
		log.info("obtenerDetallesByArticulo servicio...");
		if(idArticulo == null) {
			throw new Exception ("Se requiere el objeto para consultar");
		}
		Detalle detalle = new Detalle();
		detalle.setArticulo(new Articulo());
		detalle.getArticulo().setId(idArticulo);
		
		return detalleDAO.findAll(DetalleSpec.filtrosByCriteriaDetalle(detalle));	}

	@Override
	public void guardarDetalles(Integer idOrden, List<Detalle> detalles) throws Exception {
		log.info("guardarDetalles servicio..." + idOrden);
		this. verificaDetalleEliminar(idOrden, detalles);
		
		if(!detalles.isEmpty()) {
			log.info("Actualiza:"+detalles);
			//Guarda/actualiza los detalle enviados
			detalles.forEach(detalle -> {
				try {
					this.guardarDetalle(detalle);
				} catch (Exception e) {
					 throw new RuntimeException(e);
				}
			});
		}		
	}

	private void verificaDetalleEliminar(Integer idOrden, List<Detalle> detalles) throws Exception {
		log.info("verificaDetalleEliminar..." + idOrden);
		//Consulta los detalles de la orden procesada
		List<Detalle> resultadoDetalle = this.obtenerDetallesByOrden(idOrden);
		if(!ObjectUtils.isEmpty(resultadoDetalle)) {
			//Los registros no enviados se deberan eliminar
			List<Detalle> detEliminar = null;	
			if(detalles.isEmpty()) {
				detEliminar = new ArrayList<Detalle>();
				detEliminar.addAll(resultadoDetalle);
			}else {
				for (Detalle det : resultadoDetalle) {
					//El primer valor a comparar es del objeto registrado en la base, por posibles casos de nuevas entidades(id null)
					if(!detalles.stream().anyMatch(x -> det.getId().equals(x.getId()))) {
						if(detEliminar == null) {
							detEliminar = new ArrayList<Detalle>();
						}
						detEliminar.add(det);
					}
				}
			}
			
			//Si existen valores a eliminar
			if(!ObjectUtils.isEmpty(detEliminar)) {
				detEliminar.forEach(det -> {
					try {
						Articulo art = articuloService.obtenerArticuloById(det.getArticulo().getId());		
						art.setStock(art.getStock() + det.getCantidad());
						articuloService.guardarArticulo(art);
						
						this.eliminarDetalle(det.getId());
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
			}
		}
	}
	
}
