package com.altioracorp.pedidos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.altioracorp.pedidos.entity.Orden;

@Repository
public interface OrdenDAO extends JpaRepository<Orden, Integer>, JpaSpecificationExecutor<Orden> {

	@EntityGraph(attributePaths = { "cliente"})
	public List<Orden> findAll(Specification<Orden> spec);

	@EntityGraph(attributePaths = { "cliente"})
	public Optional<Orden> findById(Integer id);
}
