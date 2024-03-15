package br.com.tarefas.repository;

import br.com.tarefas.entities.Departamento;
import br.com.tarefas.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    // Aqui não foi especificado se a tarefa deveria ser finalizada ou não, mas acredito não fazer sentido
    // uma tarefa finalizada ser considerada pendente
    @Query("SELECT t FROM Tarefa t WHERE t.pessoa IS NULL AND t.finalizado IS FALSE ORDER BY t.prazo ASC")
    List<Tarefa> findTarefasPendentesOrderByPrazo(int maxTarefas);

    @Query("SELECT COUNT(t) FROM Tarefa t WHERE t.departamento = ?1")
    long countByDepartamento(Departamento departamento);
}
