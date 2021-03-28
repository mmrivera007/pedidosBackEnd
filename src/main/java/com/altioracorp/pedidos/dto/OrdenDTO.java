package com.altioracorp.pedidos.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrdenDTO implements Serializable {
	private static final long serialVersionUID = 1L;
    
    private Integer id;
    private Date fecha;
    
    private ClienteDTO cliente;
}
