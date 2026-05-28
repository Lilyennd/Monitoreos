package cl.GestionDrones.v1.monitoreos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.GestionDrones.v1.monitoreos.model.Monitoreo;

@Service
public class MonitoreoService {

    // Simulación de base de datos en memoria
    private final List<Monitoreo> listaMonitoreos = new ArrayList<>();

    // OBTENER TODOS LOS MONITOREOS
    public List<Monitoreo> obtenerTodos() {
        return listaMonitoreos;
    }

    // GUARDAR MONITOREO
    public Monitoreo guardar(Monitoreo monitoreo) {

        // IF: Validación de objeto nulo
        if (monitoreo == null) {
            return null;
        }

        listaMonitoreos.add(monitoreo);

        return monitoreo;
    }

    // BUSCAR POR ID
    public Monitoreo obtenerPorId(int id) {

        // IF: Validación del ID
        if (id <= 0) {
            return null;
        }

        for (Monitoreo monitoreo : listaMonitoreos) {

            // IF: Coincidencia encontrada
            if (monitoreo.getId() == id) {
                return monitoreo;
            }
        }

        return null;
    }

    // ACTUALIZAR MONITOREO
    public Monitoreo actualizar(Monitoreo monitoreoActualizado) {

        // IF: Objeto inválido
        if (monitoreoActualizado == null ||
            monitoreoActualizado.getId() <= 0) {

            return null;
        }

        for (int i = 0; i < listaMonitoreos.size(); i++) {

            // IF: Se encuentra el monitoreo
            if (listaMonitoreos.get(i).getId()
                    == monitoreoActualizado.getId()) {

                listaMonitoreos.set(i, monitoreoActualizado);

                return monitoreoActualizado;
            }
        }

        return null;
    }

    // ELIMINAR MONITOREO
    public void eliminar(int id) {

        // removeIf elimina automáticamente si encuentra coincidencia
        listaMonitoreos.removeIf(monitoreo ->
                monitoreo.getId() == id);
    }

    // TOTAL DE MONITOREOS
    public int totalMonitoreos() {

        return listaMonitoreos.size();
    }

    // BUSCAR POR PLAN DE VUELO
    public List<Monitoreo> obtenerPorPlanVuelo(
            int planVueloId) {

        List<Monitoreo> resultado = new ArrayList<>();

        // IF: Validación del ID
        if (planVueloId <= 0) {
            return resultado;
        }

        for (Monitoreo monitoreo : listaMonitoreos) {

            // IF: Coincidencia de plan de vuelo
            if (monitoreo.getPlanVueloId()
                    == planVueloId) {

                resultado.add(monitoreo);
            }
        }

        return resultado;
    }

    // BUSCAR POR REGIÓN
    public List<Monitoreo> obtenerPorRegion(
            String region) {

        List<Monitoreo> resultado = new ArrayList<>();

        // IF: Región inválida
        if (region == null || region.isBlank()) {
            return resultado;
        }

        for (Monitoreo monitoreo : listaMonitoreos) {

            // IF: Coincidencia de región
            if (monitoreo.getRegion()
                    .equalsIgnoreCase(region)) {

                resultado.add(monitoreo);
            }
        }

        return resultado;
    }

    // BUSCAR VUELOS EN ZONA URBANA
    public List<Monitoreo> obtenerZonaUrbana() {

        List<Monitoreo> resultado = new ArrayList<>();

        for (Monitoreo monitoreo : listaMonitoreos) {

            // IF: El vuelo se realizó en zona urbana
            if (monitoreo.isEsZonaUrbana()) {
                resultado.add(monitoreo);
            }
        }

        return resultado;
    }

    // BUSCAR POR ESTADO DE VUELO
    public List<Monitoreo> obtenerPorEstado(
            String estadoVuelo) {

        List<Monitoreo> resultado = new ArrayList<>();

        // IF: Estado inválido
        if (estadoVuelo == null ||
            estadoVuelo.isBlank()) {

            return resultado;
        }

        for (Monitoreo monitoreo : listaMonitoreos) {

            // IF: Coincidencia de estado
            if (monitoreo.getEstadoVuelo()
                    .equalsIgnoreCase(estadoVuelo)) {

                resultado.add(monitoreo);
            }
        }

        return resultado;
    }
}