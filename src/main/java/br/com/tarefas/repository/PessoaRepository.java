package br.com.tarefas.repository;

import br.com.tarefas.entities.Departamento;
import br.com.tarefas.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    @Query("SELECT COUNT(p) FROM Pessoa p WHERE p.departamento = ?1")
    long countByDepartamento(Departamento departamento);

    Optional<Pessoa> findByNomeContainingIgnoreCase(String nome);
}
