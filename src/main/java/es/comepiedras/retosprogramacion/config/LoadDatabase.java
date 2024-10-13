package es.comepiedras.retosprogramacion.config;

import es.comepiedras.retosprogramacion.model.Empleado;
import es.comepiedras.retosprogramacion.repository.EmpleadoRepository;
//import es.comepiedras.retosprogramacion.service.EmpleadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabese(EmpleadoRepository empleadoRepository){
//    CommandLineRunner initDatabese(EmpleadoService empleadoService){
        return args -> {
            Empleado javier = new Empleado("Javier", "CTO");
            Empleado blanca = new Empleado("Blanca", "Project Manager");
            Empleado juan = new Empleado("Juan", "CEO");

//            logger.info("Carga inicial: {}, empleadoService.save(javier");
//            logger.info("Carga inicial: {}, empleadoService.save(blanca");
//            logger.info("Carga inicial: {}, empleadoService.save(juan");
            log.info("Carga inicial: {}", empleadoRepository.save(javier));
            log.info("Carga inicial: {}", empleadoRepository.save(blanca));
            log.info("Carga inicial: {}", empleadoRepository.save(juan));
        };
    }
}
