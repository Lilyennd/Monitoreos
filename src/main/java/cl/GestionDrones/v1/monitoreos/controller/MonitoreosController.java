package cl.GestionDrones.v1.monitoreos.controller;

//import cl.GestionDrones.v1.monitoreos.dto.UpdateMonitoreoRequest;
import cl.GestionDrones.v1.monitoreos.model.Monitoreo;
import cl.GestionDrones.v1.monitoreos.service.MonitoreoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/monitoreos")
public class MonitoreosController {

    @Autowired
    private MonitoreoService monitoreoService;

    @GetMapping
    public ResponseEntity<List<Monitoreo>> getAllMonitoreos() {
        return new ResponseEntity<>(
                monitoreoService.obtenerTodos(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMonitoreoById(@PathVariable Integer id) {

        Monitoreo monitoreo = monitoreoService.obtenerPorId(id);

        if (monitoreo == null) {

            Map<String, String> error = new HashMap<>();
            error.put("error", "No encontrado");
            error.put("mensaje",
                    "No existe el monitoreo con ID: " + id);

            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(monitoreo, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createMonitoreo(
            @Valid @RequestBody Monitoreo monitoreo,
            BindingResult result) {

        if (result.hasErrors()) {

            Map<String, String> errores = new HashMap<>();

            result.getFieldErrors().forEach(error ->
                    errores.put(
                            error.getField(),
                            error.getDefaultMessage()
                    )
            );

            return new ResponseEntity<>(
                    errores,
                    HttpStatus.BAD_REQUEST
            );
        }

        Monitoreo nuevoMonitoreo =
                monitoreoService.guardar(monitoreo);

        return new ResponseEntity<>(
                nuevoMonitoreo,
                HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<?> updateMonitoreo(@Valid @RequestBody Monitoreo monitoreo, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> 
                errores.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        Monitoreo monitoreoActualizado = monitoreoService.actualizar(monitoreo);
        return new ResponseEntity<>(monitoreoActualizado, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMonitoreo(
            @PathVariable Integer id) {

        String resultado =
                monitoreoService.eliminar(id);

        Map<String, String> respuestaJson =
                new HashMap<>();

        if (resultado == null) {

            respuestaJson.put("error", "No encontrado");

            respuestaJson.put(
                    "mensaje",
                    "No se puede eliminar. No existe el monitoreo con ID: " + id
            );

            return new ResponseEntity<>(
                    respuestaJson,
                    HttpStatus.NOT_FOUND
            );
        }

        respuestaJson.put("mensaje", resultado);

        return new ResponseEntity<>(
                respuestaJson,
                HttpStatus.OK
        );
    }


    @GetMapping("/region/{region}")
    public ResponseEntity<?> getMonitoreosPorRegion(
            @PathVariable String region) {

        List<Monitoreo> monitoreos =
                monitoreoService.obtenerPorRegion(region);

        if (monitoreos.isEmpty()) {

            Map<String, String> error = new HashMap<>();

            error.put("error", "No encontrado");

            error.put(
                    "mensaje",
                    "No existen monitoreos registrados para la región: " + region
            );

            return new ResponseEntity<>(
                    error,
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                monitoreos,
                HttpStatus.OK
        );
    }


    @GetMapping("/estado/{estadoVuelo}")
    public ResponseEntity<?> getMonitoreosPorEstado(
            @PathVariable String estadoVuelo) {

        List<Monitoreo> monitoreos =
                monitoreoService.obtenerPorEstado(estadoVuelo);

        if (monitoreos.isEmpty()) {

            Map<String, String> error = new HashMap<>();

            error.put("error", "No encontrado");

            error.put(
                    "mensaje",
                    "No existen monitoreos con estado: " + estadoVuelo
            );

            return new ResponseEntity<>(
                    error,
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                monitoreos,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Integer> getTotalMonitoreos() {

        return new ResponseEntity<>(
                monitoreoService.totalMonitoreos(),
                HttpStatus.OK
        );
    }
}