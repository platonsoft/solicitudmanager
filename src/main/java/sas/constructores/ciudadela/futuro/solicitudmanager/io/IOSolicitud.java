package sas.constructores.ciudadela.futuro.solicitudmanager.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


public class IOSolicitud {

    IOSolicitud() {
        throw new IllegalStateException("Clase comunicacion de servicios");
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{
        private ConstruccionENUM tipoConstruccion;
        private BigDecimal coordX;
        private BigDecimal coordY;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private String resultado;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RequestMateriales{
        private BigDecimal ce;
        private BigDecimal gr;
        private BigDecimal ar;
        private BigDecimal ma;
        private BigDecimal ad;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseConsulta{
        private String nombre;
        private BigDecimal ce;
        private BigDecimal gr;
        private BigDecimal ar;
        private BigDecimal ma;
        private BigDecimal ad;
        private LocalDateTime fechaInicio;
        private LocalDateTime fechaEntrega;
        private List<ResponseResumenPorTipo> ordenes;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseResumenPorTipo{
        private ConstruccionENUM tipo;
        private List<OrdenConstruccion> ordenes;
        private List<OrdenConstruccion> ordenesPendientes;
        private List<OrdenConstruccion> ordenesEnProceso;
        private List<OrdenConstruccion> ordenesTerminadas;
        private BigDecimal totalPendientes;
        private BigDecimal totalEnProceso;
        private BigDecimal totalTerminadas;
    }

}
