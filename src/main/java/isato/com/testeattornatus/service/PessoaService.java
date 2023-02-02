package isato.com.testeattornatus.service;

import isato.com.testeattornatus.model.Pessoa;
import isato.com.testeattornatus.repository.EnderecoRepository;
import isato.com.testeattornatus.repository.PessoaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public Optional<Pessoa> createPessoa(Pessoa pessoa){
        Optional<Pessoa> optPessoa = Optional.of(pessoaRepository.save(pessoa));

        if(pessoa.getEnderecos() != null) {
            pessoa.getEnderecos().forEach(end -> {
                end.setPessoa(new Pessoa());
                end.getPessoa().setId(optPessoa.get().getId());
                enderecoService.createEndereco(end);
            });

            optPessoa.get().setEnderecos(pessoa.getEnderecos());
            return optPessoa;
        } else{
            return optPessoa;
        }
    }
}
