package cl.GestionDrones.v1.monitoreos.repository;

import cl.GestionDrones.v1.monitoreos.model.Monitoreo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoreosRepository extends JpaRepository<Monitoreo, Long> {

    List<Monitoreo> findByPlanVueloId(Long planVueloId);

    List<Monitoreo> findByEmpresaProveedoraId(Long empresaProveedoraId);

}
 