package cl.GestionDrones.v1.monitoreos.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

import cl.GestionDrones.v1.monitoreos.model.Monitoreo;
import cl.GestionDrones.v1.monitoreos.dto.*;
import cl.GestionDrones.v1.monitoreos.exception.ResourceNotFoundException;
import cl.GestionDrones.v1.monitoreos.mapper.MonitoreosMapper;
import cl.GestionDrones.v1.monitoreos.repository.MonitoreosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MonitoreoService {

    private final MonitoreosRepository monitoreoRepository;
    private final WebClient planesVueloWebClient;
    private final WebClient incidenciasWebClient;

    @Autowired
    public MonitoreoService(
            MonitoreosRepository monitoreoRepository,
            @Qualifier("planesVueloWebClient") WebClient planesVueloWebClient,
            @Qualifier("incidenciasWebClient") WebClient incidenciasWebClient) {
        this.monitoreoRepository = monitoreoRepository;
        this.planesVueloWebClient = planesVueloWebClient;
        this.incidenciasWebClient = incidenciasWebClient;
    }

    public MonitoreoEnriquecidoResponse obtenerMonitoreoCompleto(Long id) {
        Monitoreo m = monitoreoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monitoreo no encontrado con ID: " + id));

        PlanesDeVuelosResponse plan = null;
        try {
            plan = planesVueloWebClient.get()
                    .uri("/{id}", m.getPlanVueloId())
                    .retrieve()
                    .bodyToMono(PlanesDeVuelosResponse.class)
                    .block();
        } catch (Exception e) {
            System.out.println("🔴 Error al conectar con Planes de Vuelo: " + e.getMessage());
        }

        List<IncidenciaResponse> incidencias = Collections.emptyList();
        try {
            incidencias = incidenciasWebClient.get()
                    .uri("/plan-vuelo/{id}", m.getPlanVueloId())
                    .retrieve()
                    .bodyToFlux(IncidenciaResponse.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            System.out.println("🔴 Error al conectar con Incidencias: " + e.getMessage());
        }

        return new MonitoreoEnriquecidoResponse(
                m.getId(), 
                m.getPlanVueloId(), 
                m.getEmpresaProveedoraId(), 
                m.getRegion(),
                m.getFechaHoraMonitoreo(), 
                m.getTipoOperacion(), 
                m.getEstadoVuelo(),
                m.isEsZonaUrbana(), 
                m.getZonaRestringidaId(), 
                plan, 
                incidencias
        );
    }

    public List<Monitoreo> obtenerTodos() {
        return monitoreoRepository.findAll();
    }

    public Monitoreo guardar(CreateMonitoreoRequest request) {
        if (request == null) return null;
        
        Monitoreo monitoreo = MonitoreosMapper.toEntity(request);
        
        return monitoreoRepository.save(monitoreo);
    }

    public Monitoreo obtenerPorId(Long id) {
        if (id == null || id <= 0) return null;
        return monitoreoRepository.findById(id).orElse(null);
    }

    public Monitoreo actualizar(UpdateMonitoreoRequest request) {
        if (request == null || request.id() == null || request.id() <= 0) {
            return null;
        }
        
        if (monitoreoRepository.existsById(request.id())) {
            Monitoreo monitoreoActualizado = MonitoreosMapper.toMonitoreo(request);
            
            return monitoreoRepository.save(monitoreoActualizado);
        }
        
        return null;
    }

    public String eliminar(Long id) {
        if (id != null && monitoreoRepository.existsById(id)) {
            monitoreoRepository.deleteById(id);
            return "Monitoreo eliminado correctamente.";
        }
        return "No se encontró el monitoreo con el ID proporcionado.";
    }

    public Long totalMonitoreos() {
        return monitoreoRepository.count();
    }

    public List<Monitoreo> obtenerPorPlanVuelo(Long planVueloId) {
        if (planVueloId == null || planVueloId <= 0) return new ArrayList<>();
        return monitoreoRepository.findByPlanVueloId(planVueloId);
    }

    public List<Monitoreo> obtenerPorEmpresa(Long empresaProveedoraId) {
        if (empresaProveedoraId == null || empresaProveedoraId <= 0) return new ArrayList<>();
        return monitoreoRepository.findByEmpresaProveedoraId(empresaProveedoraId);
    }
}