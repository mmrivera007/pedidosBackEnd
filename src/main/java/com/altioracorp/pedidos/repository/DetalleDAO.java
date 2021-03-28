package com.altioracorp.pedidos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.altioracorp.pedidos.entity.Detalle;

@Repository
public interface DetalleDAO extends JpaRepository<Detalle, Integer>, JpaSpecificationExecutor<Detalle> {
	
	@EntityGraph(attributePaths = { "articulo", "orden"})
	public List<Detalle> findAll(Specification<Detalle> spec);

	@EntityGraph(attributePaths = { "articulo", "orden"})
	public Optional<Detalle> findById(Integer id);
}
