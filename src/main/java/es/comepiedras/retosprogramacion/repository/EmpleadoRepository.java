package es.comepiedras.retosprogramacion.repository;

import es.comepiedras.retosprogramacion.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
}
