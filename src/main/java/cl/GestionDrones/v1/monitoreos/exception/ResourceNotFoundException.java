package cl.GestionDrones.v1.monitoreos.exception;


public class ResourceNotFoundException extends RuntimeException{
         public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
    
    public ResourceNotFoundException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

