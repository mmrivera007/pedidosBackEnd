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

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("Modelo Cliente")
@Data
@NoArgsConstructor
@Entity
@Table(name = "cliente", schema = "public")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SEQ_CLIENTE_GENERATOR", sequenceName = "seq_cliente", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CLIENTE_GENERATOR")
    @Column(unique=true, nullable=false, precision=10)
    private Integer id;
    
    @NotBlank
    @Column(nullable=false, length=64)
    private String nombre;
    
    @NotBlank
    @Column(nullable=false, length=64)
    private String apellido;
    
    @JsonIgnore    
    @OneToMany(fetch = FetchType.LAZY, mappedBy="cliente")
    private List<Orden> orden;
   

}
