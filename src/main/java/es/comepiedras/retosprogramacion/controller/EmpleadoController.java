package es.comepiedras.retosprogramacion.controller;

import es.comepiedras.retosprogramacion.model.Empleado;
import es.comepiedras.retosprogramacion.service.EmpleadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmpleadoController {

    private static final Logger log = LoggerFactory.getLogger(EmpleadoController.class);
    private EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("/api/empleados")
    public List<Empleado> getAllEmpleado(){
        return empleadoService.findAll();
    }

    @GetMapping("/api/empleados/{id}")
    public Empleado getEmpleado(@PathVariable Long id){
        return empleadoService.findById(id);
    }

    @PostMapping("/api/empleados")
    public Empleado createEmpleado(@RequestBody Empleado empleado){
        return empleadoService.save(empleado);
    }

    @PutMapping("/api/empleados/{id}")
    public Empleado updateEmpleado(@PathVariable Long id,
                                   @RequestBody Empleado empleado){
        try {
            Empleado aux = empleadoService.findById(id);
            log.info("El empleado ha sido encontrado: " + aux);
            aux.setNombre(empleado.getNombre());
            aux.setPuesto(empleado.getPuesto());
            log.info("Actualizado: " + aux);
            return empleadoService.save(aux);
        } catch (RuntimeException e) {
            log.warn("el empleado no se encuentras, se guarda como nuevo: {}", empleado);
            return empleadoService.save(empleado);
        }
    }

    @DeleteMapping("/api/empleados/{id}")
    public void deleteEmpleado(@PathVariable Long id){
        empleadoService.deleteById(id);
    }
}
