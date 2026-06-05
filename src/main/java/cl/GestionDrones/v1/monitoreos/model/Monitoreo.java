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
    private Long id;

    
    @Column(name = "plan_vuelo_id", nullable = false)
    private Long planVueloId;

    
    @Column(name = "empresa_proveedora_id", nullable = false)
    private Long empresaProveedoraId;

    @Column(name = "region", nullable = false, length = 100)
    private String region;

    @Column(name = "fecha_hora_monitoreo", nullable = false)
    private LocalDateTime fechaHoraMonitoreo;

    @Column(name = "tipo_operacion", nullable = false, length = 50)
    private String tipoOperacion;

    @Column(name = "estado_vuelo", nullable = false, length = 30)
    private String estadoVuelo; 

    @Column(name = "es_zona_urbana", nullable = false)
    private boolean esZonaUrbana;

    
    @Column(name = "zona_restringida_id", nullable = true)
    private Long zonaRestringidaId;

    
    public Monitoreo() {}

    
    public Monitoreo(Long id, Long planVueloId, Long empresaProveedoraId, String region, 
                     LocalDateTime fechaHoraMonitoreo, String tipoOperacion, 
                     String estadoVuelo, boolean esZonaUrbana, Long zonaRestringidaId) {
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

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlanVueloId() {
        return planVueloId;
    }

    public void setPlanVueloId(Long planVueloId) {
        this.planVueloId = planVueloId;
    }

    public Long getEmpresaProveedoraId() {
        return empresaProveedoraId;
    }

    public void setEmpresaProveedoraId(Long empresaProveedoraId) {
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

    public Long getZonaRestringidaId() {
        return zonaRestringidaId;
    }

    public void setZonaRestringidaId(Long zonaRestringidaId) {
        this.zonaRestringidaId = zonaRestringidaId;
    }
}