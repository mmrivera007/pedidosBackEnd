package com.altioracorp.pedidos.repository.spec;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import com.altioracorp.pedidos.entity.Detalle;

/**
 * Definición de especificaciones de detalle con criteria para consultas más elaboradas
 */
public class DetalleSpec {

	@SuppressWarnings("serial")
	public static Specification<Detalle> filtrosByCriteriaDetalle(Detalle detalleBusqueda) {
		return new Specification<Detalle>() {
			@Override
			public Predicate toPredicate(Root<Detalle> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				if (detalleBusqueda != null) {
					List<Predicate> predicates = new ArrayList<>();
					if (!ObjectUtils.isEmpty(detalleBusqueda.getId())) {
						predicates.add(cb.equal(root.get("id"), detalleBusqueda.getId()));
					}
					if (detalleBusqueda.getArticulo() != null
							&& !ObjectUtils.isEmpty(detalleBusqueda.getArticulo().getId())) {
						predicates.add(cb.equal(root.join("articulo").get("id"), detalleBusqueda.getArticulo().getId()));
					}
					if (detalleBusqueda.getOrden() != null
							&& !ObjectUtils.isEmpty(detalleBusqueda.getOrden().getId())) {
						predicates.add(cb.equal(root.join("orden").get("id"), detalleBusqueda.getOrden().getId()));
					}
					
					return !predicates.isEmpty() ? cb.and(predicates.toArray(new Predicate[0])) : null;
				} else {
					return null;
				}
			}

		};
	}
}
