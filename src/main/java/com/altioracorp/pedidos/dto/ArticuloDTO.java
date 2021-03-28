package com.altioracorp.pedidos.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticuloDTO implements Serializable {
	private static final long serialVersionUID = 1L;
   
    private Integer id;
    private String codigo;
    private String nombre;
    private Double precio;
}
