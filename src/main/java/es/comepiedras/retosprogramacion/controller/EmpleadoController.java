package es.comepiedras.retosprogramacion.controller;

import es.comepiedras.retosprogramacion.model.Empleado;
import es.comepiedras.retosprogramacion.service.EmpleadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class EmpleadoController {

    private static final Logger log = LoggerFactory.getLogger(EmpleadoController.class);
    private EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("/api/empleados")
    public ResponseEntity<List<Empleado>> getAllEmpleado(){
        return ResponseEntity.ok(empleadoService.findAll());
    }

    @GetMapping("/api/empleados/{id}")
    public ResponseEntity<Empleado> getEmpleado(@PathVariable Long id){
        try {
            Empleado empleado = empleadoService.findById(id);
            return ResponseEntity.ok(empleado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/empleados")
    public ResponseEntity<Empleado> createEmpleado(@RequestBody Empleado empleado,
                                                   UriComponentsBuilder ucb){
        Empleado empleadoGuardado = empleadoService.save(empleado);
        URI uriEmpleado = ucb.path("/api/empleados/{id}").buildAndExpand(empleadoGuardado.getId()).toUri();
        return ResponseEntity.created(uriEmpleado).build();
    }

    @PutMapping("/api/empleados/{id}")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable Long id,
                                                   @RequestBody Empleado empleado
//                                                   .UriComponentsBuilder ucb
    ){
        try {
            Empleado aux = empleadoService.findById(id);
            log.info("El empleado ha sido encontrado: {}", aux);
            aux.setNombre(empleado.getNombre());
            aux.setPuesto(empleado.getPuesto());
            log.info("Actualizado: {}", aux);
            empleadoService.save(aux);
            return ResponseEntity.ok(aux);
        } catch (RuntimeException e) {
            log.warn("el empleado no se encuentras, se guarda como nuevo: {}", empleado);
            Empleado empleadoGuardado = empleadoService.save(empleado);
//            URI uriEmpleado = ucb.path("/api/empleados/{id}").buildAndExpand(empleadoGuardado.getId()).toUri();
//            return ResponseEntity.created(uriEmpleado).build();
            return ResponseEntity.ok(empleadoGuardado);
        }
    }

    @DeleteMapping("/api/empleados/{id}")
    public ResponseEntity<Empleado> deleteEmpleado(@PathVariable Long id){
        empleadoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
