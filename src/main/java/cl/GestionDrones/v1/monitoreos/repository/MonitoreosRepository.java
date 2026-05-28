package cl.GestionDrones.v1.monitoreos.repository;

import cl.GestionDrones.v1.monitoreos.model.Monitoreo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoreosRepository
        extends JpaRepository<Monitoreo, Integer> {

    // BUSCAR POR PLAN DE VUELO
    @Query(value = """
            SELECT * 
            FROM monitoreo_vuelo
            WHERE plan_vuelo_id = :planVueloId
            """, nativeQuery = true)
    List<Monitoreo> buscarPorPlanVuelo(
            @Param("planVueloId") int planVueloId);

    // BUSCAR POR EMPRESA PROVEEDORA
    @Query(value = """
            SELECT * 
            FROM monitoreo_vuelo
            WHERE empresa_proveedora_id = :empresaId
            """, nativeQuery = true)
    List<Monitoreo> buscarPorEmpresaProveedora(
            @Param("empresaId") int empresaId);

    // BUSCAR POR REGIÓN
    @Query(value = """
            SELECT * 
            FROM monitoreo_vuelo
            WHERE region = :region
            """, nativeQuery = true)
    List<Monitoreo> buscarPorRegion(
            @Param("region") String region);

    // BUSCAR POR ESTADO DEL VUELO
    @Query(value = """
            SELECT * 
            FROM monitoreo_vuelo
            WHERE estado_vuelo = :estadoVuelo
            """, nativeQuery = true)
    List<Monitoreo> buscarPorEstadoVuelo(
            @Param("estadoVuelo") String estadoVuelo);

    // BUSCAR OPERACIONES EN ZONA URBANA
    @Query(value = """
            SELECT * 
            FROM monitoreo_vuelo
            WHERE es_zona_urbana = true
            """, nativeQuery = true)
    List<Monitoreo> buscarZonaUrbana();

    // BUSCAR POR ZONA RESTRINGIDA
    @Query(value = """
            SELECT * 
            FROM monitoreo_vuelo
            WHERE zona_restringida_id = :zonaId
            """, nativeQuery = true)
    List<Monitoreo> buscarPorZonaRestringida(
            @Param("zonaId") Integer zonaId);

    // BUSCAR POR TIPO DE OPERACIÓN
    @Query(value = """
            SELECT * 
            FROM monitoreo_vuelo
            WHERE tipo_operacion = :tipoOperacion
            """, nativeQuery = true)
    List<Monitoreo> buscarPorTipoOperacion(
            @Param("tipoOperacion") String tipoOperacion);

    // BUSCAR MONITOREOS DESDE UNA FECHA
    @Query(value = """
            SELECT * 
            FROM monitoreo_vuelo
            WHERE fecha_hora_monitoreo >= :fecha
            """, nativeQuery = true)
    List<Monitoreo> buscarDesdeFecha(
            @Param("fecha") LocalDateTime fecha);

}