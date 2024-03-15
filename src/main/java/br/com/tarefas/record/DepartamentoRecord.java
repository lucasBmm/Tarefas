package br.com.tarefas.record;

import br.com.tarefas.entities.Departamento;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DepartamentoRecord(
        @NotNull
        Long id,
        @NotNull
        @NotEmpty
        String titulo
) {
    public DepartamentoRecord(Departamento departamento) {
        this(departamento.getId(), departamento.getTitulo());
    }
}

