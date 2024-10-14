package es.comepiedras.retosprogramacion.controller;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import es.comepiedras.retosprogramacion.model.Empleado;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmpleadoControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturnAllEmpleadosWhenListIsRequested(){
        ResponseEntity<String> response = restTemplate.getForEntity("/api/empleados", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int empleadosCount = documentContext.read("$.length()");
        assertThat(empleadosCount).isEqualTo(3);

        JSONArray ids = documentContext.read("$..id");
        assertThat(ids).containsExactlyInAnyOrder(1,2,3);

        JSONArray nombre = documentContext.read("$..nombre");
        assertThat(nombre).containsExactlyInAnyOrder("Javier", "Juan", "Blanca");
    }

    @Test
    void shouldReturnAnEmpleadoById(){
        ResponseEntity<String> response = restTemplate.getForEntity("/api/empleados/3", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(3);

        String nombre = documentContext.read("$.nombre");
        assertThat(nombre).isEqualTo("Juan");

        String puesto = documentContext.read("$.puesto");
        assertThat(puesto).isEqualTo("CEO");
    }

    @Test
    void shouldNotReturnAnEmpleadoWithAnUnknowId(){
        ResponseEntity<String> response = restTemplate.getForEntity("/api/empleados/1000", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @DirtiesContext
    void shouldCreateANewEmpleado(){
        Empleado empleado = new Empleado("Marta", "Hacedora");
        ResponseEntity<Void> response = restTemplate.getRestTemplate().postForEntity("/api/empleados", empleado, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        URI location = response.getHeaders().getLocation();
        ResponseEntity<String> getReponse = restTemplate.getForEntity(location, String.class);
        assertThat(getReponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getReponse.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isNotNull();
        String nombre = documentContext.read("$.nombre");
        assertThat(nombre).isEqualTo("Marta");
        String puesto = documentContext.read("$.puesto");
        assertThat(puesto).isEqualTo("Hacedora");
    }

    @Test
    @DirtiesContext
    void shouldUpdateAnExistingEmpleado(){
        Empleado empleado = new Empleado("José", "Carpintero");

        HttpEntity<Empleado> request = new HttpEntity<>(empleado);
        ResponseEntity<Void> response = restTemplate.exchange("/api/empleados/3", HttpMethod.PUT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getResponse = restTemplate.getForEntity("/api/empleados/3", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(3);
        String nombre = documentContext.read("$.nombre");
        assertThat(nombre).isEqualTo("José");
        String puesto = documentContext.read("$.puesto");
        assertThat(puesto).isEqualTo("Carpintero");
    }

    @Test
    @DirtiesContext
    void shouldDeleteAnEmpleadoById(){
//        restTemplate.delete("/api/empleados/3");
        ResponseEntity<Void> response = restTemplate.exchange("/api/empleados/3", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate.getForEntity("/api/empleados", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        int empleadosCount = documentContext.read("$.length()");
        assertThat(empleadosCount).isEqualTo(2);

    }
}
