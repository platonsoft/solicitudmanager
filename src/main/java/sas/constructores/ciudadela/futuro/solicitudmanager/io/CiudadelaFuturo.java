package sas.constructores.ciudadela.futuro.solicitudmanager.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CiudadelaFuturo {
    private String nombre;
    private BigDecimal ce;
    private BigDecimal gr;
    private BigDecimal ar;
    private BigDecimal ma;
    private BigDecimal ad;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaEntrega;
}
