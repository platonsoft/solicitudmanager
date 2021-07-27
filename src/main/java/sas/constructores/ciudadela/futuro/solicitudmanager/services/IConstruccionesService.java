package sas.constructores.ciudadela.futuro.solicitudmanager.services;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import sas.constructores.ciudadela.futuro.solicitudmanager.io.IOSolicitud;

import javax.servlet.http.HttpServletRequest;

public interface IConstruccionesService {
    IOSolicitud.Response crearOrdenConstruccion(IOSolicitud.Request request);
    IOSolicitud.ResponseConsulta consulta();
    ResponseEntity<Resource> consultaFile(HttpServletRequest request);
    String consultaSoloFecha();
    IOSolicitud.Response updateMateriales(IOSolicitud.RequestMateriales request);
}
