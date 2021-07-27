package sas.constructores.ciudadela.futuro.solicitudmanager.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdenConstruccion {
    private Long nroOrden;
    private EstadoSolicitudENUM estado;
    private ConstruccionENUM tipo;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFinal;
}
