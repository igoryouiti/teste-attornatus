package isato.com.testeattornatus.repository;

import isato.com.testeattornatus.model.Endereco;
import isato.com.testeattornatus.model.Pessoa;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnderecoRepositoryTest {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @BeforeAll
    void start(){
        enderecoRepository.deleteAll();

    }

    @Test
    @DisplayName("Return 3 Endereco")
    public void mustReturnAllEnderecoFromPessoa(){


        Pessoa pessoa = pessoaRepository.save(new Pessoa("Mariana", new Date("1994/05/12")));

        enderecoRepository.save(new Endereco("Rua Joao", "69885410", "55", "Mogi das Cruzes", false));
        enderecoRepository.save(new Endereco("Rua Kleber", "54789888", "7", "Mogi das Cruzes", false, pessoa));
        enderecoRepository.save(new Endereco("Rua Gloria", "58749856", "12", "Mogi das Cruzes", true, pessoa));
        enderecoRepository.save(new Endereco("Rua Maria", "65201478", "57", "Mogi das Cruzes", false, pessoa));

        List<Endereco> enderecos = enderecoRepository.findAllEnderecoByPessoaId(pessoa.getId()).get();

        assertEquals(3, enderecos.size());

    }

    @Test
    @DisplayName("Return 1 Endereco principal == true from Pessoa")
    public void mustReturnEnderecoPrincipalFromPessoa(){

        Pessoa pessoa = pessoaRepository.save(new Pessoa("Marcia", new Date("1992/05/12")));

        enderecoRepository.save(new Endereco("Rua Amarantes", "69885410", "55", "Mogi das Cruzes", false));
        enderecoRepository.save(new Endereco("Rua Olímpiadas", "54789888", "7", "Mogi das Cruzes", false, pessoa));
        enderecoRepository.save(new Endereco("Rua Gloria", "58749856", "12", "Mogi das Cruzes", true, pessoa));
        enderecoRepository.save(new Endereco("Rua Attornatus", "65201478", "57", "Mogi das Cruzes", false, pessoa));


        Endereco endereco = enderecoRepository.findEnderecoPrincipalByPessoa(pessoa.getId()).get();

        assertTrue(endereco.getPrincipal().equals(true));

    }

}
