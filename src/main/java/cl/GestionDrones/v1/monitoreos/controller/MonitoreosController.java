package cl.GestionDrones.v1.monitoreos.controller;

import cl.GestionDrones.v1.monitoreos.dto.CreateMonitoreoRequest;
import cl.GestionDrones.v1.monitoreos.dto.MonitoreoEnriquecidoResponse;
import cl.GestionDrones.v1.monitoreos.dto.UpdateMonitoreoRequest;
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
@RequestMapping("/api/v1/monitoreos")
public class MonitoreosController {

    @Autowired
    private MonitoreoService monitoreoService;

    // 1. Endpoint Orquestador
    @GetMapping("/{id}/completo")
    public ResponseEntity<?> getMonitoreoCompleto(@PathVariable Long id) {
        MonitoreoEnriquecidoResponse respuesta = monitoreoService.obtenerMonitoreoCompleto(id);
        if (respuesta == null) {
            return buildErrorResponse("No encontrado", "No se pudo generar el monitoreo completo para el ID: " + id, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(respuesta);
    }

    // 2. Endpoints CRUD básicos
    @GetMapping
    public ResponseEntity<List<Monitoreo>> getAllMonitoreos() {
        List<Monitoreo> lista = monitoreoService.obtenerTodos();
        if (lista.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMonitoreoById(@PathVariable Long id) {
        Monitoreo monitoreo = monitoreoService.obtenerPorId(id);
        if (monitoreo == null) {
            return buildErrorResponse("No encontrado", "No existe el monitoreo con ID: " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(monitoreo, HttpStatus.OK);
    }
    
    @PostMapping
public ResponseEntity<?> createMonitoreo(@Valid @RequestBody CreateMonitoreoRequest request, BindingResult result) {
    if (result.hasErrors()) return getValidationErrorResponse(result);
    
    // Le pasamos el objeto request (DTO) al servicio
    Monitoreo nuevo = monitoreoService.guardar(request);
    
    if (nuevo == null) {
        return buildErrorResponse("Error", "No se pudo crear el monitoreo", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
}

    @PutMapping
public ResponseEntity<?> updateMonitoreo(@Valid @RequestBody UpdateMonitoreoRequest request, BindingResult result) {
    if (result.hasErrors()) return getValidationErrorResponse(result);
    
    // Enviamos el DTO de actualización al servicio
    Monitoreo updated = monitoreoService.actualizar(request);
    
    if (updated == null) {
        return buildErrorResponse("Error", "No se pudo actualizar, el ID no existe o es inválido", HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(updated);
}

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMonitoreo(@PathVariable Long id) {
        String resultado = monitoreoService.eliminar(id);
        if (resultado.contains("No se encontró")) {
            return buildErrorResponse("No encontrado", resultado, HttpStatus.NOT_FOUND);
        }
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", resultado);
        return ResponseEntity.ok(respuesta);
    }

    // 3. Endpoints de Búsqueda Personalizada
    @GetMapping("/total")
    public ResponseEntity<Long> getTotalMonitoreos() {
        return ResponseEntity.ok(monitoreoService.totalMonitoreos());
    }

    @GetMapping("/plan-vuelo/{planVueloId}")
    public ResponseEntity<?> getMonitoreosPorPlanVuelo(@PathVariable Long planVueloId) {
        List<Monitoreo> lista = monitoreoService.obtenerPorPlanVuelo(planVueloId);
        if (lista.isEmpty()) return buildErrorResponse("No encontrado", "No hay monitoreos para el plan de vuelo: " + planVueloId, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<?> getMonitoreosPorEmpresa(@PathVariable Long empresaId) {
        List<Monitoreo> lista = monitoreoService.obtenerPorEmpresa(empresaId);
        if (lista.isEmpty()) return buildErrorResponse("No encontrado", "No hay monitoreos para la empresa: " + empresaId, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(lista);
    }

    // --- Métodos de apoyo ---
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