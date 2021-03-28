package com.altioracorp.pedidos.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("Modelo Cliente")
@Data
@NoArgsConstructor
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
    
    private Integer id;
    
    @NotBlank
    private String nombre;
    
    @NotBlank
    private String apellido;
    
}
