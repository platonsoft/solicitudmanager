package sas.constructores.ciudadela.futuro.solicitudmanager.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ciudadela_futuro")
public class CiudadelaFuturoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCiudadelaFuturo;
    private String nombre = "CIUDADELA FUTURO";
    private BigDecimal ce = BigDecimal.valueOf(1000);
    private BigDecimal gr = BigDecimal.valueOf(1000);
    private BigDecimal ar = BigDecimal.valueOf(1000);
    private BigDecimal ma = BigDecimal.valueOf(1000);
    private BigDecimal ad = BigDecimal.valueOf(1000);
    private LocalDateTime fechaInicio = LocalDateTime.now();
    private LocalDateTime fechaEntrega;
    private LocalDateTime fechaRegistro = LocalDateTime.now();
}
