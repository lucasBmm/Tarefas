package br.com.tarefas.record;

import br.com.tarefas.entities.Tarefa;

import java.time.LocalDate;

public record TarefaRecord(
        Long id,
        String titulo,
        String descricao,
        LocalDate prazo,
        DepartamentoRecord departamento,
        Long duracao,

        boolean finalizado
) {
    public TarefaRecord(Tarefa tarefa) {
        this(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getPrazo(),
                new DepartamentoRecord(tarefa.getDepartamento()),
                tarefa.getDuracao(),
                tarefa.isFinalizado()
        );
    }
}
