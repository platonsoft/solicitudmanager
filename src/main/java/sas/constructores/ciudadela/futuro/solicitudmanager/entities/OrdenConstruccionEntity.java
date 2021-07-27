package sas.constructores.ciudadela.futuro.solicitudmanager.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sas.constructores.ciudadela.futuro.solicitudmanager.io.ConstruccionENUM;
import sas.constructores.ciudadela.futuro.solicitudmanager.io.EstadoSolicitudENUM;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ordenes_construccion")
public class OrdenConstruccionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long nroOrden;
    @Enumerated(EnumType.ORDINAL)
    private EstadoSolicitudENUM estado;
    @Enumerated(EnumType.ORDINAL)
    private ConstruccionENUM tipo;
    private BigDecimal coordenadaX;
    private BigDecimal coordenadaY;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFinal;
}
