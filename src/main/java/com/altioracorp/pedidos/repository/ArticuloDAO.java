package com.altioracorp.pedidos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altioracorp.pedidos.entity.Articulo;

@Repository
public interface ArticuloDAO extends JpaRepository<Articulo, Integer> {

	Optional<Articulo> findByCodigo(String codigo);
}
