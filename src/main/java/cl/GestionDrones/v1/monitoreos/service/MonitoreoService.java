package cl.GestionDrones.v1.monitoreos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.GestionDrones.v1.monitoreos.model.Monitoreo;

@Service
public class MonitoreoService {

    
    private final List<Monitoreo> listaMonitoreos = new ArrayList<>();

    
    public List<Monitoreo> obtenerTodos() {
        return listaMonitoreos;
    }

    
    public Monitoreo guardar(Monitoreo monitoreo) {

        
        if (monitoreo == null) {
            return null;
        }

        listaMonitoreos.add(monitoreo);

        return monitoreo;
    }

    
    public Monitoreo obtenerPorId(int id) {

        
        if (id <= 0) {
            return null;
        }

        for (Monitoreo monitoreo : listaMonitoreos) {

            
            if (monitoreo.getId() == id) {
                return monitoreo;
            }
        }

        return null;
    }

    
    public Monitoreo actualizar(Monitoreo monitoreoActualizado) {

         
        if (monitoreoActualizado == null ||
            monitoreoActualizado.getId() <= 0) {

            return null;
        }

        for (int i = 0; i < listaMonitoreos.size(); i++) {

            
            if (listaMonitoreos.get(i).getId()
                    == monitoreoActualizado.getId()) {

                listaMonitoreos.set(i, monitoreoActualizado);

                return monitoreoActualizado;
            }
        }

        return null;
    }

    
    public String eliminar(int id) {

        
        boolean eliminado = listaMonitoreos.removeIf(monitoreo ->
                monitoreo.getId() == id);

        if (eliminado) {
            return "Monitoreo eliminado correctamente.";
        } else {
            return "No se encontró el monitoreo con el ID proporcionado.";
        }
    }

    
    public int totalMonitoreos() {

        return listaMonitoreos.size();
    }

    
    public List<Monitoreo> obtenerPorPlanVuelo(
            int planVueloId) {

        List<Monitoreo> resultado = new ArrayList<>();

        
        if (planVueloId <= 0) {
            return resultado;
        }

        for (Monitoreo monitoreo : listaMonitoreos) {

            
            if (monitoreo.getPlanVueloId()
                    == planVueloId) {

                resultado.add(monitoreo);
            }
        }

        return resultado;
    }

    
    public List<Monitoreo> obtenerPorRegion(
            String region) {

        List<Monitoreo> resultado = new ArrayList<>();

        
        if (region == null || region.isBlank()) {
            return resultado;
        }

        for (Monitoreo monitoreo : listaMonitoreos) {

            
            if (monitoreo.getRegion()
                    .equalsIgnoreCase(region)) {

                resultado.add(monitoreo);
            }
        }

        return resultado;
    }

    
    public List<Monitoreo> obtenerZonaUrbana() {

        List<Monitoreo> resultado = new ArrayList<>();

        for (Monitoreo monitoreo : listaMonitoreos) {

            
            if (monitoreo.isEsZonaUrbana()) {
                resultado.add(monitoreo);
            }
        }

        return resultado;
    }

    
    public List<Monitoreo> obtenerPorEstado(
            String estadoVuelo) {

        List<Monitoreo> resultado = new ArrayList<>();

        
        if (estadoVuelo == null ||
            estadoVuelo.isBlank()) {

            return resultado;
        }

        for (Monitoreo monitoreo : listaMonitoreos) {

            
            if (monitoreo.getEstadoVuelo()
                    .equalsIgnoreCase(estadoVuelo)) {

                resultado.add(monitoreo);
            }
        }

        return resultado;
    }
}