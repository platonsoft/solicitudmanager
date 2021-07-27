package sas.constructores.ciudadela.futuro.solicitudmanager.services;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import sas.constructores.ciudadela.futuro.solicitudmanager.entities.OrdenConstruccionEntity;
import sas.constructores.ciudadela.futuro.solicitudmanager.io.EstadoSolicitudENUM;
import sas.constructores.ciudadela.futuro.solicitudmanager.repository.CiudadelaFuturoDAO;
import sas.constructores.ciudadela.futuro.solicitudmanager.repository.OrdenConstruccionDAO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;


@Service
@Data
public class ValidacionDiariaJobService {
    private static final Logger logger = LogManager.getLogger(ValidacionDiariaJobService.class);

    private final OrdenConstruccionDAO ordenConstruccionDAO;
    private final CiudadelaFuturoDAO ciudadelaFuturoDAO;

    private AtomicInteger count = new AtomicInteger();

    public void executeValidacionDiariaJob() {
        logger.info("Inicializando validacion diaria No. {}.", getNumberOfInvocations());
        try {
            List<OrdenConstruccionEntity> list = ordenConstruccionDAO.findAll();

            for (int i = 0; i < list.size(); i++) {
                if (Objects.equals(list.get(i).getEstado(), EstadoSolicitudENUM.PENDIENTE)
                        && list.get(i).getFechaInicio().isBefore(LocalDateTime.now())){
                    list.get(i).setEstado(EstadoSolicitudENUM.EN_PROGRESO);
                    logger.info("Se cambio la solicitud {} a {}", list.get(i).getNroOrden(), EstadoSolicitudENUM.EN_PROGRESO.getEstado());
                }else if (Objects.equals(list.get(i).getEstado(), EstadoSolicitudENUM.EN_PROGRESO)
                        && list.get(i).getFechaFinal().isBefore(LocalDateTime.now())){
                    list.get(i).setEstado(EstadoSolicitudENUM.TERMINADA);
                    logger.info("Se cambio la solicitud {} a {}", list.get(i).getNroOrden(), EstadoSolicitudENUM.TERMINADA.getEstado());
                }else {
                    logger.info("No tiene solicitudes pendientes");
                }
            }

            ordenConstruccionDAO.saveAll(list);

            logger.info("El job de validacion ha finalizado");
        } catch (Exception e) {
            logger.error("Error ejecutando el job", e);
        } finally {
            count.incrementAndGet();
            logger.info("Finalizando el Job");
        }
    }

    public int getNumberOfInvocations() {
        return count.get();
    }

}
