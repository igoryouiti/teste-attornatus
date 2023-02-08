package isato.com.testeattornatus.service;


import isato.com.testeattornatus.model.Endereco;
import isato.com.testeattornatus.model.Pessoa;
import isato.com.testeattornatus.repository.EnderecoRepository;
import isato.com.testeattornatus.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnderecoServiceTest {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @BeforeAll
    void start(){
        enderecoRepository.deleteAll();


    }

    @Test
    @DisplayName("Trata Endereco preferido para ser único")
    public void pessoaHaveOnlyOneEnderecoPreferido(){

        Pessoa pessoa = pessoaRepository.save(new Pessoa("Mariana", new Date("1994/05/12")));

        enderecoRepository.save(new Endereco("Rua Joao", "69885410", "55", "Mogi das Cruzes", true, pessoa));


        Endereco savedEndereco = new Endereco(
                "Rua Kleber", "54789888",
                "7", "Mogi das Cruzes", true, pessoa
        );

        enderecoService.createEndereco(savedEndereco);

        Optional<Pessoa> updatedPessoaWithEndereco = pessoaRepository.findById(pessoa.getId());

        Endereco endereco1 = updatedPessoaWithEndereco.get().getEnderecos().get(0);
        Endereco endereco2 = updatedPessoaWithEndereco.get().getEnderecos().get(1);

        assertTrue(endereco1.getPrincipal().equals(false));
        assertTrue(endereco2.getPrincipal().equals(true));
    }
}
