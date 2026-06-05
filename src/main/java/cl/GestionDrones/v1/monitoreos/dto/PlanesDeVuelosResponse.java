package cl.GestionDrones.v1.monitoreos.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record PlanesDeVuelosResponse(
    Long id,
    String runPiloto,
    String patenteDron,
    LocalDate fechaEstimadaVuelo,
    LocalTime horaInicio,
    LocalTime horaFin,
    String coordenadasOrigen,
    String coordenadasDestino,
    Double altitudMaximaMt,
    String rutContratista
) {}
