// Generated with g9.

package com.altioracorp.pedidos.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.altioracorp.pedidos.entity.Detalle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetalleDTO implements Serializable {
	private static final long serialVersionUID = 1L;
    
    @NotBlank
    private Integer idOrden;    
    private List<Detalle> detalles;

}
