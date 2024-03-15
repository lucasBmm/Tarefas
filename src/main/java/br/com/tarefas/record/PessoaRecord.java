package br.com.tarefas.record;

import br.com.tarefas.entities.Pessoa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public record PessoaRecord(
        Long id,
        @NotEmpty
        String nome,
        @NotNull
        DepartamentoRecord departamento,
        List<TarefaRecord> tarefas
) {
    public PessoaRecord(Pessoa pessoa) {
        this(
                pessoa.getId(),
                pessoa.getNome(),
                new DepartamentoRecord(pessoa.getDepartamento()),
                pessoa.getTarefas().stream().map(TarefaRecord::new).collect(Collectors.toList())
        );
    }
}
