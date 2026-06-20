package cl.GestionDrones.v1.monitoreos.controller;

import cl.GestionDrones.v1.monitoreos.dto.CreateMonitoreoRequest;
import cl.GestionDrones.v1.monitoreos.dto.MonitoreoEnriquecidoResponse;
import cl.GestionDrones.v1.monitoreos.dto.UpdateMonitoreoRequest;
import cl.GestionDrones.v1.monitoreos.model.Monitoreo;
import cl.GestionDrones.v1.monitoreos.service.MonitoreoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Monitoreos", description = "Operaciones relacionadas con el monitoreo de vuelos de drones")
@RestController
@RequestMapping("/api/v1/monitoreos")
public class MonitoreosController {

    @Autowired
    private MonitoreoService monitoreoService;

    @Operation(summary = "Obtener monitoreo completo", description = "Retorna un monitoreo enriquecido orquestando información de múltiples microservicios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Información del monitoreo completo obtenida con éxito",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = MonitoreoEnriquecidoResponse.class))),
        @ApiResponse(responseCode = "404", description = "No se pudo generar el monitoreo para el ID provisto")
    })
    @GetMapping("/{id}/completo")
    public ResponseEntity<?> getMonitoreoCompleto(
        @Parameter(description = "ID único del monitoreo a consultar", required = true, example = "1")
        @PathVariable Long id
    ) {
        MonitoreoEnriquecidoResponse respuesta = monitoreoService.obtenerMonitoreoCompleto(id);
        if (respuesta == null) {
            return buildErrorResponse("No encontrado", "No se pudo generar el monitoreo completo para el ID: " + id, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(respuesta);
    }


    @Operation(summary = "Obtener todos los monitoreos", description = "Retorna una lista completa de todos los monitoreos registrados en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de monitoreos obtenida con éxito",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = Monitoreo.class))),
        @ApiResponse(responseCode = "204", description = "No hay monitoreos registrados en el sistema")
    })
    @GetMapping
    public ResponseEntity<List<Monitoreo>> getAllMonitoreos() {
        List<Monitoreo> lista = monitoreoService.obtenerTodos();
        if (lista.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @Operation(summary = "Obtener monitoreo por ID", description = "Busca y retorna los detalles de un registro de monitoreo individual")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Monitoreo encontrado exitosamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = Monitoreo.class))),
        @ApiResponse(responseCode = "404", description = "No se encontró ningún monitoreo con el ID especificado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getMonitoreoById(
        @Parameter(description = "Identificador único de monitoreo", required = true, example = "1")
        @PathVariable Long id
    ) {
        Monitoreo monitoreo = monitoreoService.obtenerPorId(id);
        if (monitoreo == null) {
            return buildErrorResponse("No encontrado", "No existe el monitoreo con ID: " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(monitoreo, HttpStatus.OK);
    }

    @Operation(summary = "Crear un nuevo monitoreo", description = "Registra un nuevo monitoreo en el sistema validando previamente sus campos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Monitoreo creado con éxito",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = Monitoreo.class))),
        @ApiResponse(responseCode = "400", description = "Parámetros o formato de datos incorrectos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al intentar persistir los datos")
    })
    @PostMapping
    public ResponseEntity<?> createMonitoreo(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Estructura JSON del nuevo monitoreo a registrar", required = true)
        @Valid @RequestBody CreateMonitoreoRequest request, 
        BindingResult result
    ) {
        if (result.hasErrors()) return getValidationErrorResponse(result);
        
        Monitoreo nuevo = monitoreoService.guardar(request);
        
        if (nuevo == null) {
            return buildErrorResponse("Error", "No se pudo crear el monitoreo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar monitoreo", description = "Modifica los datos de un registro de monitoreo existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Monitoreo modificado correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = Monitoreo.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos para realizar la modificación"),
        @ApiResponse(responseCode = "404", description = "No se pudo actualizar debido a que el ID provisto no existe")
    })
    @PutMapping
    public ResponseEntity<?> updateMonitoreo(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Estructura JSON con los datos del monitoreo a actualizar", required = true)
        @Valid @RequestBody UpdateMonitoreoRequest request, 
        BindingResult result
    ) {
        if (result.hasErrors()) return getValidationErrorResponse(result);
        
        Monitoreo updated = monitoreoService.actualizar(request);
        
        if (updated == null) {
            return buildErrorResponse("Error", "No se pudo actualizar, el ID no existe o es inválido", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Eliminar monitoreo", description = "Elimina permanentemente un monitoreo del sistema usando su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Monitoreo eliminado de forma exitosa"),
        @ApiResponse(responseCode = "404", description = "El monitoreo con el ID especificado no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMonitoreo(
        @Parameter(description = "ID del monitoreo a eliminar", required = true, example = "1")
        @PathVariable Long id
    ) {
        String resultado = monitoreoService.eliminar(id);
        if (resultado.contains("No se encontró")) {
            return buildErrorResponse("No encontrado", resultado, HttpStatus.NOT_FOUND);
        }
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", resultado);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Obtener total de monitoreos", description = "Devuelve un conteo numérico absoluto de todos los registros de monitoreo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cantidad total recuperada exitosamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class)))
    })
    @GetMapping("/total")
    public ResponseEntity<Long> getTotalMonitoreos() {
        return ResponseEntity.ok(monitoreoService.totalMonitoreos());
    }

    @Operation(summary = "Obtener monitoreos por Plan de Vuelo", description = "Lista todos los monitoreos asociados a un Plan de Vuelo específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registros obtenidos correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = Monitoreo.class))),
        @ApiResponse(responseCode = "404", description = "No existen monitoreos registrados para ese plan de vuelo")
    })
    @GetMapping("/plan-vuelo/{planVueloId}")
    public ResponseEntity<?> getMonitoreosPorPlanVuelo(
        @Parameter(description = "ID del plan de vuelo de referencia", required = true, example = "101")
        @PathVariable Long planVueloId
    ) {
        List<Monitoreo> lista = monitoreoService.obtenerPorPlanVuelo(planVueloId);
        if (lista.isEmpty()) return buildErrorResponse("No encontrado", "No hay monitoreos para el plan de vuelo: " + planVueloId, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener monitoreos por Empresa", description = "Lista todos los monitoreos asociados a una Empresa Proveedora")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registros obtenidos correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = Monitoreo.class))),
        @ApiResponse(responseCode = "404", description = "No existen monitoreos registrados para esa empresa")
    })
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<?> getMonitoreosPorEmpresa(
        @Parameter(description = "ID de la empresa proveedora de referencia", required = true, example = "45")
        @PathVariable Long empresaId
    ) {
        List<Monitoreo> lista = monitoreoService.obtenerPorEmpresa(empresaId);
        if (lista.isEmpty()) return buildErrorResponse("No encontrado", "No hay monitoreos para la empresa: " + empresaId, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(lista);
    }

    private ResponseEntity<Map<String, String>> getValidationErrorResponse(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Map<String, String>> buildErrorResponse(String error, String mensaje, HttpStatus status) {
        Map<String, String> body = new HashMap<>();
        body.put("error", error);
        body.put("mensaje", mensaje);
        return new ResponseEntity<>(body, status);
    }
}