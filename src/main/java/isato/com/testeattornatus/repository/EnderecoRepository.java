package isato.com.testeattornatus.repository;

import isato.com.testeattornatus.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query(value = "SELECT * FROM tb_enderecos WHERE fk_pessoas_id = ?1", nativeQuery = true)
    public Optional<List<Endereco>> findAllEnderecoByPessoaId(Long id);

    @Query(value = "SELECT * FROM tb_enderecos WHERE fk_pessoas_id = ?1 and principal = true", nativeQuery = true)
    public Optional<Endereco> findEnderecoPrincipalByPessoa(Long id);

}
