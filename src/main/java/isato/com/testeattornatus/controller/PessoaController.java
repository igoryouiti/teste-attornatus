package isato.com.testeattornatus.controller;

import isato.com.testeattornatus.model.Endereco;
import isato.com.testeattornatus.model.Pessoa;
import isato.com.testeattornatus.repository.EnderecoRepository;
import isato.com.testeattornatus.repository.PessoaRepository;
import isato.com.testeattornatus.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @GetMapping
    public ResponseEntity<List<Pessoa>> getAll(){
        return ResponseEntity.ok(pessoaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> getById(@PathVariable Long id){
        return pessoaRepository.findById(id)
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Pessoa> post(@Valid @RequestBody Pessoa pessoa){
        return pessoaService.createPessoa(pessoa)
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping
    public ResponseEntity<Pessoa> put(@Valid @RequestBody Pessoa pessoa){
        return pessoaRepository.findById(pessoa.getId())
                .map(response -> ResponseEntity.status(HttpStatus.OK)
                    .body(pessoaRepository.save(pessoa)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/{id}/enderecos")
    public ResponseEntity<List<Endereco>> getAllEnderecos(@PathVariable Long id){
        System.out.println(id);
        return enderecoRepository.findAllEnderecoByPessoaId(id)
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/{id}/enderecos/principal")
    public ResponseEntity<Endereco> getEnderecoPrincipal(@PathVariable Long id){
                return enderecoRepository.findEnderecoPrincipalByPessoa(id)
            .map(response -> ResponseEntity.ok(response))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
