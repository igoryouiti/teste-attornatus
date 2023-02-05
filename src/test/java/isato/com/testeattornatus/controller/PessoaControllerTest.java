package isato.com.testeattornatus.controller;

import isato.com.testeattornatus.model.Endereco;
import isato.com.testeattornatus.model.Pessoa;
import isato.com.testeattornatus.repository.EnderecoRepository;
import isato.com.testeattornatus.repository.PessoaRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PessoaControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;



    @BeforeAll
    void start(){
        pessoaRepository.deleteAll();
        enderecoRepository.deleteAll();
    }

    @Test
    @Order(6)
    @DisplayName("Create Pessoa")
    public void mustCreatePessoa(){

        List<Endereco> enderecos = new ArrayList<Endereco>();

        Endereco endereco = new Endereco("Rua Gloria", "08775520", "12", "Mogi das Cruzes", false);

        enderecos.add(endereco);

        Pessoa savedPessoa = new Pessoa("Guilherme", new Date("1992/05/12"));


        HttpEntity<Pessoa> requestBody = new HttpEntity<Pessoa>(savedPessoa);

        ResponseEntity<Pessoa> responseBody = testRestTemplate
                .exchange("/pessoas", HttpMethod.POST, requestBody, Pessoa.class);

        assertEquals(HttpStatus.OK, responseBody.getStatusCode());

        assertEquals(requestBody.getBody().getNome(), responseBody.getBody().getNome());
    }


    @Test
    @DisplayName("List All Pessoa")
    @Order(5)
    public void mustListPessoas(){

        pessoaRepository.save(new Pessoa("Any", new Date("1992/05/12")));
        pessoaRepository.save(new Pessoa("Sthefanie", new Date("1992/05/12")));

        ResponseEntity<String> response = testRestTemplate
                .exchange("/pessoas", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(2)
    @DisplayName("List Pessoa by id")
    public void mustReturnOnePessoa(){

        Pessoa savedPessoa = pessoaRepository.save(new Pessoa("Jhonata", new Date("1992/05/12")));

        ResponseEntity<Pessoa> response = testRestTemplate
                .exchange(("/pessoas/" + savedPessoa.getId()), HttpMethod.GET, null, Pessoa.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(savedPessoa.getId(), response.getBody().getId());

    }

    @Test
    @Order(3)
    @DisplayName("Update Pessoa")
    public void mustUpdatePessoa(){

        Pessoa savedPessoa = pessoaRepository.save(new Pessoa("Allan", new Date("1992/05/12")));

        Pessoa updatePessoa = new Pessoa("Allan", new Date("1989/12/01"));

        updatePessoa.setId(savedPessoa.getId());

        HttpEntity<Pessoa> requestBody = new HttpEntity<Pessoa>(updatePessoa);

        ResponseEntity<Pessoa> responseBody = testRestTemplate
                .exchange("/pessoas", HttpMethod.PUT, requestBody, Pessoa.class);


        assertEquals(HttpStatus.OK, responseBody.getStatusCode());

        assertEquals(updatePessoa.getNome(), responseBody.getBody().getNome());

        assertEquals(savedPessoa.getId(), responseBody.getBody().getId());

    }


    @Test
    @Order(4)
    @DisplayName("List of Enderecos by Pessoa")
    public void mustReturnListEnderecosByPessoas(){

        Pessoa savedPessoa = pessoaRepository.save(new Pessoa("Caique", new Date("1989/03/28")));

        enderecoRepository.save(new Endereco("Rua Gloria", "58749856", "12", "Mogi das Cruzes", true, savedPessoa));
        enderecoRepository.save(new Endereco("Rua Maria", "65201478", "57", "Mogi das Cruzes", false, savedPessoa));


        ResponseEntity<String> responseBody = testRestTemplate
                .exchange(("/pessoas/" + savedPessoa.getId() + "/enderecos"), HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, responseBody.getStatusCode());

    }

    @Test
    @Order(1)
    @DisplayName("Endereco Principal by Pessoa")
    public void mustReturnEnderecoPrincipalByPessoas(){

        Pessoa savedPessoa = pessoaRepository.save(new Pessoa("Cycero", new Date("1992/01/23")));

        enderecoRepository.save(new Endereco("Rua Projetada 2", "58749856", "12", "Mogi das Cruzes", true, savedPessoa));
        enderecoRepository.save(new Endereco("Rua Emilia", "65201478", "57", "Mogi das Cruzes", false, savedPessoa));


        ResponseEntity<Endereco> responseBody = testRestTemplate
                .exchange(("/pessoas/" + savedPessoa.getId() + "/enderecos/principal"), HttpMethod.GET, null, Endereco.class);

        assertEquals(HttpStatus.OK, responseBody.getStatusCode());

        assertEquals(responseBody.getBody().getPrincipal(), true);

    }

}
