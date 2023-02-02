package isato.com.testeattornatus.service;

import isato.com.testeattornatus.model.Endereco;
import isato.com.testeattornatus.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional(rollbackOn = Exception.class)
    private void setPrincipal(Endereco endereco){
        if(endereco.getPrincipal() == true){
            Optional<List<Endereco>> enderecos = (enderecoRepository.findAllEnderecoByPessoaId(endereco.getPessoa().getId()));
            enderecos.get().forEach(end -> {
                if(end.getPrincipal() == true) {

                    end.setPrincipal(false);
                    enderecoRepository.save(end);
                }
            });
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public Optional<Endereco> createEndereco(Endereco endereco){
        setPrincipal(endereco);

        return Optional.ofNullable(enderecoRepository.save(endereco));
    }
}
