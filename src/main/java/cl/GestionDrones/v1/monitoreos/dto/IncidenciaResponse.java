package cl.GestionDrones.v1.monitoreos.dto;

import java.time.LocalDateTime;

public record IncidenciaResponse(
    Long id,
    Long idPlanVuelo,
    String origenReporte,
    String tipoIncidencia,
    String descripcion,
    LocalDateTime fechaHoraReporte,
    String ubicacionReferencial
) {}
