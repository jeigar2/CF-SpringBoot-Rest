package es.comepiedras.retosprogramacion.config;

import es.comepiedras.retosprogramacion.model.Empleado;
import es.comepiedras.retosprogramacion.service.EmpleadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabese(EmpleadoService empleadoService){
        return args -> {
            Empleado javier = new Empleado("Javier", "CTO");
            Empleado blanca = new Empleado("Blanca", "Project Manager");
            Empleado juan = new Empleado("Juan", "CEO");

            log.info("Carga inicial: {}", empleadoService.save(javier));
            log.info("Carga inicial: {}", empleadoService.save(blanca));
            log.info("Carga inicial: {}", empleadoService.save(juan));
        };
    }
}
