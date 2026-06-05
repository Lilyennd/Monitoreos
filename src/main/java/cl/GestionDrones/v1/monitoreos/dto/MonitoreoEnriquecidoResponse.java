package cl.GestionDrones.v1.monitoreos.dto;

import java.time.LocalDateTime;
import java.util.List;

public record MonitoreoEnriquecidoResponse(
    Long id,
    Long planVueloId,
    Long empresaProveedoraId,
    String region,
    LocalDateTime fechaHoraMonitoreo,
    String tipoOperacion,
    String estadoVuelo,
    boolean esZonaUrbana,
    Long zonaRestringidaId,
    
    PlanesDeVuelosResponse planVuelo, 
    List<IncidenciaResponse> incidencias 
) {}
