package com.altioracorp.pedidos.service;

import java.util.List;

import com.altioracorp.pedidos.entity.Articulo;

public interface ArticuloService {
	/**
	 * Crea o actualiza un articulo
	 * @param articulo
	 * @return Integer
	 * @throws Exception
	 */
	Integer guardarArticulo(Articulo articulo) throws Exception ;

	/**
	 * Elimina un articulo por su Id
	 * @param id
	 * @throws Exception
	 */
	void eliminarArticulo(Integer id) throws Exception;

	/**
	 * Consulta un articulo por su Id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Articulo obtenerArticuloById(Integer id) throws Exception;
	
	/**
	 * Obtiene los articulos
	 * @return List
	 * @throws Exception
	 */
	List<Articulo> obtenerArticulos() throws Exception;
}
