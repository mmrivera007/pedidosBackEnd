package com.altioracorp.pedidos.repository.spec;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import com.altioracorp.pedidos.entity.Orden;

/**
 * Definición de especificaciones de orden con criteria para consultas más elaboradas
 */
public class OrdenSpec {

	@SuppressWarnings("serial")
	public static Specification<Orden> filtrosByCriteriaOrden(Orden ordenBusqueda) {
		return new Specification<Orden>() {
			@Override
			public Predicate toPredicate(Root<Orden> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				if (ordenBusqueda != null) {
					List<Predicate> predicates = new ArrayList<>();
					if (!ObjectUtils.isEmpty(ordenBusqueda.getId())) {
						predicates.add(cb.equal(root.get("id"), ordenBusqueda.getId()));
					}
					if (!ObjectUtils.isEmpty(ordenBusqueda.getFecha())) {
						predicates.add(
								cb.equal(root.get("fecha"), ordenBusqueda.getFecha()));
					}
					if (ordenBusqueda.getCliente() != null
							&& !ObjectUtils.isEmpty(ordenBusqueda.getCliente().getId())) {
						predicates.add(cb.equal(root.join("cliente").get("id"), ordenBusqueda.getCliente().getId()));
					}
					
					return !predicates.isEmpty() ? cb.and(predicates.toArray(new Predicate[0])) : null;
				} else {
					return null;
				}
			}

		};
	}
}
