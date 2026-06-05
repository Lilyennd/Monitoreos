package cl.GestionDrones.v1.monitoreos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CreateMonitoreoRequest(

    // Nota: Si el ID es autogenerado por la BD, normalmente no lo necesitas aquí.
    // Pero si lo envías, debe ser @NotNull, no @NotBlank.
    @NotNull(message = "El ID es obligatorio") 
    Long id,
    
    @NotNull(message = "El ID del plan no puede ser nulo") 
    Long planVueloId,    
    
    @NotNull(message = "El ID de la empresa no puede ser nulo") 
    Long empresaProveedoraId,  
    
    @NotBlank(message = "La region no puede estar vacía") 
    String region,
    
    @NotNull(message = "La fecha es obligatoria") 
    LocalDateTime fechaHoraMonitoreo,
    
    @NotBlank(message = "El tipo de operación no puede estar vacío") 
    String tipoOperacion,
    
    @NotBlank(message = "El estado de vuelo es obligatorio") 
    String estadoVuelo,

    // boolean no puede ser nulo, @NotNull aquí es opcional pero ayuda a la claridad
    boolean esZonaUrbana,

    @NotNull(message = "El ID de la zona es obligatorio") 
    Long zonaRestringidaId

) {}



