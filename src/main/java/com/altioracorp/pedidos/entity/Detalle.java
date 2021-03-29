// Generated with g9.

package com.altioracorp.pedidos.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("Modelo Detalle")
@Data
@NoArgsConstructor
@Entity
@Table(name = "detalle", schema = "public")
public class Detalle implements Serializable {
	private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "SEQ_DETALLE_GENERATOR", sequenceName = "seq_detalle", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DETALLE_GENERATOR")
    @Column(unique=true, nullable=false, precision=10)
    private Integer id;
    
    private Integer cantidad;
    
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="id_articulo", nullable=false)
    private Articulo articulo;
    
    @JsonIgnoreProperties({"cliente"})
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="id_orden", nullable=false)
    private Orden orden;

}
