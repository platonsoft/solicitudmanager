package sas.constructores.ciudadela.futuro.solicitudmanager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sas.constructores.ciudadela.futuro.solicitudmanager.io.IOSolicitud;
import sas.constructores.ciudadela.futuro.solicitudmanager.services.IConstruccionesService;

import javax.servlet.http.HttpServletRequest;

@RestController
@Data
@RequestMapping("/api")
@Api(tags = { "SolicitudManager" })
public class SolicitudManager {

    private final IConstruccionesService iConstruccionesService;

    @GetMapping("/solmanager")
    @ApiOperation(value = "Devolvera la informacion completa de la gestion")
    public ResponseEntity<IOSolicitud.ResponseConsulta> getDataProyecto () {
        return new ResponseEntity<>(iConstruccionesService.consulta(), HttpStatus.OK);
    }

    @GetMapping("/solmanager/fecha/entrega")
    @ApiOperation(value = "Devolvera los datos de una solicitud")
    public ResponseEntity<String> getFechaEntrega () {
        return new ResponseEntity<>(iConstruccionesService.consultaSoloFecha(), HttpStatus.OK);
    }

    @GetMapping("/solmanager/informe/txt")
    @ApiOperation(value = "Devolvera un archivo en .txt con el resumen de la informacion")
    public  ResponseEntity<Resource> getInformeTxt(HttpServletRequest request) {
        return iConstruccionesService.consultaFile(request);
    }

    @PostMapping("/solmanager/solicitud")
    @ApiOperation(value = "Crea una solicitud a partir del tipo de construccion y las coordenadas(X,Y)")
    public ResponseEntity<IOSolicitud.Response> addSolicitud (@RequestBody IOSolicitud.Request request) {
        return new ResponseEntity<>(iConstruccionesService.crearOrdenConstruccion(request), HttpStatus.OK);
    }

    @PutMapping("/solmanager/update")
    @ApiOperation(value = "Recarga los materiales de construccion de la empresa")
    public ResponseEntity<IOSolicitud.Response> updateMateriales(@RequestBody IOSolicitud.RequestMateriales request) {
        return new ResponseEntity<>(iConstruccionesService.updateMateriales(request), HttpStatus.OK);
    }
}
