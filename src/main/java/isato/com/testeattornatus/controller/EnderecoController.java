package isato.com.testeattornatus.controller;

import isato.com.testeattornatus.model.Endereco;
import isato.com.testeattornatus.repository.EnderecoRepository;
import isato.com.testeattornatus.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<List <Endereco>> getAll(){
        return ResponseEntity.ok(enderecoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> getById(@PathVariable Long id){ // @PathVariable valida as especificações na model
        return enderecoRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))   // map do optional, nada há ver com hashmap
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @PostMapping
    public ResponseEntity<Endereco> post(@Valid @RequestBody Endereco endereco){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(enderecoService.createEndereco(endereco).get());
    }

    @PutMapping
    public ResponseEntity<Endereco> put(@Valid @RequestBody Endereco endereco){
        return enderecoRepository.findById(endereco.getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.OK)
                        .body(enderecoService.createEndereco(endereco).get()))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        Optional<Endereco> endereco = enderecoRepository.findById(id);

        if(endereco.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        enderecoRepository.deleteById(id);
    }


}
