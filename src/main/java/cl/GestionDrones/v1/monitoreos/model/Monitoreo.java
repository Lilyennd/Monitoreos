package cl.GestionDrones.v1.monitoreos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Table(name = "monitoreos")
public class Monitoreo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // Relación o referencia al Plan de Vuelo que se está monitoreando
    @Column(name = "plan_vuelo_id", nullable = false)
    private int planVueloId;

    // ID de referencia que proviene del microservicio externo de Empresa Proveedora.
    @Column(name = "empresa_proveedora_id", nullable = false)
    private int empresaProveedoraId;

    @Column(name = "region", nullable = false, length = 100)
    private String region;

    @Column(name = "fecha_hora_monitoreo", nullable = false)
    private LocalDateTime fechaHoraMonitoreo;

    @Column(name = "tipo_operacion", nullable = false, length = 50)
    private String tipoOperacion;

    @Column(name = "estado_vuelo", nullable = false, length = 30)
    private String estadoVuelo; // Ejemplo: "Planificado", "Activo", "Finalizado"

    @Column(name = "es_zona_urbana", nullable = false)
    private boolean esZonaUrbana;

    // ID de referencia que proviene del microservicio externo de Zona Restringida.
    // Puede ser NULL si el vuelo opera en un espacio aéreo completamente libre[cite: 44].
    @Column(name = "zona_restringida_id", nullable = true)
    private Integer zonaRestringidaId;

    // Constructor sin argumentos requerido por JPA/Hibernate
    public Monitoreo() {}

    // Constructor completo
    public Monitoreo(int id, int planVueloId, int empresaProveedoraId, String region, 
                     LocalDateTime fechaHoraMonitoreo, String tipoOperacion, 
                     String estadoVuelo, boolean esZonaUrbana, Integer zonaRestringidaId) {
        this.id = id;
        this.planVueloId = planVueloId;
        this.empresaProveedoraId = empresaProveedoraId;
        this.region = region;
        this.fechaHoraMonitoreo = fechaHoraMonitoreo;
        this.tipoOperacion = tipoOperacion;
        this.estadoVuelo = estadoVuelo;
        this.esZonaUrbana = esZonaUrbana;
        this.zonaRestringidaId = zonaRestringidaId;
    }

    // Getters y Setters manuales
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlanVueloId() {
        return planVueloId;
    }

    public void setPlanVueloId(int planVueloId) {
        this.planVueloId = planVueloId;
    }

    public int getEmpresaProveedoraId() {
        return empresaProveedoraId;
    }

    public void setEmpresaProveedoraId(int empresaProveedoraId) {
        this.empresaProveedoraId = empresaProveedoraId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public LocalDateTime getFechaHoraMonitoreo() {
        return fechaHoraMonitoreo;
    }

    public void setFechaHoraMonitoreo(LocalDateTime fechaHoraMonitoreo) {
        this.fechaHoraMonitoreo = fechaHoraMonitoreo;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getEstadoVuelo() {
        return estadoVuelo;
    }

    public void setEstadoVuelo(String estadoVuelo) {
        this.estadoVuelo = estadoVuelo;
    }

    public boolean isEsZonaUrbana() {
        return esZonaUrbana;
    }

    public void setEsZonaUrbana(boolean esZonaUrbana) {
        this.esZonaUrbana = esZonaUrbana;
    }

    public Integer getZonaRestringidaId() {
        return zonaRestringidaId;
    }

    public void setZonaRestringidaId(Integer zonaRestringidaId) {
        this.zonaRestringidaId = zonaRestringidaId;
    }
}