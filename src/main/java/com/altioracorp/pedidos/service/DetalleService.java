package com.altioracorp.pedidos.service;

import java.util.List;

import com.altioracorp.pedidos.entity.Detalle;

public interface DetalleService {
	/**
	 * Crea o actualiza un detalle
	 * @param detalle
	 * @return Integer
	 * @throws Exception
	 */
	Integer guardarDetalle(Detalle detalle) throws Exception ;
	/**
	 * Crea o actualiza varios detalles
	 * @param idOrden 
	 * @param detalles
	 * @throws Exception
	 */
	void guardarDetalles(Integer idOrden, List<Detalle> detalles) throws Exception ;

	/**
	 * Elimina un detalle por su Id
	 * @param id
	 * @throws Exception
	 */
	void eliminarDetalle(Integer id) throws Exception;

	/**
	 * Consulta un detalle por su Id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Detalle obtenerDetalleById(Integer id) throws Exception;
	
	/**
	 * Obtiene los detalles de una orden
	 * @param idOrden
	 * @return List
	 * @throws Exception
	 */
	List<Detalle> obtenerDetallesByOrden(Integer idOrden) throws Exception;
}
