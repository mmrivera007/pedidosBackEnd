package com.altioracorp.pedidos.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("Modelo Orden")
@Data
@NoArgsConstructor
@Entity
@Table(name = "orden", schema = "public")
public class Orden implements Serializable {
	private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "SEQ_ORDEN_GENERATOR", sequenceName = "seq_orden", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORDEN_GENERATOR")
    @Column(unique=true, nullable=false, precision=10)
    private Integer id;
    
    @NotNull
    @Column(nullable=false)
    private Date fecha;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="id_cliente", nullable=false)
    private Cliente cliente;
    
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy="orden")
    private List<Detalle> detalle;

}
