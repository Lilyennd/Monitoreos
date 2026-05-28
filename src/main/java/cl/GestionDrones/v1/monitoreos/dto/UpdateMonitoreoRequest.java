package cl.GestionDrones.v1.monitoreos.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateMonitoreoRequest (

    @NotBlank(message = "El ID no puede estar vacío") 
    int id,
    
    @NotBlank(message = "El ID del plan no puede estar vacío") 
    int planVueloId,    
    
    @NotBlank(message = "El ID de la empresa no puede estar vacío") 
    int empresaProveedoraId,  
    
    @NotBlank(message = "La region no puede estar vacía") 
    String region,
    
    @NotBlank(message = "La fecha no puede estar vacía") 
    LocalDateTime fechaHoraMonitoreo,
    
    @NotBlank(message = "El tipo de operación no puede estar vacío") 
    String tipoOperacion,
    
    @NotNull(message = "El estado de vuelo es obligatorio") 
    String estadoVuelo,

    @NotNull(message = "El tipo de zona es obligatorio") 
    boolean esZonaUrbana,

    @NotNull(message = "El ID de la zona es obligatorio") 
    Integer zonaRestringidaId


){}
