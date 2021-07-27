package sas.constructores.ciudadela.futuro.solicitudmanager.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sas.constructores.ciudadela.futuro.solicitudmanager.config.LocalDateAdapter;
import sas.constructores.ciudadela.futuro.solicitudmanager.config.SolicitudManagerException;
import sas.constructores.ciudadela.futuro.solicitudmanager.entities.CiudadelaFuturoEntity;
import sas.constructores.ciudadela.futuro.solicitudmanager.entities.OrdenConstruccionEntity;
import sas.constructores.ciudadela.futuro.solicitudmanager.io.*;
import sas.constructores.ciudadela.futuro.solicitudmanager.repository.CiudadelaFuturoDAO;
import sas.constructores.ciudadela.futuro.solicitudmanager.repository.OrdenConstruccionDAO;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Data
@PropertySource("classpath:application.properties")
public class ConstruccionesService implements IConstruccionesService{
    private static final Logger logger = LogManager.getLogger(ConstruccionesService.class);

    private final OrdenConstruccionDAO ordenConstruccionDAO;
    private final CiudadelaFuturoDAO ciudadelaFuturoDAO;

    @Value("${ciudadela.nombre}")
    private String ciudadelaNombre;

    @Value("${ciudadela.ce}")
    private String ciudadelaCe;

    @Value("${ciudadela.gr}")
    private String ciudadelaGr;

    @Value("${ciudadela.ar}")
    private String ciudadelaAr;

    @Value("${ciudadela.ma}")
    private String ciudadelaMa;

    @Value("${ciudadela.ad}")
    private String ciudadelaAd;

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
            .create();

    public CiudadelaFuturoEntity getCiudadelaFuturo(){
        Optional<CiudadelaFuturoEntity> entity = ciudadelaFuturoDAO.findAll().stream().findFirst();
        return entity.orElse(ciudadelaFuturoDAO.save(CiudadelaFuturoEntity.builder()
                .fechaInicio(LocalDateTime.now())
                .nombre(this.ciudadelaNombre)
                .ad(new BigDecimal(this.ciudadelaAd))
                .ar(new BigDecimal(this.ciudadelaAr))
                .ce(new BigDecimal(this.ciudadelaCe))
                .ma(new BigDecimal(this.ciudadelaMa))
                .gr(new BigDecimal(this.ciudadelaGr))
                .fechaRegistro(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0))
                .build()));
    }

    @Override
    public IOSolicitud.Response crearOrdenConstruccion(IOSolicitud.Request request){
        try {
            logger.info("Iniciando validacion para la creacion de la solicitud");
            List<OrdenConstruccionEntity> list = ordenConstruccionDAO.findAll();
            LocalDateTime fechaInicio;
            LocalDateTime fechaFinal;

            CiudadelaFuturoEntity ciudadelaFuturoBase = getCiudadelaFuturo();
            CiudadelaFuturo ciudadelaFuturo = CiudadelaFuturo.builder()
                    .ce(BigDecimal.ZERO)
                    .ar(BigDecimal.ZERO)
                    .ma(BigDecimal.ZERO)
                    .ad(BigDecimal.ZERO)
                    .gr(BigDecimal.ZERO)
                    .fechaInicio(ciudadelaFuturoBase.getFechaInicio())
                    .build();

            //Verificamos cantidad de materiales contra lo que hay en las solicitudes y si tiene alguna construccion en la misma coordenada
            for (OrdenConstruccionEntity entity : list) {

                if (request.getCoordX().compareTo(entity.getCoordenadaX())==0 && request.getCoordY().compareTo(entity.getCoordenadaY())==0){
                    throw new SolicitudManagerException("Hay Otra construccion en las mismas coordenadas");
                }

                ciudadelaFuturo.setCe(ciudadelaFuturo.getCe().add(BigDecimal.valueOf(entity.getTipo().getCe())));
                ciudadelaFuturo.setAd(ciudadelaFuturo.getCe().add(BigDecimal.valueOf(entity.getTipo().getAd())));
                ciudadelaFuturo.setAr(ciudadelaFuturo.getCe().add(BigDecimal.valueOf(entity.getTipo().getAr())));
                ciudadelaFuturo.setGr(ciudadelaFuturo.getCe().add(BigDecimal.valueOf(entity.getTipo().getGr())));
                ciudadelaFuturo.setMa(ciudadelaFuturo.getCe().add(BigDecimal.valueOf(entity.getTipo().getMa())));
            }

            ciudadelaFuturo.setCe(ciudadelaFuturo.getCe().add(BigDecimal.valueOf(request.getTipoConstruccion().getCe())));
            ciudadelaFuturo.setAd(ciudadelaFuturo.getCe().add(BigDecimal.valueOf(request.getTipoConstruccion().getAd())));
            ciudadelaFuturo.setAr(ciudadelaFuturo.getCe().add(BigDecimal.valueOf(request.getTipoConstruccion().getAr())));
            ciudadelaFuturo.setGr(ciudadelaFuturo.getCe().add(BigDecimal.valueOf(request.getTipoConstruccion().getGr())));
            ciudadelaFuturo.setMa(ciudadelaFuturo.getCe().add(BigDecimal.valueOf(request.getTipoConstruccion().getMa())));

            if (ciudadelaFuturo.getCe().compareTo(ciudadelaFuturoBase.getCe())>0){
                throw new SolicitudManagerException("No tiene Suficiente Cemento");
            }else if (ciudadelaFuturo.getAd().compareTo(ciudadelaFuturoBase.getAd())>0){
                throw new SolicitudManagerException("No tiene Suficiente Adobe");
            }else if (ciudadelaFuturo.getAr().compareTo(ciudadelaFuturoBase.getAr())>0){
                throw new SolicitudManagerException("No tiene Suficiente Arena");
            }else if (ciudadelaFuturo.getGr().compareTo(ciudadelaFuturoBase.getGr())>0){
                throw new SolicitudManagerException("No tiene Suficiente Grava");
            }else if (ciudadelaFuturo.getMa().compareTo(ciudadelaFuturoBase.getMa())>0){
                throw new SolicitudManagerException("No tiene Suficiente Madera");
            }

            logger.info("Validacion correcta, se continua con la creacion de la orden...");

            //Si encuentra un registro toma el ultimo y le setea  y le sumamos un dia al inicio de la construccion
            Optional<OrdenConstruccionEntity> ordenConstruccionEntityOptional = list.stream().max(Comparator.comparing(OrdenConstruccionEntity::getFechaRegistro));

            fechaInicio = ordenConstruccionEntityOptional
                    .map(ordenConstruccionEntity -> ordenConstruccionEntity.getFechaFinal().withHour(0).withMinute(0).withSecond(0).withNano(0).plusDays(1))
                    .orElseGet(() -> ciudadelaFuturo.getFechaInicio().withHour(0).withMinute(0).withSecond(0).withNano(0).plusDays(1));

            fechaFinal = fechaInicio.plusDays(request.getTipoConstruccion().getDuracion());


            //Aumentamos el tiempo de entrega de la ciudadela
            ciudadelaFuturoBase.setFechaEntrega(fechaFinal);
            ciudadelaFuturoDAO.save(ciudadelaFuturoBase);

            //Guardamos la orden
            ordenConstruccionDAO.save(OrdenConstruccionEntity.builder()
                    .estado(EstadoSolicitudENUM.PENDIENTE)
                    .tipo(request.getTipoConstruccion())
                    .coordenadaX(request.getCoordX())
                    .coordenadaY(request.getCoordY())
                    .fechaRegistro(LocalDateTime.now())
                    .fechaInicio(fechaInicio)
                    .fechaFinal(fechaFinal)
                    .build());
            logger.info("La orden fue creada correctamente...");
            return IOSolicitud.Response.builder().resultado("Orden creada correctamente").build();
        }catch (Exception exception){
            logger.error(exception.getMessage());
            return IOSolicitud.Response.builder().resultado(exception.getMessage()).build();
        }
    }

    @Override
    public IOSolicitud.ResponseConsulta consulta(){
        IOSolicitud.ResponseConsulta responseConsulta = (new Gson()).fromJson((new Gson()).toJson(getCiudadelaFuturo()), IOSolicitud.ResponseConsulta.class);
        responseConsulta.setOrdenes(new ArrayList<>());

        for (ConstruccionENUM valor: ConstruccionENUM.values()) {
            responseConsulta.getOrdenes().add(getResumenPorTipo(valor));
        }
        return responseConsulta;
    }

    private IOSolicitud.ResponseResumenPorTipo getResumenPorTipo(ConstruccionENUM tipo){
        IOSolicitud.ResponseResumenPorTipo resumenPorTipo = IOSolicitud.ResponseResumenPorTipo.builder().build();
        resumenPorTipo.setOrdenes(ordenConstruccionDAO.findAll().stream()
                .filter(oc-> Objects.equals(oc.getTipo(), tipo))
                .map(orden -> OrdenConstruccion.builder()
                        .nroOrden(orden.getNroOrden())
                        .estado(orden.getEstado())
                        .tipo(orden.getTipo())
                        .fechaInicio(orden.getFechaInicio())
                        .fechaFinal(orden.getFechaFinal())
                        .fechaRegistro(orden.getFechaRegistro())
                        .build())
                .collect(Collectors.toList()));
        resumenPorTipo.setOrdenesPendientes(resumenPorTipo.getOrdenes().stream().filter(oc -> Objects.equals(oc.getEstado(),EstadoSolicitudENUM.PENDIENTE)).collect(Collectors.toList()));
        resumenPorTipo.setTotalPendientes(new BigDecimal(resumenPorTipo.getOrdenesPendientes().size()));
        resumenPorTipo.setOrdenesEnProceso(resumenPorTipo.getOrdenes().stream().filter(oc -> Objects.equals(oc.getEstado(),EstadoSolicitudENUM.EN_PROGRESO)).collect(Collectors.toList()));
        resumenPorTipo.setTotalEnProceso(new BigDecimal(resumenPorTipo.getOrdenesEnProceso().size()));
        resumenPorTipo.setOrdenesTerminadas(resumenPorTipo.getOrdenes().stream().filter(oc -> Objects.equals(oc.getEstado(),EstadoSolicitudENUM.TERMINADA)).collect(Collectors.toList()));
        resumenPorTipo.setTotalTerminadas(new BigDecimal(resumenPorTipo.getOrdenesTerminadas().size()));
        resumenPorTipo.setTipo(tipo);
        return resumenPorTipo;


    }

    @Override
    public ResponseEntity<Resource> consultaFile(HttpServletRequest request) {
        String filename = "informe.json";

        try(FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
            Path targetLocation = Paths.get(filename);

            String data = gson.toJson(consulta());
            fileOutputStream.write(data.getBytes());

            Resource resource = new UrlResource(targetLocation.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(request.getServletContext().getMimeType(resource.getFile().getAbsolutePath())))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new SolicitudManagerException("Archivo no encontrado");
            }
        }catch (Exception ex){
            logger.error(ex.getMessage());
        }

        return ResponseEntity.unprocessableEntity()
                .body(null);
    }

    @Override
    public String consultaSoloFecha() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format(Date.from(consulta().getFechaEntrega().atZone(ZoneId.systemDefault()).toInstant()));
        }catch (Exception ex){
            logger.error(ex.getMessage());
            return "No se ha establecido la fecha de entrega aun";
        }
    }

    @Override
    public IOSolicitud.Response updateMateriales(IOSolicitud.RequestMateriales request) {
        try{
            CiudadelaFuturoEntity entity = getCiudadelaFuturo();

            entity.setCe(entity.getCe().add(request.getCe()));
            entity.setAr(entity.getAr().add(request.getAr()));
            entity.setAd(entity.getAd().add(request.getAd()));
            entity.setGr(entity.getGr().add(request.getGr()));
            entity.setMa(entity.getMa().add(request.getMa()));

            ciudadelaFuturoDAO.save(entity);
            return IOSolicitud.Response.builder().resultado("Los materiales se actualizaron correctamente").build();
        }catch (Exception ex){
            logger.error(ex.getMessage());
            return IOSolicitud.Response.builder().resultado(ex.getMessage()).build();
        }
    }
}
