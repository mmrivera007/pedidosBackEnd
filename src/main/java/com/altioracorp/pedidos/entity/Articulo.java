package com.altioracorp.pedidos.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("Modelo Articulo")
@Data
@NoArgsConstructor
@Entity
@Table(name = "articulo", schema = "public")
public class Articulo implements Serializable {
	private static final long serialVersionUID = 1L;
   
    @Id
    @SequenceGenerator(name = "SEQ_ARTICULO_GENERATOR", sequenceName = "seq_articulo", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ARTICULO_GENERATOR")
    @Column(unique=true, nullable=false, precision=10)
    private Integer id;
    
    @NotBlank
    @Column(unique=true, nullable=false, length=128)
    private String codigo;
    
    @NotBlank
    @Column(nullable=false, length=64)
    private String nombre;
    
    @NotNull
    @Column(nullable=false, precision=17, scale=17)
    private Double precio;
    
    private Integer stock;
    
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy="articulo")
    private List<Detalle> detalle;

}
