package cl.GestionDrones.v1.monitoreos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.GestionDrones.v1.monitoreos.model.Monitoreo;
import cl.GestionDrones.v1.monitoreos.service.MonitoreoService;

@RestController
@RequestMapping("/api/v1/monitoreos")
public class MonitoreosController {

    private final MonitoreoService monitoreoService;

    // Inyección por constructor
    public MonitoreosController(MonitoreoService monitoreoService) {
        this.monitoreoService = monitoreoService;
    }

    // LISTAR TODOS LOS MONITOREOS
    @GetMapping
    public ResponseEntity<List<Monitoreo>> listarMonitoreos() {

        List<Monitoreo> monitoreos = monitoreoService.obtenerTodos();

        // IF: Si no existen registros de monitoreo
        if (monitoreos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(monitoreos);
    }

    // CREAR NUEVO MONITOREO
    @PostMapping
    public ResponseEntity<Monitoreo> agregarMonitoreo(
            @RequestBody Monitoreo monitoreo) {

        // IF: Validación del ID del plan de vuelo
        if (monitoreo.getPlanVueloId() <= 0) {
            return ResponseEntity.badRequest().build();
        }

        // IF: Validación del ID de empresa proveedora
        if (monitoreo.getEmpresaProveedoraId() <= 0) {
            return ResponseEntity.badRequest().build();
        }

        // IF: Validación de región vacía
        if (monitoreo.getRegion() == null || monitoreo.getRegion().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Monitoreo nuevoMonitoreo =
                monitoreoService.guardar(monitoreo);

        // IF: Error interno al guardar
        if (nuevoMonitoreo == null) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevoMonitoreo);
    }

    // BUSCAR MONITOREO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Monitoreo> buscarMonitoreo(
            @PathVariable int id) {

        // IF: Validación del ID
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Monitoreo monitoreo =
                monitoreoService.obtenerPorId(id);

        // IF: No encontrado
        if (monitoreo == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(monitoreo);
    }

    // ACTUALIZAR MONITOREO
    @PutMapping("/{id}")
    public ResponseEntity<Monitoreo> actualizarMonitoreo(
            @PathVariable int id,
            @RequestBody Monitoreo monitoreo) {

        // IF: Validación ID URL
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        monitoreo.setId(id);

        Monitoreo monitoreoActualizado =
                monitoreoService.actualizar(monitoreo);

        // IF: No existe el monitoreo
        if (monitoreoActualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(monitoreoActualizado);
    }

    // ELIMINAR MONITOREO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMonitoreo(
            @PathVariable int id) {

        // IF: Validación del ID
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        monitoreoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    // TOTAL DE MONITOREOS
    @GetMapping("/total")
    public ResponseEntity<Integer> totalMonitoreos() {

        int total = monitoreoService.totalMonitoreos();

        // IF: Error lógico en conteo
        if (total < 0) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(0);
        }

        return ResponseEntity.ok(total);
    }

    // BUSCAR POR PLAN DE VUELO
    @GetMapping("/plan-vuelo/{planVueloId}")
    public ResponseEntity<List<Monitoreo>> buscarPorPlanVuelo(
            @PathVariable int planVueloId) {

        // IF: Validación del ID
        if (planVueloId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        List<Monitoreo> monitoreos =
                monitoreoService.obtenerPorPlanVuelo(planVueloId);

        // IF: Sin registros
        if (monitoreos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(monitoreos);
    }
}