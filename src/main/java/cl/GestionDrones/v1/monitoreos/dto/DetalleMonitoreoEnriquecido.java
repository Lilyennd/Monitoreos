package cl.GestionDrones.v1.monitoreos.dto;

import java.util.List;

public record DetalleMonitoreoEnriquecido(
        PlanesDeVuelosResponse planVuelo,
        List<IncidenciaResponse> incidencias // Usamos List porque un vuelo puede tener múltiples reportes
) {}
