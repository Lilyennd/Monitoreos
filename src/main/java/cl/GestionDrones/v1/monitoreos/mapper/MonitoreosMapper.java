package cl.GestionDrones.v1.monitoreos.mapper;

import cl.GestionDrones.v1.monitoreos.dto.CreateMonitoreoRequest;
import cl.GestionDrones.v1.monitoreos.dto.UpdateMonitoreoRequest;
import cl.GestionDrones.v1.monitoreos.model.Monitoreo;

public class MonitoreosMapper {

    public static Monitoreo toEntity(CreateMonitoreoRequest request) {
        Monitoreo monitoreo = new Monitoreo();
        
        monitoreo.setId(request.id());
        monitoreo.setPlanVueloId(request.planVueloId()); 
        monitoreo.setEmpresaProveedoraId(request.empresaProveedoraId()); 
        monitoreo.setRegion(request.region());
        monitoreo.setFechaHoraMonitoreo(request.fechaHoraMonitoreo());
        monitoreo.setTipoOperacion(request.tipoOperacion());
        monitoreo.setEstadoVuelo(request.estadoVuelo());
        monitoreo.setEsZonaUrbana(request.esZonaUrbana());
        monitoreo.setZonaRestringidaId(request.zonaRestringidaId());
        
        return monitoreo;
    }
    

    public static Monitoreo toMonitoreo(UpdateMonitoreoRequest request) {
        Monitoreo monitoreo = new Monitoreo();
        
        monitoreo.setId(request.id());
        monitoreo.setPlanVueloId(request.planVueloId());
        monitoreo.setEmpresaProveedoraId(request.empresaProveedoraId());
        monitoreo.setFechaHoraMonitoreo(request.fechaHoraMonitoreo());
        monitoreo.setTipoOperacion(request.tipoOperacion());
        monitoreo.setEstadoVuelo(request.estadoVuelo());
        monitoreo.setEsZonaUrbana(request.esZonaUrbana());
        monitoreo.setZonaRestringidaId(request.zonaRestringidaId());
        
        return monitoreo;
    }

}
