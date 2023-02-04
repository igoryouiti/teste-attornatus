package isato.com.testeattornatus.controller;


import isato.com.testeattornatus.model.Endereco;
import isato.com.testeattornatus.repository.EnderecoRepository;
import isato.com.testeattornatus.service.EnderecoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.http.HttpResponse;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnderecoControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoService enderecoService;


    @BeforeAll
    void start(){
        enderecoRepository.deleteAll();
    }

    @Test
    @DisplayName("Create Endereco")
    public void mustCreateEndereco(){


        HttpEntity<Endereco> requestBody = new HttpEntity<Endereco>(new Endereco(
                "Rua Gloria", "58749856", "12", "Mogi das Cruzes", false
        ));

        ResponseEntity<Endereco> responseBody = testRestTemplate
                .exchange("/enderecos", HttpMethod.POST, requestBody, Endereco.class);

        assertEquals(HttpStatus.CREATED, responseBody.getStatusCode());

        assertEquals(requestBody.getBody().getLogradouro(), responseBody.getBody().getLogradouro());
    }


    @Test
    @DisplayName("List All Enderecos")
    public void mustListEnderecos(){

        enderecoRepository.save(new Endereco("Rua Gloria", "58749856", "12", "Mogi das Cruzes", true));
        enderecoRepository.save(new Endereco("Rua Maria", "65201478", "57", "Mogi das Cruzes", false));

        ResponseEntity<String> response = testRestTemplate
                .exchange("/enderecos", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("List Endereco by id")
    public void mustListOneEndereco(){

        Endereco savedEndereco = enderecoRepository.save(new Endereco("Rua Kleber", "54789888", "7", "Mogi das Cruzes", false));

        ResponseEntity<Endereco> response = testRestTemplate
                .exchange(("/enderecos/" + savedEndereco.getId()), HttpMethod.GET, null, Endereco.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(savedEndereco.getId(), response.getBody().getId());

    }

    @Test
    @DisplayName("Update Endereco")
    public void mustUpdateEndereco(){

        Endereco savedEndereco = enderecoRepository.save(new Endereco("Rua Azelino", "54789811", "12", "Mogi das Cruzes", false));

        Endereco updateEndereco = new Endereco("Rua João Benegas", "54789888", "7", "Mogi das Cruzes", false);

        updateEndereco.setId(savedEndereco.getId());

        HttpEntity<Endereco> requestBody = new HttpEntity<Endereco>(updateEndereco);

        ResponseEntity<Endereco> responseBody = testRestTemplate
                .exchange("/enderecos", HttpMethod.PUT, requestBody, Endereco.class);


        assertEquals(HttpStatus.OK, responseBody.getStatusCode());

        assertEquals(updateEndereco.getLogradouro(), responseBody.getBody().getLogradouro());

        assertEquals(savedEndereco.getId(), responseBody.getBody().getId());

    }

    @Test
    @DisplayName("Delete Endereco by Id")
    public void mustDeleteEndereco(){

        Endereco savedEndereco = enderecoRepository.save(new Endereco("Rua Marina", "54789811", "12", "Mogi das Cruzes", false));

        ResponseEntity<Endereco> responseBody = testRestTemplate
                .exchange(("/enderecos/" + savedEndereco.getId()), HttpMethod.DELETE, null, Endereco.class);

        assertEquals(HttpStatus.NO_CONTENT, responseBody.getStatusCode());

        responseBody = testRestTemplate
                .exchange(("/enderecos/" + savedEndereco.getId()), HttpMethod.DELETE, null, Endereco.class);

        assertEquals(HttpStatus.NOT_FOUND, responseBody.getStatusCode());

    }


}
