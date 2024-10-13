package es.comepiedras.retosprogramacion.service;

import es.comepiedras.retosprogramacion.model.Empleado;
import es.comepiedras.retosprogramacion.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public Empleado save(Empleado empleado){
        return empleadoRepository.save(empleado);
    }

    public List<Empleado> findAll(){
        return empleadoRepository.findAll();
    }

    public Empleado findById(Long id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        return empleado.orElseThrow(() -> new RuntimeException("Empleado no encontrado: " + id));
    }

    public void deleteById(Long id){
        empleadoRepository.deleteById(id);
    }
}
