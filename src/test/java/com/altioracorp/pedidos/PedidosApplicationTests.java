package com.altioracorp.pedidos;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.altioracorp.pedidos.entity.Cliente;
import com.altioracorp.pedidos.service.ClienteService;

@SpringBootTest
class PedidosApplicationTests {

	@Test
	void contextLoads() {
	}
	
	private static final Log log = LogFactory.getLog(PedidosApplicationTests.class);

	@Autowired
	ClienteService clienteService;

	@Test
	void addCliente() {
		log.info("addCliente");
		try {
			Cliente usuario = new Cliente();
			usuario.setNombre("Juan");
			usuario.setApellido("Pérez");

			Integer codigo = clienteService.guardarCliente(usuario);
			assertNotEquals(codigo, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
