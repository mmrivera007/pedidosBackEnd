package com.altioracorp.pedidos.service;

import java.util.List;

import com.altioracorp.pedidos.entity.Orden;

public interface OrdenService {
	/**
	 * Crea o actualiza una orden
	 * @param orden
	 * @return Integer
	 * @throws Exception
	 */
	Integer guardarOrden(Orden orden) throws Exception ;

	/**
	 * Elimina una orden por su Id
	 * @param id
	 * @throws Exception
	 */
	void eliminarOrden(Integer id) throws Exception;

	/**
	 * Consulta una orden por su Id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Orden obtenerOrdenById(Integer id) throws Exception;
	
	/**
	 * Obtiene las ordenes de un cliente
	 * @param idCliente
	 * @return List
	 * @throws Exception
	 */
	List<Orden> obtenerOrdenesByCliente(Integer idCliente) throws Exception;
}
