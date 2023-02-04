package isato.com.testeattornatus.service;

import isato.com.testeattornatus.model.Endereco;
import isato.com.testeattornatus.model.Pessoa;
import isato.com.testeattornatus.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PessoaServiceTest {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaRepository pessoaRepository;

    @BeforeAll
    void start(){
        pessoaRepository.deleteAll();
    }

    @Test
    @DisplayName("Create Pessoa Without Endereco")
    public void mustCreatePessoaWithoutEndereco(){

        Pessoa savedPessoa = new Pessoa("Samantha", new Date("1991/05/12"));

        Optional<Pessoa> optPessoa = pessoaService.createPessoa(savedPessoa);

        assertEquals(optPessoa.isEmpty(), false);

        assertEquals(savedPessoa.getNome(), optPessoa.get().getNome());

    }

    @Test
    @DisplayName("Create Pessoa With Endereco")
    public void mustCreatePessoaWithEndereco(){

        List<Endereco> enderecos = new ArrayList<Endereco>();

        Endereco endereco = new Endereco("Rua Gloria", "58749856", "12", "Mogi das Cruzes", true);

        enderecos.add(endereco);

        Pessoa savedPessoa = new Pessoa("Any", enderecos, new Date("1992/05/12"));

        Optional<Pessoa> optPessoa = pessoaService.createPessoa(savedPessoa);

        assertEquals(optPessoa.isEmpty(), false);

        assertEquals(savedPessoa.getNome(), optPessoa.get().getNome());

        assertEquals(optPessoa.get().getEnderecos().size(), 1);

    }
}
