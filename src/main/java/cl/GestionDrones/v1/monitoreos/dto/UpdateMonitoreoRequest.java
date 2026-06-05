package cl.GestionDrones.v1.monitoreos.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateMonitoreoRequest (

    @NotNull(message = "El ID no puede ser nulo") 
    Long id,
    
    @NotNull(message = "El ID del plan no puede ser nulo") 
    Long planVueloId,    
    
    @NotNull(message = "El ID de la empresa no puede ser nulo") 
    Long empresaProveedoraId,  
    
    @NotBlank(message = "La region no puede estar vacía") 
    String region,
    
    @NotNull(message = "La fecha no puede estar vacía") 
    LocalDateTime fechaHoraMonitoreo,
    
    @NotBlank(message = "El tipo de operación no puede estar vacío") 
    String tipoOperacion,
    
    @NotBlank(message = "El estado de vuelo es obligatorio") 
    String estadoVuelo,

    @NotNull(message = "El tipo de zona es obligatorio") 
    boolean esZonaUrbana,

    @NotNull(message = "El ID de la zona es obligatorio") 
    Long zonaRestringidaId


){}
