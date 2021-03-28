package com.altioracorp.pedidos.service;

import java.util.List;

import com.altioracorp.pedidos.entity.Cliente;

public interface ClienteService {
	/**
	 * Crea o actualiza un cliente
	 * @param cliente
	 * @return Integer
	 * @throws Exception
	 */
	Integer guardarCliente(Cliente cliente) throws Exception ;

	/**
	 * Elimina un cliente por su Id
	 * @param id
	 * @throws Exception
	 */
	void eliminarCliente(Integer id) throws Exception;

	/**
	 * Consulta un cliente por su Id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Cliente obtenerClienteById(Integer id) throws Exception;
	
	/**
	 * Obtiene los clientes
	 * @return List
	 * @throws Exception
	 */
	List<Cliente> obtenerClientes() throws Exception;
}
