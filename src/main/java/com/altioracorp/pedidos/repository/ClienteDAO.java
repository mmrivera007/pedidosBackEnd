package com.altioracorp.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altioracorp.pedidos.entity.Cliente;

@Repository
public interface ClienteDAO extends JpaRepository<Cliente, Integer> {

}
